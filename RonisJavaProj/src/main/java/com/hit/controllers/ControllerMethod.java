package main.java.com.hit.controllers;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.server.Server;
import main.java.com.hit.server.request.BodyRequest;
import java.lang.reflect.Method;

public class ControllerMethod<T>
{
    private final Method method;
    private final boolean hasBody;
    Class<T> requestClass;
    String reqString;

    public ControllerMethod(Method method,  Class<T> bodyClass)
    {
        this.method = method;
        this.requestClass = bodyClass;
        this.hasBody = true;
    }

    public ControllerMethod(Method method,  Class<T> bodyClass, boolean hasBody)
    {
        this.method = method;
        this.requestClass = bodyClass;
        this.hasBody = hasBody;
    }


    public Method getMethod()
    {
        return method;
    }

    public boolean isHasBody() {
        return hasBody;
    }

    public T getBody(String reqString)
    {
        this.reqString = reqString;
        if (hasBody) {
            var req = (BodyRequest<T>)Server.gson.fromJson(reqString, TypeToken.getParameterized(BodyRequest.class, requestClass).getType());
            return req.getBody();
        }
        else {
            return null;
        }
    }

}
