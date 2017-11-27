/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author student
 */
public class ChatController implements Initializable {

	@FXML
	private ListView<?> userList;
	@FXML
	public ListView<String> taskList;
	@FXML
	private TextArea chatView;
	@FXML
	private TextArea chatView2;
	@FXML
	private TextField searchTask;
	@FXML
	private ImageView searchTasks;
	@FXML
	private ImageView profileImage;
	@FXML
	private ImageView sendButton;
	@FXML
	private TextField chatBox;
	@FXML

	private Label time;
	@FXML
	private Hyperlink logOut;
	private ChatClient client;

	protected String userID;

	Stage prevStage;
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}    

	protected void setClient(ChatClient _client) {
		client = _client;
	}

	@FXML
	private void userClicked(MouseEvent event) {
	}

	@FXML
	private void taskDragged(MouseEvent event) {
	}

	@FXML
	private void taskClicked(MouseEvent event) {
	}

	//	@FXML
	//	private void logOut(MouseEvent event) throws IOException {
	//		
	//	}

	//        @FXML
	//        void logOffClicked(MouseEvent event) {
	//        System.exit(0);
	//        }

	@FXML
	private void enterPressedTask(KeyEvent event) {
	}

	@FXML
	private void searchClicked(MouseEvent event) {
		String a = searchTask.getText();
		taskList.getItems().add(a);

	}
	@FXML
	void logoutPressed(ActionEvent event) {
		System.exit(0);
	}
	@FXML
	private void sendPressed(MouseEvent event) {

		String message = chatBox.getText();
		String catchPhrase = "@task";
		if( message.contains(catchPhrase)) {
			taskList.getItems().add(message.replace("@task", ""));
		}
		else {
			client.sendMessageToGroup(chatBox.getText());
			if(message.contains("right")){
				append2(message);
			}
			else
				append(message);
		}
		client.sendMessageToGroup(chatBox.getText());
		chatBox.setText("");

	}

	@FXML
	private void enterPressedChat(ActionEvent event) {
		String message = chatBox.getText();
		String catchPhrase = "@task";
		if (message.contains(catchPhrase)) {

			String task = message.replace("@task", "");
			taskList.getItems().add(task);

		}
		else if(message.contains("right")){
			append2(message);
		}
		else{
			append(message);
		}
		client.sendTaskToGroup(message);
		chatBox.setText("");
	}




	public void append(String str) {
		chatView.appendText(str + "\n");
		chatView.selectPositionCaret(chatView.getText().length()-1);
		chatView2.appendText("\n");
		chatView2.selectPositionCaret(chatView2.getText().length()-1);
	}
	public void append2(String str) {
		chatView2.appendText(str + "\n");
		chatView2.selectPositionCaret(chatView2.getText().length()-1);
		chatView.appendText("\n");
		chatView.selectPositionCaret(chatView.getText().length()-1);
	}

	public void setID(String _userID){
		this.userID = _userID;
	}

	void connectionFailed() {
		//        boolean connected = false;
	}
}