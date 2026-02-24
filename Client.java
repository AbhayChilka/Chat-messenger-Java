import java.io.*;
import java.net.*;

class Client
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Connecting to server...");
        Socket sobj = new Socket("localhost", 5100);
        System.out.println("Connected to server!");

        BufferedReader in = new BufferedReader(new InputStreamReader(sobj.getInputStream()));
        PrintStream out = new PrintStream(sobj.getOutputStream(), true);
        BufferedReader local = new BufferedReader(new InputStreamReader(System.in));

        // Thread to receive messages from server
        Thread receiveThread = new Thread(() -> 
        {
            try 
            {
                String msg;
                while ((msg = in.readLine()) != null) 
                {
                    System.out.println("Server: " + msg);
                }

                System.out.println("Server disconnected.");
                sobj.close();   // Close socket

            } 
            catch (Exception e)
            {
                System.out.println("Connection closed.");
            }
        });

        // Thread to send messages to server
        Thread sendThread = new Thread(() -> 
        {
            try 
            {
                String msg;
                while (!sobj.isClosed() && (msg = local.readLine()) != null) 
                {
                    out.println(msg);
                }
            } 
            catch (Exception e) 
            {
                System.out.println("Send thread stopped.");
            }
        });

        receiveThread.start();
        sendThread.start();
    }
}

