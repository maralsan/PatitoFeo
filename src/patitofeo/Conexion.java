package patitofeo;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Conexion {

    String url = "jdbc:mysql://localhost:3306/patitofeo";
    static Connection c;

    static int familia, id_niño; //id's de la familia y del niño.
    Date fSel; //Recoge la fecha de la que se quieren mostrar los items.
    public String[] descripcion; //Recoge los items de la base de datos.

    Statement s1, s2, s3, s4, s5;
    ResultSet ninos, mascotas, familias; //Recogen los datos del usuario que se logea.
    Blob fotN = null, fotM = null; //Almacenan las fotos del niño y la mascota
    String nombreN = "", nombreM = "", nombrePa = "", nombreMa = "", direccion = "",
            email = "", tlf = "", tlf2 = "";
    Date nacN = null; //Fecha nacimiento del niño.
    int edadM; //Edad de la mascota.

    public static int Last_idItem; //Último id_item almacenado.
    public static int id_item; //id para el siguiente item a almacenar.

//Constructor de la clase Conexión que recibe el id de la familia y obtiene todos los datos.
    public Conexion(int familia) {
        this.descripcion = new String[]{"", "", "", "", "", ""};
        this.edadM = 0;
        Conexion.familia = familia;
        try {
            //Se realiza la conexión a la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(url, "root", "");

            this.s1 = c.createStatement();

            //Se recogen los datos del niño correspondiente al usuario.
            this.ninos = s1.executeQuery("SELECT * FROM niños WHERE familia=" + familia);

            //Cada uno de almacena en su correspondiente variable.
            while (ninos.next()) {
                id_niño = ninos.getInt("id_niño");
                fotN = ninos.getBlob("foto");
                nombreN = ninos.getString("nombre");
                nacN = ninos.getDate("fecha_nac");
            }

            this.s2 = c.createStatement();

            //Se recogen los datos de la mascota del niño.
            this.mascotas = s2.executeQuery("SELECT * FROM mascotas WHERE id_niño=("
                    + "SELECT id_niño FROM niños "
                    + "WHERE familia=" + familia + ")");
            //Se almacenan en su variable correspondiente.
            while (mascotas.next()) {
                fotM = mascotas.getBlob("foto");
                nombreM = mascotas.getString("nombre_mascota");
                edadM = mascotas.getInt("edad");

            }

            this.s3 = c.createStatement();

            //Consulta que recoge los datos de los padres del niño.
            this.familias = s3.executeQuery("SELECT * FROM familias WHERE id_familia=" + familia);

            while (familias.next()) {
                nombreMa = familias.getString("madre");
                nombrePa = familias.getString("padre");
                direccion = familias.getString("direccion");
                email = familias.getString("email");
                tlf = familias.getString("tf1");
                tlf2 = familias.getString("tf2");
            }

            c.close(); //Se cierra la conexión.

        } catch (SQLException | ClassNotFoundException ex) {

            //Mensaje que recibe el usuario en caso de error.
            JOptionPane.showMessageDialog(null, "Problema interno "
                    + "\n Disculpe las molestias", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] items(Date fSel) {

        try {
            this.fSel = fSel; //Recoge e iguala la fecha de la que mostrar la información.

            c = DriverManager.getConnection(url, "root", "");

            s4 = c.createStatement();
            //ResultSet que almacena los items del día seleccionado.
            ResultSet items = s4.executeQuery("SELECT * FROM items where id_niño=("
                    + "SELECT id_niño FROM niños WHERE familia=" + familia + ")"
                    + " AND fecha='" + fSel + "'");

            /*Se recorre el ResultSet añadiendo los daots al Array 'descripción'
             Los items se colocan en el Array dependiendo del tipo que sean. */
            while (items.next()) {

                descripcion[items.getInt("tipo")] = items.getString("descripcion");

            }
            s5=c.createStatement();
            
            //Recoge todos los id_item de la tabla.
            ResultSet it= s5.executeQuery("SELECT id_item from items");
            
            //Se posiciona en el último.
            it.last();
            Last_idItem = it.getInt("id_item"); //Se recoge en una variable.
            
            //Al último id_item se le suma +1, siendo el resultante el id para los nuevos items.
            id_item=Last_idItem+1;
            
            c.close(); //Se cierra la conexión.

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.descripcion; //Devuelve un Array con los datos necesarios.
    }

    //Método que devuelve la imagen del alumno.
    public ImageIcon imagenAlumno() {
        byte[] datos = null;
        try {
            //Se recogen los bytes de la imagen;
            datos = fotN.getBytes(1, (int) fotN.length());

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Se obtiene el ImageIcon a partir de los bytes, es lo que devuelve el método.
        return new ImageIcon(datos);
    }

    //Devuelve la edad actual del alumno.
    public int edadAlumno() {
        //Se obtiene la diferencia de edad entre la fecha actual y la de nacimiento.
        long mil = Calendar.getInstance().getTimeInMillis() - nacN.getTime();
        //Se obtienen los años correspondientes a los milisegundos.
        int edad = (int) (mil / 1000 / 60 / 60 / 24 / 365);
        return edad;
    }

    //Devuelve la imagen de la mascota

    public ImageIcon imagenMascota() {
        //El proceso es el mismo que para la imagen del niño.
        byte[] b = null;
        try {
            b = this.fotM.getBytes(1, (int) fotM.length());
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ImageIcon(b);
    }
}
