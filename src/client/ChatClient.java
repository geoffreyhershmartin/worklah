package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import gui.ChatController;

public class ChatClient extends Thread {

	private String ip;
	private int port;
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ChatController guiController;
	private String userID;
	private Group currentGroup;

	public ChatClient(String ip, int p, ChatController _guiController, String _userID) {
		this.ip = ip;
		this.port = p;
		this.guiController = _guiController;
		this.currentGroup = new Group("myself");
		this.userID = _userID;
		try {
			this.connection = new Socket(this.ip, port);
			this.out = new ObjectOutputStream(connection.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		Thread reading = new Thread() {
			@Override
			public void run() {
				read();
			}
		};
		reading.start();
	}

	private void read() {
		boolean keepRunning = true;

		while (keepRunning) {
			try {
				Message msg = (Message) in.readObject();
				System.out.println(msg.sender + " : "+ msg.toString());
				if (msg.type.equals("message")) {
					this.displayMessage("[" + msg.sender + "] : " + msg.content + "\n");
				}
				else if (msg.type.equals("task")) {
					if (msg.recipient.equals(this.userID)) {
						guiController.taskList.getItems().add("[" + msg.sender + " > Me] : " + msg.content + "\n");
					}
				}
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
		Message newMessage = new Message("message", this.userID, "everyone", message, this.currentGroup);
		send(newMessage);
	}
	
	public void broadcastTaskToGroup(String task) {
		Message newMessage = new Message("task", this.userID, task, "everyone", this.currentGroup);
		send(newMessage);
	}

	public void send(Message message) {
		try {
			out.writeObject(message);
			out.flush();
			if (message.type.equals("message") && !message.content.equals(".bye")) {
				String messageTime = (new Date()).toString();
			}
		} 
		catch (IOException ex) {
			System.out.println("Exception: send() in ChatClient");
			ex.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
