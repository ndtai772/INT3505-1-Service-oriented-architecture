package week_02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPair extends Remote {
    int getSum() throws RemoteException;
    int getMul() throws RemoteException;
}
