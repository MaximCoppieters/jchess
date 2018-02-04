import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AI extends Player {

    public AI(Color color) {
        super(color);
    }

    public void makeRandomMove() {
        boolean moveIsValid;
        Random rng = new Random();

        List<Piece> piecesNotCaptured = super.getPieces()
                .stream()
                .filter(piece -> piece.isCaptured() == false)
                .collect(Collectors.toList());

        int amountOfPiecesInPlay = piecesNotCaptured.size();

        int indexPieceToMove = rng.nextInt(amountOfPiecesInPlay);

        Piece pieceToMove = piecesNotCaptured.get(indexPieceToMove);

        Square squareLocatedOn = pieceToMove.getSquareLocatedOn();

        List<Square> moveableSquares = pieceToMove.getListOfMovableSquares(squareLocatedOn, super.gameBoard);
    }


}
