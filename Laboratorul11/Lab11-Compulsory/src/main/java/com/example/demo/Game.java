package com.example.demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private Board board = new Board();
    private Player[] players = new Player[2];
    public Game() {
    }

    public void addPlayer(Player player) throws IOException {
        if(players[0] == null) {
            player.setColor(-1);
            player.setGame(this);
            players[0] = player;
            player.notify("You have to wait for another player to join....");
        }
        else {
            player.setColor(1);
            player.setGame(this);
            players[1] = player;
            startGame();
        }
    }
    private void startGame() throws IOException {
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
        players[0].setGame(null);
        players[0] = null;
        players[1].setGame(null);
        players[1] = null;
    }

    public boolean submitMove(int x, int y, Player player) throws IOException {
        boolean result = board.submitMove(x, y, player.getColor());
        if(result){
            //verificam daca niciunul din jucatori nu a facut miscarea castigatoare
            if(board.checkWin(players[0].getColor()))
            {
                players[0].notify("Congratulations!You won!");
                players[1].notify("You lost!Try again.");
                resetGame();
                return true;
            }

            if(board.checkWin(players[1].getColor()))
            {
                players[1].notify("Congratulations!You won!");
                players[0].notify("You lost!Try again.");
                resetGame();
                return true;
            }
            // anunta ambii jucatori de mutare
            if(players[0] == player) {
                players[0].notify("Move accepted\n"+board.display());
                players[1].notify("Your opponent's move is: " + x + " " + y+"\n"+board.display());
                players[0].changeTurn();
                players[1].changeTurn();
                return true;
            }
            else{
                players[1].notify("Move accepted\n"+board.display());
                players[0].notify("Your opponent's move is: " + x + " " + y+"\n"+board.display());
                players[0].changeTurn();
                players[1].changeTurn();
                return true;
            }

        }
        else{
            // anunta doar jucatorul actual
            player.notify("Invalid move. Try again!");
            return false;
        }
    }

}
