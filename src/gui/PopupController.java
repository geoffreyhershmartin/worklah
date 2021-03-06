/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *Contains injected GUI Components from Popup.fxml. Is used to select new online users to talk to.
 * @author student
 */
public class PopupController implements Initializable {

	@FXML
	private ListView<String> onlineList;
	@FXML
	private Button button2;
	protected Client client;
	protected ChatController chatController; 
	protected ListView<String> primaryUserList;

	/**
	 * The next two listener methods take the selected users from the online list and add them to the user list on the
	 * previous page.
	 * @param event 
	 */
	@FXML
	void button2Listener(ActionEvent event) {
		List<String> showing = onlineList.getSelectionModel().getSelectedItems();
		if (!this.checkMembers(new ArrayList<String>(showing), this.chatController.conversations)) {
			client.updateGroup(new ArrayList<String>(showing));
			client.addConversation(new ArrayList<String>(showing));
			final Node source = (Node) event.getSource();
			final Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			this.chatController.chatView.setText("");
			this.chatController.conversantName.setText(String.join(", ", showing));
			this.chatController.conversantImage.setVisible(true);
			this.chatController.greenCircle.setVisible(true);
		}
	}

	@FXML
	void enterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER){
			List<String> showing = onlineList.getSelectionModel().getSelectedItems();
			if (!this.checkMembers(new ArrayList<String>(showing), this.chatController.conversations)) {
				client.updateGroup(new ArrayList<String>(showing));
				client.addConversation(new ArrayList<String>(showing));
				final Node source = (Node) event.getSource();
				final Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
				this.chatController.chatView.setText("");
				this.chatController.conversantName.setText(String.join(", ", showing));
				this.chatController.conversantImage.setVisible(true);
				this.chatController.greenCircle.setVisible(true);
			}
		}
	}
	/**
	 * Method to check the Members of a conversation.
	 * @param group1
	 * @param group2
	 * @return 
	 */
	public boolean checkMembers(ArrayList <String> group1, ArrayList <ArrayList <String>> group2) {
		boolean flag = false;
		for (ArrayList <String> conversation : group2) {
			Collections.sort(group1);
			Collections.sort(conversation);
			if (conversation.equals(group1)) {
				flag = true;
			}
		}
		return(flag);
	}

	void setChatController(ChatController _chatController) {
		this.chatController = _chatController;
	}

	void setClient(Client _client) {
		this.client = _client;
		this.client.setPopupController(this);
	}

	public void addUserElement(String _user) {
		onlineList.getItems().add(_user);
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		onlineList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

}
