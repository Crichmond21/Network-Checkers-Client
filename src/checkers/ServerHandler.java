package checkers;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Main application to communicate with server and to set up framework JavaFX
 * 
 * @author Carter Richmond
 *
 */
public class ServerHandler {
	private static Socket s = null;
	private static DataInputStream dins = null;
	private static DataOutputStream douts = null;
	private static int ClientNum = 0;
	
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
			
			//Gets the client number from the server
			ClientNum = dins.readInt() - 100;
			System.out.println("ClientNum Assigned");

			
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static int getClientNum() {
		System.out.println("ClientNum Requested");
		return ClientNum;
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
	 * @throws InterruptedException 
	 */
	public static ArrayList<Integer> getBoard() {
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
				
				ArrayList<Integer> returnArr = new ArrayList<>();
				returnArr.add(initialRow);
				returnArr.add(initialColumn);
				returnArr.add(destinationRow);
				returnArr.add(destinationColumn);
				
				//Send status code back
				douts.writeInt(200);
				
				Thread.sleep(100);
				
				int returnCode = dins.readInt();
				System.out.println("Return Code:" + returnCode);
				
				if(returnCode == 215){
					for(int i = 0; i < dins.readInt(); i ++) {
						returnArr.add(dins.readInt());
					}
					douts.writeInt(200);
					
					Thread.sleep(100);
					
					dins.readInt();
				}
				
				
				return returnArr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//if exception caught or game state unchanged return null
		return null;
	}
}
