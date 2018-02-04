import java.util.List;

public class Pawn extends Piece {

    public boolean firstMovement;

    public Pawn(Color color) {
        super(color);
        firstMovement = true;
    }

    @Override
    public char getShortNotation() {
        return 'P';
    }

    @Override
    public List<Square> getListOfMovableSquares(Square location, Board board) {
        addSquares(location, board);

        return listOfMovableSquares;
    }

    private void addSquares(Square location, Board board) {
        if (this.getColor() == Color.BLACK) {
            addSquaresBlack(location, board);
        } else {
            addSquaresWhite(location, board);
        }
    }

    private void addSquaresWhite(Square location, Board board) {
        Square squareInFront = null;
        squareInFront = board.
                getSquareAtCoordinates(location.getX(), location.getY() + Direction.DOWN.getChangeInY());
        if (squareInFront != null) {
            if (squareInFront.getOccupyingPiece() == null) {
                listOfMovableSquares.add(squareInFront);
                if (firstMovement == true) {
                    Square squareTwoStepsForward = board.
                            getSquareAtCoordinates(location.getX(), location.getY() + 2 * Direction.DOWN.getChangeInY());
                    if (squareTwoStepsForward.getOccupyingPiece() == null) {
                        listOfMovableSquares.add(squareTwoStepsForward);
                    }
                }
            }
        }

        Square squareCaptureDiagonallyLeft = board.
                getSquareAtCoordinates(location.getX() + Direction.DOWNLEFT.getChangeInX(),
                        location.getY() + Direction.DOWNLEFT.getChangeInY());
        Square squareCaptureDiagonallyRight = board.
                getSquareAtCoordinates(location.getX() + Direction.DOWNRIGHT.getChangeInX(),
                        location.getY() + Direction.DOWNRIGHT.getChangeInY());

        if (squareCaptureDiagonallyLeft != null) {
            addIfPieceAtSquareisOpposing(squareCaptureDiagonallyLeft);
        }
        if (squareCaptureDiagonallyRight != null) {
            addIfPieceAtSquareisOpposing(squareCaptureDiagonallyRight);
        }

    }

    private void addIfPieceAtSquareisOpposing(Square squareCaptureDiagonallyLeft) {
        if (squareCaptureDiagonallyLeft.getOccupyingPiece() != null
                && squareCaptureDiagonallyLeft.getOccupyingPiece().getColor() != this.getColor()) {
            listOfMovableSquares.add(squareCaptureDiagonallyLeft);
        }
    }

    private void addSquaresBlack(Square location, Board board) {
        Square squareInFront = null;
        squareInFront = board.getSquareAtCoordinates(location.getX(), location.getY() - 1);
        if (squareInFront != null) {
            if (squareInFront.getOccupyingPiece() == null) {
                listOfMovableSquares.add(squareInFront);
                if (firstMovement == true) {
                    Square squareTwoStepsForward = board.getSquareAtCoordinates(location.getX(), location.getY() - 2);
                    if (squareTwoStepsForward.getOccupyingPiece() == null) {
                        listOfMovableSquares.add(squareTwoStepsForward);
                    }
                }
            }
        }

        Square squareCaptureDiagonallyLeft = board.getSquareAtCoordinates(location.getX() - 1, location.getY() - 1);
        Square squareCaptureDiagonallyRight = board.getSquareAtCoordinates(location.getX() + 1, location.getY() - 1);

        if (squareCaptureDiagonallyLeft != null) {
            addIfPieceAtSquareisOpposing(squareCaptureDiagonallyLeft);
        }
        if (squareCaptureDiagonallyRight != null) {
            addIfPieceAtSquareisOpposing(squareCaptureDiagonallyRight);
        }
    }

    public void setFirstMovement(boolean firstMovement) {
        this.firstMovement = firstMovement;
    }
}
