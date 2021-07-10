package main.java.com.hit.server.socket;
import main.java.com.hit.server.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketExchange implements ISocketExchange
{

    private  DataInputStream in;
    private  DataOutputStream out;
    private final Socket currSocket;
    private String data = null;

    @Override
    public String recieveData()
    {
        if (data != null) return data;

        try {
            this.data = this.in.readUTF();
            return this.data;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    return null;
    }

    @Override
    public void sendData(String myData)
    {
        try {
            this.out.writeUTF(myData);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public <T> void sendData(T myData)
    {
        try {
            var dataString = Server.gson.toJson(myData);
            sendData(dataString);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection()
    {
        try{
            this.currSocket.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public SocketExchange(Socket currSocket)
    {
        this.currSocket = currSocket;
        try {
            this.in = new DataInputStream(currSocket.getInputStream());
            this.out = new DataOutputStream(currSocket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
