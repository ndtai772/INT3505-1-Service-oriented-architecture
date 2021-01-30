package week_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    private final PrintWriter writer;


    public Client(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    public void doTask() throws IOException {
        while (true) {
            System.out.println("Input your array of integers: ");
            var input = keyboard.readLine();
            if (input.equals("q")) break;
            writer.println(input);
            System.out.println(reader.readLine());
        }
    }

    public static void main(String[] args) throws IOException {
        var client = new Client("localhost", 9000);
        client.doTask();
        client.close();
    }
}
