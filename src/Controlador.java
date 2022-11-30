import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Controlador extends Remote {
    void guardar(int largo) throws RemoteException;
    String imprimir() throws RemoteException;
    void limpiar() throws RemoteException;
    String buscarSecuencial() throws RemoteException;
    String buscarForkJoin() throws RemoteException;
    String buscarExecutor() throws RemoteException;
    long getTiempo() throws RemoteException;
    boolean verificarArregloFinal() throws RemoteException;
    boolean verificarArregloAuxiliar() throws RemoteException;
}
