package server;

import java.net.*;
import client.Group;
import client.Message;

import java.io.*;


public class ClientThread extends Thread {

	private Socket client;
	public PrintWriter pw;
	private BufferedReader br;
	private ChatServer server;
	private ObjectOutputStream out;
	private ObjectInputStream in; 

	public ClientThread(Socket c, ChatServer server)
	{
		this.server = server;
		this.client = c;
		try {
//			this.pw = new PrintWriter(this.client.getOutputStream());	
//			this.br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			out = new ObjectOutputStream(this.client.getOutputStream());
			out.flush();
			in = new ObjectInputStream(this.client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMessage(Message message) throws IOException {
		synchronized(this) {
			for (ClientThread c : server.clients) {
				c.out.writeObject(message);
			} 
		}
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
//		String inc = "";
//		try {
//			while((inc = this.br.readLine()) != null)
//			{
//				this.broadcastMessage(inc);
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		System.out.println("Connection was closed!");
		boolean keepRunning = true;

		while (keepRunning) {
			try {
				Message msg = (Message) in.readObject();
				this.broadcastMessage(msg);
			}
			catch (Exception e) {
				keepRunning = false;
				System.out.println("Connection Failure");
				this.cleanConnection();
				System.out.println("Exception ChatClient sendToServer()");
				e.printStackTrace();
			}
		}
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