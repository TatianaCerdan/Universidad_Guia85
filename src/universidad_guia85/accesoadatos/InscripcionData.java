package universidad_guia85.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import universidad_guia85.entidades.Alumno;
import universidad_guia85.entidades.Inscripcion;

/**
 *
 * @author Guillermo Rodriguez
 */
public class InscripcionData {

    private Connection con = null;

    private AlumnoData aluData;
    private MateriaData matData;

    public InscripcionData() {
        con = Conexion.getConexion();
    }

    private static void mensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void guardarInscripcion(Inscripcion insc) {

        
        try {
            String sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria) VALUES ('?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, insc.getNota());
            ps.setInt(2, insc.getAlumno().getIdAlumno());
            ps.setInt(3, insc.getMateria().getIdMateria());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
            
                insc.setIdInscripcion(rs.getInt("idInscripcion"));
                mensaje("Inscripcion guardada con exito");
            }
            ps.close();
            

        } catch (SQLException ex) {
            mensaje("Error al acceder a la tabla inscripcion " + ex.getMessage());
        }

    }
    
    public List<Inscripcion> obtenerInscripcion(){
            
            List<Inscripcion> inscrip = new ArrayList<>();
            try {
            String sql = "SELECT * FROM inscripcion";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Inscripcion inscripcion;
            while (rs.next()){
            inscripcion = new Inscripcion();
            inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
            inscripcion.setNota(rs.getDouble("nota"));
            
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InscripcionData.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    

}
