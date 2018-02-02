import java.util.ArrayList;
import java.util.List;

public class Player {
	private Color color;
	private List<Piece> pieces;
	private boolean isPlayerTurn;
	private static final int PAWN_COUNT = 8;
	
	public Player(Color color) {
		this.color = color;
		pieces = new ArrayList<>();
		generatePieces();
	}
	
	
	private void generatePieces() {
		System.out.println(this.color);
		if(this.color == Color.BLACK) {
			for(int i=0; i < PAWN_COUNT; i++) {
				pieces.add(new Pawn(color));
			}
			pieces.add(new Rook(color));
			pieces.add(new Knight(color));
			pieces.add(new Bishop(color));
			pieces.add(new Queen(color));
			pieces.add(new King(color));
			pieces.add(new Bishop(color));
			pieces.add(new Knight(color));
			pieces.add(new Rook(color));
		} else {
			pieces.add(new Rook(color));
			pieces.add(new Knight(color));
			pieces.add(new Bishop(color));
			pieces.add(new Queen(color));
			pieces.add(new King(color));
			pieces.add(new Bishop(color));
			pieces.add(new Knight(color));
			pieces.add(new Rook(color));
			for(int i=0; i < PAWN_COUNT; i++) {
				pieces.add(new Pawn(color));
			}
		}
	}
	
	public List<Piece> getPieces(){
		return pieces;
	}
	
	public boolean isKingCaptured() {
		if(getKingPiece().isCaptured()) return true;
		
		return false;
	}
	
	private King getKingPiece() {
		for(Piece piece : pieces) {
			if(piece.getClass().getSimpleName().equals("King")) return (King) piece;
		}
		
		throw new IllegalArgumentException("Koning niet gevonden");
	}
	
	public void setPlayerTurn(boolean isPlayerTurn) {
		this.isPlayerTurn = isPlayerTurn;
	}
	
	public boolean getPlayerTurn() {
		return isPlayerTurn;
	}
	
	public Color getColor() {
		return color;
	}
}
