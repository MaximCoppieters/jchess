import java.util.List;

public class King extends Piece {
	
	public King(Color color) {
		super(color);
	}

	@Override
	public char getShortNotation() {
		return 'K';
	}

	@Override
	public List<Square> getListOfMovableSquares(Square location, Board board) {
		listOfMovableSquares.clear();

		super.addOneSquareFromDirection(location, board, Direction.DOWN);
		super.addOneSquareFromDirection(location, board, Direction.UP);
		super.addOneSquareFromDirection(location, board, Direction.LEFT);
		super.addOneSquareFromDirection(location, board, Direction.RIGHT);

		return listOfMovableSquares;
	}
}
