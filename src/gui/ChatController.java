/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author student
 */
public class ChatController implements Initializable {

	@FXML
	private ListView<String> userList;
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
        private Button sendButton2;
        @FXML
        private Button newChat;
	@FXML
	private Hyperlink logOut;
        @FXML
        private Button attachButton2;
        @FXML
        private ImageView attachButton;
	private Client client;

	protected String userID;

	Stage prevStage;
    private Scene scene;
    private Stage stage;
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}    

	protected void setClient(Client _client) {
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
          @FXML
        void newChatPressed(ActionEvent event) throws IOException {
                
	
		stage = new Stage();


		FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Popup.fxml"));     
		AnchorPane frame = fxmlLoader.load();          
		PopupController controller = (PopupController)fxmlLoader.getController();
                controller.primaryUserList = userList;
		
		
		
                scene = new Scene(frame); 
		stage.setScene(scene);
		PopupController popupController;
                popupController = new PopupController();

		
		stage.setMinHeight(300);
		stage.setMinWidth(500);
		stage.show();
                
		
        }

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
		if (message.contains(catchPhrase)) {

			String task = message.replace("@task", "");
			taskList.getItems().add(task);
                        chatBox.setText("");
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

	@FXML
	private void enterPressedChat(ActionEvent event) {
		String message = chatBox.getText();
		String catchPhrase = "@task";
		if (message.contains(catchPhrase)) {

			String task = message.replace("@task", "");
			taskList.getItems().add(task);
                        chatBox.setText("");
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
        @FXML
        void attachButtonPressed(MouseEvent event) {

        }

	public void append(String str) {
            String timeStamp;
            timeStamp = new SimpleDateFormat("HH:mm ").format(Calendar.getInstance().getTime());
		chatView.appendText(timeStamp + " " + str  +  "\n");
		chatView.selectPositionCaret(chatView.getText().length()-1);
		chatView2.appendText("\n");
		chatView2.selectPositionCaret(chatView2.getText().length()-1);
                chatBox.setText("");
	}
	public void append2(String str) {
            String timeStamp;
            timeStamp = new SimpleDateFormat("HH:mm ").format(Calendar.getInstance().getTime());
		chatView2.appendText(str + " " + timeStamp+ "\n");
		chatView2.selectPositionCaret(chatView2.getText().length()-1);
		chatView.appendText("\n");
		chatView.selectPositionCaret(chatView.getText().length()-1);
                chatBox.setText("");
	}

	public void setID(String _userID){
		this.searchTask.setText(_userID);
	}
        public void populateUserList(String _user){
            this.userList.getItems().add(_user);
        }

        public void redirectHome(Stage stage, String name) {
            stage.setScene(scene);
            searchTask.setText(name);
            stage.hide();
            stage.show();
        }
	void connectionFailed() {
		//        boolean connected = false;
	}
}