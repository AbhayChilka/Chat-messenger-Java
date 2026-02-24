import java.io.*;
import java.net.*;

class Server
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket ss = new ServerSocket(5100);
        System.out.println("Server started. Waiting for client...");

        Socket sobj = ss.accept();
        System.out.println("Client connected!");

        BufferedReader in = new BufferedReader(new InputStreamReader(sobj.getInputStream()));
        PrintStream out = new PrintStream(sobj.getOutputStream(), true);
        BufferedReader local = new BufferedReader(new InputStreamReader(System.in));

        // Thread to receive messages from client
        Thread receiveThread = new Thread(() -> 
        {
            try 
            {
                String msg;
                while ((msg = in.readLine()) != null) 
                {
                    System.out.println("Client: " + msg);
                    LogFile.log("Client: " + msg);
                }
                
                System.out.println("Client disconnected.");
                sobj.close();   // close socket

            } 
            catch (Exception e) 
            {
                System.out.println("Connection closed.");
            }
        });

        // Thread to send messages to client
        Thread sendThread = new Thread(() -> 
        {
            try 
            {
                String msg;
                while (!sobj.isClosed() && (msg = local.readLine()) != null)
                {
                    out.println(msg);

                    LogFile.log("Server: " + msg);
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

