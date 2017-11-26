package server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import client.ClientThread;
import client.Group;

public class ChatServer
{
	private ServerSocket servSock;
	private Socket client;
	private PrintWriter pw;
	private ArrayList <ClientThread> clients;

	public ChatServer(int port)
	{
		try {
			this.servSock = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void acceptClientLoop()
	{
		clients = new ArrayList <ClientThread>();
		while (true)
		{
			Socket c;
			try {
				c = this.servSock.accept();
				ClientThread th = new ClientThread(c, this);
				th.start();
				clients.add(th);
				System.out.println("Client Accepted.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main (String[] args) throws Exception
	{
		ChatServer serv = new ChatServer(8080);

		serv.acceptClientLoop();
	}

	public void broadcastMessage(String message, Group clientGroup, ClientThread sender) {
		synchronized(this) {
			for (ClientThread c : clientGroup.groupMembers) {
			    if (!c.equals(sender)) {
			    		c.pw.println(message);
			    		c.pw.flush();
			    }
			} 
		}
	}
}