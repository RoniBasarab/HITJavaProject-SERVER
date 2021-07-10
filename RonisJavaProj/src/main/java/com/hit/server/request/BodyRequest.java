package main.java.com.hit.server.request;

public class BodyRequest<T> extends Request
{
    private final T body;

    public BodyRequest(String action, T body) {
        super(action);
        this.body = body;
    }

    public T getBody() {
        return body;
    }

}
