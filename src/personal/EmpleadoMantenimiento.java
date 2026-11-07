package personal;

import reclamos.EstadoReclamo;
import reclamos.Reclamo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoMantenimiento extends Personal {
    private Especialidad especialidad;
    private List<Reclamo> reclamos;

    public EmpleadoMantenimiento(int legajo, String nombre, LocalDate fechaIngreso, Especialidad especialidad) {
        super(legajo, nombre, fechaIngreso);
        this.especialidad = especialidad;
        this.reclamos = new ArrayList<>();
    }

    public void agregarReclamo(Reclamo reclamo) {
        reclamos.add(reclamo);
    }

    public int contarReclamosEnCurso() {
        int cantidad = 0;
        for (Reclamo r : reclamos) {
            if (r.getEstado() == EstadoReclamo.EN_CURSO) {
                cantidad++;
            }
        }
        return cantidad;
    }

    @Override
    public String toString() {
        return super.toString() + " " + especialidad;
    }
}
