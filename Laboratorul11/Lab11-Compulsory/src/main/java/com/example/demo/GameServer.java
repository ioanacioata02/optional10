package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GameServer {
    // Define the port on which the server is listening
    private static final int PORT = 8100;
    private static boolean running = true;
    protected final List<Game> games = new ArrayList<>();

    public GameServer(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName("127.0.0.1"));

            serverSocket.setSoTimeout(3000); // 3 sec
            while (running) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    continue;
                }
                System.out.println("Client accepted ...");
                // Execute the client's request in a new thread
                if(running)
                    new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Ooops... " + e);
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(GameServer.class, args);
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