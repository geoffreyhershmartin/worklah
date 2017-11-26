package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

	private String ip;
	private int port;
	private Socket connection;
	private BufferedReader br;
	private PrintWriter pw;
	
	
	public ChatClient(String ip, int p)
	{
		this.ip = ip;
		this.port = p;
		try {
			this.connection = new Socket(this.ip, this.port);
			this.br = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
			this.pw = new PrintWriter(this.connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessageToServer(String message)
	{
		this.pw.println(message);
		this.pw.flush();
	}
	
	public void closeConnection()
	{
		try {
			this.pw.close();
			this.br.close();
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args)
	{
		ChatClient mc = new ChatClient("127.0.0.1", 8080);
		mc.sendMessageToServer("HELLO");
		while(true)
		{
			
		}
	}
}
