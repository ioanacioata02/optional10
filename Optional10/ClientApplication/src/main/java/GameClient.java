import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

    private boolean running = true;

    public void run() throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        Scanner scanner = new Scanner(System.in);
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
            while (running) {
                String line = scanner.nextLine();
                line = line.trim();
                if (line.equals("exit"))
                    running = false;
                out.println(line);
                if (!line.isEmpty()) {
                    String response = in.readLine();
                    System.out.println(response);
                    if(response.equals("Give position:"))
                    {
                        int x=scanner.nextInt();
                        int y=scanner.nextInt();
                        DataOutputStream out2=new DataOutputStream(socket.getOutputStream());
                        out2.writeInt(x);
                        out2.writeInt(y);
                    }
                }
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
}