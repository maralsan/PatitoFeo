package patitofeo;

import UpperEssential.UpperEssentialLookAndFeel;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import suavena.RubberBorderCrystal;

public final class DatosDia extends javax.swing.JFrame {

    public static String diaSel, titulo;
    public static Date fSel;
    public static String[] datos;
    public static int familia = Login.familia;
    //public static int id_item;
    /*  Creación de una conexión a la base de datos de la familia en concreto
     y la obtención de los items del día indicado en un Array. */
    Conexion cc = new Conexion(familia);

    public DatosDia() {
        try {
            UIManager.setLookAndFeel(new UpperEssentialLookAndFeel());
            setPreferredSize(new java.awt.Dimension(600, 400));
            setMinimumSize(new java.awt.Dimension(600, 400));

            initComponents();
            bordes();
            estiloTexto();
            fecha();
            datos();
            setAlwaysOnTop(true);

            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            setLocationRelativeTo(null);

            botonActualizar.setEnabled(false);

        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DatosDia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*  Método que añade bordes a los paneles y etiquetas de la ventana
     Las etiquetas emplean un borde a partir del jar EWE OU */
    public void bordes() {

        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tituloComida.setBorder(new RubberBorderCrystal(true, true, true, true));
        tituloDeposiciones.setBorder(new RubberBorderCrystal(true, true, true, true));
        tituloDescanso.setBorder(new RubberBorderCrystal(true, true, true, true));
        tituloObservaciones.setBorder(new RubberBorderCrystal(true, true, true, true));
        tituloAutorizaciones.setBorder(new RubberBorderCrystal(true, true, true, true));

        comida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        deposiciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        descanso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        observaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ScrollAutorizaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }

    /*  Añade line wrap a los textos para que se 'enrrollen' al redimensionar la 
     ventana. La caja de texto para las autorizaciones incluye 'wrapStyleWord',
     que sirve para saltar de linea la palabra completa y no letra a letra. */
    public void estiloTexto() {

        comida.setLineWrap(true);
        deposiciones.setLineWrap(true);
        descanso.setLineWrap(true);
        observaciones.setLineWrap(true);
        autorizaciones.setLineWrap(true);
        autorizaciones.setWrapStyleWord(true);
    }

    /*  Método que, a partir de la fecha seleccionada en el calendario, la separa 
     en sus distintos componentes (día, mes y año) para darles formato */
    public void fecha() {
        try {
            String[] fecha = VPrincipal.calendario.getDate(); //Fecha seleccionada en el calendario.
            int a, m, d; //Variables que recogen año, mes y día del Array. 
            String anio, mes, dia, fechaSel;

            /*  Se obtiene el valor numérico para poder manipular las fechas,
             debido a que meses y días comienzan en 0 y hay que sumarles +1 */
            a = Integer.parseInt(fecha[0]);
            m = Integer.parseInt(fecha[1]) + 1;
            d = Integer.parseInt(fecha[2]) + 1;

            /*Se recoge el valor actualizado para poder darle el formato adecuado
             y trabajar con la fecha dada. */
            anio = String.valueOf(a);
            mes = String.valueOf(m);
            dia = String.valueOf(d);
            fechaSel = anio + "-" + mes + "-" + dia; //Cadena con la fecha.

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//Formateador.

            //Se recoge la fecha como sql.Date para poder trabajar con la base de datos.
            fSel = new java.sql.Date(sdf.parse(fechaSel).getTime());

            //El título del frame indica la fecha consultada.
            titulo = new SimpleDateFormat("dd/MM/yyyy").format(fSel);
            setTitle(titulo);

        } catch (ParseException ex) {
            Logger.getLogger(DatosDia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*  Método que obtiene los datos del día seleccionado a partir de la fecha
     seleccionada en el método anterior*/
    public void datos() {
        String d = "Nada anotado"; //Texto que se muestra por defecto.

        //Se rellena el Array con los items de la fecha indicada.
        datos = cc.items(fSel);

        /*Mediante un condicional if-else comprobamos si hay datos en los
         diferentes apartados. En caso negativo se muestra el texto por defecto*/
        if (datos[1].isEmpty()) {
            comida.setText(d);
        } else {
            comida.setText(datos[1]);

        }
        if (datos[2].isEmpty()) {
            deposiciones.setText(d);
        } else {
            deposiciones.setText(datos[2]);

        }
        if (datos[3].isEmpty()) {
            descanso.setText(d);
        } else {
            descanso.setText(datos[3]);

        }
        if (datos[4].isEmpty()) {
            observaciones.setText(d);
        } else {
            observaciones.setText(datos[4]);

        }
        if (!datos[5].isEmpty()) {
            autorizaciones.setText(datos[5]);

        }

    }

    /*Método que se lanza al pulsar en el botón actualizar de la ventana*/
    public void actualizar() {
        //Se realiza la conexión a la base de datos
        String txt = "";

        try {
            //Dirección del driver.
            Class.forName("com.mysql.jdbc.Driver");

            //Dirección de la base de datos.
            String url = "jdbc:mysql://localhost:3306/patitofeo";

            //Creación de una conexión a la bd a través del usuario root.
            Connection c = DriverManager.getConnection(url, "root", "");

            Statement s = c.createStatement();
            //Se obtienen las autorizaciones para el día seleccionado.
            ResultSet items = s.executeQuery("SELECT * FROM items where id_niño=("
                    + "SELECT id_niño FROM niños WHERE familia=" + familia + ")"
                    + " AND fecha='" + fSel + "' AND tipo=5");
            while (items.next()) {
                txt = items.getString("descripcion");

            }

            if (txt.isEmpty()) {//En la base de datos no se reflejan datos para ese día.
                if (!autorizaciones.getText().isEmpty()) {//Pero en el TextArea hay texto
                    //Se realiza una inserción de los datos introducidos.
                    s.execute("INSERT INTO items VALUES (" + Conexion.id_item + ", '"
                            + fSel + "'," + 5 + ",'" + autorizaciones.getText() + "' ,"
                            + Conexion.id_niño + ")");

                    JOptionPane.showMessageDialog(this, "Ha añadido autorizaciones para el dia " + titulo,
                            "Dia actualizado", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    //Si no hay texto en el TextArea, muestra un panel de aviso.

                    JOptionPane.showMessageDialog(this, "No hay nada escrito");
                }

            } else {    //Si en la base de datos hay reflejado texto para ese día.
                if (!autorizaciones.getText().isEmpty()) {  //Y en el TextArea se ha escrito.
                    //Se realiza una Actualización de los datos.
                    PreparedStatement ps = c.prepareStatement("UPDATE items "
                            + "SET descripcion='" + autorizaciones.getText() + "' "
                            + "WHERE id_niño=(SELECT id_niño FROM niños "
                            + "WHERE familia=" + familia + ") "
                            + "AND fecha='" + fSel + "' AND tipo=5");
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Ha modificado las "
                            + "autorizaciones para el dia " + titulo, "Dia actualizado",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {//Si no hay texto quiere decir que se ha borrado.
                    //Se realiza una operación de borrado.
                    PreparedStatement borrar2 = c.prepareStatement(
                            "DELETE FROM items WHERE id_niño=? "
                            + "AND fecha=? AND tipo=5");
                    borrar2.setInt(1, Conexion.id_niño);
                    borrar2.setDate(2, fSel);
                    borrar2.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Ha eliminado las "
                            + "autorizaciones para el dia " + titulo,
                            "Dia actualizado", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatosDia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new javax.swing.JPanel();
        tituloComida = new javax.swing.JLabel();
        comida = new org.jdesktop.swingx.JXLabel();
        tituloDeposiciones = new javax.swing.JLabel();
        deposiciones = new org.jdesktop.swingx.JXLabel();
        tituloDescanso = new javax.swing.JLabel();
        descanso = new org.jdesktop.swingx.JXLabel();
        tituloObservaciones = new javax.swing.JLabel();
        observaciones = new org.jdesktop.swingx.JXLabel();
        panel2 = new javax.swing.JPanel();
        tituloAutorizaciones = new javax.swing.JLabel();
        ScrollAutorizaciones = new javax.swing.JScrollPane();
        autorizaciones = new org.jdesktop.swingx.JXTextArea();
        botonActualizar = new org.jdesktop.swingx.JXButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panel1.setLayout(new java.awt.GridLayout(8, 0));

        tituloComida.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tituloComida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloComida.setText("Comida");
        panel1.add(tituloComida);

        comida.setText("--");
        panel1.add(comida);

        tituloDeposiciones.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tituloDeposiciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloDeposiciones.setText("Deposiciones");
        panel1.add(tituloDeposiciones);

        deposiciones.setText("--");
        panel1.add(deposiciones);

        tituloDescanso.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tituloDescanso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloDescanso.setText("Descanso");
        tituloDescanso.setPreferredSize(new java.awt.Dimension(85, 16));
        panel1.add(tituloDescanso);

        descanso.setText("--");
        panel1.add(descanso);

        tituloObservaciones.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tituloObservaciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloObservaciones.setText("Observaciones");
        tituloObservaciones.setToolTipText("");
        panel1.add(tituloObservaciones);

        observaciones.setText("--");
        panel1.add(observaciones);

        getContentPane().add(panel1);

        panel2.setLayout(new java.awt.BorderLayout());

        tituloAutorizaciones.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tituloAutorizaciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloAutorizaciones.setText("Autorizaciones");
        panel2.add(tituloAutorizaciones, java.awt.BorderLayout.PAGE_START);

        autorizaciones.setColumns(20);
        autorizaciones.setRows(5);
        autorizaciones.setToolTipText("");
        autorizaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                autorizacionesKeyPressed(evt);
            }
        });
        ScrollAutorizaciones.setViewportView(autorizaciones);
        autorizaciones.getAccessibleContext().setAccessibleName("");

        panel2.add(ScrollAutorizaciones, java.awt.BorderLayout.CENTER);

        botonActualizar.setText("Actualizar");
        botonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarActionPerformed(evt);
            }
        });
        panel2.add(botonActualizar, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(panel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarActionPerformed
        //Acción realizada por el botón de actualizar.
        actualizar();

    }//GEN-LAST:event_botonActualizarActionPerformed

    private void autorizacionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_autorizacionesKeyPressed
        //Hasta que no se escriba en la caja de texto el botón no se activa.
        botonActualizar.setEnabled(true);

    }//GEN-LAST:event_autorizacionesKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollAutorizaciones;
    private org.jdesktop.swingx.JXTextArea autorizaciones;
    private org.jdesktop.swingx.JXButton botonActualizar;
    private org.jdesktop.swingx.JXLabel comida;
    private org.jdesktop.swingx.JXLabel deposiciones;
    private org.jdesktop.swingx.JXLabel descanso;
    private org.jdesktop.swingx.JXLabel observaciones;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JLabel tituloAutorizaciones;
    private javax.swing.JLabel tituloComida;
    private javax.swing.JLabel tituloDeposiciones;
    private javax.swing.JLabel tituloDescanso;
    private javax.swing.JLabel tituloObservaciones;
    // End of variables declaration//GEN-END:variables
}
