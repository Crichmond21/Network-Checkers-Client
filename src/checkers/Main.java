package checkers;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private static Socket cs = null;
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
			douts.writeUTF("movePiece");
			douts.flush();
			System.out.println("flushed");
			douts.writeInt(originalRow);
			douts.writeInt(originalColumn);
			douts.flush();
		}catch(Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
