package patitofeo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import suavena.RubberBorderCrystal;

public final class VPrincipal extends javax.swing.JFrame {

    int familia= Login.familia; //Obtiene el código de la familia del Login.
    String user= Login.user; //Obtiene el nombre de usuario.
    Conexion cc = new Conexion(familia); //Se crea una nueva conexión a la bd con el usuario.
    RubberBorderCrystal bordeTitulo;
    Font Helvetica_18P, Helvetica_24B;

    public VPrincipal() {
        
        //Se crean las fuentes y bordes más empleados.
        this.Helvetica_18P = new Font("Helvetica Neue", Font.PLAIN, 18);
        this.Helvetica_24B = new Font("Helvetica Neue", Font.BOLD, 24);
        this.bordeTitulo = new RubberBorderCrystal(true, true, false, false);

        //Se obtiene la dimensión de la pantalla del usuario.
        java.awt.Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        
        //Se da un tamaño relativo a la ventana de la aplicación.
        getContentPane().setPreferredSize(new java.awt.Dimension((pantalla.width - 400),(pantalla.height - 200)));
        
        initComponents();
        this.setResizable(false); //No se puede reajustar.
        setLocationRelativeTo(null); //Se centra en la pantalla.
        tabs();
        perfil();
        mascota();
        bordesCal();
        usuario();
    }

    /*Añade fuente y títulos a las pestañas*/
    public void tabs(){
    panelTabs.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        panelTabs.setTitleAt(0, "Perfil");
        panelTabs.setTitleAt(1, "Calendario");
        panelTabs.setTitleAt(2, "Mascota");
        panelTabs.setTitleAt(3, user); // Se nombra como el usuario.
    }
    
    /*Edita la pestaña del perfil*/
    public void perfil() {

        //Se añaden los bordes adecuados.
        panelDatosN.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        tituloNombreA.setBorder(bordeTitulo);
        tituloEdadA.setBorder(bordeTitulo);

        //Especificamos una nueva fuente.
        tituloNombreA.setFont(Helvetica_24B);
        nombre.setFont(Helvetica_18P);
        tituloEdadA.setFont(Helvetica_24B);
        edad.setFont(Helvetica_18P);

        foto.setIcon(cc.imagenAlumno()); //Se añade la foto del alumno.       
        nombre.setText(cc.nombreN);      //Indicamos el nombre.
        int ed = cc.edadAlumno();        //Edad del alumno.

        //Se compara la edad para indicar si son meses, año o años.
        if (ed > 1) {
            this.edad.setText(ed + " años");
        } else if (ed == 1) {
            this.edad.setText(ed + " año");
        } else {
            this.edad.setText(ed + " meses");
        }
    }
    
    /*Edita la pestaña de la mascota*/
    public void mascota() {
        
        this.fotoM.setIcon(cc.imagenMascota());//Imagen de la mascota
        
        //Se añaden los bordes.
        panelDatosM.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        tituloNombreM.setBorder(bordeTitulo);
        tituloEdadM.setBorder(bordeTitulo);
        
        //Se indican las fuentes.
        tituloNombreM.setFont(Helvetica_24B);
        nombreM.setFont(Helvetica_18P);        
        tituloEdadM.setFont(Helvetica_24B);
        edadM.setFont(Helvetica_18P);
        
        this.nombreM.setText(cc.nombreM); //Nombre de la mascota.
        
        //Se comprueba si se indica año o años.
        String e = " años";
        if (cc.edadM == 1) {
            e = " año";
        }
        this.edadM.setText(String.valueOf(cc.edadM) + e); //Edad de la mascota en texto.

    }
    
    /*Añade bordes a la pestaña del Calendario y al Calendario*/
    public void bordesCal(){
        panelMF.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        calendario.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
    }
    
    public void usuario() {
        //Se añaden bordes a los campos y a los paneles.
        panelTituloDatos.setBorder(BorderFactory.createEmptyBorder(90, 200, 90, 200));
        panelDatosPadres.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelDC.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        panelContacto.setBorder(new RubberBorderCrystal(false, false, true, false));
        tituloDatos.setBorder(new RubberBorderCrystal(true, true, true, true));
        tituloMadre.setBorder(bordeTitulo);
        tituloPadre.setBorder(bordeTitulo);
        
        //Se indica un alineamiento horizontal.
        tituloMadre.setHorizontalAlignment(JLabel.CENTER);
        tituloPadre.setHorizontalAlignment(JLabel.CENTER);
        direccion.setHorizontalAlignment(JLabel.CENTER);
        tlf1.setHorizontalAlignment(JLabel.CENTER);
        tlf2.setHorizontalAlignment(JLabel.CENTER);
        email.setHorizontalAlignment(JLabel.CENTER);
        
        //Se añaden las diferentes fuentes.
        tituloDatos.setFont(new Font("Helvetica Neue", Font.BOLD, 36));
        tituloMadre.setFont(Helvetica_24B);
        tituloPadre.setFont(Helvetica_24B);
        madre.setFont(Helvetica_18P);
        padre.setFont(Helvetica_18P);
        direccion.setFont(Helvetica_18P);
        tlf1.setFont(Helvetica_18P);
        tlf2.setFont(Helvetica_18P);
        email.setFont(Helvetica_18P);

        //Se añade el texto a los diferentes campos.
        tituloMadre.setText("Madre");
        tituloPadre.setText("Padre");
        madre.setText(cc.nombreMa);
        padre.setText(cc.nombrePa);
        direccion.setText(cc.direccion);
        tlf1.setText(cc.tlf);
        tlf2.setText(cc.tlf2);
        email.setText(cc.email);      
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTabs = new javax.swing.JTabbedPane();
        panelPerfil = new javax.swing.JPanel();
        panelFotoN = new javax.swing.JPanel();
        foto = new javax.swing.JLabel();
        panelDatosN = new javax.swing.JPanel();
        tituloNombreA = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        tituloEdadA = new javax.swing.JLabel();
        edad = new javax.swing.JLabel();
        panelCalendario = new javax.swing.JPanel();
        calendario = new suavena.WCalendar();
        panelMF = new javax.swing.JPanel();
        mostrarFecha = new javax.swing.JButton();
        panelMascota = new javax.swing.JPanel();
        panelFotoM = new javax.swing.JPanel();
        fotoM = new javax.swing.JLabel();
        panelDatosM = new javax.swing.JPanel();
        tituloNombreM = new javax.swing.JLabel();
        nombreM = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        tituloEdadM = new javax.swing.JLabel();
        edadM = new javax.swing.JLabel();
        panelUsuario = new javax.swing.JPanel();
        panelTituloDatos = new javax.swing.JPanel();
        tituloDatos = new javax.swing.JLabel();
        panelDC = new javax.swing.JPanel();
        panelDatosPadres = new javax.swing.JPanel();
        tituloMadre = new javax.swing.JLabel();
        madre = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        tituloPadre = new javax.swing.JLabel();
        padre = new javax.swing.JLabel();
        panelContacto = new javax.swing.JPanel();
        direccion = new javax.swing.JLabel();
        tlf1 = new javax.swing.JLabel();
        tlf2 = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Patito Feo");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(0, 1));

        panelTabs.setToolTipText("");
        panelTabs.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        panelPerfil.setLayout(new java.awt.GridLayout(2, 0));

        panelFotoN.setLayout(new java.awt.BorderLayout());

        foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelFotoN.add(foto, java.awt.BorderLayout.CENTER);

        panelPerfil.add(panelFotoN);

        panelDatosN.setLayout(new java.awt.GridLayout(0, 1));

        tituloNombreA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloNombreA.setText("Nombre");
        panelDatosN.add(tituloNombreA);

        nombre.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        nombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre.setText("jLabel1");
        panelDatosN.add(nombre);
        panelDatosN.add(filler3);

        tituloEdadA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloEdadA.setText("Edad");
        panelDatosN.add(tituloEdadA);

        edad.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        edad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        edad.setText("jLabel2");
        panelDatosN.add(edad);

        panelPerfil.add(panelDatosN);

        panelTabs.addTab("", panelPerfil);

        panelCalendario.setLayout(new javax.swing.BoxLayout(panelCalendario, javax.swing.BoxLayout.Y_AXIS));
        panelCalendario.add(calendario);

        panelMF.setLayout(new javax.swing.BoxLayout(panelMF, javax.swing.BoxLayout.LINE_AXIS));

        mostrarFecha.setText("Ir");
        mostrarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarFechaActionPerformed(evt);
            }
        });
        panelMF.add(mostrarFecha);

        panelCalendario.add(panelMF);

        panelTabs.addTab("", panelCalendario);

        panelMascota.setLayout(new java.awt.GridLayout(2, 0));

        panelFotoM.setLayout(new java.awt.BorderLayout());

        fotoM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelFotoM.add(fotoM, java.awt.BorderLayout.CENTER);

        panelMascota.add(panelFotoM);

        panelDatosM.setLayout(new java.awt.GridLayout(0, 1));

        tituloNombreM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloNombreM.setText("Nombre");
        panelDatosM.add(tituloNombreM);

        nombreM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreM.setText("jLabel2");
        panelDatosM.add(nombreM);
        panelDatosM.add(filler2);

        tituloEdadM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloEdadM.setText("Edad");
        panelDatosM.add(tituloEdadM);

        edadM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        edadM.setText("jLabel3");
        panelDatosM.add(edadM);

        panelMascota.add(panelDatosM);

        panelTabs.addTab("", panelMascota);

        panelUsuario.setLayout(new java.awt.GridLayout(2, 2));

        panelTituloDatos.setLayout(new java.awt.GridLayout(1, 0));

        tituloDatos.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        tituloDatos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloDatos.setText("Datos Personales");
        panelTituloDatos.add(tituloDatos);

        panelUsuario.add(panelTituloDatos);

        panelDC.setLayout(new java.awt.GridLayout(0, 2));

        panelDatosPadres.setLayout(new java.awt.GridLayout(5, 0));

        tituloMadre.setText("jLabel1");
        panelDatosPadres.add(tituloMadre);

        madre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        madre.setText("jLabel1");
        panelDatosPadres.add(madre);
        panelDatosPadres.add(filler1);

        tituloPadre.setText("jLabel2");
        panelDatosPadres.add(tituloPadre);

        padre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        padre.setText("jLabel2");
        panelDatosPadres.add(padre);

        panelDC.add(panelDatosPadres);

        panelContacto.setLayout(new java.awt.GridLayout(4, 0));

        direccion.setText("jLabel3");
        panelContacto.add(direccion);

        tlf1.setText("jLabel4");
        panelContacto.add(tlf1);

        tlf2.setText("jLabel5");
        panelContacto.add(tlf2);

        email.setText("jLabel6");
        panelContacto.add(email);

        panelDC.add(panelContacto);

        panelUsuario.add(panelDC);

        panelTabs.addTab("", panelUsuario);

        getContentPane().add(panelTabs);
        getContentPane().add(panelTabs, BorderLayout.CENTER);

        jMenu1.setText("Patito Feo");

        jMenuItem1.setText("Acerca de Patito Feo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Ayuda");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Cerrar Sesión");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Salir");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarFechaActionPerformed
        /*Acción del botón mostrarFecha del Calendario. 
        Muestra una ventana con los datos del día seleccionado. */
        new DatosDia().setVisible(true);
    }//GEN-LAST:event_mostrarFechaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //Muestra una ventana que muestra información de la aplicación.
        String mensaje="Aplicación para la guardería Patito Feo. \n Desarrollado por Martín Alonso Sánchez";
        JOptionPane.showMessageDialog(this, mensaje, "Acerca de Patito Feo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        //Cierra la aplicación al seleccionar 'Salir' en la barra de menús.
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       //Muestra la página de ayuda.
       new Ayuda().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        //Acción al pulsar en el menú 'Cerrar Sesión'. Muestra la ventana de login.
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
         /*Al pulsar en el botón para cerrar la ventana muestra un diálogo 
            para verificar si el usuario realmente quiere salir de la aplicación.*/
        int confirm = JOptionPane.showOptionDialog(this, "¿Desea salir de la aplicación?",
                 "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == 0) {
                   System.exit(0);
                }
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static suavena.WCalendar calendario;
    private javax.swing.JLabel direccion;
    private javax.swing.JLabel edad;
    private javax.swing.JLabel edadM;
    private javax.swing.JLabel email;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel foto;
    private javax.swing.JLabel fotoM;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel madre;
    private javax.swing.JButton mostrarFecha;
    private javax.swing.JLabel nombre;
    private javax.swing.JLabel nombreM;
    private javax.swing.JLabel padre;
    private javax.swing.JPanel panelCalendario;
    private javax.swing.JPanel panelContacto;
    private javax.swing.JPanel panelDC;
    private javax.swing.JPanel panelDatosM;
    private javax.swing.JPanel panelDatosN;
    private javax.swing.JPanel panelDatosPadres;
    private javax.swing.JPanel panelFotoM;
    private javax.swing.JPanel panelFotoN;
    private javax.swing.JPanel panelMF;
    private javax.swing.JPanel panelMascota;
    private javax.swing.JPanel panelPerfil;
    private javax.swing.JTabbedPane panelTabs;
    private javax.swing.JPanel panelTituloDatos;
    private javax.swing.JPanel panelUsuario;
    private javax.swing.JLabel tituloDatos;
    private javax.swing.JLabel tituloEdadA;
    private javax.swing.JLabel tituloEdadM;
    private javax.swing.JLabel tituloMadre;
    private javax.swing.JLabel tituloNombreA;
    private javax.swing.JLabel tituloNombreM;
    private javax.swing.JLabel tituloPadre;
    private javax.swing.JLabel tlf1;
    private javax.swing.JLabel tlf2;
    // End of variables declaration//GEN-END:variables
}
