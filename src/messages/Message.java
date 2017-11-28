package messages;

import java.io.Serializable;
import java.util.ArrayList;
import groups.Group;
import tasks.Task;

public class Message implements Serializable {

	private static final long serialVersionUID = -4398980948410147192L;

	public String type, sender, content, recipient;
	public ArrayList <String> userList;
	public ArrayList <Task> taskList;
	public ArrayList <Message> chatHistory;
	public ArrayList <Group> groups;
	public String group;

	public Message(String _type, String _sender, String _content, String _recipient) {
		this.type = _type;
		this.sender = _sender;
		this.content = _content;
		this.recipient = _recipient;
		this.userList = null;
		this.taskList = null;
		this.chatHistory = null;
		this.group = null;
		this.groups = null;
	}

	@Override
	public String toString() {
		return "{" + "\n" +
				"\ttype : " + type + "\n" +
				"\tsender : " + sender + "\n" +
				"\tcontent : " + content + "\n" +
				"}";
	}
	
	public void setUserList(ArrayList <String> _userList) {
		this.userList = _userList;
	}
	
	public void setTaskList(ArrayList <Task> _taskList) {
		this.taskList = _taskList;
	}
	
	public void setUserChatHistory(ArrayList <Message> _chatHistory) {
		this.chatHistory = _chatHistory;
	}
	
	public void setUserData(ArrayList <Group> _groups) {
		this.groups = _groups;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
}


