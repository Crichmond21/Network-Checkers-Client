package checkers.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import checkers.ClientApp;
import checkers.model.gamePiece;

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
	
	private ClientApp app;
	
	public buttonController() {}
	
	@FXML
	public void initialize() {
		
	}
	
	public void setMainApp(ClientApp app) {
		this.app = app;
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
}
