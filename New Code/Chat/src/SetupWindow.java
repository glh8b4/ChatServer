import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11/12/11
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SetupWindow extends JPanel {
    JLabel hostNameLabel, userNameLabel;
    JTextField hostNameField, userNameField;
    Insets insets = new Insets(10,10,10,10);

    public SetupWindow()
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        hostNameLabel = new JLabel("Host Name:");
        c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 35; //make this tall
		c.ipadx = 300;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = insets;
		add(hostNameLabel,c);

        hostNameField = new JTextField("");
        c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 0;
		c.insets = insets;
        add(hostNameField,c);

        userNameLabel = new JLabel("User Name:");
		c.gridx = 0;
		c.gridy = 1;
		c.insets = insets;
        add(userNameLabel,c);

        userNameField = new JTextField("");
		c.gridx = 1;
		c.gridy = 1;
		c.insets = insets;
        add(userNameField,c);
    }
}