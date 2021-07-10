package main.java.com.hit.dao.exceptions;

public class CouldNotFindException extends DaoException
{
    public CouldNotFindException()
    {

    }

    public CouldNotFindException(String message) {
        super(message);
    }

    public CouldNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotFindException(Throwable cause) {
        super(cause);
    }

    public CouldNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
