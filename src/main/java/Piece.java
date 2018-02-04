import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece implements Movable, Serializable {
    private Color color;
    private boolean captured;
    public List<Square> listOfMovableSquares;
    private Board gameBoard;
    private Square squareLocatedOn;

    public Piece(Color color) {
        listOfMovableSquares = new ArrayList<>();
        this.color = color;
    }

    public abstract char getShortNotation();

    public boolean isCaptured() {
        return captured;
    }

    public Color getColor() {
        return color;
    }

    protected void addMultipleSquaresFromDirection(Square location, Board board, Direction direction) {
        int squarePositionX = location.getX() + direction.getChangeInX();
        int squarePositionY = location.getY() + direction.getChangeInY();
        boolean collided = false;

        Square nextSquareInDirection = board.getSquareAtCoordinates(squarePositionX, squarePositionY);

        while (nextSquareInDirection != null && collided == false) {
            if (nextSquareInDirection.getOccupyingPiece() != null) {
                if (ifOccupyingPieceIsAlly(nextSquareInDirection)) {
                    collided = true;
                } else {
                    listOfMovableSquares.add(nextSquareInDirection);
                    collided = true;
                }
            }

            if (collided == false) {
                listOfMovableSquares.add(nextSquareInDirection);
            }

            squarePositionX = squarePositionX + direction.getChangeInX();
            squarePositionY = squarePositionY + direction.getChangeInY();

            nextSquareInDirection = board.getSquareAtCoordinates(squarePositionX, squarePositionY);
        }
    }

    protected void addOneSquareFromDirection(Square location, Board board, Direction direction) {
        int squarePositionX = location.getX() + direction.getChangeInX();
        int squarePositionY = location.getY() + direction.getChangeInY();
        boolean collided = false;

        Square squareInDirection = board.getSquareAtCoordinates(squarePositionX, squarePositionY);

        addSquareIfValidAndNoPieceOfOwnTeamPresent(squareInDirection);
    }

    protected void addSquareIfValidAndNoPieceOfOwnTeamPresent(Square square) {
        if (square != null) {
            if (square.getOccupyingPiece() != null) {
                if (!ifOccupyingPieceIsAlly(square)) {
                    listOfMovableSquares.add(square);
                }
            } else {
                listOfMovableSquares.add(square);
            }
        }
    }

    protected boolean ifOccupyingPieceIsAlly(Square squareInDirection) {
        return squareInDirection.getOccupyingPiece().getColor() == this.getColor();
    }

    public String toString() {

        return String.format("%5s %5s %5s%n", color.name().toLowerCase(), this.getClass().getSimpleName(),
                (captured == true ? "captured" : "in-play"));
    }

    public void setIsCaptured() {
        this.captured = true;
    }

    public boolean getIsCaptured() {
        return isCaptured();
    }

    public Square getSquareLocatedOn() {
        return squareLocatedOn;
    }

    public void setSquareLocatedOn(Square squareLocatedOn) {
        this.squareLocatedOn = squareLocatedOn;
    }

}
