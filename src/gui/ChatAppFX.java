/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author student
 */
public class ChatAppFX extends Application {
	public static String screen1ID = "main";
	public static String screen1File = "Login Screen.fxml";
	public static String screen2ID = "screen2";
	public static String screen2File = "Screen2.fxml";

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Login Screen.fxml"));
		Pane myPane = (Pane)myLoader.load();

		LoginScreenController controller = (LoginScreenController) myLoader.getController();

		controller.setPrevStage(primaryStage);

		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
		//        ScreensController mainContainer = new ScreensController();
		//        mainContainer.loadScreen(ChatAppFX.screen1ID, ChatAppFX.screen1File);
		//        
		//        mainContainer.loadScreen(ChatAppFX.screen2ID, ChatAppFX.screen2File);
		//        
		//        
		//        mainContainer.setScreen(ChatAppFX.screen1ID);
		//Group root = new Group();
		//root.getChildren().addAll(mainContainer);

		//        Parent root = FXMLLoader.load(getClass().getResource("Login Screen.fxml"));
		//        
		//        Scene scene = new Scene(root);
		//        stage.initStyle(StageStyle.UNDECORATED);
		//        stage.setScene(scene);
		//        stage.show();
		//        scene.setRoot(root);
		//        stage.setScene(scene);
		//        stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
