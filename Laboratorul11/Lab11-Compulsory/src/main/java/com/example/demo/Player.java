package com.example.demo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
    private static int nextAvailableId = 1;
    private int color; // 1 sau -1
    @JsonIgnore
    private Socket socket = null;
    private Game game = null;
    private int id;
    private String name = null;
    private boolean active = false;
    public Player() {
    }


    public Player(int color, String name) {
        this.color = color;
        this.name = name;
        this.id = nextAvailableId++;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void submitMove(int x, int y) throws IOException {
        if(!isPlayerTurn()){
            this.notify("Not your turn!");
            return;
        }
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

    public void notify(String message) throws IOException {
        //System.out.println(socket + " " + message);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(message);
        out.flush();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Player{" +
                "color=" + color +
                ", socket=" + socket +
                ", game=" + game +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }

    public static int incrementNr(){
        return nextAvailableId++;
    }
}
