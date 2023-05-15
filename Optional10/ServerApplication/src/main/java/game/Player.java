package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
    private int color; // 1 sau -1
    private Socket socket;
    private Game game = null;

    private boolean active;

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

    public void submitMove(int x, int y) {
        game.submitMove(x, y, this);
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean inGame() {
        return (this.game != null);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void notify(String message) {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(message);
            out.flush();
        } catch (IOException ex) {
            System.err.println("Communication error... " + ex);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPlayerTurn(){
        return active;
    }

    public void changeTurn(){
        active = !active;
    }
}
