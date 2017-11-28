package client;

import java.io.IOException;
import messages.Message;
import tasks.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import groups.Group;
import gui.ChatController;
import gui.PopupController;

public class Client extends Thread {

	private String ip;
	private int port;
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ChatController guiController;
	private PopupController popupController;
	private String username;

	public Client(String ip, int p, ChatController _guiController, String _username) throws ClassNotFoundException {
		this.ip = ip;
		this.port = p;
		this.guiController = _guiController;
		this.username = _username;
		this.popupController = null;
		try {
			this.connection = new Socket(this.ip, port);
			this.out = new ObjectOutputStream(connection.getOutputStream());
			this.in = new ObjectInputStream(connection.getInputStream());
			setUser(_username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPopupController(PopupController _popupController) {
		this.popupController = _popupController;
	}

	public void setUser(String _username) {
		Message setUser = new Message("setUser", this.username, _username, "");
		send(setUser);
	}

	public void updateGroup(ArrayList <String> newGroup) {
		Message updateGroup = new Message("updateGroup", this.username, String.join(",", newGroup), "");
		updateGroup.userList = newGroup;
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
	
	public void getOnlineUsers() {
		Message newMessage = new Message("getUsers", this.username, "", "");
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
				Message message = (Message) in.readObject();
				System.out.println(message);
				if (message.type.equals("message")) {
					this.displayMessage("[" + message.sender + "] : " + message.content + "\n");
				} else if (message.type.equals("task")) {
					guiController.taskList.getItems().add("[" + message.sender + " > Me] : " + message.content + "\n");
				} else if (message.type.equals("userList")) {
					for (String _username : message.userList) {
						this.popupController.addUserElement(_username);
					}
				} else if (message.type.equals("chatHistory")) {
					// TODO PRINT CHAT HISTORY
				} else if (message.type.equals("loadUserData")) {
					for (Group group : message.groups) {
						guiController.populateUserList(group.groupName);
					}
					for (Task task : message.taskList) {
						guiController.populateTaskList(task.task + " due by " + task.deadline.toString());
					}
				} else if (message.type.equals("notifyUser")) {
					this.notifyUser(message);
				}
			}
			catch (Exception e) {
				System.out.println("Connection Failure");
				this.closeConnection();
				System.out.println("Exception Client sendToServer()");
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void notifyUser(Message message) {
		// TODO append user list of users to chat bar and notify
	}

	public void displayMessage(String message) {
		guiController.append(message);
	}

	public void displayTask(String message) {
		guiController.append(message);
	}

	public void send(Message message) {
		synchronized (this) {
			try {
				System.out.println(message);
				out.writeObject(message);
				out.flush();
			} 
			catch (IOException ex) {
				System.out.println("Exception: send() in Client");
				ex.printStackTrace();
			}
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
