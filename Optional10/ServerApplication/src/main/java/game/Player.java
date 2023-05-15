package game;

import java.net.Socket;

public class Player {
    private int color; // 1 sau -1
    private Socket socket;
    private Game game;

    public Player(int color) {
        this.color = color;
    }

    public Player(Socket socket) {
        this.socket = socket;
    }

    public Player(int color, Game game) {
        this.color = color;
        this.game = game;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void submitMove(int x, int y){

    }

    public Socket getSocket() {
        return socket;
    }

    public boolean inGame(){
        return !(game == null);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
