package checkers.model;

public class gamePiece {
	private String team = null;
	private boolean king = false;
	private boolean initialized = false;
	private int row;
	private int column;
	
	/**
	 * Initializes game piece with String team
	 * @param team String of red or black to set which team the piece is on
	 */
	gamePiece(String team, int row, int column){
		this.row = row;
		this.column = column;
		this.team = team;
		initialized = true;
	}
	
	/**
	 * Gets team 
	 * @return team color as a string
	 */
	public String getTeam() {
		if(initialized) {
			return team;
		}else {
			throw new SecurityException("Game Piece not Initialized");
		}
	}
	
	/**
	 * Gets king status
	 * @return boolean status of king
	 */
	public boolean getKing() {
		if(initialized) {
			return king;
		}else {
			throw new SecurityException("Game Piece not Initialized");
		}
	}
	
	/**
	 * Sets piece to be king
	 */
	public void king() {
		king = true;
	}
	
	public int[] getPosition() {
		int[] temp = {row, column};
		return temp;
	}
	
	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {
		if(initialized) {
			return team;
		}else {
			return null;
		}

	}
}
