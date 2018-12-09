package checkers;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Main application to communicate with server and to set up framework JavaFX
 * 
 * @author Carter Richmond
 *
 */
public class Main extends Application {
	private static Socket s = null;
	private static DataInputStream dins = null;
	private static DataOutputStream douts = null;
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
	
	/**
	 * Connects to server under given ip address and port number
	 * @param ip ip address in plain text dotted decimal notation ("127.0.0.1")
	 * @param port integer port number to connect to
	 */
	public static void connectToServer(String ip, int port) {
		try {
			//Open Socket to server on specified port
			s = new Socket(ip, port);
			
			//Get input and output datastreams from server
			dins = new DataInputStream(s.getInputStream());
			douts = new DataOutputStream(s.getOutputStream());
			
			//Print out status connection code from server
			System.out.println(dins.readInt());
			
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Sends a the selected piece movement to the server
	 * @param originalRow
	 * @param originalColumn
	 */
	public static void selectPiece(int originalRow, int originalColumn) {
		try {
			douts.flush();
			//Send code "selectPiece"
			douts.writeUTF("selectPiece");
			//Send row and column
			douts.writeInt(originalRow);
			douts.writeInt(originalColumn);
			douts.flush();

			//Prints status code from sending piece
			System.out.println(dins.readInt());
			
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void checkDINs() {
		try {
			System.out.println(dins.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends destination movement spot for the previously selected piece
	 * @param destinationRow
	 * @param destinationColumn
	 * @return true if move is valid and move performed
	 */
	public static boolean selectMovementSpot(int destinationRow, int destinationColumn) {
		try {
			douts.flush();
			//send code "selectMovementSpot"
			douts.writeUTF("selectMovementSpot");
			//Send destination row and column of piece
			douts.writeInt(destinationRow);
			douts.writeInt(destinationColumn);
			douts.flush();
				
			//read and print code given from server
			int code = dins.readInt();
			System.out.println(code);
			
			//if code equals valid move return true
			if(code == 200) {
				return true;
			}
			//if code is not valid return false
			if(code == 300) {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//if code is not 200 or 300 return false as well
		return false;
	}
	
	/**
	 * Get the status of last moved piece from opponent 
	 * @return integer array of initial position to destination position
	 */
	public static int[] getBoard() {
		try {
			//send command get game state
			douts.writeUTF("getGameState");
			douts.flush();
			
			//get status code returned
			int code = dins.readInt();
			//if code is 210 (game state changed) then recieve the positions from the server
			if(code == 210) {
				//get the 4 ints from the server
				int initialRow = dins.readInt();
				int initialColumn = dins.readInt();
				int destinationRow = dins.readInt();
				int destinationColumn = dins.readInt();
				
				//store the positions in the array and return the array
				int[] returnArr = {initialRow, initialColumn, destinationRow, destinationColumn};
				return returnArr;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//if exception caught or game state unchanged return null
		return null;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
