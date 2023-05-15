import game.Game;
import game.Player;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Player player;
    private GameServer gameServer;
    private boolean running = true;

    public ClientThread(Socket socket, GameServer gameServer) {
        this.player = new Player(socket);
        this.gameServer = gameServer;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
             PrintWriter out = new PrintWriter(player.getSocket().getOutputStream())){
            while (running) {
                while (!in.ready()){

                }
                String request = in.readLine();
                String raspuns = null;
                if (request.equals("exit"))
                    running = false;
                else if (request.equals("stop") || !GameServer.isRunning()) {
                    raspuns = "Server stopped";
                    GameServer.setRunning(false);
                    running = false;
                } else {
                    raspuns = processRequest(request);
                    //raspuns = "Server received the request ... " + request;
                }
                // Send the response to the oputput stream: server → client
                out.println(raspuns);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
            try {
                player.getSocket().close(); // or use try-with-resources
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        try {
            player.getSocket().close(); // or use try-with-resources
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    private String processRequest(String request) {
        if (request.equals("create game")) {
            synchronized (gameServer.games) {
                gameServer.addGame(new Game());
            }
            return "new Game created";
        }

        if (request.equals("join game")) {
            if(player.inGame())
                return "Already joined a game";
            Game game;
            synchronized (gameServer.games) {
                game = gameServer.getFirstAvailableGame();
                if (game != null)
                    game.addPlayer(player);
            }
            if (game == null)
                return "no available game. try to create one";

        }
        if (request.equals("submit move")) {
            if(!player.inGame())
                return "Join a game first!";
            else
            {player.notify("Give a position:");
                try {
                    DataInputStream input= new DataInputStream(player.getSocket().getInputStream());
                    int x,y;
                    x=input.readInt();
                    y=input.readInt();
                    player.submitMove(x,y);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            }



        //submit move

        // leave game?
        return "Comanda invalida";
    }
}