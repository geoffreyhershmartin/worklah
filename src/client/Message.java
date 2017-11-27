package client;

import java.io.Serializable;

import server.Group;

public class Message implements Serializable {

	private static final long serialVersionUID = -4398980948410147192L;
	// universal version identifier, to make sure that a loaded class corresponds to a serialised object
	// if no match is found, then an InvalidClassException


	public String type, sender, content, recipient;
	public Group group;

	public Message(String _type, String _sender, String _content, String _recipient) {
		this.type = _type;
		this.sender = _sender;
		this.content = _content;
		this.recipient = _recipient;
	}

	@Override
	public String toString() {
		return "{type='"+type+"', sender='"+sender+"', content='"+content+"'}";
	}
}


