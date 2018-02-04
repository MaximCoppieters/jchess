import java.io.Serializable;

public enum Direction implements Serializable {
    UP(0, -1), DOWN(0, 1), RIGHT(1, 0), LEFT(-1, 0),
    UPLEFT(-1, -1), UPRIGHT(1, -1), DOWNLEFT(-1, +1), DOWNRIGHT(+1, +1);

    private int changeInX;
    private int changeInY;

    Direction(int changeInX, int changeInY) {
        this.changeInX = changeInX;
        this.changeInY = changeInY;
    }

    public int getChangeInX() {
        return changeInX;
    }

    public int getChangeInY() {
        return changeInY;
    }
}
