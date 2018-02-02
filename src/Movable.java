import java.util.List;

public interface Movable {


	List<Square> getListOfMovableSquares(Square location, Board board);
}
