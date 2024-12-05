package oraclegeneral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * Clase que contiene los metodos para la conexion de la base de datos asi como
 * los metodos de busqueda y de actualizacion de la base.
 *
 * @author Erik David Zubia Hernandez
 * @version 1.0
 * @since 15/05/2015
 */
public class Conexion {

    private static final String url = "jdbc:oracle:thin:@192.168.1.66:1521:XE";
    private static String usuario = "panaderia";
    private static String contrasena = "abcd1234";
    private static Connection con;
    private static Statement st;
    private static String query = null;
    private static Conexion INSTANCE;

   /**
    * Constructor donde creamos la conexion a base de datos 
    */
    
    private Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, usuario, contrasena);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método que obtiene la instancia de la conexion en la base de datos
     * @return INSTANCE instancia que hemos creado
     */
    public static Conexion getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Conexion();
        }
        return INSTANCE;
    
    }
    
    /**
     * Método que obtiene la conexion a la base de datos
     * @return 
     */    
    public Connection getCon(){
        return con;
    }

    /**
     * Metodo que verifica la existencia del usuario que utilizara todo el
     * sistema
     *
     * @return falso cuando el usuario no existe y verdadero cuando si existe.
     */
    public static Boolean verificarUsuario() {
        try {
            if (getInstance().getCon() != null) {
                getInstance().getCon().close();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }

    }

    /**
     * Metodo que conecta a la base de datos con la aplicacion.
     *
     * @return conexion a la base de datos para poder interactuar con ella
     */
    /*public static Connection getDBConexion() {
        try {
            con = DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException ex) {
            con = null;
        }
        return con;
    }*/

    /**
     * Metodo que crea el usuario que se utilizara para todo el sistema. Este
     * solamente se usara una vez que sera al correr por primera vez el
     * programa.
     */
    public static Boolean creacionUsuario() {
        JOptionPane.showMessageDialog(null, "Lo sentimos es la primera vez que entrara al sistema.\n"

                + "numero 3 Pongase en contacto  con su DBA para que ingrese la contraseña del usuario \"System\" ...");


                + "numero2 Pongase en contacto con su DBA para que ingrese la contraseña del usuario \"System\" ...");

                + "numero1 Pongase en contacto con su DBA para que ingrese la contraseña del usuario \"System\" ...");


        File file = new File("CrearUsuario.txt");
        usuario = "system";
        contrasena = JOptionPane.showInputDialog("Introduzca la contraseña del usuario system");
        try {
            FileReader in = new FileReader(file.getAbsolutePath());
            BufferedReader br = new BufferedReader(in);
            //con = Conexion.getDBConexion();
            System.out.println("con = " + getInstance().getCon());
            st = getInstance().getCon().createStatement();
            while (br.readLine() != null) {
                query = br.readLine().toString();
                System.out.println(query);
                st.execute(query);
                query = null;
            }
            br.close();
            st.close();
            getInstance().getCon().close();
        } catch (Exception e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;

    }

    /**
     * Metodo que Crea la base de datos para su utilizacion en el sistema. Este
     * metodo solo se usara una vez al correr el programa por primera vez.
     */
    public static Boolean creacionBase() {
        FileReader fstream = null;
        usuario = "panaderia";
        contrasena = "abcd1234";
        File file = new File("CrearBase.txt");
        try {
            //con = Conexion.getDBConexion();
            st = getInstance().getCon().createStatement();
            fstream = new FileReader(file.getAbsolutePath());
            BufferedReader br = new BufferedReader(fstream);
            while (br.readLine() != null) {
                query = br.readLine().toString();
                System.out.println(query);
                st.executeUpdate(query);
            }
            br.close();
            st.close();
            getInstance().getCon().close();
        } catch (Exception e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;

    }

}
