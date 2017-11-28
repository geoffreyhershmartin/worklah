package messages;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	private static final long serialVersionUID = -4398980948410147192L;

	public String type, sender, content, recipient;
	public ArrayList <String> userList;

	public Message(String _type, String _sender, String _content, String _recipient) {
		this.type = _type;
		this.sender = _sender;
		this.content = _content;
		this.recipient = _recipient;
		this.userList = new ArrayList <String>();
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
	
}


