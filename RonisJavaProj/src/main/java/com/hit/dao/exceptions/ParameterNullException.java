package main.java.com.hit.dao.exceptions;

public class ParameterNullException extends DaoException
{
    public ParameterNullException()
    {
    }

    public ParameterNullException(String message) {
        super(message);
    }

    public ParameterNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterNullException(Throwable cause) {
        super(cause);
    }

    public ParameterNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
