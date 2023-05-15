package game;

public class Board {
    private final int[][] board = new int[15][15]; // -1=alb 1=negru 0=liber

    public boolean submitMove(int x, int y, int color){
        if(x<0 || x > 14 || y<0 || y > 14) {
            return false;
        }
        if(board[x][y] == 0) {
            board[x][y] = color;
            return true;
        }
        return false; // deja se afla o piesa aici
    }
}
