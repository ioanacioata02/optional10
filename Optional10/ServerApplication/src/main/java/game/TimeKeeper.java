package game;

import game.Player;

public class TimeKeeper extends Thread {
    private long limit;
    private boolean running = true;
   private Player player;

    public TimeKeeper(long timeLimit, Player player) {

        this.limit = timeLimit;

    }

    @Override
    public void run() {
        /*
        System.out.println("am pornit timekeeperul");
        long start = System.currentTimeMillis();
        while (running) {
            long period = System.currentTimeMillis() - start;
            if (period >= limit) {
               player.setMyTurn(false);
                running = false;
            }

            try {
                sleep(8000);  //actualizam la 8 secunde perioada
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
        */
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
