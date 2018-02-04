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
        MenuOption menuOptionToExecute = null;

        System.out.println("Welcome to jchess, a small chess game designed to be played in commandline");
        System.out.println("What would you like to do? (type help for options)");
        String actionWords[] = getPlayerActionSeparated();
        menuOptionToExecute = getMenuOptionFromAction(actionWords);

        while (menuOptionToExecute != MenuOption.EXIT) {
            String gameName = null;
            switch (menuOptionToExecute) {
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
                    printOptions();
                    break;
                case EXIT:
                    return;
                case LOAD:
                    try {
                        gameName = actionWords[1];
                        currentGame = loadGame(gameName);
                        currentGame.run();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("LOAD option structure is wrong, type \"load <gamename>\"");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (ClassNotFoundException cnfe) {
                        cnfe.printStackTrace();
                    }
            }

            System.out.println("What would you like to do? (type help for options)");
            actionWords = getPlayerActionSeparated();
            menuOptionToExecute = getMenuOptionFromAction(actionWords);
        }

        keyboard.close();
    }

    private static String[] getPlayerActionSeparated() {
        return keyboard.nextLine().split(" ");
    }


    public static MenuOption getMenuOptionFromAction(String[] actionWords) {
        boolean correctOption = false;
        MenuOption menuOptionToExecute = null;

        while (correctOption == false) {
            try {
                menuOptionToExecute = MenuOption.getOptionByString(actionWords[0]);
                correctOption = true;
            } catch (IllegalArgumentException iae) {
                System.out.println("Menu option doesn't exist, try again!");
                actionWords = getPlayerActionSeparated();
                menuOptionToExecute = MenuOption.getOptionByString(actionWords[0]);
            }
        }

        return menuOptionToExecute;
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
        for (MenuOption menuOption : MenuOption.values()) {
            System.out.println(menuOption);
        }
    }

    private static void showRules() {
        System.out.println("When it is your turn issue a movement by specifying the coordinate "
                + "of the piece you want the move, followed by the target location. This goes for both "
                + "normal movement and capturing");
    }
}
