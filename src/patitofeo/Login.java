package patitofeo;

import UpperEssential.UpperEssentialLookAndFeel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class Login extends javax.swing.JFrame {

    public static int familia; //Almacena el código de la familia.
    public static String user; //Recoge el nombre de usuario.

    public Login() {

        try {
            /*  Se indica el look and feel que por defecto tendrá el programa.
                La ventana no tiene bordes y está centrada */
            UIManager.setLookAndFeel(new UpperEssentialLookAndFeel());
            this.setUndecorated(true);
            initComponents();
            campos();
            bordes();
            logo();

            setLocationRelativeTo(null);

        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /*  Método que añade el texto por defecto de los campos, el tipo de fuente 
        creado y un text promt. Este último tiene la función de mostrar un texto
        por defecto en los campos cuando no hay nada escrito y que desaparece 
        al comenzar a rellenar los mismos. */
    public void campos() {
        Font f= new Font("Helvetica Neue", Font.PLAIN, 12);
        campoUsuario.setText("Usuario");
        campoUsuario.setForeground(Color.GRAY);

        campoUsuario.setFont(f);

        campoContra.setText("contra");

        new TextPrompt("Usuario", campoUsuario);
        new TextPrompt("Contraseña", campoContra);
    }
    
    /*  Método que añade bordes a los distintos paneles de la ventana 
        para que queden bien definidos. */
    public void bordes() {

        panelLogo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        panelUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    //Método que obtiene la imagen que se muestra en la cabecera de la ventana.
    public void logo() {
        ImageIcon ii = new ImageIcon("src/Recursos/logo.png");
        imagenLogo.setText("");
        imagenLogo.setIcon(ii);
        imagenLogo.requestFocus();
        //Pide el foco y así no queda seleccionado ningún campo de texto.
    }

    /*  Método que se lanza al pulsar el botón "Iniciar Sesión" y comprueba si
        el usuario y la contraseña son correctos. */
    public void autenticar() {
        //Se obtiene el texto introducido en ambos campos.
        String usuario = this.campoUsuario.getText();
        String pas = String.valueOf(this.campoContra.getPassword());
        //Se realiza la conexión a la base de datos    
        try {
            //Dirección del driver.
            Class.forName("com.mysql.jdbc.Driver");

            //Dirección de la base de datos.
            String url = "jdbc:mysql://localhost:3306/patitofeo";

            //Creación de una conexión a la bd a través del usuario root.
            Connection con = DriverManager.getConnection(url, "root", "");

            //Sentencia para realizar la consulta.
            Statement st = con.createStatement();
            //Se recogen los datos en un ResultSet.
            ResultSet rs = st.executeQuery("SELECT * FROM cuentas");

            /*  Se crean nuevos ArrayList para almacenar los diferentes datos 
                recibidos de las cuentas de usuario. */
            ArrayList<String> usuarios = new ArrayList<>();
            ArrayList<String> contras = new ArrayList<>();
            ArrayList<Integer> familias = new ArrayList<>();

            //Estos se rellenan mediante un bucle while.
            while (rs.next()) {
                usuarios.add(rs.getString("login"));
                contras.add(rs.getString("password"));
                familias.add(rs.getInt("familia"));

            }

            int upos = 0; //Es la posición del usuario en el ArrayList
            int tam = usuarios.size(); //Cantidad de usuarios.
            int i = 0; //Cuenta la cantidad de usuarios con los que 
            //no corresponde el introducido

            if (usuario.equals("Usuario")) { //Lo compara con el texto por defecto.

                i = tam; //Le decimos que tiene el número máximo, 
                //es decir, que no es válido

            } else {
                for (String u : usuarios) { //Compara el usuario con los de la Lista 

                    if (usuario.equals(u)) {
                        /*  Si es igual se obtiene el nº de la familia.
                            ·user se iguala al usuario introducido.
                            ·upos recoge el índice del usuario en la lista */
                        familia = familias.get(usuarios.indexOf(u));
                        user = u;
                        upos = usuarios.indexOf(u);

                    } else {
                        //No ha coincidido con ningún usuario y se incrementa 'i'.
                        i++;
                    }

                }

            }

            /*  Si la cantidad de usuarios con los que se coteja es igual a su 
                totalidad se muestra un mensaje indicando que 
                el usuario es incorrecto. */
            if (i == tam) {
                JOptionPane.showMessageDialog(this, "Usuario incorrecto",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                /*  Si ha coincidido con alguno, se continúa con la
                    comprobación en la lista de contraseñas.
                
                    La posición de la contraseña a comparar debe estar en el 
                    mismo índice (posición en la Lista) que el usuario
                
                    Si coinicide se inicia sesión. En caso contrario se muestra 
                    un mensaje de error.*/
                if (contras.get(upos).compareTo(pas) == 0) {
                    iniciarSesion();
                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            con.close(); //Se cierra la conexión

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Problema interno \n Disculpe las molestias", "Error", JOptionPane.WARNING_MESSAGE);

            Logger.getLogger(DatosDia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*  Método que se lanza la ser válidos usuario y contraseña.
        Lanza una barra de progreso y elimina la ventana de validación. */
    public void iniciarSesion() {
        new Carga().setVisible(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogo = new javax.swing.JPanel();
        imagenLogo = new javax.swing.JLabel();
        panelDatos = new javax.swing.JPanel();
        panelUsuario = new javax.swing.JPanel();
        campoUsuario = new javax.swing.JTextField();
        panelContra = new javax.swing.JPanel();
        campoContra = new javax.swing.JPasswordField();
        panelBoton = new javax.swing.JPanel();
        botonIniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(300, 600));
        setMinimumSize(new java.awt.Dimension(320, 450));
        setPreferredSize(new java.awt.Dimension(320, 450));
        getContentPane().setLayout(new java.awt.GridLayout(3, 0));

        panelLogo.setLayout(new java.awt.GridLayout(1, 0));

        imagenLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagenLogo.setText("jLabel1");
        panelLogo.add(imagenLogo);

        getContentPane().add(panelLogo);

        panelDatos.setLayout(new java.awt.GridLayout(0, 1));

        panelUsuario.setLayout(new java.awt.GridLayout(1, 0));

        campoUsuario.setToolTipText("");
        campoUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoUsuarioFocusLost(evt);
            }
        });
        campoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoUsuarioKeyPressed(evt);
            }
        });
        panelUsuario.add(campoUsuario);

        panelDatos.add(panelUsuario);

        panelContra.setLayout(new java.awt.GridLayout(1, 0));

        campoContra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoContraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoContraFocusLost(evt);
            }
        });
        campoContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoContraKeyPressed(evt);
            }
        });
        panelContra.add(campoContra);

        panelDatos.add(panelContra);

        getContentPane().add(panelDatos);

        panelBoton.setLayout(new java.awt.GridLayout(1, 0));

        botonIniciar.setText("Iniciar Sesión");
        botonIniciar.setMaximumSize(new java.awt.Dimension(40, 10));
        botonIniciar.setMinimumSize(new java.awt.Dimension(40, 10));
        botonIniciar.setPreferredSize(new java.awt.Dimension(40, 10));
        botonIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonIniciarMouseClicked(evt);
            }
        });
        botonIniciar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                botonIniciarKeyPressed(evt);
            }
        });
        panelBoton.add(botonIniciar);

        getContentPane().add(panelBoton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonIniciarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonIniciarMouseClicked
        autenticar(); //Evento del botón 'iniciar sesión' que llama a autenticar().
    }//GEN-LAST:event_botonIniciarMouseClicked

    private void botonIniciarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botonIniciarKeyPressed
        //Si se ha pulsado la tecla de retorno de carro se realiza la autentificación.
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            autenticar();
        }
    }//GEN-LAST:event_botonIniciarKeyPressed

    private void campoContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoContraKeyPressed
        //Igual que el anterior.
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            autenticar();
        }
    }//GEN-LAST:event_campoContraKeyPressed

    private void campoUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoUsuarioKeyPressed
        //Igual que el anterior.
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            autenticar();
        }
    }//GEN-LAST:event_campoUsuarioKeyPressed

    private void campoUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoUsuarioFocusGained
        //Cuando gana el foco y el texto es el de por defecto, se vacía.
        if (campoUsuario.getText().equals("Usuario")) {
            campoUsuario.setText("");
        }
    }//GEN-LAST:event_campoUsuarioFocusGained

    private void campoUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoUsuarioFocusLost
        //Si pierde el foco y el campo está vacío, se rellena con el texto por defecto.
        if (campoUsuario.getText().isEmpty()) {
            campoUsuario.setText("Usuario");
        }
    }//GEN-LAST:event_campoUsuarioFocusLost

    private void campoContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoContraFocusGained
        //Si gana el foco y es el texto por defecto, se vacía.
        if (String.valueOf(campoContra.getPassword()).equals("contra")) {
            campoContra.setText("");
        }
    }//GEN-LAST:event_campoContraFocusGained

    private void campoContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoContraFocusLost
        //Si pierde el foco y el campo está vacío, se rellena con el texto por defecto.
        if (String.valueOf(campoContra.getPassword()).isEmpty()) {
            campoContra.setText("contra");
        }
    }//GEN-LAST:event_campoContraFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonIniciar;
    private javax.swing.JPasswordField campoContra;
    private javax.swing.JTextField campoUsuario;
    private javax.swing.JLabel imagenLogo;
    private javax.swing.JPanel panelBoton;
    private javax.swing.JPanel panelContra;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelLogo;
    private javax.swing.JPanel panelUsuario;
    // End of variables declaration//GEN-END:variables
}
