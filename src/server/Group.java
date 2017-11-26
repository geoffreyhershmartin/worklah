package server;

import java.util.ArrayList;

public class Group {
	
	protected String groupName;
	protected ArrayList <ClientThread> groupMembers;
	
	public Group(String _groupName) {
		this.groupName = _groupName;
		this.groupMembers = new ArrayList <ClientThread>();
	}
	
	protected void addUser(ClientThread newUser) {
		this.groupMembers.add(newUser);
	}
	
}
