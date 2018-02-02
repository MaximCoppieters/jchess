import java.util.*;

public class Game {
	private static Player playerWhite = new Player(Color.WHITE);
	private static Player playerBlack = new Player(Color.BLACK);
	private static Board board = new Board(playerWhite.getPieces(), playerBlack.getPieces());
	private static Scanner keyboard = new Scanner(System.in);
	
	private static int[] yCoordinates = {8,7,6,5,4,3,2,1,0};
	private static Map<Character, Integer> xCoordinates = new HashMap<>();
	private static boolean validMove;
	private static Square sourceSquare;
	private static Square targetSquare;

    static {
		int index = 0;
		
		for(char i = 'a'; i <= 'h'; i++) {
			xCoordinates.put(i, index++);
		}
	}
	
	public static void main(String[] args) {
        System.out.println("Welcome to jchess, a small chess game designed to be played in commandline");
        System.out.println("What would you like to do? (type help for options)");
        String playerAction = keyboard.nextLine();
        String actionWords[] = playerAction.split(" ");

        if(Arrays.asList(Option.values()).contains(actionWords[0].toUpperCase())) {
            Option optionToExecute = Option.getOptionByString(actionWords[0]);

            
        } else {

        }

	    board.initializeBoard();
		board.placePieces();
		board.drawBoard();

		playerWhite.setPlayerTurn(true);

		showRules();

		while(gameEnded(playerWhite, playerBlack) == false) {
			play();
			board.drawBoard();
		}

		if(playerWhite.isKingCaptured()) {
			System.out.println("Player black wins!");
		} else {
			System.out.println("Player white wins!");
		}

		keyboard.close();
	}

    private static void printOptions() {
        for(Option option : Option.values()) {
            System.out.println(option);
        }
    }

    private static void showRules() {
		System.out.println("When it is your turn issue a movement by specifying the coordinate "
				+ "of the piece you want the move, followed by the target location. This goes for both "
				+ "normal movement and capturing");
	}

	private static boolean gameEnded(Player white, Player black) {
		if(white.isKingCaptured() == true || black.isKingCaptured() == true) return true;
		
		return false;
	}
	
	private static void play() {
		String move;
		
		if(playerWhite.getPlayerTurn()) {
			System.out.println("It's player white's turn, what is your command?");
			move = keyboard.nextLine();

            while(commandContainsKeyword(move) == false) {
                System.out.println("Doesn't contain command keyword, try again");
                move = keyboard.nextLine();
            }

            validMove = false;

            while(validMove == false) {
                try {
                    validMove = isMoveValid(move, playerWhite);
                } catch(InvalidMoveException ime) {
                    System.out.println(ime.getMessage());
                    System.out.println("Try again");
                    move = keyboard.nextLine();
                }

            }
			
			move();
			playerWhite.setPlayerTurn(false);
			playerBlack.setPlayerTurn(true);
		} else {
			System.out.println("It's player black's turn, what is your move?");
			move = keyboard.nextLine();

            while(commandContainsKeyword(move) == false) {
                System.out.println("Doesn't contain command keyword, try again");
                move = keyboard.nextLine();
            }

			validMove = false;

            while(validMove == false) {
                try {
                    validMove = isMoveValid(move, playerBlack);
                } catch(InvalidMoveException ime) {
                    System.out.println(ime.getMessage());
                    System.out.println("Try again");
                    move = keyboard.nextLine();
                }

            }

			move();
			
			playerBlack.setPlayerTurn(false);
			playerWhite.setPlayerTurn(true);
		}
	}

    private static boolean commandContainsKeyword(String move) {
        if(move.toLowerCase().indexOf("move") < 0) return false;

        return true;
    }

    private static void move() {
        Piece pieceToMove = sourceSquare.getOccupyingPiece();

        if(targetSquare.getOccupyingPiece() != null) {
            targetSquare.getOccupyingPiece().setCaptured();
        }

        targetSquare.setOccupyingPiece(pieceToMove);

        if(targetSquare.getOccupyingPiece() instanceof Pawn) {
            ((Pawn)targetSquare.getOccupyingPiece()).setFirstMovement(false);
        }

        sourceSquare.setOccupyingPiece(null);
	}
	
	public static boolean isMoveValid(String move, Player actingPlayer)
		throws InvalidMoveException{
		String[] commands = move.split(" ");
		int xBeforeMovement = 0;
		int yBeforeMovement = 0;
		int xAfterMovement = 0;
		int yAfterMovement = 0;

		if(commands.length != 3) return false;
		
		try {
			xBeforeMovement = getXOfCommand(commands[1]);
			yBeforeMovement = getYOfCommand(commands[1]);
			
			xAfterMovement = getXOfCommand(commands[2]);
			yAfterMovement = getYOfCommand(commands[2]);
					
		} catch(Exception e) {
		    e.printStackTrace();
			System.out.println("Structure of command is wrong!");
			return false;
		} finally {
		    sourceSquare = board.getSquareAtCoordinates(xBeforeMovement, yBeforeMovement);
            targetSquare = board.getSquareAtCoordinates(xAfterMovement, yAfterMovement);

			if(sourceSquare == null) {
				throw new InvalidMoveException("The square coordinate of the piece to move doesn't exist!");
			}
			
			Piece pieceAtSquareBefore = sourceSquare.getOccupyingPiece();
			if(pieceAtSquareBefore == null) {
				throw new InvalidMoveException("There is no piece to move at that square!");
			}

            System.out.println(pieceAtSquareBefore);
			
			if(pieceAtSquareBefore.getColor().equals(actingPlayer.getColor()) == false) {
				throw new InvalidMoveException("The piece at that location is not yours!");
			}
			
			
			if(targetSquare == null){
                System.out.println(xAfterMovement + " " + yAfterMovement);
				throw new InvalidMoveException("The square you want to move towards doesn't exist");
			}

			if(!pieceAtSquareBefore.getListOfMovableSquares(sourceSquare, board).contains(targetSquare)) {
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
}
