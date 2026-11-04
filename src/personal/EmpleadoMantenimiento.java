package personal;

import java.time.LocalDate;

public class EmpleadoMantenimiento extends Personal {
    private Especialidad especialidad;

    public EmpleadoMantenimiento(int legajo, String nombre, LocalDate fechaIngreso, Especialidad especialidad) {
        super(legajo, nombre, fechaIngreso);
        this.especialidad = especialidad;
    }


    @Override
    public String toString() {
        return super.toString() + " " + especialidad;
    }
}
