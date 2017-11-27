package server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import client.Group;
import client.Message;

public class ChatServer
{
	private ServerSocket servSock;
	protected ArrayList <Socket> clients;
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
		clients = new ArrayList <Socket>();
		while (true)
		{
			Socket c;
			try {
				c = this.servSock.accept();
				clients.add(c);
				ClientThread th = new ClientThread(c, this);
				th.start();
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