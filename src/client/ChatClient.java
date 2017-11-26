package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import com.socket.Message;
import com.ui.ChatFrame;

public class ChatClient {

	private String ip;
	private int port;
	private Socket connection;
	private BufferedReader br;
	private PrintWriter pw;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public ChatClient(String ip, int p)
	{
		this.ip = ip;
		this.port = p;
		try {
			this.connection = new Socket(InetAddress.getByName(this.ip), port);
			this.br = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
			this.pw = new PrintWriter(this.connection.getOutputStream());
	        out = new ObjectOutputStream(connection.getOutputStream());
	        out.flush();
	        in = new ObjectInputStream(connection.getInputStream());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// previously called sendMessageToServer
	
	public void run(Message message)
	{
		boolean keepRunning = true;
		
        while(keepRunning)
        {
        	try {
        		
	        	Message msg = (Message) in.readObject();
	        	// identifying sender of message
	        	System.out.println(msg.sender+" : "+msg.toString());
	        	
	        	
	        	/* 
	        	 * this is based on certain assumptions regarding the gui:
	        		* first, that the username  is saved as username once it is inputted
	        		* second, that there is some textbox/allocated area for the username in the gui
	        	*/
	        	if(msg.type.equals("chat"))
	        	{
	        		gui.[ textbox for username  ].append("["+msg.sender+"] : " + msg.content + "\n");
	        	}
	        	
	        	if(msg.type.equals("task"))
	        	{
	        		if(msg.recipient.equals(gui.username)){
		                gui.[ textbox for username  ].append("["+msg.sender +" > Me] : " + msg.content + "\n");
		            }
		        	
		        	// inserting time stamp of message
		        	if(!msg.content.equals(".bye"))
		        	{
		                 String messageTime = (new Date()).toString();
		        	} 
	        	}
	        	
        	}
        	
        	catch(Exception e) {
                keepRunning = false;
                
                System.out.println(" Connection Failure ");

                
                server.ClientThread.stop();
                
                System.out.println("Exception ChatClient sendToServer()");
                e.printStackTrace();
            }
        }
	}
	
	public void send(Message message)
	{
		 try {
			 	out.writeObject(message);
	            out.flush();
	            System.out.println("Sending : "+message.toString());
	            
	            if(message.type.equals("message") && !message.content.equals(".bye")){
	                String messageTime = (new Date()).toString();
	            }
	        } 
	        catch (IOException ex) {
	            System.out.println("Exception: send() in ChatClient");
	        }
	}
	
	public void closeConnection()
	{
		try {
			this.pw.close();
			this.br.close();
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static void main (String[] args)
	{
		ChatClient mc = new ChatClient("127.0.0.1", 8080);
		mc.sendMessageToServer("HELLO");
		while(true)
		{
			
		}
	}
	

}
