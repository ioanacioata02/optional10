import game.Game;
import game.Player;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                System.out.println("Received " + request);
                if (request.equals("exit"))
                    running = false;
                else if (request.equals("stop") || !GameServer.isRunning()) {
                    player.notify("Server stopped");
                    GameServer.setRunning(false);
                    running = false;
                } else {
                    processRequest(request);
                    //raspuns = "Server received the request ... " + request;
                }
                // Send the response to the oputput stream: server → client
                //out.println(raspuns);
                //out.flush();
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


    private void processRequest(String request) throws IOException {
        if (request.equals("create game")) {
            synchronized (gameServer.games) {
                gameServer.addGame(new Game());
            }
            player.notify("new Game created");
        }

        else if (request.equals("join game")) {
            if(player.inGame())
                player.notify("Already joined a game");
            Game game;
            synchronized (gameServer.games) {
                game = gameServer.getFirstAvailableGame();
                if (game != null)
                    game.addPlayer(player);
            }
            if (game == null)
                player.notify("no available game. try to create one");

        }
        else if (request.startsWith("submit move")) {
            if(!player.inGame())
                player.notify("Join a game first!");
            else
            {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(request);
                int k = 0;
                int[] coord = new int[2];
                while (matcher.find()) {
                    if(k == 2) {
                        player.notify("Comanda invalida");
                        return;
                    }
                    coord[k] = Integer.parseInt(matcher.group());
                }
                player.submitMove(coord[0], coord[1]);
            }
            }
        else
            player.notify("Comanda invalida");
    }
}