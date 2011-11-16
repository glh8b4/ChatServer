import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

class ClientWorker implements Runnable {
  private Socket client;
  private JTextArea textArea;
  
  ClientWorker(Socket client, JTextArea textArea) {
   this.client = client;
   this.textArea = textArea;   
  }

  public void run(){
        String line;
        BufferedReader in = null;
        PrintWriter out = null;
        try
        {
              in = new BufferedReader(new InputStreamReader(client.getInputStream()));
              out = new PrintWriter(client.getOutputStream(), true);
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
                    System.out.println("Sending to client: " + line);
                    out.println(line);
                    textArea.append(line);
              } catch (IOException e)
              {
                    System.out.println("Read failed");
                    System.exit(-1);
              }
        }
  }
}