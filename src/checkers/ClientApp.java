package checkers;

import java.io.IOException;

import checkers.view.BoardRefresh;
import checkers.view.buttonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientApp extends Application {
	
	private Stage primaryStage;
	private AnchorPane homeScreen;
	private buttonController controller;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Network Checkers");
	
		showHomeScreen();
	}
	
	public void moveGridPieces(Node temp, int dr, int dc) {
		System.out.println(temp);
		System.out.println(controller.grid);
		//TODO: FIX THIS NULL POINTER EXCEPTION GRID DOESNT POINT TO ANYTHING
		controller.grid.getChildren().remove(temp);
		controller.grid.add(temp, dc, dr);
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
	
	public void switchScene(String fxmlFile) {
		try {
			//Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxmlFile));
			homeScreen = (AnchorPane) loader.load();
			
			Object[] temp = homeScreen.getChildren().toArray();
			System.out.println(temp[0].getClass().toGenericString());
			
			buttonController controller = loader.getController();
	        controller.setMainApp(this);
	        
	        //Show the scene containing the homescreen
	        Scene scene = new Scene(homeScreen);
	        
	        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Thread.sleep(1000);
			
			Thread br = new BoardRefresh((GridPane) temp[0], this);
			br.start();
			
		}catch(IOException | InterruptedException e) {
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
