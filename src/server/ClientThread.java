package server;

import java.net.*;
import messages.Message;
import groups.Group;
import java.util.ArrayList;

import groups.Group;

import java.io.*;


public class ClientThread extends Thread {

	private Socket client;
	private Server server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Group currentGroup;
	private ArrayList <Group> allGroups;
	private String username;

	public ClientThread(Socket c, Server server) {
		this.server = server;
		this.client = c;
		this.username = "";
		this.allGroups = new ArrayList <Group>();
		this.currentGroup = new Group("myself");
		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			in = new ObjectInputStream(this.client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(Message message) throws IOException {
		for (ClientThread c : currentGroup.groupMembers) {
			send(message, c);
		} 
	}

	private void sendTask(Message message) throws IOException {
		for (ClientThread c : currentGroup.groupMembers) {
			if (message.recipient.equals(c.username)) {
				send(message, c);
			}
		} 
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
		for (Group g : allGroups) {
			if (g.groupName.equals(message.content)) {
				this.currentGroup = g;
				newGroupCreated = false;
			}
		}
		if (newGroupCreated) {
			Group newGroup = new Group(message.content);
			this.currentGroup = newGroup;
			this.allGroups.add(newGroup);
		}
	}

	private void updateUsername(Message message) {
		this.username = message.content;
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
				if (message.type.equals("updateUsername")) {
					this.updateUsername(message);
				} else if (message.type.equals("updateGroup")) {
					this.updateGroup(message);
					System.out.println(this.currentGroup.groupName);
				} else if (message.type.equals("task")) {
					this.sendTask(message);
				} else {
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
