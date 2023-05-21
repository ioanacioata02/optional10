package com.example.demo;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private static int nextAvailableId = 1;
    private int id;
    private Board board = new Board();
    private Player[] players = new Player[2];
    public Game() {
    }

    public boolean isAvailable(){
        return players[0]==null || players[1]==null;
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public static int incrementNr(){
        return nextAvailableId++;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", board=" + board +
                ", players=" + Arrays.toString(players) +
                '}';
    }
}
