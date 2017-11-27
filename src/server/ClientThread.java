package server;

import java.net.*;
import java.util.ArrayList;

import java.io.*;


public class ClientThread extends Thread {

	private Socket client;
	private ChatServer server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Group currentGroup;
	private ArrayList <Group> allGroups;
	private String username;

	public ClientThread(Socket c, ChatServer server) {
		this.server = server;
		this.client = c;
		this.username = "";
		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			out.flush();
			in = new ObjectInputStream(this.client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(Message message) throws IOException {
		synchronized (this) {
			for (ClientThread c : currentGroup.groupMembers) {
				c.out.writeObject(message);
			} 
		}
	}

	private void sendTask(Message message) throws IOException {
		synchronized (this) {
			for (ClientThread c : currentGroup.groupMembers) {
				if (message.recipient.equals(c.username)) {
					c.out.writeObject(message);
				}
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
		if (!newGroupCreated) {
			Group newGroup = new Group(message.recipient);
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
				read();
			}
		};
		reading.start();
	}

	public void read() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				Message message = (Message) in.readObject();
				if (message.type.equals("updateUsername")) {
					this.updateUsername(message);
				} else if (message.type.equals("updateGroup")) {
					this.updateGroup(message);
				} else if (message.type.equals("task")) {
					this.sendTask(message);
				} else {
					this.sendMessage(message);
				}					
			}
			catch (Exception e) {
				keepRunning = false;
				System.out.println("Connection Failure");
				try {
					this.closeConnection();
				} catch (IOException e1) {
					System.out.println("Exception ChatClient sendToServer()");
					e.printStackTrace();
				}
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
