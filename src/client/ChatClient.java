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
import gui.ChatController;

public class ChatClient extends Thread {
	
	private String ip;
	private int port;
	private Socket connection;
//	private BufferedReader in;
	private ObjectOutputStream out;
	private ObjectInputStream in;
//	private PrintWriter out;
	private ChatController guiController;
	private String userID;
	private Group currentGroup;

	public ChatClient(String ip, int p, ChatController _guiController, String _userID)
	{
		this.ip = ip;
		this.port = p;
		this.guiController = _guiController;
		this.currentGroup = new Group("myself");
		this.userID = _userID;
		try {
			this.connection = new Socket(this.ip, port);
			this.out = new ObjectOutputStream(connection.getOutputStream());
//			this.out = new PrintWriter(connection.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(connection.getInputStream());
//			this.in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void run()
	{
		Thread reading = new Thread() {
            @Override
            public void run() {
            		read();
            }
        };
		reading.start();
	}
	
	private void read()
	{
//		String inc = "";
//		try {
//			System.out.print("HERE");
//			while((inc = this.in.readLine()) != null)
//			{
//				System.out.print(inc);
//				this.displayMessage(inc);
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		System.out.println("Connection was closed!");
		boolean keepRunning = true;

		while (keepRunning) {
			try {
				
				Message msg = (Message) in.readObject();
				// identifying sender of message
				System.out.println(msg.sender + " : "+ msg.toString());

				/* 
				 * this is based on certain assumptions regarding the gui:
				 * first, that the username  is saved as username once it is inputted
				 * second, that there is some textbox/allocated area for the username in the gui
				 */
				if (msg.type.equals("message"))
				{
					this.displayMessage("[" + msg.sender + "] : " + msg.content + "\n");
				}

//				if (msg.type.equals("task"))
//				{
//					if (msg.recipient.equals(gui.username)){
//						gui.taskList.append("[" + msg.sender + " > Me] : " + msg.content + "\n");
//					}
//
//					// inserting time stamp of message
//					if (!msg.content.equals(".bye"))
//					{
//						String messageTime = (new Date()).toString();
//					} 
//				}

			}

			catch (Exception e) {
				keepRunning = false;
				System.out.println("Connection Failure");
				this.closeConnection();
				System.out.println("Exception ChatClient sendToServer()");
				e.printStackTrace();
			}
		}
	}
	
	public void displayMessage(String message) {
		guiController.append(message);
	}

	public void broadcastMessageToGroup(String message) {
		Message newMessage = new Message("message", this.userID, message, this.currentGroup);
		send(newMessage);
	}

	public void send(Message message)
	{
		try {
			out.writeObject(message);
			out.flush();
			if (message.type.equals("message") && !message.content.equals(".bye")){
				String messageTime = (new Date()).toString();
			}
		} 
		catch (IOException ex) {
			System.out.println("Exception: send() in ChatClient");
			ex.printStackTrace();
		}
	}

	public void closeConnection()
	{
		try {
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
