import game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    // Define the port on which the server is listening
    private static final int PORT = 8100;
    private static boolean running = true;
    protected final List<Game> games = new ArrayList<>();

    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(5000); // 5 sec
            while (running) {
                System.out.println("Waiting for a client ...");
                Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    continue;
                }
                // Execute the client's request in a new thread
                if(running)
                    new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
    }

    public static void setRunning(boolean running) {
        GameServer.running = running;
    }

    public static boolean isRunning() {
        return running;
    }

    protected void addGame(Game game){
        games.add(game);
    }

    protected Game getFirstAvailableGame(){
        for (Game game : games) {
            if (game.isAvailable())
                return game;
        }
        return null; // no available game
    }
}