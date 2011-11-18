import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Harmon and Werckmann
 * User: Administrator
 * Date: 11/11/11
 * Time: 2:35 PM
 */
public class SocketObserver implements Runnable{
    MessageListener listener;
    BufferedReader in;

    //Constructor takes input stream to listen on the socket
    public SocketObserver(BufferedReader i)
    {
        in = i;
    }

    //run method that must
    public void run()
    {
        //infinite loop
        while(true)
        {
            //keep checking the socket to see if something new has been entered
            try
            {
                String message = in.readLine();
                if (message != null)
                {
                    listener.messageRecieved(message);
                }
            } catch (IOException e)
            {
                //This will only happen when the socket is closed, return ends the thread
                return;
            }

        }
    }
}
