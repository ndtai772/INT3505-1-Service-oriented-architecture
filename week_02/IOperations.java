package week_02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOperations extends Remote {
    Pair getSumAndMul() throws RemoteException;
}
