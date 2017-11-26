/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
	//	protected Client client;
	//	protected String userID;
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}    

	@FXML
	private void loginPressed(ActionEvent event) throws IOException {

		//        String userID = Username.getText().trim();
		//        if (userID.length() == 0)
		//            return;
		//        int port = 8080;
		//        String server= "localhost";
		//        client = new Client(server, port, userID);
		Stage stage = new Stage();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Screen2.fxml"));     

		Parent root = (Parent) fxmlLoader.load();          
		Screen2Controller controller = fxmlLoader.<Screen2Controller>getController();
		controller.setClient("asldjlk");
		Scene scene = new Scene(root); 

		stage.setScene(scene);    

		stage.show();    
	}



	void setPrevStage(Stage stage) {
		this.prevStage = stage;
	}



}
