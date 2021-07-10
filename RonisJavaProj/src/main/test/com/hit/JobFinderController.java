package main.test.com.hit;

import main.java.com.hit.Startup;
import main.java.com.hit.datamodels.Job;
import main.java.com.hit.server.enums.Commands;
import main.java.com.hit.server.request.RequestFactory;
import main.java.com.hit.server.socket.SocketExchange;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class JobFinderController
{
    public static InetAddress clientAddress;
    public static final int port = 1441;

    @BeforeClass
    public static void StartServer() throws IOException, InterruptedException {
        (new Thread(() -> Startup.main(null))).start();

        Thread.sleep(1000);
        // set this pc current ip address for sending requests
        clientAddress = InetAddress.getLocalHost();
    }

    @Test
    public void addToListTest()
    {
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.ADD.toString(), new Job("2", "3000", "Tel Aviv", "500"));
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
        e.printStackTrace();
        Assert.fail();
    }
    }

    @Test
    public void addSeveralToListTest()
    {

        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.ADD_SEVERAL.toString(), new Job[]
                    {new Job("11", "38593", "Zfat", "900"),
                    new Job("12", "112212", "Kiryat Arba", "1000"),
                    new Job("13", "200300", "Tel Aviv", "888")});
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
        e.printStackTrace();
        Assert.fail();
    }
    }

    @Test
    public void getAllTest()
    {
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.GET_ALL.toString());
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
        e.printStackTrace();
        Assert.fail();
    }
    }

    @Test
    public void getByCityTest()
    {
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.GET_CITY.toString(),"Haifa");
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
        e.printStackTrace();
        Assert.fail();
    }
    }

    @Test
    public void getByIdTest()
    {
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.GET_ID.toString(),"1");
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void getBySalaryTest()
    {
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.GET_SALARY.toString(), "22000");
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void updateFromListTest()
    {
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.UPDATE.toString(),new Job("5", "100000", "Beer Yaakov", "5"));
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void removeFromListTest()
    {
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            var req = RequestFactory.createRequest(Commands.REMOVE_SEVERAL.toString(), new String[] {"2"});
            exchange.sendData(req);
            var res= exchange.recieveData();
            Assert.assertNotNull(res);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
