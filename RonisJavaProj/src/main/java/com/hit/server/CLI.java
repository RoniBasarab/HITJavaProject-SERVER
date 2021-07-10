package main.java.com.hit.server;
import main.java.com.hit.server.request.Request;
import main.java.com.hit.server.socket.SocketExchange;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CLI implements Runnable
{
    public static final String instructions = "Instructions: " +
            "\n\tstart - launches the server" +
            "\n\tstop - stops the servers" +
            "\n\tsend [JSON] - send a [JSON] request to the server -> example: {\"body\":3,\"header\":{\"action\":\"GET_ID\"}}" +
            "if you wish to get all listings -> {\"header\":{\"action\":\"GET_ALL\"}}" +
            "\n\texit - exits everything";
    Scanner scanner = new Scanner(System.in);


    @Override
    public void run()
    {
        System.out.println(instructions);
        while(Server.isRunning.get())
        {
            try {
                handleRequest(getUserInput());
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            if(!Server.isRunning.get())
            {
                return;
            }
        }
    }

    private void handleRequest(String req) throws InterruptedException, IOException {
        switch (req) {
            case "start":
                if (!(Server.isServerRunning.get())) {
                    System.out.println("Server is starting...\n");
                    Server.runServer();
                    System.out.println("Server launched!\n");
                } else {
                    System.out.println("Server has already started.");
                }
                break;
            case "stop":
                if (Server.isServerRunning.get()) {
                    Server.closeServer();
                    System.out.println("Server closed.");
                } else {
                    System.out.println("Server is not running.");
                }
                break;
            case "exit":
                Server.executor.submit(Server::serverShutDown);
                Server.isRunning.set(false);
                break;
            default:
                if (req.startsWith("send ")) {
                    var json = req.substring(5);
                    System.out.println("Sending " + json + " to the server");
                    var reqObj = getJsonReqIfExists(json);
                    if (reqObj != null) {
                        if (Server.isServerRunning.get()) {
                            Server.executor.submit(() -> {
                                SocketExchange exchange = null;
                                try {
                                    exchange = new SocketExchange(new Socket(Server.getClientAddress(), Server.getPort()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    exchange.sendData(reqObj);
                                    System.out.println(exchange.recieveData());
                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                finally {
                                    exchange.closeConnection();
                                }
                            });
                        } else {
                            System.out.println("Server is not running, what are you trying to do ?");
                        }
                    } else {
                        System.out.println("Json is invalid. please try again.");
                    }
                }
                break;
        }
    }

    private String getUserInput() {
        return scanner.nextLine();
    }

    private Request getJsonReqIfExists(String req) {
        try {
            return Server.gson.fromJson(req, Request.class);
        } catch (Exception ex) {
            return null;
        }
    }
}
