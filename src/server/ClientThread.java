package server;

import java.net.*;
import messages.Message;
import tasks.Task;
import users.User;
import groups.Group;
import java.util.ArrayList;
import java.io.*;

public class ClientThread extends Thread {

	private Socket client;
	private Server server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private User user;

	public ClientThread(Socket c, Server server) {
		this.server = server;
		this.client = c;
		this.user = null;
		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			in = new ObjectInputStream(this.client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(Message message) throws IOException {
		for (User user : this.user.getGroupMembers()) {
			send(message, user.getClientThread());
		}
		this.user.currentGroup.chatHistory.add(message);
	}

	private void handleTask(Message message) throws IOException {
		if (message.recipient.equals(this.user.username)) {
			Task newTask = new Task(message.content);
			this.user.tasks.add(newTask);
			return;
		}
		for (User user : this.user.getGroupMembers()) {
			if (message.recipient.equals(this.user.username)) {
				send(message, user.getClientThread());
			}
		} 
	}
	
	public void sendUsers() {
		Message newMessage = new Message("userList", "", "", "");
		ArrayList <String> userList = new ArrayList <String>();
		for (User user : server.users) {
			userList.add(user.username);
		}
		newMessage.setUserList(userList);
		send(newMessage, this);
	}
	
	public void send(Message message, ClientThread c) {
		synchronized (this) {
			try {
				c.out.writeObject(message);
				c.out.flush();
			} 
			catch (IOException ex) {
				System.out.println("Exception: send() in ClientThread");
				ex.printStackTrace();
			}
		}
	}
	
	private void updateGroup(Message message) {
		boolean newGroupCreated = true;
		for (Group g : this.user.allGroups) {
			if (g.groupName.equals(message.content)) {
				this.user.currentGroup = g;
				newGroupCreated = false;
			}
		}
		if (newGroupCreated) {
			Group newGroup = new Group(message.content);
			for (String user : message.userList) {
				newGroup.addUser(getUser(user));
			}
			this.user.currentGroup = newGroup;
			this.user.allGroups.add(newGroup);
			server.groups.add(newGroup);
		}
		Message chatHistory = new Message("chatHistory", "", "", "");
		chatHistory.setUserChatHistory(this.user.currentGroup.chatHistory);
		send(chatHistory, this);
	}
	
	private User getUser(String _user) {
		for (User user : server.users) {
			if (_user.equals(user.username)) {
				return(user);
			}
		}
		return(null);
	}

	private void setUser(Message message) {
		for (User user : server.users) {
			if (message.content.equals(user.username)) {
				this.user = user;
				return;
			}
		}
		this.user = new User(message.content, this);
		Message loadUserData = new Message("loadUserData", "", "", "");
		loadUserData.setUserData(user.allGroups);
		loadUserData.setTaskList(user.tasks);
		send(loadUserData, this);
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

	public void read() throws IOException {
		while (true) {
			try {
				Message message = (Message) in.readObject();
				if (message.type.equals("setUser")) {
					this.setUser(message);
				} else if (message.type.equals("updateGroup")) {
					this.updateGroup(message);
				} else if (message.type.equals("task")) {
					this.handleTask(message);
				} else if (message.type.equals("getUsers")) {
					this.sendUsers();
				} else if (message.type.equals("message")) {
					this.sendMessage(message);
				}
			}
			catch (Exception e) {
				System.out.println("Connection Failure");
				e.printStackTrace();
				try {
					this.closeConnection();
				} catch (IOException e1) {
					System.out.println("Exception ChatClient sendToServer()");
					e1.printStackTrace();
				}
				break;
			}
		}
	}

	public void closeConnection() throws IOException
	{
		System.out.println("Client disconnecting, cleaning the data!");
		this.out.close();
		try {
			this.in.close();
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
