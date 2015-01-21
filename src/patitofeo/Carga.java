package patitofeo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public final class Carga extends javax.swing.JFrame {

    private Timer temporizador;
    int cont;
    public final static int mXp = 10; // Milisegundos por porcentaje

    /*Constructor de la barra de progreso. 
     Se eliminan los bordes. Se centra a la pantalla  y se llama al método 
     que inicia el temporizador. */
    public Carga() {
        this.setUndecorated(true);
        setLocationRelativeTo(null);
        initComponents();
        barra();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        barra = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panel.setLayout(new java.awt.GridLayout(1, 0));
        panel.add(barra);

        getContentPane().add(panel);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /*  TimeListener que contiene la acción que va a realizar el temporizador
     para la barra de progreso */

    class TimerListerner implements ActionListener {

        @Override
        /* En cuando se inicia el Timer creado: */
        public void actionPerformed(ActionEvent e) {
            cont++; //El contador empieza a aumentar.
            barra.setValue(cont); //La barra va incrementando de valor.

            /*  Cuando el valor ha llegado al máximo de 100, se detiene el Timer 
             y se llama al método mostrarApp()*/
            if (cont == 100) {
                temporizador.stop();
                mostrarApp();

            }
        }
    }

    /*  Método que da a la barra los valores establecidos y activa el Timer.
     El contador comienza en -1; El 1er valor que muestra es 0. */
    public void barra() {
        cont = -1;
        barra.setValue(0);
        barra.setStringPainted(true); // Indica que se colorea según incrementa el valor

        //Se un timer a partir del listener creado y de los mXP establecidos.
        temporizador = new Timer(mXp, new TimerListerner());
        activar();
    }

    // Inicia el temporizador;
    public void activar() {
        temporizador.start();
    }

    /* Se lanza cuando la barra de progreso se ha completado. */
    public void mostrarApp() {
        new VPrincipal().setVisible(true); //Crea una ventana principal.
        this.dispose(); //Elimina la barra de progreso.
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barra;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
