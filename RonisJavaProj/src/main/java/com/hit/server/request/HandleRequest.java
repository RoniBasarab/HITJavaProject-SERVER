package main.java.com.hit.server.request;
import main.java.com.hit.controllers.JobFinderController;
import main.java.com.hit.datamodels.Job;
import main.java.com.hit.controllers.ControllerMethod;
import main.java.com.hit.server.Server;
import main.java.com.hit.server.socket.SocketExchange;
import main.java.com.hit.server.enums.Commands;

import java.util.Map;

public class HandleRequest
{
    private static final Map<Commands, ControllerMethod> MethodMap = createMap();

    public static void handler(SocketExchange exchange, String userRequest)
    {
        Request temp = Server.gson.fromJson(userRequest,Request.class);
        Commands userTempCommand = Commands.fromString(temp.header.action);
        assert MethodMap != null;
        var method = MethodMap.get(userTempCommand);
        try{
            if(method.isHasBody())
            {
                var body = method.getBody(exchange.recieveData());
                method.getMethod().invoke(new JobFinderController(), exchange , body);
            }
            else
            {
                method.getMethod().invoke(new JobFinderController(), exchange);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static Map<Commands,ControllerMethod> createMap()
    {
        try{
            Map<Commands,ControllerMethod> MethodMap = Map.of(
                    Commands.GET_ALL, new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("getAll", SocketExchange.class), null, false),
                    Commands.GET_ID, new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("getById", SocketExchange.class, Object.class), String.class),
                    Commands.GET_CITY,new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("getByCity", SocketExchange.class,Object.class),String.class),
                    Commands.GET_SALARY,new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("getBySalary", SocketExchange.class, Object.class),String.class),
                    Commands.ADD, new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("addToList", SocketExchange.class, Object.class), Job.class),
                    Commands.UPDATE, new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("updateFromList", SocketExchange.class, Object.class), Job.class),
                    Commands.REMOVE,new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("removeFromList", SocketExchange.class, Object.class),String.class),
                    Commands.REMOVE_SEVERAL,new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("removeSeveralFromList", SocketExchange.class, Object.class), String[].class),
                    Commands.ADD_SEVERAL,new ControllerMethod<>(JobFinderController.class.getDeclaredMethod("addSeveralToList", SocketExchange.class, Object.class), Job[].class));
            return MethodMap;

        }catch (NoSuchMethodException e)
        {
            e.printStackTrace();

        }
        return null;
    }
}
