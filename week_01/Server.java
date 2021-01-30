package week_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ServerSocket listener;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public Server(int port) throws IOException {
        listener = new ServerSocket(port);
    }

    private static String getSumAndProduct(String input) {
        int sum = 0, product = 1;
        for (String s : input.split("\\s+")) {
            int i = Integer.parseInt(s);
            sum += i;
            product *= i;
        }
        return String.format("Sum: %d, Product: %d", sum, product);
    }

    public void start() throws IOException {
        while (true) {
            var client = listener.accept();
            threadPool.execute(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                    while (true) {
                        var input = reader.readLine();
                        if (input == null) break;
                        printlnMessage("received: " + input);
                        writer.println(getSumAndProduct(input));
                        printlnMessage("sent response");
                    }
                    printlnMessage("A client closed the connect!");
                    client.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
    }

    private static void printlnMessage(String message) {
        System.out.printf("[Server - threadId %d] %s\n", Thread.currentThread().getId(), message);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(9000);
        server.start();
    }
}
