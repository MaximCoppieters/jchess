import java.util.List;

public class Bishop extends Piece {

	public Bishop(Color color) {
		super(color);
	}

	@Override
	public char getShortNotation() {
		return 'B';
	}

	@Override
	public List<Square> getListOfMovableSquares(Square location, Board board) {
        listOfMovableSquares.clear();

		super.addMultipleSquaresFromDirection(location, board, Direction.UPLEFT);
		super.addMultipleSquaresFromDirection(location, board, Direction.UPRIGHT);
		super.addMultipleSquaresFromDirection(location, board, Direction.DOWNLEFT);
		super.addMultipleSquaresFromDirection(location, board, Direction.DOWNRIGHT);

		return listOfMovableSquares;
	}

	@Override
	public void addMultipleSquaresFromDirection(Square location, Board board, Direction direction) {

	}
}
