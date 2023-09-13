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
import universidad_guia85.entidades.Materia;

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
            String sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria) VALUES (?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, insc.getNota());
            ps.setInt(2, insc.getAlumno().getIdAlumno());
            ps.setInt(3, insc.getMateria().getIdMateria());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {

                insc.setIdInscripcion(rs.getInt("idInscripcion"));
                mensaje("Inscripcion guardada con exito");
            }
            ps.close();

        } catch (SQLException ex) {
            mensaje("Error al acceder a la tabla inscripcion " + ex.getMessage());
        }

    }

    public List<Inscripcion> obtenerInscripcion(){
            
        
            List<Inscripcion> inscripciones = new ArrayList<>();
            try {
            String sql = "SELECT * FROM inscripcion";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Inscripcion inscripcion;
            while (rs.next()){
            inscripcion = new Inscripcion();
            inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
            inscripcion.setNota(rs.getDouble("nota"));
            inscripcion.setAlumno(aluData.buscarAlumno(rs.getInt("idAlumno")));
            inscripcion.setMateria(matData.buscarMateria(rs.getInt("idMateria")));
            }
            ps.close();
                        
            } catch (SQLException ex) {
                 Logger.getLogger(InscripcionData.class.getName()).log(Level.SEVERE, null, ex);
            }
            return inscripciones;
        }
    
    public List<Inscripcion> obtenerInscripcionPorAlumno(int id){
    
        List<Inscripcion> lista = new ArrayList<>();
        try{
            String sql = "SELECT idInscripto, nota, a.nombre, apellido, dni, fechaNacimiento, a.estado" 
                + "m.nombre, año, m.estado FROM inscripcion i JOIN alumno a, materia m WHERE i.idAlumno = a.idAlumno" 
                + " AND i.idMateria = m.idMateria = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Alumno alumno;
            while(rs.next()){                   //Falta completar
                alumno = new Alumno();
                
            }
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InscripcionData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    
    }
    public List<Materia> obtenerMateriaCursadas(int id) {
        List<Materia> materias = new ArrayList<Materia>();
        try {
            String sql = "SELECT inscripcion.idMateria, nombre, año FROM inscripcion, materia"
                    + "WHERE inscripcion.idMateria = materia.idMateria AND inscripcion.idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Materia materia;
            while(rs.next()){
             materia = new Materia();
             materia.setIdMateria(rs.getInt("idMateria"));
             materia.setNombre(rs.getString("nombre"));
             materia.setAño(rs.getInt("año"));
             materias.add(materia);
            }
            ps.close();
            
        }catch (SQLException ex) {
            mensaje("Error al obtener inscripcion " + ex.getMessage());
        }
        return materias;

    }
}
