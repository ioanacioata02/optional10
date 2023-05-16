import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

    private boolean running = true;

    public void run() throws IOException {
        String serverAddress = "172.20.10.6"; // The server's IP address
        int PORT = 8100; // The server's port
        Scanner scanner = new Scanner(System.in);
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ) {
            ReadClientThread readClientThread = new ReadClientThread(this, in);
            readClientThread.start();
            while (running) {
                String line = scanner.nextLine();
                line = line.trim();
                if (line.equals("exit"))
                    running = false;
                if (line.equals("stop"))
                    running = false;
                System.out.println(line);
                out.println(line);
                out.flush();
            }

        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
            running = false;
        } catch (ConnectException e) {
            System.err.println(e);
            running = false;
        } catch (SocketException e) {
            System.err.println(e);
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}