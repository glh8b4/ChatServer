import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

class SocketThrdServer extends JFrame{

   JLabel label = new JLabel("Text received over socket:");
   JPanel panel;
   JTextArea textArea = new JTextArea();
   ServerSocket server = null;

   SocketThrdServer(){ //Begin Constructor
     panel = new JPanel();
     panel.setLayout(new BorderLayout());
     panel.setBackground(Color.white);
     getContentPane().add(panel);
     panel.add("North", label);
     panel.add("Center", textArea);
   } //End Constructor

  public void listenSocket(){
    try{
      server = new ServerSocket(4444);
    } catch (IOException e) {
      System.out.println("Could not listen on port 4444");
      System.exit(-1);
    }
    while(true){
      ClientWorker w;
      try{
        w = new ClientWorker(server.accept(), textArea);
        Thread t = new Thread(w);
        t.start();
      } catch (IOException e) {
        System.out.println("Accept failed: 4444");
        System.exit(-1);
      }
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
        WindowListener l = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                        System.exit(0);
                }
        };
        frame.addWindowListener(l);
        frame.pack();
        frame.setVisible(true);
        frame.listenSocket();
  }
}