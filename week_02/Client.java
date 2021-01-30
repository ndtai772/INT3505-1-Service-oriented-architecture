package week_02;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static final int PORT = 1099;
    private static final String REGISTRY_NAME = "services";

    public static void main(String... args) throws RemoteException {
        System.out.println("[Client] Client start");
        Scanner scanner = new Scanner(System.in);
        IOperations servicesStub = null;

        while (servicesStub == null)
            try {
                servicesStub = (IOperations) LocateRegistry.getRegistry(PORT).lookup(REGISTRY_NAME);
            } catch (ConnectException e) {
                handlerConnectException();
            } catch (NotBoundException e) {
                System.out.println("[Client] Couldn't found any registry name " + REGISTRY_NAME);
                return;
            }
        System.out.println("[Client] Connected");

        while (true) {
            System.out.print("[Client] Input your array: ");
            String input = scanner.nextLine();
            if (input.equals("q")) break;
            var inputArr = Arrays.stream(input.split("\\s+")).mapToInt(Integer::parseInt).toArray();
            var response = servicesStub.getSumAndMul();
            System.out.printf("[Client] Response -> sum = %d, product = %d\n", response.getSum(inputArr), response.getMul(inputArr));
        }
        System.out.println("[Client] Quit");
    }

    private static void handlerConnectException() {
        System.out.println("[Client] Couldn't connect to server");
        try {
            Thread.sleep(50);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
