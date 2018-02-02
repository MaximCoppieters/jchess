import java.util.List;

public class Queen extends Piece {

	public Queen(Color color) {
		super(color);
	}

	@Override
	public char getShortNotation() {
		return 'Q';
	}

	@Override
	public List<Square> getListOfMovableSquares(Square location, Board board) {
        listOfMovableSquares.clear();

        super.addMultipleSquaresFromDirection(location, board, Direction.LEFT);
        super.addMultipleSquaresFromDirection(location, board, Direction.RIGHT);
        super.addMultipleSquaresFromDirection(location, board, Direction.UP);
        super.addMultipleSquaresFromDirection(location, board, Direction.DOWN);
        super.addMultipleSquaresFromDirection(location, board, Direction.UPLEFT);
        super.addMultipleSquaresFromDirection(location, board, Direction.UPRIGHT);
        super.addMultipleSquaresFromDirection(location, board, Direction.DOWNLEFT);
        super.addMultipleSquaresFromDirection(location, board, Direction.DOWNRIGHT);

        return listOfMovableSquares;
	}


}
