import java.util.List;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public char getShortNotation() {
        return 'R';
    }

    @Override
    public List<Square> getListOfMovableSquares(Square location, Board board) {
        listOfMovableSquares.clear();

        super.addMultipleSquaresFromDirection(location, board, Direction.LEFT);
        super.addMultipleSquaresFromDirection(location, board, Direction.RIGHT);
        super.addMultipleSquaresFromDirection(location, board, Direction.UP);
        super.addMultipleSquaresFromDirection(location, board, Direction.DOWN);

        return listOfMovableSquares;
    }

}
