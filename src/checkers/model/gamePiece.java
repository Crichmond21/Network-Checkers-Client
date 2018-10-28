package checkers.model;

public class gamePiece {
	private String team = null;
	private boolean king = false;
	private boolean initialized = false;
	
	gamePiece(String team){
		this.team = team;
		initialized = true;
	}
	
	public String getTeam() {
		return team;
	}
	
	public boolean getKing() {
		return king;
	}
	
	public void king() {
		king = true;
	}
}
