import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Harmon and Werckmann
 * User: Administrator
 * Date: 11/12/11
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class Chat extends JApplet implements ActionListener {
    JPanel currentlyViewing;
    ChatWindow chatWindow;
    SetupWindow setupWindow;
    JButton connect;

    //This is effectively the constructor used initialize the JApplet
    public void init()
    {
        setLayout(new BorderLayout());

        connect = new JButton("Connect");
        connect.addActionListener(this);

        getContentPane().add(connect, BorderLayout.SOUTH);

        setupWindow = new SetupWindow();
        currentlyViewing = setupWindow;

        getContentPane().add(currentlyViewing);
    }

    //Listens for the connect button to be pressed
    public void actionPerformed(ActionEvent e)
    {
        String userName = setupWindow.getUserName();
        String hostName = setupWindow.getHostName();
        getContentPane().remove(currentlyViewing);
        validate();

        if (e.getSource() == connect)
        {
            chatWindow = new ChatWindow(userName);
            chatWindow.listenSocket(hostName);
            currentlyViewing = chatWindow;     //switch from the setup window to the chat window
            connect.setVisible(false);  //make the button invisible
        }

        getContentPane().add(currentlyViewing);
        this.validate();
        this.repaint();
    }
}