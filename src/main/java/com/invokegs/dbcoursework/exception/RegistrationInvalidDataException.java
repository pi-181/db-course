package com.invokegs.dbcoursework.exception;

public class RegistrationInvalidDataException extends RuntimeException {
    private final boolean emailInUse;
    private final boolean usernameInUse;

    public RegistrationInvalidDataException(boolean emailInUse, boolean usernameInUse) {
        super();
        this.emailInUse = emailInUse;
        this.usernameInUse = usernameInUse;
    }

    public RegistrationInvalidDataException(boolean emailInUse, boolean usernameInUse, String msg) {
        super(msg);
        this.emailInUse = emailInUse;
        this.usernameInUse = usernameInUse;
    }

    public RegistrationInvalidDataException(boolean emailInUse, boolean usernameInUse, String msg, Throwable cause) {
        super(msg, cause);
        this.emailInUse = emailInUse;
        this.usernameInUse = usernameInUse;
    }

    public boolean isEmailInUse() {
        return emailInUse;
    }

    public boolean isUsernameInUse() {
        return usernameInUse;
    }
}
