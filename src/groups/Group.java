package groups;

import java.io.Serializable;
import messages.Message;
import java.util.ArrayList;
import java.util.Collections;

import users.User;

public class Group implements Serializable {

	private static final long serialVersionUID = 7710781962991992645L;

	public String groupName;
	public ArrayList <User> groupMembers;
	public ArrayList <String> groupMemberNames;
	public ArrayList <Message> chatHistory;
	
	public Group() {
		this.groupName = null;
		this.groupMembers = new ArrayList <User>();
		this.groupMemberNames = new ArrayList <String>();
		this.chatHistory = new ArrayList <Message>();
	}

	public void addUser(User newUser) {
		this.groupMembers.add(newUser);
		System.out.println(newUser.username);
		this.groupMemberNames.add(newUser.username);
	}
	
	public void updateGroupName() {
		ArrayList <String> groupMemberNames = new ArrayList<String>();
		for (User user : this.groupMembers) {
			groupMemberNames.add(user.username);
		}
		this.groupName = String.join(",", groupMemberNames);
	}
	
	public boolean checkMembers(ArrayList <String> members) {
		Collections.sort(members);
		Collections.sort(this.groupMemberNames);
		return(members.equals(groupMemberNames));
	}
	

}
