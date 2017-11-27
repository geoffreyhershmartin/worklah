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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author student
 */
public class ChatController implements Initializable {

    @FXML
    private ListView<?> userList;
    @FXML
    private ListView<String> taskList;
    @FXML
    private TextArea chatView;
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
    private ChatClient client;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
  @FXML
    private void logOut(MouseEvent event) {
        System.exit(0);
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
    private void sendPressed(MouseEvent event) {
        
        String message = chatBox.getText();
        String catchPhrase = "@task";
        if( message.contains(catchPhrase)){
        taskList.getItems().add(message.replace("@task", ""));
        }
    		client.broadcastMessageToGroup(chatBox.getText());

        chatBox.setText("");
        

    }

    @FXML
    private void enterPressedChat(ActionEvent event) {
        String message = chatBox.getText();
        String catchPhrase = "@task";
        if( message.contains(catchPhrase)){
        taskList.getItems().add(message.replace("@task", ""));
        }
        else{
    		client.broadcastMessageToGroup(chatBox.getText());
                String name;
                LoginScreenController loginScreenController = new LoginScreenController();
            name = loginScreenController.getID();
                append(name + ": " + message);}
        chatBox.setText("");
    }
    
    public void append(String str) {
        chatView.appendText(str + "\n");
        chatView.selectPositionCaret(chatView.getText().length()-1);
    }
    
     void connectionFailed() {
//        boolean connected = false;
    }
}