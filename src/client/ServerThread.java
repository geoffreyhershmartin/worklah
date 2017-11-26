package client;

import java.io.*;
import java.util.*;

public class ReadingThread extends Thread
{
	private BufferedReader incomingText;
	private ClientThread clientThread;
	
	public ReadingThread(BufferedReader br, ClientThread cl)
	{
		this.incomingText = br;
		this.clientThread = cl;
	}

	@Override
	public void run()
	{
		String inc = "";
		try {
			while((inc = this.incomingText.readLine()) != null)
			{
				System.out.println("I received the following message: " + inc);
				this.clientThread.sendConfirmation();
				this.clientThread.broadcastMessageToOtherClients(inc);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Connection was closed!");

	}
}