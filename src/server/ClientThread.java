package server;

import java.net.*;
import messages.Message;
import tasks.Task;
import users.User;
import groups.Group;
import java.util.ArrayList;
import java.io.*;

/* 
 By extending Thread, each newly spawned thread is instantiated as as a unique object associated. 
 We decided against implementing Runnable, because if we had done so, many threads could share the same object instance. 
*/

public class ClientThread extends Thread {

	private Socket client;
	private Server server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private User user;
		
	/* Note: Should we change these to buffered input/output streams to be more efficient?
	 * If we implement Buffered I/O streams,this will optimize input and output 
	 * by reducing the number of calls to the native API.
	 */
	
	// constructor
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

	private void handleTask(Message message) throws IOException {
		String recipient = (String) message.content;
		if (recipient.equals(this.user.username)) {
			Task newTask = new Task((String) message.content);
			this.user.tasks.add(newTask);
			return;
		}
		for (User user : this.user.getGroupMembers()) {
			if (recipient.equals(user.username)) {
				send(message, user.getClientThread());
			}
		} 
	}
	
	public void sendUsers() {
		Message newMessage = new Message("userList", null, null);
		ArrayList <String> userList = new ArrayList <String>();
		for (User user : server.users) {
			if (user.username != this.user.username && (user.onlineStatus)) {
				userList.add(user.username);
			}
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
		ArrayList <String> userList = (ArrayList <String>) message.content;
		boolean newGroupCreated = true;
		for (Group g : this.user.allGroups) {
			if (g.checkMembers(userList)) {
				this.user.currentGroup = g;
				newGroupCreated = false;
			}
		}
		if (newGroupCreated) {
			Group newGroup = new Group();
			for (String user : userList) {
				newGroup.addUser(getUser(user));
			}
			newGroup.addUser(this.user);
			newGroup.updateGroupName();
			this.user.currentGroup = newGroup;
			this.user.allGroups.add(newGroup);
			server.groups.add(newGroup);
		}
		Message chatHistory = new Message("chatHistory", null, null);
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
			if (message.sender.equals(user.username)) {
				if (message.content.equals(user.password)) {
					this.user = user;
					user.goOnline(this);
					return;
				}
			}
		}
		this.user = new User(message.sender, (String) message.content, this);
		server.users.add(this.user);
		Message loadUserData = new Message("loadUserData", null, null);
		ArrayList <Object> userData = new ArrayList <Object>();
		userData.add(user.allGroups);
		userData.add(user.tasks);
		loadUserData.content = userData;
		send(loadUserData, this);
	}
	
	/* 
	* Not quite sure why we have separate run() and read() methods.
	*/

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
	
	/*
	* To make this safer code, I suggest that we use finally to close the output stream, 
	* so that we still terminate the thread even if the thread is forcibly stopped.
	* Right now, our code only appears to be handling the case when an IOException occurs.
	
	* Related: In the GUI, we do not close the screen immediately.
	* This is a design choice: a user may not want to close the app completely just because they have lost connection to it.
	* That is, users going in lifts, or through tunnels.
	*/

	public void read() throws IOException {
		while (true) {
			try {
				Message message = (Message) in.readObject();
				if (this.user != null) {
					message.setGroup(this.user.currentGroup.groupMemberNames);
				}
				if (message.type.equals("setUser")) {
					this.setUser(message);
				} else if (message.type.equals("updateGroup")) {
					this.updateGroup(message);
				} else if (message.type.equals("task")) {
					this.handleTask(message);
				} else if (message.type.equals("getUsers")) {
					this.sendUsers();
				} else if (message.type.equals("message")) {
					if (message.sender == this.user.username) {
						this.messageHandler(message);
					}
				} else if (message.type.equals("goOffline")) {
					this.user.goOffline();
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
	
	public void messageHandler(Message message) throws IOException {
		this.user.currentGroup.chatHistory.add(message);
		for (User user : this.user.getGroupMembers()) {
			if (!user.currentGroup.checkMembers(this.user.currentGroup.groupMemberNames)) {
				user.allGroups.add(this.user.currentGroup);
				message.type = "notifyUser";
				if (!user.equals(this.user)) {
					this.send(message, user.getClientThread());
				}
			} else {
				if (!user.equals(this.user)) {
					this.send(message, user.getClientThread());
				}
			}
		}
	}

	public void closeConnection() throws IOException
	{
		System.out.println("Client disconnecting, cleaning the data!");
		try {
			this.out.close();
			this.in.close();
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
