package game;

import java.util.Arrays;
import java.util.Random;

public class Game {
    private Board board = new Board();
    private Player[] players = new Player[2];
    public Game() {
    }

    public void addPlayer(Player player){
        if(players[0] == null) {
            player.setColor(-1);
            player.setGame(this);
            players[0] = player;
        }
        else {
            player.setColor(1);
            player.setGame(this);
            players[1] = player;
            startGame();
        }
    }
    private void startGame(){
        int currentPlayer = new Random().nextInt(2);
        players[currentPlayer].setActive(true);
        players[1-currentPlayer].setActive(false);
        players[currentPlayer].notify("Game started!\nYour turn!");
        players[1-currentPlayer].notify("Game started!\nWait for you opponent's move!");
    }
    public boolean isAvailable(){
        return players[0]==null || players[1]==null;
    }
    private void gameOver(){
        //anunta jucatorii
        resetGame();
    }

    public void resetGame(){
        board = new Board();
        Arrays.stream(players).forEach(player -> player = null);
    }

    public void submitMove(int x, int y, Player player){
        boolean result = board.submitMove(x, y, player.getColor());
        if(result){
            //verificam daca niciunul din jucatori nu a facut miscarea castigatoare
            if(board.checkWin(players[0].getColor()))
            {
                players[0].notify("Congratulations!You won!");
                players[1].notify("You lost!Try again.");
               resetGame();
                return;
            }

            if(board.checkWin(players[1].getColor()))
            {
                players[1].notify("Congratulations!You won!");
                players[0].notify("You lost!Try again.");
               resetGame();
                return;
            }
            // anunta ambii jucatori de mutare
            if(players[0] == player) {
                players[0].notify("Move accepted\n"+board.display());
                players[1].notify("Your opponent's move is: " + x + " " + y+"\n"+board.display());
            }
            else{
                players[1].notify("Move accepted\n"+board.display());
                players[0].notify("Your opponent's move is: " + x + " " + y+"\n"+board.display());
            }
            players[0].changeTurn();
            players[1].changeTurn();
        }
        else{
            // anunta doar jucatorul actual
            player.notify("Invalid move. Try again!");
        }
    }

}
