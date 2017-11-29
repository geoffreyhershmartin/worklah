/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swarnibot.*;
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
        private CheckBox botCheckBox;
        @FXML
	private Hyperlink logOut;
        @FXML
        private Button attachButton2;
        @FXML
        private ImageView attachButton;
        @FXML
        private TextField conversantName;
        @FXML
        private TextField userName;
        @FXML
        private ImageView newChatIcon;
        @FXML
        private DatePicker dateSelector;
        
        
        private Client client;

	protected String userID;

	Stage prevStage;
        private Scene scene;
        private Stage stage;
        private boolean sassiSwitch;
        
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
//                userName.setText(userID);
	}    

	protected void setClient(Client _client) {
		client = _client;
	}
        protected void setUserID(String _userID) {
		userID = _userID;
                userName.setText(userID);
                
	}

	@FXML
	private void userClicked(MouseEvent event) {
            conversantName.setText(userList.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void taskDragged(MouseEvent event) {
            int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
            String theTask= taskList.getItems().get(selectedIndex);
            taskList.getSelectionModel().clearSelection();
            taskList.getItems().remove(theTask);
//            taskList.getItems().remove(selectedIndex+1);
	}
        
	@FXML
	private void taskClicked(MouseEvent event) {
            
	}
        
        @FXML
        void newChatPressed(MouseEvent event) throws IOException {
                
	
		stage = new Stage();


		FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Popup.fxml"));     
		AnchorPane frame = fxmlLoader.load();          
		PopupController controller = (PopupController)fxmlLoader.getController();
                controller.primaryUserList = userList;
                controller.setClient(this.client);
		
    		client.getOnlineUsers();

                scene = new Scene(frame); 
		stage.setScene(scene);
		PopupController popupController;
                popupController = new PopupController();

		
		stage.setMinHeight(300);
		stage.setMinWidth(500);
		stage.show();
                
		
        }

	@FXML
        void botChecked(ActionEvent event) {
            chatView.setText("");
                chatView2.setText("");
//        swarniSwitch = true;
//        System.out.print("set to true");
        }

	
	@FXML
	void logoutPressed(ActionEvent event) {
		System.exit(0);
	}
        
	@FXML
	private void sendPressed(MouseEvent event) throws InterruptedException {
            String message = chatBox.getText();
            if (botCheckBox.isSelected()){
                
                if (message.contains("why")){
                    append2(message);
                    TimeUnit.SECONDS.sleep(1);
                    append("Because you fell as a child");
                }
                else if (message.contains("how")){
                    append2(message);
                    TimeUnit.SECONDS.sleep(1);
                    append("How would I know? Aren't you the 'smarter' one, human?");}
                else if (message.contains("who")){
                    append2(message);
                    TimeUnit.SECONDS.sleep(1);
                    append("Yo Mama!");}
            }
            else{
		String catchPhrase = "@task";
		if (message.contains(catchPhrase)) {
			String task = message.replace("@task", "");
			taskList.getItems().add(task);
            chatBox.setText("");
            client.sendTaskToGroup(message);
		}
		else{
			client.sendMessageToGroup(message);
                        append2(chatBox.getText());
		}

		chatBox.setText("");
        }
        }

        @FXML
        void dateSelected(ActionEvent event) {
            int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
            String theTask = taskList.getSelectionModel().getSelectedItem();
            String selectedDate = dateSelector.getValue().toString();
            taskList.getItems().add(selectedIndex, theTask +" due by " + selectedDate );
            taskList.getItems().remove(selectedIndex+1);
        }
	@FXML
	private void enterPressedChat(ActionEvent event) throws InterruptedException {
		String message = chatBox.getText();
                if (botCheckBox.isSelected()){
                SwarniMain sassiAnswer = new SwarniMain();
//                if (message.contains("why")){
                    append2(message);
//                    append(sassiAnswer.getResponse());
//                }
//                else if (message.contains("how")){
//                    append2(message);
//                    append(sassiAnswer.getHow());
//                }
//                else if (message.contains("what")){
//                    append2(message);
//                    append("Yo Mama!");
//                    append(sassiAnswer.getResponse(message));
            }
            else{
		String catchPhrase = "@task";
		if (message.contains(catchPhrase)) {
			String task = message.replace("@task", "");
			taskList.getItems().add(task);
            chatBox.setText("");
            client.sendTaskToGroup(message);
		}
		else{
			client.sendMessageToGroup(message);
                        append2(chatBox.getText());
		}

		chatBox.setText("");

	}
        }
        @FXML
        void attachButtonPressed(MouseEvent event) {
            System.out.print(userID);
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
            timeStamp = new SimpleDateFormat("HH:m"
                    + "m ").format(Calendar.getInstance().getTime());
		chatView2.appendText(str + " " + timeStamp+ "\n");
		chatView2.selectPositionCaret(chatView2.getText().length()-1);
		chatView.appendText("\n");
		chatView.selectPositionCaret(chatView.getText().length()-1);
                chatBox.setText("");
	}

//	public void setID(String _userID){
//		this.searchTask.setText(_userID);
//	}
        public void populateUserList(String _user){
            if(userList.getItems().contains(_user)){
            String notification = _user;
            userList.getItems().remove(_user);
            userList.getItems().add(_user+"Read Please!");}
            userList.getItems().add(_user);
        }
        
        public void populateTaskList(String _task){
            taskList.getItems().add(_task);
        }


//        public void redirectHome(Stage stage, String name) {
//            stage.setScene(scene);
//            searchTask.setText(name);
//            stage.hide();
//            stage.show();
//        }
	void connectionFailed() {
		//        boolean connected = false;
	}
        public void loadHistory (ArrayList<String> string){
            int size = string.size();
            for (int i = 0; i<size;i++){
                if(string.get(i).contains(userID)){
                    append2(string.get(i));
                } else{
                    append(string.get(i));
                }
            }
        }
        
//        public void loadHistory(ArrayList<String>){
//            
//        }
}
