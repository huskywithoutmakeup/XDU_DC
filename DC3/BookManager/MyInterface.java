package BookManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyInterface extends Remote{
    boolean add(Book b) throws RemoteException;
    Book queryByID(int bookID) throws RemoteException;
    BookList queryByName(String name) throws RemoteException;
    boolean delete(int bookID) throws RemoteException;
    boolean setNum(int bookID,int bookNum) throws RemoteException;
    String showInfo() throws RemoteException;
}