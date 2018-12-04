package checkers.view;

import checkers.ClientApp;
import checkers.Main;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;


/**
 * 
 * @author richmondc1
 *
 */
public class BoardRefresh extends Thread{
	private int[] moves = null;
	private GridPane grid;
	private ClientApp app;
		
	public BoardRefresh(GridPane newGrid, ClientApp app){
		grid = newGrid;
		System.out.println(grid);
		this.app = app;
	}
	//
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			moves = Main.getBoard();
			
			if(checkState()) {
				int[] temp = getMoves();
				moveOpPiece(temp[0], temp[1], temp[2], temp[3]);
			}
		}	
	}
	
	public boolean checkState() {
		if(moves == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public int[] getMoves() {
		if(checkState()) {
			int[] temp = moves;
			moves = null;
			return temp;
		}else {
			return moves;
		}
	}
	
	/**
	 * Gets desired node from girdpane
	 * @param gridPane grid to search
	 * @param col column
	 * @param row row
	 * @return Desired node in pane
	 */
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		//Get list of nodes
		ObservableList<Node> temp = gridPane.getChildren();
		
		//Loop through list and find node
	    for (int i = 0; i < temp.size(); i ++) {
	    	Node tempNode = temp.get(i);
	    	
	    	//Get column and row of temp node
	    	Integer colIndex = GridPane.getColumnIndex(tempNode);
	    	Integer rowIndex = GridPane.getRowIndex(tempNode);
	    	
	    	//acount for null = 0
	    	if(colIndex == null) {
	    		colIndex = 0;
	    	}else if(rowIndex == null){
	    		rowIndex = 0;
	    	}
	    	
	    	//if node is correct return the node
	        if (colIndex == col && rowIndex == row && tempNode.getClass().equals(Circle.class)) {
	            return tempNode;
	        }
	    }
	    //return null if nothing found
	    return null;
	}
	
	
	/**
	 * Move piece in gridpane
	 * @param ir initial row
	 * @param ic initial column
	 * @param dr destination row
	 * @param dc destination column
	 */
	private void moveOpPiece(int ir, int ic, int dr, int dc) {
		//get cirlce from gridpane
		Circle temp = (Circle)getNodeFromGridPane(grid, ic, ir);
		
		//if no circle then return without anything
		if(temp == null) {
			return;
		}else {
			//call client app to move piece
			app.moveGridPiece(temp, dr, dc);
			//update moves to null
			moves = null;
		}	
	}
}
