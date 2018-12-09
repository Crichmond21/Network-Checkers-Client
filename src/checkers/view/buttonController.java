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

import java.util.Iterator;

import checkers.ClientApp;
import checkers.Main;

/**
 * Button Controller for FXML files "MainMenu" and "PlayScreen"
 * 
 * @author Carter Richmond
 *
 */
public class buttonController {
	/**
	 * Instance variables for the JavaFX elements on screen
	 */
	@FXML
	private GridPane grid;
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
	
	private ClientApp app;
	private Circle currentCircle;
	
	/**
	 * Default Constructor
	 */
	public buttonController() {}
	
	@FXML
	public void initialize() {}
	
	/**
	 * Sets Main app to ClientApp
	 * @param app ClientApp
	 */
	public void setMainApp(ClientApp app) {
		this.app = app;
	}
	
	/**
	 * Getter method for gridpane
	 * @return gridpane
	 */
	public GridPane getGrid() {
		return grid;
	}
	
	/**
	 * Opens server (on windows) in a command line and connects to server
	 * @param event Button Click Event
	 */
	@FXML
	private void hostGame(ActionEvent event){
		
		try {			
			//Run Server From Command Line
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"java -jar CheckersServer.jar\""); 
			
			//Give server time to start before connecting
			Thread.sleep(500);
			
			//Change Screen to play screen
			app.switchScene("view/PlayScreen.fxml");
			
			//Establish connection with server
			Main.connectToServer("localhost", 7065);
			
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	/**
	 * Swap Two pieces in the grid pane
	 * @param ir initial row
	 * @param ic initial column
	 * @param dr destination row
	 * @param dc destination column
	 */
	public void swapPieces(int ir, int ic, int dr, int dc) {
		//get iterator of objects from grid pane
		//System.out.println(grid);
		Iterator<Node> gridObjects = grid.getChildren().listIterator();
		
		Circle piece = null;
		
		while(gridObjects.hasNext()) {
			//Create temp node object
			Node temp = gridObjects.next();
			
			//Get position in grid pane
			Integer row = (Integer)temp.getProperties().get("gridpane-row");
			Integer column = (Integer)temp.getProperties().get("gridpane-column");
			
			//If object is a circle at the specified point then store object in Circle piece
			if(temp.getClass().equals(Circle.class) && row == ir && column == ic) {
				piece = (Circle)temp;
			}
		}
		
		if(piece != null) {
			//Move piece to destination
			grid.getChildren().remove(piece);
			grid.add(piece, dc, dr);
		}
	}
	
	/**
	 * Switches to mini join connect screen
	 * @param event Button Click Event
	 */
	@FXML
	private void joinGame(ActionEvent event) {
		//hides main menu options and replaces them with ip addreess filed and join button
		hostJoinBox.setVisible(false);
		joinBox.setVisible(true);
	}
	
	/**
	 * Takes user back to main menu
	 * @param event Button Click Event
	 */
	@FXML
	private void back(ActionEvent event) {
		//hides join menu and goes back to main menu
		joinBox.setVisible(false);
		hostJoinBox.setVisible(true);
	}
	
	/**
	 * Connects to server when join server is hit
	 * @param event Button Click Event
	 */
	@FXML
	private void connectToServer(ActionEvent event) {
		//Change screen
		app.switchScene("view/PlayScreen.fxml");
		
		try {
			//Call main to connect to server already established server with default port and ip plain text
			Main.connectToServer(ipAddress.getText(), 7065);
			Thread.sleep(500);
			
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	/**
	 * Called when any spot on the board is pressed. If Checker piece then set the movable piece, if open spot try to move the piece
	 * @param event Mouse Click Event
	 */
	@FXML
	private void movePiece(MouseEvent event){
		
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
		
		//If class is circle then call select piece
		if(temp.getClass().equals(Circle.class)) {
			currentCircle = (Circle) temp;
			Main.selectPiece(row, column);
		}
		//if main class is rectangle then try to move selected piece to spot
		if(temp.getClass().equals(Rectangle.class)) {
			if(Main.selectMovementSpot(row, column)) {
				grid.getChildren().remove(currentCircle);
				grid.add(currentCircle, column, row);
			}	
		}
	}
}
