package tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
	
	private String task;
	private Date deadline;
	
	public Task(String task) {
		this.task = task;
	}
	
	public void setDeadline(String deadline) throws ParseException {
		DateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		this.deadline = myDateFormat.parse(deadline);
	}
}
