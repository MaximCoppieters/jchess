import java.io.Serializable;
import java.util.Arrays;

public enum Command implements Serializable {
    PVP(false, "You can start playing a new player vs player game with the following format:\n " +
            "pvp <gamename>"),
    PVE(false, "You can start playing a player vs npc game with the following format:\n" +
            "pve <gamename>"),
    HELP(false, "Shows this help dump"),
    LOAD(false, "You can also load a game with the following format:\n" +
            "load <gamename>"),
    QUIT(false, "Closes the game with or without having saved, " +
            "if you aren't currently playing a game this closes the program"),
    SURRENDER(true,"You forfeit the game, the game ends and you lose"),
    MOVE(true,"You can move a pawn you own by using the following format:\n" +
                 "move <XYsource> <XYdestination> (eg. move a7 a5)"),
    SAVE(true,"While playing a game, you can save it by simply typing save"),
    OPTION(false, "Let's you set certain in-game settings such as which player begins, player colors, etc.");

    private String description;
    private boolean isGameOption;

    Command(boolean isGameOption, String description) {
        this.isGameOption = isGameOption;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static Command getOptionByString(String actionWord) {
        for (Command command : values()) {
            if (actionWord.toUpperCase().equals(command.name())) {
                return command;
            }
        }

        throw new IllegalArgumentException("Command not found!");
    }

    public static void printOptions(boolean isGameOption) {
        Arrays.stream(values())
                .filter(option -> option.isGameOption == isGameOption)
                .forEach(System.out::println);
    }

    public boolean getIsGameOption() {
        return isGameOption;
    }
}
