package server;

import java.net.*;
import java.util.ArrayList;
import client.Group;
import client.Message;

import java.io.*;


public class ClientThread extends Thread {

	private Socket client;
	private ChatServer server;
	private ObjectOutputStream out;
	private ObjectInputStream in; 

	public ClientThread(Socket c, ChatServer server) {
		this.server = server;
		this.client = c;
		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			out.flush();
			in = new ObjectInputStream(this.client.getInputStream());
			this.out.writeObject(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMessage(Message message) throws IOException {
		ArrayList <ClientThread> targetGroup = message.group.groupMembers;
		synchronized(this) {
			for (ClientThread c : targetGroup) {
				c.out.writeObject(message);
			} 
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
		public void read() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				Message msg = (Message) in.readObject();
				this.broadcastMessage(msg);
			}
			catch (Exception e) {
				keepRunning = false;
				System.out.println("Connection Failure");
				try {
					this.cleanConnection();
				} catch (IOException e1) {
					System.out.println("Exception ChatClient sendToServer()");
					e.printStackTrace();
				}
			}
		}
	}

	public void cleanConnection() throws IOException
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
