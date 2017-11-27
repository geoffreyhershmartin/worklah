package groups;

import java.io.Serializable;
import messages.Message;
import java.util.ArrayList;
import server.ClientThread;

public class Group implements Serializable {

	private static final long serialVersionUID = 7710781962991992645L;

	public String groupName;
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
