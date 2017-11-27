package server;

import java.net.*;

import client.Group;

import java.io.*;


public class ClientThread extends Thread {

	private Socket client;
	private ReadingThread reading;
	public PrintWriter pw;
	private BufferedReader br;
	private Group currentGroup;
	
	private ChatServer server;

	public ClientThread(Socket c, ChatServer server)
	{
		this.server = server;
		this.currentGroup = new Group("myself");
		this.client = c;
		try {
			this.pw = new PrintWriter(this.client.getOutputStream());	
			this.br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			this.reading = new ReadingThread(this.br, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMessage(String message) throws IOException
	{
		this.server.broadcastMessage(message, this.currentGroup, this);
	}
	
	public synchronized void sendMessage(String message)
	{
		this.pw.println(message);
		this.pw.flush();
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
	
	public void read()
	{
		String inc = "";
		try {
			while((inc = this.br.readLine()) != null)
			{
				this.broadcastMessage(inc);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Connection was closed!");
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