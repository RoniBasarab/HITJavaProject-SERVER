package main.java.com.hit.server.socket;

public interface ISocketExchange
{
    public String recieveData();
    void sendData(String myData);
    <T>void sendData(T myData);
    public void closeConnection();


}
