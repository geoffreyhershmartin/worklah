package client;

import java.net.*;

import server.ChatServer;

import java.io.*;


public class ClientThread extends Thread {

	private Socket client;
	private ReadingThread reading;
	protected PrintWriter pw;
	private BufferedReader br;
	private Group currentGroup;
	
	private ChatServer server;

	public ClientThread(Socket c, ChatServer server)
	{
		this.server = server;
		this.currentGroup = new Group("myself");
		this.currentGroup.addUser(this);
		this.client = c;
		try {
			this.pw = new PrintWriter(this.client.getOutputStream());	
			this.br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			this.reading = new ReadingThread(this.br, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMessageToGroup(String message)
	{
		this.server.broadcastMessage(message, this.currentGroup, this);
	}
	
	public synchronized void sendMessage(String message)
	{
		this.pw.println(message);
		this.pw.flush();
	}
	
	protected void printMessage(String message) {
		guiController.append(message);
	}
	
	public synchronized void sendConfirmation()
	{
		this.pw.println("Confirmation");
		this.pw.flush();
	}

	@Override
	public void run()
	{
		this.reading.start();
	}

	public void cleanConnection()
	{
		System.out.println("Client disconnecting, cleaning the data!");
		this.pw.close();
		try {
			this.br.close();
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}