/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author student
 */
public class Screen2Controller implements Initializable {
ScreensController myController;
    @FXML
    private Button exitButton;
    @FXML
    private Hyperlink logoutButton;
    @FXML
    private TextField searchUsers;
    @FXML
    private ListView<?> userList;
    @FXML
    private TextField searchTask;
    @FXML
    private ListView<?> taskList;
    @FXML
    private TextField chatBox;
    @FXML
    private Button attachButton;
    @FXML
    private MenuItem gifButton;
    @FXML
    private MenuItem attachmentButton;
    @FXML
    private Button sendButton;
    @FXML
    private MenuItem translateButton;
    @FXML
    private TextArea chatView;
    public TextField primaryTextField;
//private Client client;
private boolean connected;
    @FXML
    private CheckBox Active;
    @FXML
    private TextArea nameView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void exitButtonPressed(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void logoutPressed(MouseEvent event) {
    }

    @FXML
    private void logoutButtonPressed(ActionEvent event) {
//        client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
          myController.setScreen(ChatAppFX.screen1ID);
    }

    @FXML
    private void enterPressedUser(KeyEvent event) {
    }

    @FXML
    private void userClicked(MouseEvent event) {
    }

    @FXML
    private void enterPressedTask(KeyEvent event) {
    }

    @FXML
    private void taskClicked(MouseEvent event) {
    }

    @FXML
    private void enterPressedChat(KeyEvent event) {
    }

    @FXML
    private void gifClicked(ActionEvent event) {
    }
 @FXML
    private void attaPressed(ActionEvent event) {
//        client = new Client("localhost", 8080, "Taha", this);
    }
    @FXML
    private void attachmentClicked(ActionEvent event) {
        
    }

    @FXML
    private void translatePressed(ActionEvent event) {
    }

    @FXML
    private void sendPressed(ActionEvent event) {
//        client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, chatBox.getText()));
        chatBox.setText("");
    }

//    @Override
//    public void setScreenParent(ScreensController screenPage) {
//myController = screenPage;      }
////    public void appendText(String string) {
////       chatView.appendText(string + "\n");
////    }
    void append(String str) {
        chatView.appendText("\n"+ str);
        chatView.selectPositionCaret(chatView.getText().length()-1);
    }
    
    void connectionFailed() {
        connected=false;
    }

    @FXML
    private void activePressed(ActionEvent event) {
        
    }
    

// to start the whole thing the server
	public static void main(String[] args) {
		
	}

}