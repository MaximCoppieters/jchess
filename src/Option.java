public enum Option {
    PVP("You can start playing a new player vs player game with the following format: \n " +
            "new pvp <gamename>"),
    PVE("You can start playing a player vs npc game with the following format: \n" +
            "new pve <gamename>"),
    HELP("Shows this help dump"),
    LOAD("You can also load a game with the following format: \n" +
            "load <gamename>"),
    SAVE("While playing a game, you can save it by simply typing save"),
    EXIT("Closes the game with or without having saved");

    private String description;

    private Option(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static Option getOptionByString(String actionWord) {
        for(Option option : values()) {
            if(actionWord.toUpperCase().equals(option.name())){
                return option;
            }
        }

        throw new IllegalArgumentException("Option not found!");
    }
}
