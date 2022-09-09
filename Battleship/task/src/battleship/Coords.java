package battleship;

public class Coords {
    private int horizontal;
    private int vertical;

    public Coords(int vertical, int horizontal) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getLetter() {
        return vertical;
    }

    public int getNumber() {
        return horizontal;
    }
}
