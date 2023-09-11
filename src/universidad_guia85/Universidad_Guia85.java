
package universidad_guia85;

import java.time.LocalDate;
import java.time.Month;
import universidad_guia85.accesoadatos.AlumnoData;
import universidad_guia85.entidades.Alumno;

public class Universidad_Guia85 {

    public static void main(String[] args) {

        Alumno Pedro = new Alumno(23456789,"Perez","Pedro",LocalDate.of(1980, 4, 15),true);
        AlumnoData alu = new AlumnoData();
        alu.guardarAlumno(Pedro);
    }
    
}
