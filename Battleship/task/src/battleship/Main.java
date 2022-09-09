package battleship;

public class Main {

    public static void main(String[] args) {
        Field player1 = new Field();
        System.out.println("Player 1, place your ships on the game field");
        player1.setShips();
        player1.giveTurn();
        Field player2 = new Field();
        System.out.println("Player 2, place your ships on the game field");
        player2.setShips();
        player2.giveTurn();
        boolean victory = false;

        while (!victory) {
            //player1 is playing
            player2.printFogNet();
            System.out.println("---------------------");
            player1.print();
            System.out.println("Player 1, it's your turn:");
            victory = player1.takeShots(player2.playNet, player2.fogNet);
            if (victory) break;
            Field.giveTurn();

            //player2 is playing
            player1.printFogNet();
            System.out.println("---------------------");
            player2.print();
            System.out.println("Player 2, it's your turn:");
            victory = player2.takeShots(player1.playNet, player1.fogNet);
            if (victory) break;
            Field.giveTurn();
        }
    }
}

