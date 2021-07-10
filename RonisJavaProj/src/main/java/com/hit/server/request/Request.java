package main.java.com.hit.server.request;

public class Request
{
    public Request(String action)
    {
        this.header = new RequestHeader(action);
    }

    RequestHeader header;
}
