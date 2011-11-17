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
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11/11/11
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChatWindow extends JPanel implements ActionListener{
    JTextArea textArea;
	JButton sendButton;
	JScrollPane scrollingArea;
	Insets insets = new Insets(10,10,10,10);
    JTextField textField;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    public ChatWindow()
    {
        setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

        textArea = new JTextArea("");

		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollingArea = new JScrollPane(textArea);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 200; //make this tall
		c.ipadx = 200;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = insets;
		add(scrollingArea,c);
		
        textField = new JTextField("Enter a message");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
        textField.addActionListener(this);
		add(textField,c);
		
		sendButton = new JButton("Send Message.");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 34;
		c.ipadx = 20;
		c.gridx = 1;
		c.gridy = 1;
        sendButton.addActionListener(this);
		add(sendButton,c);
    }

    public void actionPerformed(ActionEvent event)
    {
         Object source = event.getSource();

         if(source == sendButton)
         {
              //Send data over socket
              String text = textField.getText();
              out.println(text);
              textField.setText(new String(""));
              //Receive text from server
              try
              {
                  String line = in.readLine();
                  System.out.println("Text received :" + line);
              } catch (IOException e)
              {

              System.out.println("Read failed");
              System.exit(1);
              }
         }
    }
    public void listenSocket()
    {
        //Create socket connection
        try
        {
            socket = new Socket("ASSKICKER", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Enter in a user name:");
            String userName = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.println(userName);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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