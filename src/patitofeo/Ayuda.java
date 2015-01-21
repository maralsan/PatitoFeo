package patitofeo;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Ayuda extends javax.swing.JFrame {

    /*Ventana que muestra la ayuda de la aplicación.*/
    public Ayuda() {
     
            initComponents();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            etiqueta.setIcon(new ImageIcon("src/Recursos/ayuda.png"));
            setTitle("Ayuda");
            setSize(835, 650);
            setLocationRelativeTo(null);
            setResizable(false);
            jScrollPane1.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.lightGray));
            jScrollPane1.setBackground(Color.white);        

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        etiqueta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(0, 1));

        jScrollPane1.setViewportView(etiqueta);

        getContentPane().add(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiqueta;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
