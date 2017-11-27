/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author student
 */
public class LoginScreenController implements Initializable {

	Stage prevStage;

	@FXML
	private TextField Username;

	@FXML
	private Button loginButton;
	@FXML
	private PasswordField passwordField;    

	protected ChatClient client;
	protected String userID;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}    

	@FXML
	private void loginPressed(ActionEvent event) throws IOException {

		String userID = Username.getText().trim();
		if (userID.length() == 0)
			return;
		Stage stage = new Stage();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatScreen.fxml"));     
		Parent root = (Parent) fxmlLoader.load();          
		ChatController controller = fxmlLoader.<ChatController>getController();

		// Creates a server/client;
		int port = 8080;
		String server = "127.0.0.1";
		client = new ChatClient(server, port, controller, userID);
		client.start();

		controller.setClient(client);
		Scene scene = new Scene(root); 
		stage.setScene(scene);
		ChatController chatController = new ChatController();

		chatController.setID(Username.getText().trim());
		stage.show();
		Stage stage1 = (Stage) prevStage.getScene().getWindow();
		stage1.close();

	}


	void setPrevStage(Stage stage) {
		this.prevStage = stage;

	}

	public String getID(){
		return this.userID;
	}


	public void setID(String _userID){
		this.userID = _userID;
	}

}
