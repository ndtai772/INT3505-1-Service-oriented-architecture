package week_02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Pair extends Remote {
    int getSum(final int[] srcArr) throws RemoteException;
    int getMul(final int[] srcArr) throws RemoteException;
}
