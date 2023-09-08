package com.adminpanel.exception;

import java.sql.SQLIntegrityConstraintViolationException;

public class SQLCustomException extends SQLIntegrityConstraintViolationException {

    public SQLCustomException() {
        super();
    }

    public SQLCustomException(String reason) {
        super(reason);
    }

    public SQLCustomException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public SQLCustomException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public SQLCustomException(Throwable cause) {
        super(cause);
    }

    public SQLCustomException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public SQLCustomException(String reason, String SQLState, Throwable cause) {
        super(reason, SQLState, cause);
    }

    public SQLCustomException(String reason, String SQLState, int vendorCode, Throwable cause) {
        super(reason, SQLState, vendorCode, cause);
    }
}
