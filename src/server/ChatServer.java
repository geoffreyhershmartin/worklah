package server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import client.Group;
import client.Message;

public class ChatServer
{
	private ServerSocket servSock;
	protected ArrayList <ClientThread> clients;
	private PrintWriter pw;

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

}