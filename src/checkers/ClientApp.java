package checkers;

import java.io.IOException;

import checkers.view.BoardRefresh;
import checkers.view.buttonController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
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
	private GridPane grid;
	
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
			
			//Load controller
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
	        
	        //get list of all elements on the homeScreen
			ObservableList<Node> temp = homeScreen.getChildren();
			
			//Create new thread to check for updates
			grid = (GridPane)temp.get(0);
			Thread br = new BoardRefresh(grid, this);
			br.start();
	        
	        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(event -> {
			    br.interrupt();
			    br.interrupt();
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves piece in grid pane for client sync
	 * @param temp piece to move
	 * @param dr destination row
	 * @param dc destination column
	 */
	public void moveGridPiece(Circle temp, int dr, int dc) {
		//Run move on the JavaFX thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {				
				//move piece
				grid.getChildren().remove(temp);
				grid.add(temp, dc, dr);
			}
		});
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
