import java.io.Serializable;

public enum MenuOption implements Serializable {
    PVP("You can start playing a new player vs player game with the following format: \n " +
            "pvp <gamename>"),
    PVE("You can start playing a player vs npc game with the following format: \n" +
            "pve <gamename>"),
    HELP("Shows this help dump"),
    LOAD("You can also load a game with the following format: \n" +
            "load <gamename>"),
    EXIT("Closes the game with or without having saved");

    private String description;

    MenuOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static MenuOption getOptionByString(String actionWord) {
        for (MenuOption menuOption : values()) {
            if (actionWord.toUpperCase().equals(menuOption.name())) {
                return menuOption;
            }
        }

        throw new IllegalArgumentException("MenuOption not found!");
    }
}
