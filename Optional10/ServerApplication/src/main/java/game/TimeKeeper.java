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
        System.out.println("am pornit timekeeperul");
        long start = System.currentTimeMillis();
        while (running) {
            long period = System.currentTimeMillis() - start;
            System.out.println("Au trecut " + period + " ms de la inceputul explorarii");
            if (period >= limit) {
               player.setMyTurn(false);
                running = false;
                System.out.println("S-a terminat timpul alocat explorarii.Incheiem executia.");

            }

            try {
                sleep(8000);  //actualizam la 8 secunde perioada
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
