package checkers;

import java.io.IOException;

import checkers.view.buttonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientApp extends Application {
	
	private Stage primaryStage;
	private AnchorPane homeScreen;
	
	@Override
	public void start(Stage primaryStage) {
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