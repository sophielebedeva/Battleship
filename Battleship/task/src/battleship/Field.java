package battleship;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Field {
    Scanner scanner = new Scanner(System.in);
    public char[][] playNet;
    public char[][] fogNet;
    Map<Character, Integer> charToInt = new HashMap<>();
    String userInput;
    int counterCrosses = 0;

    public Field() {
        this.playNet = new char[10][10];
        for (int i = 0; i < playNet.length; i++) {
            for (int j = 0; j < playNet.length; j++) {
                playNet[i][j] = '~';
            }
        }
        int num = 1;
        for (char c = 'A'; c <= 'J'; ++c) {
            charToInt.put(c, num++);
        }

        this.fogNet = new char[10][10];
        for (int i = 0; i < fogNet.length; i++) {
            for (int j = 0; j < fogNet.length; j++) {
                fogNet[i][j] = '~';
            }
        }
    }

    public void print() {
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        char c;
        for (c = 'A'; c <= 'J'; ++c) {
            System.out.print(c);
            for (int i = 0; i < playNet.length; i++) {
                System.out.print(' ');
                System.out.print(playNet[charToInt.get(c) - 1][i]);
            }
            System.out.println();
        }
    }

    public void printFogNet() {
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        char c;
        for (c = 'A'; c <= 'J'; ++c) {
            System.out.print(c);
            for (int a = 0; a < fogNet.length; a++) {
                System.out.print(' ');
                System.out.print(fogNet[charToInt.get(c) - 1][a]);
            }
            System.out.println();
        }
    }

    public void setShips() {
        printFogNet();
        for (ShipType shipType : ShipType.values()) {
            System.out.println("Enter the coordinates of the " + shipType.className() + " (" + shipType.size() + " cells):");
            userInput = scanner.nextLine();
            String[] checkUserInput = userInput.split(" ");
            Coords coord1 = new Coords(charToInt.get(userInput.charAt(0)), Integer.parseInt(checkUserInput[0].substring(1)));
            Coords coord2 = new Coords(charToInt.get(checkUserInput[1].charAt(0)), Integer.parseInt(checkUserInput[1].substring(1)));
            while (!(checkLength(coord1, coord2, shipType) && checkLocation(coord1, coord2, shipType) && checkNeighbors(coord1, coord2, shipType))) {
                userInput = scanner.nextLine();
                checkUserInput = userInput.split(" ");
                coord1 = new Coords(charToInt.get(userInput.charAt(0)), Integer.parseInt(checkUserInput[0].substring(1)));
                coord2 = new Coords(charToInt.get(checkUserInput[1].charAt(0)), Integer.parseInt(checkUserInput[1].substring(1)));
            }
            if (coord1.getLetter() == coord2.getLetter()) {
                for (int x = Math.min(coord1.getNumber(), coord2.getNumber()) - 1, iter = 0; iter < shipType.size(); x++, iter++) {
                    playNet[coord1.getLetter() - 1][x] = 'O';
                }

                print();
            } else {
                for (int y = Math.min(coord1.getLetter(), coord2.getLetter()) - 1, iter = 0; iter < shipType.size(); y++, iter++) {
                    playNet[y][coord1.getNumber() - 1] = 'O';
                }
                print();
            }
        }
    }

    public boolean checkLength(Coords coords1, Coords coords2, ShipType shipType) {
        if ((Math.max(coords1.getNumber(), coords2.getNumber()) - Math.min(coords1.getNumber(), coords2.getNumber()) + 1 == shipType.size())
                || (Math.max(coords1.getLetter(), coords2.getLetter()) - Math.min(coords1.getLetter(), coords2.getLetter()) + 1 == shipType.size())) {
            return true;
        }
        System.out.println("Error! Wrong length of the " + shipType.name() + " Try again:");
        return false;
    }

    public boolean checkLocation(Coords coords1, Coords coords2, ShipType shipType) {
        if (coords1.getLetter() != coords2.getLetter() && coords1.getNumber() != coords2.getNumber()) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        return true;
    }

    public boolean checkNeighbors(Coords coord1, Coords coord2, ShipType shipType) {
        if (coord1.getLetter() == coord2.getLetter()) {
            int startVertical = coord1.getLetter() - 2;
            for (int m = startVertical; m < startVertical + 3; m++) {
                if (m < 0 || m > 9) continue;
                for (int i = Math.min(coord1.getNumber(), coord2.getNumber()) - 2, iter = 0; iter < shipType.size() + 2; i++, iter++) {
                    if (i < 0 || i > 9) continue;
                    if (playNet[m][i] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }

        } else {
            int startHorizontal = coord1.getNumber() - 2;
            for (int m = startHorizontal; m < startHorizontal + 3; m++) {
                if (m < 0 || m > 9) continue;
                for (int i = Math.min(coord1.getLetter(), coord2.getLetter()) - 2, iter = 0; iter < shipType.size() + 2; i++, iter++) {
                    if (i < 0 || i > 9) continue;
                    if (playNet[i][m] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean takeShots(char[][] enemyNet, char[][] fogNet) {
        //System.out.println("Take a shot!");
        // while (counterCrosses != amountOfCrosses) {
        String shotInput = scanner.next();
        String letter = shotInput.substring(0, 1);
        String number = shotInput.substring(1);
        if (!("ABCDEFGHIJ").contains(letter)
                || !("1 2 3 4 5 6 7 8 9 10").contains(number)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        } else {
            int shotCoordX = charToInt.get(shotInput.charAt(0)) - 1;
            int shotCoordY = Integer.parseInt(number) - 1;
            if (enemyNet[shotCoordX][shotCoordY] == '~') {
                fogNet[shotCoordX][shotCoordY] = 'M';
                enemyNet[shotCoordX][shotCoordY] = 'M';
                System.out.println("You missed!");
                return false;
            }
            if (enemyNet[shotCoordX][shotCoordY] == 'M') {
                System.out.println("You missed!");
                return false;
            }

            if (enemyNet[shotCoordX][shotCoordY] == 'X') {
                System.out.println("You hit a ship!");
                return false;
            }

            if (enemyNet[shotCoordX][shotCoordY] == 'O') {
                enemyNet[shotCoordX][shotCoordY] = 'X';
                fogNet[shotCoordX][shotCoordY] = 'X';
                counterCrosses++;

                if (counterCrosses == 17) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    return true;
                }
                if (isSunkShip(shotCoordX, shotCoordY, enemyNet)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!");
                }
            }
        }
        return false;
    }


    private boolean isSunkShip(int x, int y, char[][] playNet) {
        int i = 1;
        int j = 1;
        while (y - i >= 0 && playNet[x][y - i] != 'M' && playNet[x][y - i] != '~') {
            if (playNet[x][y - i] == 'O') return false;
            i++;
        }
        while (y + i < 10 && playNet[x][y + j] != 'M' && playNet[x][y + j] != '~') {
            if (playNet[x][y + j] == 'O') return false;
            j++;
        }
        while (x - i >= 0 && playNet[x - i][y] != 'M' && playNet[x - i][y] != '~') {
            if (playNet[x - i][y] == 'O') return false;
            i++;
        }
        while (x + j < 10 && playNet[x + j][y] != 'M' && playNet[x + j][y] != '~') {
            if (playNet[x + j][y] == 'O') return false;
            j++;
        }
        return true;
    }

    public static void giveTurn() {
        System.out.println("Press Enter and pass the move to another player\n" +
                "...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

