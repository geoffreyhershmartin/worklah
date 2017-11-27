package client;

import java.io.IOException;
import messages.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import gui.ChatController;

public class ChatClient extends Thread {

	private String ip;
	private int port;
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ChatController guiController;
	private String username;

	public ChatClient(String ip, int p, ChatController _guiController, String _username) throws ClassNotFoundException {
		this.ip = ip;
		this.port = p;
		this.guiController = _guiController;
		this.username = _username;
		try {
			this.connection = new Socket(this.ip, port);
			this.out = new ObjectOutputStream(connection.getOutputStream());
			this.in = new ObjectInputStream(connection.getInputStream());
			this.updateGroup("Taha");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateUsername(String _username) {
		Message updateUsername = new Message("updateUsername", this.username, _username, "");
		send(updateUsername);
	}

	public void updateGroup(String newGroup) {
		Message updateGroup = new Message("updateGroup", this.username, newGroup, "");
		send(updateGroup);
	}

	public void sendMessageToGroup(String message) {
		Message newMessage = new Message("message", this.username, message, "");
		send(newMessage);
	}

	public void sendTaskToGroup(String task) {
		Message newMessage = new Message("task", this.username, task, "");
		send(newMessage);
	}

	@Override
	public void run() {
		Thread reading = new Thread() {
			@Override
			public void run() {
				try {
					read();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		reading.start();
	}

	private void read() throws IOException {

		while (true) {
			try {
				Message msg = (Message) in.readObject();
				System.out.println(msg.sender + " : "+ msg.toString());
				if (msg.type.equals("message")) {
					this.displayMessage("[" + msg.sender + "] : " + msg.content + "\n");
				}
				else if (msg.type.equals("task")) {
					guiController.taskList.getItems().add("[" + msg.sender + " > Me] : " + msg.content + "\n");
				}
			}
			catch (Exception e) {
				System.out.println("Connection Failure");
				this.closeConnection();
				System.out.println("Exception ChatClient sendToServer()");
				e.printStackTrace();
				break;
			}
		}
	}

	public void displayMessage(String message) {
		guiController.append(message);
	}

	public void displayTask(String message) {
		guiController.append(message);
	}

	public void send(Message message) {
		try {
			out.writeObject(message);
			out.flush();
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
