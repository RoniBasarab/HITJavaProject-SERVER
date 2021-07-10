package main.java.com.hit.server.request;

public class RequestFactory
{
    public static  <T> BodyRequest<T> createRequest(String action, T data)
    {
        return new BodyRequest(action, data);
    }

    public static Request createRequest(String action)
    {
        return new Request(action);
    }

}
