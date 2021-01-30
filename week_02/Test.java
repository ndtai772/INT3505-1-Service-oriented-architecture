package week_02;

import java.rmi.RemoteException;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        var server =  new Thread(Server::main);
        var client = new Thread(() -> {
            try {
                Client.main();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        server.start();
        client.start();
        client.join();
        System.exit(0);
    }

}
