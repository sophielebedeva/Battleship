package battleship;

public enum ShipType {
    CARRIER("Aircraft Carrier", 5, 1),
    BATTLESHIP("Battleship", 4, 1),
    SUBMARINE("Submarine", 3, 1),
    CRUISER("Cruiser", 3, 1),
    DESTROYER("Destroyer", 2, 1);

    private final String name;
    private final int size;
    private final int number;

    ShipType(String name, int size, int number) {
        this.name = name;
        this.size = size;
        this.number = number;
    }

    public String className() {
        return name;
    }

    public int size() {
        return size;
    }

    public int number() {
        return number;
    }
}
