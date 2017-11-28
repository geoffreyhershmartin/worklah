package groups;

import java.io.Serializable;
import messages.Message;
import java.util.ArrayList;
import users.User;

public class Group implements Serializable {

	private static final long serialVersionUID = 7710781962991992645L;

	public String groupName;
	public ArrayList <User> groupMembers;
	public ArrayList <Message> chatHistory;

	public Group(String _groupName) {
		this.groupName = _groupName;
		this.groupMembers = new ArrayList <User>();
		this.chatHistory = new ArrayList <Message>();
	}
	
	public Group() {
		this.groupName = null;
		this.groupMembers = new ArrayList <User>();
		this.chatHistory = new ArrayList <Message>();
	}

	public void addUser(User newUser) {
		this.groupMembers.add(newUser);
	}

}
