package week_02;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Server implements IOperations {
    private static final int PORT = 1099;
    private static final String REGISTRY_NAME = "services";

    @Override
    public Pair getSumAndMul() throws RemoteException {
        return (Pair) UnicastRemoteObject.exportObject(new Pair() {
            @Override
            public int getSum(int[] srcArr) {
                return Arrays.stream(srcArr).sum();
            }

            @Override
            public int getMul(int[] srcArr) {
                return Arrays.stream(srcArr).reduce(1, (a, b) -> a * b);
            }
        }, 0);
    }

    public static void main(String... args) {
        try {
            System.out.println("[Server] Server start");
            IOperations servicesStub = (IOperations) UnicastRemoteObject.exportObject(new Server(), 0);
            LocateRegistry.createRegistry(PORT).rebind(REGISTRY_NAME, servicesStub);
            System.out.println("[Server] Server ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
