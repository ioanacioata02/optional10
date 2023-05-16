import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadClientThread extends Thread {
    GameClient gameClient;
    BufferedReader in;

    ReadClientThread(GameClient gameClient, BufferedReader in) {
        this.gameClient = gameClient;
        this.in = in;
    }

    @Override
    public void run() {
        while (gameClient.isRunning()) {
            String response = null;
            try {
                response = in.readLine();
            } catch (IOException e) {
                System.err.println(e);
                //gameClient.setRunning(false);
            }
            System.out.println(response);
        }
    }
}
