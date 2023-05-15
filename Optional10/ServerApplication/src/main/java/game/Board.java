package game;

public class Board {
    private final int[][] board = new int[15][15]; // -1=alb 1=negru 0=liber

    public boolean submitMove(int x, int y, int color) {
        if (x < 0 || x > 14 || y < 0 || y > 14) {
            return false;
        }
        if (board[x][y] == 0) {
            board[x][y] = color;
            return true;
        }
        return false; // deja se afla o piesa aici
    }

    public boolean checkWin(int player) {
        if (checkHorizontal(player) || checkVertical(player) || checkDiagonal(player))
            return true;
        return false;

    }

    public boolean checkHorizontal(int val) {
        for (int i = 0; i < 14; i++) {
            int nr = 0;
            for (int j = 0; j < 14; j++)
                if (board[i][j] ==val)
                    nr++;
                else nr = 0;
            if (nr == 5)
                return true;
        }
        return false;
    }

    public boolean checkVertical(int val) {
        for (int j = 0; j < 14; j++) {
            int nr = 0;
            for (int i = 0; i < 14; i++)
                if (board[i][j] ==val)
                    nr++;
                else nr = 0;
            if (nr == 5)
                return true;
        }
        return false;
    }

    public boolean checkDiagonal(int value) {
        for (int i = 0; i <=10; i++) {
            for (int j = 0; j <= 10; j++) {
              int nr=0;
                for (int k = 0; k < 5; k++) {
                    if (board[i+k][j+k] == value)
                        nr++;

                    else nr=0;
                }
                if (nr==5) {
                    return true;
                }
            }
        }
        return false;
    }
    public String display() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++)
        sb.append(board[i][j]).append(" ");
        sb.append("\n");
        }
        return sb.toString();
    }

}
