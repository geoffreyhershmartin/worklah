package server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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
		
		while (true)
		{
			Socket c;
			clients = new ArrayList <ClientThread>();
			try {
				c = this.servSock.accept();
				ClientThread th = new ClientThread(c, this);
				th.start();
				clients.add(th);
				System.out.println("Just accepted a client. Going to the next iteration");
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

	public void broadcastMessage(String message, ClientThread sender) {
//		for each client but the sender, send message
		
	}
}