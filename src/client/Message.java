package client;

import java.io.Serializable;

public class Message implements Serializable{
	    
	    private static final long serialVersionUID = 1L;
	    // universal version identifier, to make sure that a loaded class corresponds to a serialised object
	    // if no match is found, then an InvalidClassException
	    
	    
	    public String type, sender, content;
	    public Group group;
	    
	    public Message(String type, String sender, String content, Group _group) {
	        this.type = type;
	        this.sender = sender;
	        this.content = content;
	        this.group = _group;
	    }
	    
	    @Override
	    public String toString(){
	        return "{type='"+type+"', sender='"+sender+"', content='"+content+"'}";
	    }
	}


