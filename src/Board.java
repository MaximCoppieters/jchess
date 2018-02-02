
import java.util.List;


public class Board {
	private Square[][] board;
	private List<Piece> whitePieces;
	private List<Piece> blackPieces;
			
	public Board(List<Piece> whitePieces, List<Piece> blackPieces) {
		board = new Square[8][8];
		this.whitePieces = whitePieces;
		this.blackPieces = blackPieces;
	}

	public void drawBoard() {
		drawHorizontalCoordinates();
		drawFullLine();
		drawRows();
		drawHorizontalCoordinates();
	}

	private void drawRows() {
		int index = board.length;
		
		for(Square[] row : board) {
			System.out.print(index + " ");
			System.out.print("| ");
			for(Square square : row) {
				System.out.print(square.getOccupationCharacter() + " | ");
			}
			System.out.print(index);
			System.out.println();
			drawFullLine();		
			index--;
		}
	}

	private void drawFullLine() {
		System.out.print("  ");
		for(int i=0; i < board.length; i++) {
			System.out.print("----");
		}
		System.out.println("-");
	}

	private void drawHorizontalCoordinates() {
		System.out.print("   ");
		for(char horizontal = 'a'; horizontal <= 'h'; horizontal++) {
			System.out.print(" " + horizontal + "  ");
		}
		System.out.println();
	}
	
	public void initializeBoard() {
		for(int y=0; y < board.length; y++) {
			for(int x=0; x < board[y].length; x++) {
				board[y][x] = new Square(x, y);
			}
		}
	}
	
	public void placePieces() {
		int indexWhite=0;
		int indexBlack=0;
		
		for(int y=0; y < 2; y++) {
			for(Square square : board[y]) {
				square.setOccupyingPiece(whitePieces.get(indexWhite));
				++indexWhite;
			}
		}
		
		for(int y=6; y < board.length; y++) {
			for(Square square : board[y]) {
				square.setOccupyingPiece(blackPieces.get(indexBlack));
				++indexBlack;
			}
		}
	}
	
	public Square getSquareAtCoordinates(int x, int y) {
		if(isWithinBounds(x, y)) {
			return board[y][x];
		}
		return null;
	}
	
	private boolean isWithinBounds(int x, int y) {
		if((x >= board.length || x < 0) || (y >= board.length || y < 0)) return false;
		
		return true;
	}
	
}
