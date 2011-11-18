/**
 * Created by Harmon and Werckmann
 * User: Administrator
 * Date: 11/11/11
 * Time: 2:35 PM
 */

import javax.swing.*;

import java.io.*;
import java.net.*;

class ClientInformation { // implements Runnable {
  private Socket client;
  private JTextArea textArea;
  private String userName;
  private BufferedReader in = null;
  private PrintWriter outputToClient = null;

  //constructor takes a client, textArea, and a userName, then initializes the input and output streams
  ClientInformation(Socket client, JTextArea textArea, String userName)
  {
        this.client = client;
        this.textArea = textArea;
        this.userName = userName;

        try
        {     //initialize input and output streams
              in = new BufferedReader(new InputStreamReader(client.getInputStream()));
              outputToClient = new PrintWriter(client.getOutputStream(), true);
              outputToClient.println("Welcome to the Chat Server!");

        }catch (IOException e)
        {
              System.out.println("in or out failed");
              System.exit(-1);
        }
  }

  //get function to return the output stream
  public PrintWriter getOutputToClient()
  {
      return outputToClient;
  }

  //get function to return the userName
  public String getUserName()
  {
      return userName;
  }
}