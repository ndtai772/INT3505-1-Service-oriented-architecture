package week_02;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Server implements IOperations {
    private static final int PORT = 1099;
    private static final String REGISTRY_NAME = "operations";

    private static class PairImpl implements IPair {
        private final int sum, product;

        public PairImpl(int[] inputArr) {
            sum = Arrays.stream(inputArr).sum();
            product = Arrays.stream(inputArr).reduce(1, (a, b) -> a * b);
        }

        @Override
        public int getSum() throws RemoteException {
            return sum;
        }

        @Override
        public int getMul() throws RemoteException {
            return product;
        }
    }

    @Override
    public IPair getSumAndMul(int[] srcArr) throws RemoteException {
        return (IPair) UnicastRemoteObject.exportObject(new PairImpl(srcArr), 0);
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
