import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

class ClientWorker implements Runnable {
  private Socket client, server;
  private JTextArea textArea;
  private String userName;
  
  ClientWorker(Socket client, JTextArea textArea, String userName)//, Socket server)
  {
       this.client = client;
       this.textArea = textArea;
       this.userName = userName;
  }

  public void run(){
        String line;
        BufferedReader in = null;
        PrintWriter outClient = null;
        //PrintWriter outServer = null;
        try
        {
              in = new BufferedReader(new InputStreamReader(client.getInputStream()));
              outClient = new PrintWriter(client.getOutputStream(), true);
              //outServer = new PrintWriter(server.getOutputStream(), true);

        }catch (IOException e)
        {
              System.out.println("in or out failed");
              System.exit(-1);
        }

        while(true)
        {
              try
              {
                    line = in.readLine();
                    //Send data back to client

                    textArea.append(line);
                    textArea.append("\n");
                    outClient.println(textArea.getText());
                    System.out.println("Thread to client: " + textArea.getText());

              } catch (IOException e)
              {
                    System.out.println("Read failed");
                    System.exit(-1);
              }
        }
  }
}