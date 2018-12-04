package checkers.view;

import checkers.ClientApp;
import checkers.Main;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;


/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * OBSELETE CLASS
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
				Thread.sleep(2000);
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
	
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		ObservableList<Node> temp = gridPane.getChildren();
		
	    for (int i = 0; i < temp.size(); i ++) {
	    	Node tempNode = temp.get(i);
	    	
	    	Integer colIndex = GridPane.getColumnIndex(tempNode);
	    	Integer rowIndex = GridPane.getRowIndex(tempNode);
	    	
	    	if(colIndex == null) {
	    		colIndex = 0;
	    	}else if(rowIndex == null){
	    		rowIndex = 0;
	    	}
	    	
	    	System.out.println(tempNode.getClass().toString());
	    	System.out.println(colIndex);
	    	System.out.println(col);
	    	System.out.println(rowIndex);
	    	System.out.println(row);
	    	System.out.println(tempNode.getClass().equals(Circle.class));
	    	
	        if (colIndex == col && rowIndex == row && tempNode.getClass().equals(Circle.class)) {
	            return tempNode;
	        }
	    }
	    return null;
	}
	
	private void moveOpPiece(int ir, int ic, int dr, int dc) {
		Circle temp = (Circle)getNodeFromGridPane(grid, ic, ir);
		
		if(temp == null) {
			return;
		}else {
			//app.moveGridPieces(temp, dr, dc);
		}	
	}
}
