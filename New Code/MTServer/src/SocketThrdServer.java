import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.Vector;

class SocketThrdServer extends JFrame implements TextListener{

   JLabel label = new JLabel("Text received over socket:");
   JPanel panel;
   JTextArea textArea = new JTextArea();
   ServerSocket server = null;
   Vector<ClientWorker> clientWorkers;

   SocketThrdServer()    //Begin Constructor
   {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);
        getContentPane().add(panel);
        panel.add("North", label);
        panel.add("Center", textArea);
        clientWorkers = new Vector<ClientWorker>();

   } //End Constructor

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
        while(true)
        {
              ClientWorker clientWorker;
              try
              {
                  Socket clientSocket = server.accept();
                  BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                  String userName = in.readLine();
                  //String hostName = InetAddress.getLocalHost().getHostName();
                  //Socket serverSocket = new Socket(hostName,port);
                  clientWorker = new ClientWorker(clientSocket, textArea, userName);//, serverSocket);
                  Thread t = new Thread(clientWorker);
                  t.start();
                  clientWorkers.add(clientWorker);
              } catch (IOException e)
              {
                    System.out.println("Accept failed: 4444");
                    System.exit(-1);
              }
        }
  }

  public void textValueChanged(TextEvent e)
  {
      for (int i =0; i < clientWorkers.size(); i++)
      {

      }
  }

  protected void finalize(){
//Objects created in run method are finalized when
//program terminates and thread exits
     try{
        server.close();
    } catch (IOException e) {
        System.out.println("Could not close socket");
        System.exit(-1);
    }
  }

  public static void main(String[] args){
    SocketThrdServer frame = new SocketThrdServer();
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