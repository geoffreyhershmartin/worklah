package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import messages.Message;

public class ServerThread extends Thread {
    public Server server = null;
    public Socket connection = null;
    public int id = -1;
    public String username = "";
    public ObjectInputStream in  =  null;
    public ObjectOutputStream out = null;
    
    public long getId() {
    		return id;
    }

    public ServerThread(Server server, Socket connection) {  
    		id = connection.getPort();
        server = this.server;
        connection = this.connection;
    }
    
    public synchronized void send(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } 
        catch (IOException e) {
            System.out.println("Sorry, we were not able to send your message.");
        }
    }
    
    public void openConnection() throws IOException {  
        out = new ObjectOutputStream(connection.getOutputStream());
        out.flush();
        in = new ObjectInputStream(connection.getInputStream());
    }
    
    public void closeConnection() throws IOException {  
    	if (connection != null) connection.close();
        if (in != null)  in.close();
        if (out != null) out.close();
    }   
}
