package week_02;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.stream.IntStream;

public interface IOperations extends Remote {
    IPair getSumAndMul(int[] srcArr) throws RemoteException;
}
