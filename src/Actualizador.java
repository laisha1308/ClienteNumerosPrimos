import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Actualizador extends Thread {
    Interfaz interfaz;
    Controlador controlador;

    Actualizador(Interfaz interfaz, Controlador controlador) {
        this.interfaz = interfaz;
        this.controlador = controlador;
    }

    public void run() {
        while (true) {
            try {
                if (controlador.verificarArregloAuxiliar()) {
                    if (controlador.verificarArregloFinal()) {
                        interfaz.actualizarAreaTodos(controlador.imprimir());
                    } else {
                        interfaz.actualizarAreaTodos(controlador.imprimir() + "\nEsperando al otro\nusuario");
                    }
                } else {
                    interfaz.actualizarAreaTodos("Esperando datos...");
                }
                sleep(15);
            } catch (RemoteException e) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
