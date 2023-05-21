package com.example.demo;

import java.util.Arrays;

public class Board {
    private final int[][] board = new int[15][15]; // -1=alb 1=negru 0=liber

    @Override
    public String toString()  {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 14; i++) {
            for (int j = 0; j <= 14; j++)
                sb.append(board[i][j]).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public int[][] getBoard() {
        return board;
    }
}
