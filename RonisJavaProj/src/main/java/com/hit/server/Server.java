package main.java.com.hit.server;
import com.google.gson.Gson;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.server.request.HandleRequest;
import main.java.com.hit.server.socket.SocketExchange;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server
{
    private static final int port = 1441;
    public static Gson gson = new Gson(); // single instance of gson is required
    private static ServerSocket server;
    public static AtomicBoolean isServerRunning = new AtomicBoolean(false);
    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    private static CLI cli;
    public static IDao file;
    public static final ExecutorService executor = Executors.newCachedThreadPool();
    public static InetAddress clientAddress;


    public static void main(String[] args)
    {
        Server.runCLI();
        file = new DaoFileImpl(System.getProperty("user.dir").concat("\\RonisJavaProj\\src\\main\\resources\\JobListings.json"));
        //"C:\\Users\\iditu\\RonisJavaProject\\RonisJavaProj\\src\\main\\resources\\JobListings.json"
    }


    public static void runServer()
    {
        isServerRunning.set(true);
        executor.submit(() -> {
            try {
                server = new ServerSocket(port);
                do{
                    var currSocketExchange = new SocketExchange(server.accept());
                    var request = currSocketExchange.recieveData();
                    executor.submit(() -> HandleRequest.handler(currSocketExchange, request));
                }while(isRunning.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void closeServer() throws InterruptedException, IOException
    {
        isServerRunning.set(false);
        try
        {
            server.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void runCLI()
    {
        cli = new CLI();
        executor.submit(cli);
    }

    public static void serverShutDown() {
        try {
            if (isServerRunning.get()) {
                server.close();
            }
            cli.scanner.close();
            executor.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InetAddress getClientAddress() throws UnknownHostException {
        clientAddress = InetAddress.getLocalHost();
        return clientAddress;
    }

    public static int getPort()
    {
        return port;
    }
}
