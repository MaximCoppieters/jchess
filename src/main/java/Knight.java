import java.util.List;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }


    @Override
    public char getShortNotation() {
        return 'H';
    }


    @Override
    public List<Square> getListOfMovableSquares(Square location, Board board) {
        listOfMovableSquares.clear();

        Square knightMovementTwiceVertical;
        Square knightMovementTwiceHorizontal;

        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWNLEFT || direction == Direction.DOWNRIGHT
                    || direction == Direction.UPLEFT || direction == Direction.UPRIGHT) {
                knightMovementTwiceVertical = board.
                        getSquareAtCoordinates(location.getX() + direction.getChangeInX(),
                                location.getY() + 2 * direction.getChangeInY());

                knightMovementTwiceHorizontal = board.
                        getSquareAtCoordinates(location.getX() + 2 * direction.getChangeInX(),
                                location.getY() + direction.getChangeInY());

                super.addSquareIfValidAndNoPieceOfOwnTeamPresent(knightMovementTwiceHorizontal);
                super.addSquareIfValidAndNoPieceOfOwnTeamPresent(knightMovementTwiceVertical);

            }
        }

        return listOfMovableSquares;
    }


}
