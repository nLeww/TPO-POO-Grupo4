package personal;

import java.time.LocalDate;

public class GuardiaSeguridad extends Personal {
    private Turno turno;
    private PuestoGuardia puesto;



    //Constructor
    public GuardiaSeguridad(int legajo, String nombre, LocalDate fechaIngreso, Turno turno, PuestoGuardia puesto) {
        super(legajo, nombre, fechaIngreso);
        this.turno = turno;
        this.puesto = puesto;
    }
    @Override
    public String toString() {
        return super.toString() + " " + turno + " " + puesto;
    }
}
