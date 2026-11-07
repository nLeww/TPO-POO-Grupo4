import Dispositivos.CamaraSeguridad;
import Dispositivos.Luminaria;
import Orden_y_Expensa.ControladorExpensas;
import Orden_y_Expensa.ControladorMantenimiento;
import Orden_y_Expensa.Expensa;
import Orden_y_Expensa.OrdenMantenimiento;
import Reportes.ControladorReportes;
import excepciones.ReglaNegocioException;
import lotes.Lote;
import pagos.MedioPago;
import pagos.Pago;
import personal.EmpleadoMantenimiento;
import personal.Especialidad;
import personas.Habitante;
import personas.Titular;
import reclamos.Reclamo;
import reclamos.TipoReclamo;

import java.time.LocalDate;

public class main {
    public static void main(String[] args) throws ReglaNegocioException {
        ControladorExpensas controlExpensas = new ControladorExpensas();

        Titular t1 = new Titular("Jose","Martinez",22549021);

        Habitante h1 = new Habitante("Martin", "Martinez", 2374738);
        Habitante h2 = new Habitante("Luisa", "Martinez", 11758290);

        EmpleadoMantenimiento emp = new EmpleadoMantenimiento(123,"Juancito",LocalDate.now(), Especialidad.ELECTRICIDAD);

        Lote l1 = new Lote(101,10.0,t1);

        l1.agregarHabitante(h1);
        l1.agregarHabitante(h2);

        Lote l2 = new Lote(102,12,t1);
        l2.obtenerExpensas();

        Expensa exp1 = new Expensa(202610, 100000);
        Expensa exp2 = new Expensa(202611, 100000);

        l1.agregarExpensa(exp1);
        l1.agregarExpensa(exp2);

        controlExpensas.registrarPago(101, new Pago(LocalDate.now(), 150000, MedioPago.EFECTIVO));
        controlExpensas.mostrarDetalleDeuda(101);


        ControladorMantenimiento ctrlMant = new ControladorMantenimiento();

        CamaraSeguridad cam = new CamaraSeguridad(1, "Acceso principal", LocalDate.of(2025, 3, 10), "1080p", 30);
        Luminaria lum = new Luminaria(2, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");
        Luminaria lumB = new Luminaria(3, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");
        Luminaria lumC = new Luminaria(4, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");
        Luminaria lumD = new Luminaria(5, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");

        try {
            ctrlMant.agregarDispositivo(cam);
            ctrlMant.agregarDispositivo(lum);
            ctrlMant.agregarDispositivo(lumB);
            ctrlMant.agregarDispositivo(lumC);
            ctrlMant.agregarDispositivo(lumD);

            ctrlMant.registrarFalla(1);
            OrdenMantenimiento o1 = ctrlMant.generarOrden(1, emp, LocalDate.now(), "Camara sin imagen");
            System.out.println(o1);

            for (int codigo = 2; codigo <= 5; codigo++) {
                ctrlMant.registrarFalla(codigo);
                System.out.println(ctrlMant.generarOrden(codigo, emp, LocalDate.now(), "Luminaria apagada"));
            }

            o1.cerrar(LocalDate.now(), emp, "Se cambio el cable de red");
            System.out.println(o1);
            ctrlMant.mostrarEstados();
        } catch (ReglaNegocioException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n");

        ControladorReportes reportes = new ControladorReportes(ctrlMant);

        Reclamo seguridad = new Reclamo(23, TipoReclamo.SEGURIDAD, "Porton roto", l1, t1);
        Reclamo convivencia = new Reclamo(24, TipoReclamo.CONVIVENCIA, "Ruidos", l1, t1);

        reportes.reclamosPendientes();
        reportes.morosidadPorLote();
        reportes.equiposFueraDeServicio();

    }
}