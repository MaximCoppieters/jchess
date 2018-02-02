public class GameMenu {

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
}
