package server;

import java.io.Serializable;
import java.util.ArrayList;

import client.Message;

public class Group implements Serializable {

	private static final long serialVersionUID = 7710781962991992645L;

	protected String groupName;
	public ArrayList <ClientThread> groupMembers;
	public ArrayList <Message> chatHistory;

	public Group(String _groupName) {
		this.groupName = _groupName;
		this.groupMembers = new ArrayList <ClientThread>();
		this.chatHistory = new ArrayList <Message>();
	}

	protected void addUser(ClientThread newUser) {
		this.groupMembers.add(newUser);
	}

}
