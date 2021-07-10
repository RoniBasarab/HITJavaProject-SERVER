package main.java.com.hit.server.response;

public class BodyResponse<T> extends Response
{
    private final T body;

    public BodyResponse(T data, boolean isSuccessful)
    {
        super(isSuccessful);
        this.body = data;
    }
}
