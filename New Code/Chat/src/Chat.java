import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
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

    public void init()
    {
        setLayout(new BorderLayout());

        connect = new JButton("Connect");
        connect.addActionListener(this);

        getContentPane().add(connect, BorderLayout.SOUTH);

        chatWindow = new ChatWindow();
        chatWindow.listenSocket();
        setupWindow = new SetupWindow();
        currentlyViewing = setupWindow;

        getContentPane().add(currentlyViewing);
    }
    public void actionPerformed(ActionEvent e)
    {
        getContentPane().remove(currentlyViewing);
        validate();

        if (e.getSource() == connect)
        {
            currentlyViewing = chatWindow;
            connect.setVisible(false);
        }

        getContentPane().add(currentlyViewing);
        this.validate();
        this.repaint();
    }
}