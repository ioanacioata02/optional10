package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GameServer {
    protected final List<Game> games = new ArrayList<>();

    public GameServer() {

    }
    public static void main(String[] args) {
        SpringApplication.run(GameServer.class, args);
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