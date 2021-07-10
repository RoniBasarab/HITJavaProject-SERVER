package main.java.com.hit.dao.exceptions;

public class EmptyJobListException extends DaoException
{
    public EmptyJobListException() {
    }

    public EmptyJobListException(String message) {
        super(message);
    }

    public EmptyJobListException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyJobListException(Throwable cause) {
        super(cause);
    }

    public EmptyJobListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
