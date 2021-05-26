package com.invokegs.dbcoursework;

import com.invokegs.dbcoursework.entity.Privilege;
import com.invokegs.dbcoursework.service.PrivilegeService;
import com.invokegs.dbcoursework.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final PrivilegeService privileges;
    private final RoleService roles;
    private final EntityManager entityManager;
    private final String schema;

    protected boolean loaded = false;

    public SetupDataLoader(PrivilegeService privilege, RoleService roleService, EntityManager entityManager,
                           @Value("${spring.jpa.properties.hibernate.default_schema}") String schema) {
        this.privileges = privilege;
        this.roles = roleService;
        this.entityManager = entityManager;
        this.schema = schema;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (loaded)
            return;

        Privilege createPrivilege = privileges.createOrGetPrivilege("CREATE_PRIVILEGE");
        Privilege editPrivilege = privileges.createOrGetPrivilege("EDIT_PRIVILEGE");
        Privilege deletePrivilege = privileges.createOrGetPrivilege("DELETE_PRIVILEGE");

        Privilege editAnyPrivilege = privileges.createOrGetPrivilege("EDIT_ANY_PRIVILEGE");
        Privilege deleteAnyPrivilege = privileges.createOrGetPrivilege("DELETE_ANY_PRIVILEGE");

        roles.createOrGetRole("ROLE_ADMIN", "Admin", Integer.MAX_VALUE,
                Arrays.asList(createPrivilege, editAnyPrivilege, deleteAnyPrivilege));
        roles.createOrGetRole("ROLE_USER", "User", 0,
                Arrays.asList(createPrivilege, editPrivilege, deletePrivilege));

        entityManager.createNativeQuery("SET SCHEMA '" + schema + "'").executeUpdate();
        entityManager.createNativeQuery("""
                CREATE OR REPLACE FUNCTION handle_posts() RETURNS TRIGGER LANGUAGE plpgsql AS $$ BEGIN
                    -- handle delete
                    IF NEW IS NULL THEN
                        INSERT INTO schema_name.user_post_count(user_id, posts) VALUES (OLD.author_id, 0)
                        ON CONFLICT (user_id) DO
                            UPDATE SET posts = schema_name.user_post_count.posts - 1 WHERE schema_name.user_post_count.user_id = OLD.author_id;
                        RETURN NEW;
                    END IF;

                    -- handle insert
                    IF OLD IS NULL AND NEW IS NOT NULL THEN
                        INSERT INTO schema_name.user_post_count(user_id, posts) VALUES (NEW.author_id, 1)
                        ON CONFLICT (user_id) DO
                            UPDATE SET posts = schema_name.user_post_count.posts + 1 WHERE schema_name.user_post_count.user_id = NEW.author_id;
                        RETURN NEW;
                    END IF;

                    -- handle author update
                    IF OLD.author_id != NEW.author_id THEN
                        INSERT INTO schema_name.user_post_count(user_id, posts) VALUES (NEW.author_id, 1)
                        ON CONFLICT (user_id) DO
                            UPDATE SET posts = schema_name.user_post_count.posts + 1 WHERE schema_name.user_post_count.user_id = NEW.author_id;

                        INSERT INTO schema_name.user_post_count(user_id, posts) VALUES (OLD.author_id, 0)
                        ON CONFLICT (user_id) DO
                            UPDATE SET posts = schema_name.user_post_count.posts - 1 WHERE schema_name.user_post_count.user_id = OLD.author_id;
                        RETURN NEW;
                    END IF;
                    
                    RETURN NEW;
                    END;
                $$;
                """.replace("schema_name", schema)).executeUpdate();
        entityManager.createNativeQuery("DROP TRIGGER IF EXISTS posts_insert ON post;").executeUpdate();
        entityManager.createNativeQuery("""
                CREATE TRIGGER posts_insert
                        AFTER INSERT
                        ON post
                        FOR EACH ROW
                        EXECUTE FUNCTION handle_posts();
                """).executeUpdate();
        entityManager.createNativeQuery("DROP TRIGGER IF EXISTS posts_delete ON post;").executeUpdate();
        entityManager.createNativeQuery("""
                CREATE TRIGGER posts_delete
                        AFTER DELETE
                        ON post
                        FOR EACH ROW
                        EXECUTE FUNCTION handle_posts();
                """).executeUpdate();
        entityManager.createNativeQuery("DROP TRIGGER IF EXISTS posts_update ON post;").executeUpdate();
        entityManager.createNativeQuery("""
                CREATE TRIGGER posts_update
                        AFTER UPDATE
                        ON post
                        FOR EACH ROW
                        EXECUTE FUNCTION handle_posts();
                """).executeUpdate();
        entityManager.createNativeQuery("""
                CREATE OR REPLACE FUNCTION use_token(in ac_token varchar, inout activated bool = false)
                LANGUAGE plpgsql
                AS $BODY$
                DECLARE
                    _ac_user bigint;
                BEGIN
                    SELECT ct.user_id FROM schema_name.confirm_token ct WHERE ct.token = ac_token INTO _ac_user;

                    IF _ac_user IS NULL THEN
                        activated \\:= false;
                        RETURN;
                    END IF;

                    UPDATE schema_name."user" u SET "enabled" = true WHERE u.id = _ac_user;
                    DELETE FROM schema_name.confirm_token WHERE user_id = _ac_user;

                    activated \\:= true;
                END $BODY$;
                """.replace("schema_name", schema)).executeUpdate();


        loaded = true;
    }

}
