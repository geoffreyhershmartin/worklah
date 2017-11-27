package client;

import java.io.Serializable;
import java.util.ArrayList;

import server.ClientThread;

public class Group implements Serializable {
	
	private static final long serialVersionUID = 7710781962991992645L;
	
	protected String groupName;
	public ArrayList <ClientThread> groupMembers;
	
	public Group(String _groupName) {
		this.groupName = _groupName;
		this.groupMembers = new ArrayList <ClientThread>();
	}
	
	protected void addUser(ClientThread newUser) {
		this.groupMembers.add(newUser);
	}
	
}
