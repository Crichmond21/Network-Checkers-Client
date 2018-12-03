package checkers.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import checkers.ClientApp;
import checkers.Main;

public class buttonController {
	@FXML
	private Button hostGame;
	@FXML
	private Button joinGame;
	@FXML
	private Button connectToServer;
	@FXML
	private Button back;
	@FXML
	private TextField ipAddress;
	@FXML
	private VBox hostJoinBox;
	@FXML
	private VBox joinBox;
	@FXML
	public GridPane grid;
	
	private ClientApp app;
	
	private Circle currentCircle;
	
	public buttonController() {}
	
	@FXML
	public void initialize() {
		
	}
	
	public void setMainApp(ClientApp app) {
		this.app = app;
	}
	
	@FXML
	private void hostGame(ActionEvent event) throws InterruptedException{
		
		try {			
			//Run Server From Command Line
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"java -jar CheckersServer.jar\""); 
			
			Thread.sleep(500);
			
			//Change Screen
			app.switchScene("view/PlayScreen.fxml");
			
			Main.connectToServer("localhost", 7065);
			
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	@FXML
	private void joinGame(ActionEvent event) {
		//hides main menu options and replaces them with ip addreess filed and join button
		hostJoinBox.setVisible(false);
		joinBox.setVisible(true);
	}
	
	@FXML
	private void back(ActionEvent event) {
		//hides join menu and goes back to main menu
		joinBox.setVisible(false);
		hostJoinBox.setVisible(true);
	}
	
	@FXML
	private void connectToServer(ActionEvent event) {
		//Change screen
		app.switchScene("view/PlayScreen.fxml");
		
		//Call main to connect to server
		try {
			Main.connectToServer(ipAddress.getText(), 7065);
			
			Thread.sleep(1000);
			System.out.println(grid);
			
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
	}
	
	@FXML
	private void movePiece(MouseEvent event) throws InterruptedException {
		
		//Create temp circle object from board
		Shape temp = (Shape) event.getSource();
		
		//Get position in grid pane
		Integer row = (Integer)temp.getProperties().get("gridpane-row");
		Integer column = (Integer)temp.getProperties().get("gridpane-column");

		//Convert from null to zero if on edge
		if(row == null) {
			row = 0;
		}else if(column == null) {
			column = 0;
		}
		
		if(temp.getClass().equals(Circle.class)) {
			currentCircle = (Circle) temp;
			Main.selectPiece(row, column);
		}
		if(temp.getClass().equals(Rectangle.class)) {
			if(Main.selectMovementSpot(row, column)) {
				grid.getChildren().remove(currentCircle);
				grid.add(currentCircle, column, row);
			}	
		}
	}
}
