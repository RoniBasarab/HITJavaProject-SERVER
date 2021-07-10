package main.java.com.hit.server.response;

public class ResponseFactory
{
    public static  <T> BodyResponse<T> createSuccessfulResponse(T data)
    {
        return new BodyResponse<>(data, true);
    }

    public static Response createSuccessfulResponse()
    {
        return new Response(true);
    }

    public static BodyResponse<String> createFailedResponse(String error)
    {
        return new BodyResponse<>(error, false);
    }

// --Commented out by Inspection START (30/05/2021 23:47):
//    public static Response createFailedResponse()
//    {
//        return new Response(false);
//    }
// --Commented out by Inspection STOP (30/05/2021 23:47)
}
