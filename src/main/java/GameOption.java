import java.io.Serializable;

public enum GameOption implements Serializable {
    SURRENDER("You forfeit the game, the game ends and you lose"),
    MOVE("You can move a pawn you own by using the following format: \n" +
            "move <XYsource> <XYdestination> (eg. move a7 a5)"),
    HELP("Shows this help dump"),
    QUIT("The game stops and you return to the main menu"),
    SAVE("While playing a game, you can save it by simply typing save");

    private String description;

    GameOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static GameOption getOptionByString(String actionWord) throws IllegalArgumentException {
        for (GameOption gameOption : values()) {
            if (actionWord.toUpperCase().equals(gameOption.name())) {
                return gameOption;
            }
        }

        throw new IllegalArgumentException("Game Option not found!");
    }

}
