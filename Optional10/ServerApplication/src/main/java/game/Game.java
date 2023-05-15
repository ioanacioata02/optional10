package game;

import java.util.Arrays;

public class Game {
    private Board board = new Board();
    private Player[] players = new Player[2];
    public Game() {
    }

    public void addPlayer(Player player){ //player == null ??? isAvailable
        if(players[0] == null) {
            player.setColor(-1);
            player.setGame(this);
            players[0] = player;
            // anunta-l ca mai are de asteptat
        }
        else {
            player.setColor(1);
            player.setGame(this);
            players[1] = player;
            startGame();
        }
    }
    private void startGame(){
        // notifica jucatorii
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
        boolean restult = board.submitMove(x, y, player.getColor());
        if(restult){
            // anunta ambii jucatori de mutare
        }
        else{
            // anunta doar jucatorul actual
        }
    }

}
