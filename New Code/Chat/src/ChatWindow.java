import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Harmon and Werckmann
 * User: Administrator
 * Date: 11/11/11
 * Time: 2:35 PM
 */

public class ChatWindow extends JPanel implements ActionListener, MessageListener {
    JTextArea textArea;
	JButton sendButton, exitButton;
	JScrollPane scrollingArea;
	Insets insets = new Insets(10,10,10,10);
    JTextField textField;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    String userName ="USER NAME NOT SET PROPERLY";
    String messages = "";

    //Constructor sets up the GUI using a GridBagLayout
    public ChatWindow(String userName)
    {
        this.userName = userName;
        setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 35; //make this tall
		c.ipadx = 20;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = insets;
        exitButton.addActionListener(this);
		add(exitButton,c);

        textArea = new JTextArea("");
		textArea.setLineWrap(true);
		textArea.setEditable(false);
        textArea.setText("Welcome to the Chat Server! \n");
		scrollingArea = new JScrollPane(textArea);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 200; //make this tall
		c.ipadx = 200;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = insets;
		add(scrollingArea,c);
		
        textField = new JTextField("Enter a message");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
        textField.addActionListener(this);
		add(textField,c);
		
		sendButton = new JButton("Send Message.");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 34;
		c.ipadx = 20;
		c.gridx = 1;
		c.gridy = 2;
        sendButton.addActionListener(this);
		add(sendButton,c);
    }

    //Performs when one of the buttons is pressed
    //sendButton sends the message and exitButton ends the client
    public void actionPerformed(ActionEvent event)
    {
         if(event.getSource() == sendButton)
         {
             String text = userName + " : " + textField.getText();
             if(text.equals(userName + " : /exit") || text.equals(userName + " : /part")
                                                   || text.equals(userName + " : /quit"))
             {
                 out.println(userName + " : has left the Chat Room");
                 try
                 {
                    socket.close();
                 } catch(IOException e)
                 {
                     System.out.println("Failed to close the socket!");
                     System.exit(1);
                 }
                 System.exit(0);
             }
             else
             {
                 //Send data over socket
                 out.println(text);
                 textField.setText("");
             }


         }
         if (event.getSource() == exitButton)
         {
             //User selected exit, so the client will exit
             out.println(userName + " : has left the Chat Room");
             try
             {
                socket.close();
             } catch(IOException e)
             {
                 System.out.println("Failed to close the socket!");
                 System.exit(1);
             }
             System.exit(0);
         }
    }

    //Called by the SocketObserver when there is input coming down the socket
    public void messageRecieved(String message)
    {
        //Receive text from server
        messages = messages + "\n" + message;
        textArea.setText(messages);
    }

    //initiates the socket, input and output streams, and socket observer
    public void listenSocket(String hostName)
    {
        //Create socket connection
        try
        {
            socket = new Socket(hostName, 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(userName);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //start the means to listen in on the socket
            SocketObserver observer = new SocketObserver(in);//, serverSocket);
            observer.listener = this;
            Thread t = new Thread(observer);
            t.start();
        } catch (UnknownHostException e)
        {
            System.out.println("Unknown host: kq6py.eng");
            System.exit(1);
        } catch  (IOException e)
        {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
}