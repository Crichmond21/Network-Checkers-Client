package checkers;

import java.io.IOException;

import checkers.view.buttonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main application Java fx layout for Network Checkers Client
 * 
 * @author Carter Richmond
 *
 */
public class ClientApp extends Application {
	
	//Instance variables for client app
	private Stage primaryStage;
	private AnchorPane homeScreen;
	private buttonController controller;
	
	@Override
	public void start(Stage primaryStage) {
		//Set stage and title
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Network Checkers");
	
		showHomeScreen();
	}
	
	
	/**
	 * Shows the homescreen
	 */
	public void showHomeScreen() {
		try {
			//Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
			homeScreen = (AnchorPane) loader.load();
			
			controller = loader.getController();
	        controller.setMainApp(this);
	        
	        //Show the scene containing the homescreen
	        Scene scene = new Scene(homeScreen);
	        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Switches main scene to a new fxmlFile
	 * Loads new controller as well if needed
	 * @param fxmlFile File to load into screen
	 */
	public void switchScene(String fxmlFile) {
		try {
			//Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxmlFile));
			homeScreen = (AnchorPane) loader.load();

			//Load new buttonController from FXML file
			buttonController controller = loader.getController();
	        controller.setMainApp(this);
	        
	        //Show the scene containing the homescreen
	        Scene scene = new Scene(homeScreen);
	        
	        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
