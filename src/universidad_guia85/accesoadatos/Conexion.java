
package universidad_guia85.accesoadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {
    private static final String URL= "jdbc:mariadb://localhost/";
    private static final String DB="universidadulp";
    private static final String USUARIO="root";
    private static final  String PASSWORD="";
    
    private static Connection connection; 

    public Conexion() {
    }
    
    public static Connection getConexion(){
    if (connection==null){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection=DriverManager.getConnection(URL+DB,USUARIO,PASSWORD);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"ERROR! al cargar los driver");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR! al intentar conectarse a la base de datos");
        }
        
    }
        return connection;
}

}

