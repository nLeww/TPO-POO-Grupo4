package Persistencia;

import Orden_y_Expensa.Expensa;
import excepciones.ReglaNegocioException;
import lotes.Lote;
import pagos.MedioPago;
import pagos.Pago;
import personas.Habitante;
import personas.Titular;

import java.time.LocalDate;

public class PersistenciaPrueba {
    public static void main(String[] args) throws ReglaNegocioException {
        ArchivoLotes arcivoLotes = new ArchivoLotes();
        ArchivoExpensas archivoExpensas = new ArchivoExpensas();

        arcivoLotes.cargar();
        archivoExpensas.cargar(); //este siempre despues de lote!

        if (Lote.lotes.isEmpty()) {
            System.out.println("No habia datos: se crean y se guardan");

            Titular t = new Titular("Ana", "Lopez", 30111222);
            Lote l1 = new Lote(201, 15.0, t);
            l1.agregarHabitante(new Habitante("Luis", "Lopez", 40222333));
            new Lote(202, 20.5, t);

            l1.agregarExpensa(new Expensa(202609, 100000));
            l1.agregarExpensa(new Expensa(202610, 100000));
            l1.registrarPago(new Pago(LocalDate.now(), 150000, MedioPago.EFECTIVO));

            System.out.println("Deuda lote 201: " + l1.calcularDeudaTotal());

            arcivoLotes.guardar();
            archivoExpensas.guardar();
        } else {
            System.out.println("Datos recuperados:");
            for (Lote l : Lote.lotes) {
                System.out.println("Lote " + l.getNumero() + " - deuda: " + l.calcularDeudaTotal()
                        + " - pagos: " + l.obtenerPagos());
            }
        }
    }
}