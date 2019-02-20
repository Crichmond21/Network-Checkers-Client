package checkers.model;

import checkers.model.gamePiece;

/**
 * 
 * OBSELETE CLASS
 * 
 * 
 * 
 * 
 * @author Carter Richmond
 *
 */
public class GameBoard {
	private gamePiece[][] board = new gamePiece[8][8];
	
	public GameBoard(){
		//Place red pieces on board
		for(int i = 0; i < 3; i ++) {
			for(int j = ((i+1)%2); j < 8; j += 2) {
				addPiece(new gamePiece("red", i, j), i, j);
			}
		}
		
		for(int i = 5; i < 8; i++) {
			for(int j = ((i+1)%2); j < 8; j += 2) {
				addPiece(new gamePiece("black", i, j), i, j);
			}
		}
	}
	
	public void changeBoard(gamePiece[][] newState){
		this.board = newState;
	}
	
	/**
	 *  Adds pieces in specified row and collumn
	 * @param piece game piece to add
	 * @param row row to add in
	 * @param column column to add in
	 */
	private boolean addPiece(gamePiece piece, int row, int column) {
		if (board[row][column] == null) {
			board[row][column] = piece;
			return true;
		}else {
			return false;
		}
	}
	
}
