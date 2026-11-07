import Orden_y_Expensa.ControladorExpensas;
import Orden_y_Expensa.Expensa;
import excepciones.ReglaNegocioException;
import lotes.Lote;
import pagos.MedioPago;
import pagos.Pago;
import personas.Habitante;
import personas.Titular;

import java.time.LocalDate;

public class main {
    public static void main(String[] args) throws ReglaNegocioException {
        ControladorExpensas controlExpensas = new ControladorExpensas();

        Titular t1 = new Titular("Jose","Martinez",22549021);

        Habitante h1 = new Habitante("Martin", "Martinez", 2374738);
        Habitante h2 = new Habitante("Luisa", "Martinez", 11758290);

        Lote l1 = new Lote(101,10.0,t1);

        l1.agregarHabitante(h1);
        l1.agregarHabitante(h2);

        Lote l2 = new Lote(102,12,t1);
        l2.obtenerExpensas();

        Expensa exp1 = new Expensa(202610, 100000);
        Expensa exp2 = new Expensa(202611, 100000);

        l1.agregarExpensa(exp1);
        l1.agregarExpensa(exp2);

        controlExpensas.mostrarDetalleDeuda(101);

        System.out.println("PAGO");
        System.out.println("\n\n");

        controlExpensas.registrarPago(101, new Pago(LocalDate.now(), 150000, MedioPago.EFECTIVO));
        controlExpensas.mostrarDetalleDeuda(101);


        controlExpensas.registrarPago(101, new Pago(LocalDate.now(), 999999, MedioPago.EFECTIVO));
    }
}