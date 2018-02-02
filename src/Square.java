
public class Square {
	private int x;
	private int y;
	private Piece pieceOccupying;
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public char getOccupationCharacter() {
		if(pieceOccupying == null) {
			return ' ';
		} else {
			return pieceOccupying.getShortNotation();
		}
	}
	
	public void setOccupyingPiece(Piece p) {
		pieceOccupying = p;
	}
	
	public Piece getOccupyingPiece() {
		return pieceOccupying;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return "x: " + x + " - y: " + y;
	}

}
