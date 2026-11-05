import lotes.Lote;
import personal.*;
import personas.Habitante;
import personas.Titular;
import reclamos.Reclamo;
import reclamos.TipoReclamo;

import java.time.LocalDate;

public class main {
    public static void main(String[] args) {

        Titular t1 = new Titular("Jose","Martinez",22549021);

        Habitante h1 = new Habitante("Martin", "Martinez", 2374738);
        Habitante h2 = new Habitante("Luisa", "Martinez", 11758290);

        Lote l1 = new Lote(101,10.0,t1);

        l1.agregarHabitante(h1);
        l1.agregarHabitante(h2);

        System.out.println("lotes.Lote; " + l1.getNumero());
        System.out.println("personas.Titular: " + l1.getTitular());
        System.out.println("Habitantes: "+l1.getHabitantes());

        GuardiaSeguridad g1 = new GuardiaSeguridad(123, "Enrique", LocalDate.of(2026,11,3),Turno.MANANA, PuestoGuardia.PORTERIA);
        System.out.println(g1);

        EmpleadoMantenimiento e1 = new EmpleadoMantenimiento(132, "Petunia", LocalDate.of(2026,11,3), Especialidad.JARDINERIA);
        System.out.println(e1);

        Administrador a1 = new Administrador("Roberto");

        a1.agregarEmpleado(g1);
        a1.agregarEmpleado(e1);

        a1.mostrarEmpleados();

        Reclamo r1 = new Reclamo(1, TipoReclamo.SEGURIDAD,"Camara no fucniona",l1,t1);
        System.out.println(r1);

        r1.asignarResponsable(e1);
        System.out.println(r1);

        r1.avanzarEstado("Empezo a trabajar");
        System.out.println(r1);

        r1.avanzarEstado("camara arreglada");
        r1.mostrarHistorial();

        r1.reabrir(t1);

        r1.mostrarHistorial();

    }
}