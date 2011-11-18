/**
 * Created by Harmon and Werckmann
 * User: Administrator
 * Date: 11/11/11
 * Time: 2:35 PM
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.InterruptedException;

class Server extends JFrame implements ActionListener, MessageListener{

   JLabel label = new JLabel("Text received over socket:");
   JPanel panel;
   JButton closeServer;
   JTextArea textArea = new JTextArea();
   ServerSocket server = null;
   ArrayList<ClientInformation> clients;

   //Constructor sets up the panel
   Server()
   {
        panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        panel.setBackground(Color.white);
        getContentPane().add(panel);
        closeServer = new JButton("Close Server");
        closeServer.addActionListener(this);
        panel.add(closeServer);
        panel.add("North", label);
        panel.add("Center", textArea);
        clients = new ArrayList<ClientInformation>();

   }

  //Listens to the socket for new clients and configures them for use
  public void listenSocket()
  {
        int port = 4444;
        try
        {
              server = new ServerSocket(port);
        } catch (IOException e) {
              System.out.println("Could not listen on port 4444");
              System.exit(-1);
        }
        while(true)    //waits for more clients
        {
              ClientInformation clientInformation;
              try
              {
                  //new client gets a new socket
                  Socket clientSocket = server.accept();
                  BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                  String userName = in.readLine();
                  clientInformation = new ClientInformation(clientSocket, textArea, userName);  //contains client information

                  if (clients.size() >= 11)  //check for more than 10 users in the server
                  {
                      clientInformation.getOutputToClient().println("We're sorry, but the server is currently full!");
                  }
                  else
                  {
                      //create a socket observer to monitor the socket for input
                      SocketObserver observer = new SocketObserver(in);
                      observer.listener = this;
                      Thread observerThread = new Thread(observer);
                      observerThread.start();
                      clients.add(clientInformation);
                  }


              } catch (IOException e)
              {
                    System.out.println("Accept failed: 4444");
                    System.exit(-1);
              }
        }
  }

  //Called by the SocketObserver when there is input coming down the socket
  public void messageRecieved(String message)
  {
      if (message.contains(" : has left the Chat Room"))   //client has left the room, drop from clients arrayList
      {
          String [] tokens = message.split(" ");
          String user = tokens[0];
          for (int i = 0; i < clients.size(); i++)
          {
              if (clients.get(i).getUserName().equals(user))
              {
                  clients.remove(i);
              }
          }
      }

      for (int i = 0; i < clients.size(); i++)
      {
          PrintWriter outputToClient = clients.get(i).getOutputToClient();
          outputToClient.println(message);
      }
      textArea.append(message + "\n");
  }

  //handles the event of the button being pressed.
  public void actionPerformed(ActionEvent event)
  {
      if (event.getSource() == closeServer) //handles closing the server
      {
          for (int i = 0; i < clients.size(); i++)
          {
             PrintWriter outputToClient = clients.get(i).getOutputToClient();
             outputToClient.println("The server will be closing in 10 seconds!");
          }
          textArea.append("Server Closing in 10 seconds");

          //Wait for 10 seconds
          try
          {
             Thread.currentThread().sleep(8000);//sleep for 10 seconds
          }
          catch(InterruptedException ie)
          {
              System.out.println("Error: sleeping was interupted by another thread!");
          }

          //close clients
          for (int i = 0; i < clients.size(); i++)
          {
              clients.get(i).getOutputToClient().println("/exit");
          }

          //Wait for 10 seconds
          try
          {
             Thread.currentThread().sleep(2000);//sleep for 10 seconds
          }
          catch(InterruptedException ie)
          {
              System.out.println("Error: sleeping was interupted by another thread!");
          }

          //closes server
          finalize();
      }
  }

  //Objects created in run method are finalized when
  //program terminates and thread exits
  protected void finalize()
  {
     try
     {
         server.close();
     }catch (IOException e)
     {
         System.out.println("Could not close socket");
         System.exit(-1);
     }
  }

  //main method for the Socket
  public static void main(String[] args)
  {
        Server frame = new Server();
        frame.setTitle("Server Program");
        WindowListener l = new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                    System.exit(0);
            }
        };

        frame.addWindowListener(l);
        frame.pack();
        frame.setVisible(true);
        frame.listenSocket();
  }
}