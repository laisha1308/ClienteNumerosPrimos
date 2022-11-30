import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interfaz extends JFrame implements ActionListener {
    private Controlador controlador;
    private Actualizador actualizador;
    private JButton botonSecuencial = new JButton("Secuencial");
    private JButton botonForkJoin = new JButton("Fork/Join");
    private JButton botonExecutor = new JButton("Executor");
    private JButton botonGuardar = new JButton("Guardar");
    private JButton botonLimpiar = new JButton("Limpiar");
    private JTextField escribir = new JTextField("");
    private JTextArea areaPrimos = new JTextArea("");
    private JTextArea areaTodos = new JTextArea("");
    private JLabel tiempoSecuencial = new JLabel("Tiempo: ");
    private JLabel tiempoForkJoin = new JLabel("Tiempo: ");
    private JLabel tiempoExecutor = new JLabel("Tiempo: ");
    private JLabel arregloCompleto = new JLabel("Todos: ");
    private JLabel arregloPrimos = new JLabel("Primos: ");

    public Interfaz(Controlador controlador) {
        this.controlador = controlador;
        this.actualizador = new Actualizador(this, controlador);
        setTitle("Numeros primos");
        setSize(500, 300);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(185,245,248));
        setLayout(null);
        texts();
        labels();
        buttons();
        botonSecuencial.addActionListener(this);
        botonForkJoin.addActionListener(this);
        botonExecutor.addActionListener(this);
        botonGuardar.addActionListener(this);
        botonLimpiar.addActionListener(this);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.actualizador.start();
    }
    public void texts() {
        escribir.setBounds(20,20,140,25);
        escribir.setEditable(true);
        add(escribir);

        areaTodos.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTodos);
        scroll.setBounds(20,85,140,125);
        add(scroll);

        areaPrimos.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(areaPrimos);
        scroll2.setBounds(180,85,140,125);
        add(scroll2);
    }

    public void buttons() {
        botonGuardar.setBounds(180,20,140,25);
        add(botonGuardar);

        botonLimpiar.setBounds(100,230,140,25);
        add(botonLimpiar);

        botonSecuencial.setBounds(350,20,100,25);
        add(botonSecuencial);

        botonForkJoin.setBounds(350,100,100,25);
        add(botonForkJoin);

        botonExecutor.setBounds(350,180,100,25);
        add(botonExecutor);
    }

    public void labels() {
        arregloCompleto.setBounds(20,60,140,25);
        add(arregloCompleto);

        arregloPrimos.setBounds(180,60,140,25);
        add(arregloPrimos);

        tiempoSecuencial.setBounds(350,60,150,20);
        add(tiempoSecuencial);

        tiempoForkJoin.setBounds(350,140,150,20);
        add(tiempoForkJoin);

        tiempoExecutor.setBounds(350,220,150,20);
        add(tiempoExecutor);
    }

    public void actualizarAreaTodos(String todos) {
        areaTodos.setText(todos);
    }

    private void limpiar() {
        escribir.setText("");
        areaPrimos.setText("");
        areaTodos.setText("");
        tiempoSecuencial.setText("Tiempo: ");
        tiempoForkJoin.setText("Tiempo: ");
        tiempoExecutor.setText("Tiempo: ");
        botonGuardar.setText("Guardar");
        escribir.setEditable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            try {
                controlador.limpiar();
                limpiar();
            } catch (RemoteException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == botonGuardar) {
            try {
                controlador.guardar(Integer.parseInt(escribir.getText()));
                escribir.setEditable(false);
                botonGuardar.setText("Rehacer");
                areaPrimos.setText("");
            } catch(NumberFormatException nfe) {
                limpiar();
                JOptionPane.showMessageDialog(null, "Error, no es un valor numerico");
            } catch (RemoteException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == botonSecuencial) {
            long tiempoEjecucion = 0;
            try {
                if (controlador.verificarArregloFinal()) {
                    areaPrimos.setText(controlador.buscarSecuencial());
                    tiempoEjecucion = controlador.getTiempo();
                    if (tiempoEjecucion >= 1000) {
                        tiempoSecuencial.setText("Tiempo: " + (tiempoEjecucion / 1000) + " s");
                    } else {
                        tiempoSecuencial.setText("Tiempo: " + tiempoEjecucion + " ms");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Esperando al otro usuario");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == botonForkJoin) {
            long tiempoEjecucion = 0;
            try {
                if (controlador.verificarArregloFinal()) {
                    areaPrimos.setText(controlador.buscarForkJoin());
                    tiempoEjecucion = controlador.getTiempo();
                    if (tiempoEjecucion >= 1000) {
                        tiempoForkJoin.setText("Tiempo: " + (tiempoEjecucion / 1000) + " s");
                    } else {
                        tiempoForkJoin.setText("Tiempo: " + tiempoEjecucion + " ms");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Esperando al otro usuario");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == botonExecutor) {
            long tiempoEjecucion = 0;
            try {
                if (controlador.verificarArregloFinal()) {
                    areaPrimos.setText(controlador.buscarExecutor());
                    tiempoEjecucion = controlador.getTiempo();
                    if (tiempoEjecucion >= 1000) {
                        tiempoExecutor.setText("Tiempo: " + (tiempoEjecucion / 1000) + " s");
                    } else {
                        tiempoExecutor.setText("Tiempo: " + tiempoEjecucion + " ms");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Esperando al otro usuario");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
