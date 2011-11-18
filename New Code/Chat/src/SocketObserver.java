import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created Harmon and Werckmann
 * User: Administrator
 * Date: 11/17/11
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocketObserver implements Runnable{
    MessageListener listener;
    BufferedReader in;

    //Constructor takes an input stream to listen to
    public SocketObserver(BufferedReader i)
    {
        in = i;
    }

    //run method that must
    public void run()
    {
        while(true)
        {
            //keep checking the socket to see if something new has been entered
            try
            {
                String message = in.readLine();
                if (message != null)
                {
                    if (message.equals("/exit"))
                    {
                        System.exit(0);
                        return;
                    }
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
