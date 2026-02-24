import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LogFile 
{

    private static final String FILE_NAME = "log.txt";

    public static void log(String message)  throws Exception
    {
        try 
        {
            FileWriter fw = new FileWriter(FILE_NAME, true); // true = append mode
            PrintWriter pw = new PrintWriter(fw);

            pw.println(LocalDateTime.now() + " : " + message);

            pw.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing to log file");
        }
    }
}
