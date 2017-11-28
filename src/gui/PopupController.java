/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author student
 */
public class PopupController implements Initializable {
    
  @FXML
    private ListView<String> onlineList;
  @FXML
    private Button tester;
    
    public ListView<String> primaryUserList;
  @FXML
    void onlineUserSelected(MouseEvent event) {
//    chatController.populateUserList("taha");
primaryUserList.getItems().add(onlineList.getSelectionModel().getSelectedItem());
    }
  @FXML
    void addRandomElement(ActionEvent event) {
        Random rand = new Random ();
    onlineList.getItems().add(Integer.toString(rand.nextInt(50)));

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
}
