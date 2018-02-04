import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameMenu implements Serializable {

    private static Map<String, Game> gamesByName = new HashMap<>();
    private static Scanner keyboard = new Scanner(System.in);
    private static Game currentGame = null;

    public static void main(String[] args) {
        Command commandToExecute = null;

        System.out.println("Welcome to jchess, a small chess game designed to be played in commandline");
        System.out.println("What would you like to do? (type help for options)");
        String actionWords[] = getPlayerActionSeparated();
        commandToExecute = getMenuOptionFromAction(actionWords);

        while (commandToExecute != Command.QUIT) {
            String gameName = null;
            switch (commandToExecute) {
                case PVE:
                    try {
                        gameName = actionWords[1];
                        currentGame = new Game(new Player(Color.WHITE), new Player(Color.BLACK), keyboard, gameName);
                        currentGame.run();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("PVE option structure is wrong, type \"pve <gamename>\"");
                    }

                    break;
                case PVP:
                    try {
                        gameName = actionWords[1];
                        currentGame = new Game(new Player(Color.WHITE), new Player(Color.BLACK), keyboard, gameName);
                        currentGame.run();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("PVE option structure is wrong, type \"pve <gamename>\"");
                    }
                    break;
                case HELP:
                    Command.printOptions(false);
                    break;
                case QUIT:
                    return;
                case LOAD:
                    try {
                        gameName = actionWords[1];
                        currentGame = loadGame(gameName);
                        currentGame.run();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("LOAD option structure is wrong, type \"load <gamename>\"");
                    } catch (IOException ioe) {
                        System.out.println("Target savefile is not present in the savefiles folder");
                    } catch (ClassNotFoundException cnfe) {
                        System.out.println("The game you are trying to load is not compatible with the current version");
                    }
                case SAVE:
                case MOVE:
                case SURRENDER:
                    System.out.println("The command you entered can only be used whilst in-game!");
            }

            System.out.println("What would you like to do? (type help for options)");
            actionWords = getPlayerActionSeparated();
            commandToExecute = getMenuOptionFromAction(actionWords);
        }

        keyboard.close();
    }

    private static String[] getPlayerActionSeparated() {
        return keyboard.nextLine().split(" ");
    }


    public static Command getMenuOptionFromAction(String[] actionWords) {
        boolean correctOption = false;
        Command commandToExecute = null;

        while (correctOption == false) {
            try {
                commandToExecute = Command.getOptionByString(actionWords[0]);
                correctOption = true;
            } catch (IllegalArgumentException iae) {
                System.out.println("Menu option doesn't exist, try again!");
                actionWords = getPlayerActionSeparated();
                commandToExecute = Command.getOptionByString(actionWords[0]);
            }
        }

        return commandToExecute;
    }

    private static Game loadGame(String gameName) throws IOException, ClassNotFoundException {
        Path pathToGameName = Paths.get("savefiles/" + gameName + ".dat");
        Game game = null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(pathToGameName))) {
            game = (Game) ois.readObject();
            game.getBoard().drawBoard();
        }

        return game;
    }


    private static void printOptions() {
        for (Command command : Command.values()) {
            System.out.println(command);
        }
    }

    private static void showRules() {
        System.out.println("When it is your turn issue a movement by specifying the coordinate "
                + "of the piece you want the move, followed by the target location. This goes for both "
                + "normal movement and capturing");
    }
}
