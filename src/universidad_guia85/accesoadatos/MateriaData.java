
package universidad_guia85.accesoadatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import universidad_guia85.entidades.Alumno;
import universidad_guia85.entidades.Materia;

/**
 *
 * @author Guillermo Rodriguez
 */
public class MateriaData {

    private Connection con = null;
    
    private static void mensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public MateriaData() {
        con = Conexion.getConexion();
    }
    
    public void guardarMateria(Materia materia){
    
    String sql = "INSERT INTO materia (nombre, año, estado) "
                + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAño());
            ps.setBoolean(3, materia.isEstado());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                materia.setIdMateria(rs.getInt("idMateria"));
                JOptionPane.showMessageDialog(null, "Materia guardada con exito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla" + ex.getMessage());
        }
    
    }
    
    public Materia buscarMateria(int id) {

        Materia materia = null;
        String sql = "SELECT nombre, año, estado FROM materia WHERE idMateria= ? AND estado = 1";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                materia = new Materia();
                materia.setIdMateria(id); //AL id lo saco del que recibo por parametro
                materia.setNombre(rs.getString("nombre"));
                materia.setAño(rs.getInt("año"));
                materia.setEstado(true);

            } else {
                mensaje("No existe la materia");
            }
            ps.close();

        } catch (SQLException ex) {
            mensaje("Error al acceder a la tabla Materia" + ex.getMessage());
        }
        return materia;
    }
    
    public void modificarMateria(Materia materia) { 

        String sql = "UPDATE materia SET nombre = ?, año = ?, estado = ?"
                + "WHERE idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAño());
            ps.setBoolean(3, materia.isEstado());
            
            int exito = ps.executeUpdate(); 
                                            
            if (exito == 1) {               
                mensaje("Materia modificada");
            } else {
                mensaje("La materia NO existe");
            }

        } catch (SQLException ex) {
            mensaje("Error al acceder a la tabla Materia " + ex.getMessage());
        }
    }
    
    public void eliminarMateria(int id) {

        try {
            String sql = "UPDATE materia SET estado = 0 WHERE idMateria = ? "; 
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();
            if(fila==1){
                mensaje("Se elimino la materia");
            }
            ps.close();

        } catch (SQLException ex) {
            mensaje("Error al acceder a la tabla Materia " + ex.getMessage());
        }

    }
    
    public List<Materia> listarMatedria() {

        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT * FROM materia WHERE estado = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria")); 
                materia.setNombre(rs.getString("nombre"));
                materia.setAño(rs.getInt("año"));
                materia.setEstado(rs.getBoolean("estado"));
                materias.add(materia);
            }        
              
            ps.close();

        } catch (SQLException ex) {
            mensaje("Error al acceder a la tabla Materia " + ex.getMessage());
        }
        return materias;
    }
}
