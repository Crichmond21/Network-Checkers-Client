package checkers;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import checkers.model.GameBoard;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	private static Socket cs = null;
	private static DataInputStream dins = null;
	private static DataOutputStream douts = null;
	private static GameBoard game = new GameBoard();
	private static GridPane grid;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void connectToServer(String ip, int port) {
		try {
			//Open Socket to server
			cs = new Socket("localhost", 7065);
			
			
			dins = new DataInputStream(cs.getInputStream());
			douts = new DataOutputStream(cs.getOutputStream());
			
			System.out.println(dins.readInt());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void selectPiece(int originalRow, int originalColumn) {
		try {
			douts.writeUTF("selectPiece");
			douts.flush();
			douts.writeInt(originalRow);
			douts.writeInt(originalColumn);
			douts.flush();
			
			dins.readInt();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static boolean selectMovementSpot(int destinationRow, int destinationColumn) {
		try {
			douts.writeUTF("selectMovementSpot");
			douts.flush();
			douts.writeInt(destinationRow);
			douts.writeInt(destinationColumn);
			douts.flush();
			
			//Thread.sleep(100);
						
			int code = dins.readInt();
			System.out.println(code);
			if(code == 200) {
				return true;
			}
			if(code == 300) {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	public static int[] getBoard() {
		try {
			//send command get game state
			douts.writeUTF("getGameState");
			douts.flush();
			
			//get status code returned
			int code = dins.readInt();
			if(code == 210) {
				//get the 4 ints from the server
				int initialRow = dins.readInt();
				int initialColumn = dins.readInt();
				int destinationRow = dins.readInt();
				int destinationColumn = dins.readInt();
				
				//return array of ints
				int[] returnArr = {initialRow, initialColumn, destinationRow, destinationColumn};
				return returnArr;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//if exception caught return null
		return null;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
