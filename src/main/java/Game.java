import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game implements Serializable {

    private Player playerWhite;
    private Player playerBlack;
    private AI AIWhite;
    private Board board;
    private transient Scanner keyboard;
    private String gameName;

    private static int[] yCoordinates = {8, 7, 6, 5, 4, 3, 2, 1, 0};
    private static Map<Character, Integer> xCoordinates;
    private boolean validMove;
    private Square sourceSquare;
    private Square targetSquare;
    private boolean playerEnded;

    static {
        xCoordinates = new HashMap<>();
        int index = 0;

        for (char i = 'a'; i <= 'h'; i++) {
            xCoordinates.put(i, index++);
        }
    }

    public Game(Player playerWhite, Player playerBlack, Scanner keyboard, String gameName) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        this.keyboard = keyboard;
        this.gameName = gameName;

        if (gameName.length() == 0) throw new IllegalArgumentException();

        setupGame();
    }

    private void setupGame() {
        board = new Board(playerWhite.getPieces(), playerBlack.getPieces());
        board.initializeBoard();
        board.placePieces();
        board.drawBoard();

        playerWhite.setGameBoard(board);
        playerBlack.setGameBoard(board);

        playerWhite.setPlayerTurn(true);
    }

    public void run() {
        while (gameEnded(playerWhite, playerBlack) == false && playerEnded == false) {
            play();
            if (playerEnded == false) {
                board.drawBoard();
            }
        }

        if (playerWhite.isKingCaptured()) {
            System.out.println("Player black wins!");
        } else {
            if (playerBlack.isKingCaptured()) {
                System.out.println("Player white wins!");
            } else {
                System.out.println("Game was quit");
            }
        }
    }

    private static boolean gameEnded(Player white, Player black) {
        return white.isKingCaptured() == true || black.isKingCaptured() == true;

    }

    private void play() {
        keyboard = new Scanner(System.in);
        Command commandToExecute = null;

        while (commandToExecute != Command.QUIT) {
            if (playerWhite.getPlayerTurn()) {

                playerDoTurn(playerWhite);

                if (playerEnded) {
                    return;
                }

            } else {
                playerDoTurn(playerBlack);

                if (playerEnded) {
                    return;
                }
            }

            board.drawBoard();
        }

    }

    private void playerDoTurn(Player playerToMove) {
        Command commandToExecute = null;
        System.out.println("It's player " + playerToMove.getColor().name().toLowerCase() + "'s turn, what is your action?");
        String actionWords[] = getPlayerActionSeparated();
        commandToExecute = getCommandFromAction(actionWords);
        validMove = false;

        while (validMove == false) {
            switch (commandToExecute) {
                case SURRENDER:
                    playerToMove.setKingCaptured(true);
                    return;
                case SAVE:
                    saveGame();
                    break;
                case HELP:
                    Command.printOptions(true);
                    break;
                case QUIT:
                    playerEnded = true;
                    return;
                case MOVE:
                    try {
                        validMove = isMoveValid(actionWords, playerToMove);
                        move();
                        playerToMove.setPlayerTurn(false);

                        if (playerToMove.getColor() == Color.BLACK) {
                            playerWhite.setPlayerTurn(true);
                        } else {
                            playerBlack.setPlayerTurn(true);
                        }
                        return;
                    } catch (InvalidMoveException ime) {
                        System.out.println(ime.getMessage());
                        System.out.println("Try again");
                    }
                    break;
                case PVP:
                case PVE:
                case LOAD:
                    System.out.println("The command you entered is a menu command, it's not usable in game");
            }

            System.out.println("What would you like to do? (type help for options)");
            actionWords = getPlayerActionSeparated();
            commandToExecute = getCommandFromAction(actionWords);
        }
    }

    private void saveGame() {
        Path pathToGame = Paths.get("savefiles/" + gameName + ".dat");

        try (ObjectOutputStream gameSaver = new ObjectOutputStream(Files.newOutputStream(pathToGame))) {
            gameSaver.flush();
            gameSaver.writeObject(this);
            System.out.println("Game saved");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Game save failed, savefiles folder not found");
        }
    }

    public Command getCommandFromAction(String[] actionWords) {
        boolean correctOption = false;
        Command gameOptionToExecute = null;

        while (correctOption == false) {
            try {
                gameOptionToExecute = Command.getOptionByString(actionWords[0]);
                correctOption = true;
            } catch (IllegalArgumentException iae) {
                System.out.println("Menu option doesn't exist, try again!");
                actionWords = getPlayerActionSeparated();
                gameOptionToExecute = Command.getOptionByString(actionWords[0]);
            }
        }

        return gameOptionToExecute;
    }

    private String[] getPlayerActionSeparated() {
        return keyboard.nextLine().split(" ");
    }

    private void move() {
        Piece pieceToMove = sourceSquare.getOccupyingPiece();

        if (targetSquare.getOccupyingPiece() != null) {
            targetSquare.getOccupyingPiece().setIsCaptured();
        }

        targetSquare.setOccupyingPiece(pieceToMove);

        if (targetSquare.getOccupyingPiece() instanceof Pawn) {
            ((Pawn) targetSquare.getOccupyingPiece()).setFirstMovement(false);
        }

        sourceSquare.setOccupyingPiece(null);
    }

    public boolean isMoveValid(String[] commandWords, Player actingPlayer)
            throws InvalidMoveException {

        int xBeforeMovement = 0;
        int yBeforeMovement = 0;
        int xAfterMovement = 0;
        int yAfterMovement = 0;

        try {
            xBeforeMovement = getXOfCommand(commandWords[1]);
            yBeforeMovement = getYOfCommand(commandWords[1]);

            xAfterMovement = getXOfCommand(commandWords[2]);
            yAfterMovement = getYOfCommand(commandWords[2]);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Structure of command is wrong!");
            return false;
        } finally {
            sourceSquare = board.getSquareAtCoordinates(xBeforeMovement, yBeforeMovement);
            targetSquare = board.getSquareAtCoordinates(xAfterMovement, yAfterMovement);

            if (sourceSquare == null) {
                throw new InvalidMoveException("The square coordinate of the piece to move doesn't exist!");
            }

            Piece pieceAtSquareBefore = sourceSquare.getOccupyingPiece();
            if (pieceAtSquareBefore == null) {
                throw new InvalidMoveException("There is no piece to move at that square!");
            }

            if (pieceAtSquareBefore.getColor().equals(actingPlayer.getColor()) == false) {
                throw new InvalidMoveException("The piece at that location is not yours!");
            }


            if (targetSquare == null) {
                throw new InvalidMoveException("The square you want to move towards doesn't exist");
            }

            if (!pieceAtSquareBefore.getListOfMovableSquares(sourceSquare, board).contains(targetSquare)) {
                throw new InvalidMoveException("The move you are trying to make is not a valid move");
            }

        }

        return true;
    }

    public static int getXOfCommand(String command) {
        return xCoordinates.get(command.charAt(0));
    }

    public static int getYOfCommand(String command) {
        return yCoordinates[Integer.parseInt(command.substring(1, 2))];
    }

    public Board getBoard() {
        return board;
    }


}
