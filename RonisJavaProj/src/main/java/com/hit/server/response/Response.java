package main.java.com.hit.server.response;

public class Response
{
    ResponseHeader header;

    public Response(boolean b)
    {
        this.header = new ResponseHeader(b);
    }
}
