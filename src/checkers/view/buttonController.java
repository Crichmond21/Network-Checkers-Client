package checkers.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

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
	private void hostGame(ActionEvent event) throws InterruptedException{
		
		try {
			//Run Server From Command Line
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"java -jar CheckersServer.jar\""); 
			
			Thread.sleep(1000);
			
			//Change Screen
			hostJoinBox.setVisible(false);
			
			//Open Socket to server
			Socket cs = new Socket("localhost", 7065);
			
			DataInputStream dins = new DataInputStream(cs.getInputStream());
			DataOutputStream douts = new DataOutputStream(cs.getOutputStream());
	
			
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
		joinBox.setVisible(false);
		
		try {
			//Open Socket to server
			Socket cs = new Socket(ipAddress.getText(), 7065);
			
			DataInputStream dins = new DataInputStream(cs.getInputStream());
			DataOutputStream douts = new DataOutputStream(cs.getOutputStream());
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
	}
	
}
