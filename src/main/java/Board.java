import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.io.Serializable;
import java.util.List;


public class Board implements Serializable {
    private Square[][] board;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private static transient ColoredPrinter whitePrinter;
    private static transient ColoredPrinter blackPrinter;

    public Board(List<Piece> whitePieces, List<Piece> blackPieces) {
        board = new Square[8][8];
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;

        whitePrinter = createColoredPrinter();

        blackPrinter = createColoredPrinter();
    }

    public ColoredPrinter createColoredPrinter() {
        return new ColoredPrinter.Builder(1, false)
                .build();
    }

    public void drawBoard() {
        drawHorizontalCoordinates();
        printCapturedPiecesOfColor(Color.BLACK);
        drawFullLine();
        drawRows();
        drawHorizontalCoordinates();
        printCapturedPiecesOfColor(Color.WHITE);
        System.out.println();
    }

    public void printCapturedPiecesOfColor(Color color) {
        if (color == Color.BLACK) {
            System.out.print("\t\tCaptured black: ");
            printCapturedPieces(blackPieces);
            System.out.println();
        } else {
            System.out.print("\t\tCaptured white: ");
            printCapturedPieces(whitePieces);
            System.out.println();
        }
    }

    private void printCapturedPieces(List<Piece> pieces) {
        pieces
                .stream()
                .filter(piece -> piece.getIsCaptured() == true)
                .forEach(piece -> System.out.print(piece.getClass().getSimpleName() + " "));
    }

    private void drawRows() {
        int index = board.length;

        for (Square[] row : board) {
            System.out.print("\t\t\t\t\t" + index + " ");
            System.out.print("| ");
            for (Square square : row) {
                if (square.getOccupyingPiece() != null) {
                    if (square.getOccupyingPiece().getColor() == Color.BLACK) {
                        if (blackPrinter == null) {
                            blackPrinter = createColoredPrinter();
                        }
                        writeCharacterInColor(Ansi.FColor.BLACK, square);
                    } else {
                        if (whitePrinter == null) {
                            whitePrinter = createColoredPrinter();
                        }
                        writeCharacterInColor(Ansi.FColor.WHITE, square);
                    }
                } else {
                    System.out.print(square.getOccupationCharacter());
                }
                System.out.print(" | ");
            }
            System.out.print(index);
            System.out.println();
            drawFullLine();
            index--;
        }
    }

    private void writeCharacterInColor(Ansi.FColor color, Square square) {
        whitePrinter.setAttribute(Ansi.Attribute.BOLD);
        whitePrinter.setForegroundColor(color);
        whitePrinter.print(square.getOccupationCharacter());
        whitePrinter.clear();
    }

    private void drawFullLine() {
        System.out.print("\t\t\t\t\t  ");
        for (int i = 0; i < board.length; i++) {
            System.out.print("----");
        }
        System.out.println("-");
    }

    private void drawHorizontalCoordinates() {
        System.out.print("\t\t\t\t\t   ");
        for (char horizontal = 'a'; horizontal <= 'h'; horizontal++) {
            System.out.print(" " + horizontal + "  ");
        }
    }

    public void initializeBoard() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = new Square(x, y);
            }
        }
    }

    public void placePieces() {
        int indexWhite = 0;
        int indexBlack = 0;
        Piece pieceToOccupy;

        for (int y = 0; y < 2; y++) {
            for (Square square : board[y]) {
                pieceToOccupy = whitePieces.get(indexWhite);
                square.setOccupyingPiece(pieceToOccupy);
                ++indexWhite;
            }
        }

        for (int y = 6; y < board.length; y++) {
            for (Square square : board[y]) {
                pieceToOccupy = blackPieces.get(indexBlack);
                square.setOccupyingPiece(pieceToOccupy);
                ++indexBlack;
            }
        }
    }

    public Square getSquareAtCoordinates(int x, int y) {
        if (isWithinBounds(x, y)) {
            return board[y][x];
        }
        return null;
    }

    private boolean isWithinBounds(int x, int y) {
        return (x < board.length && x >= 0) && (y < board.length && y >= 0);
    }

}
