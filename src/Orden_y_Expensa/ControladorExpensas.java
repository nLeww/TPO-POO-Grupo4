
package Orden_y_Expensa;

import excepciones.ReglaNegocioException;
import lotes.Lote;
import pagos.Pago;

import java.util.List;

public class ControladorExpensas {

    public double consultarDeuda(int numeroLote) {
        Lote lote = Lote.buscarLote(numeroLote);
        if (lote == null) {
            throw new IllegalArgumentException("No existe un lote con ese numero");
        }
        List<Expensa> expensas = lote.obtenerExpensas();

        double deudaTotal = 0;

        for (Expensa expensa : expensas) {
            double saldo = expensa.obtenerSaldoPendiente();
            double intereses = expensa.obtenerInteresesPendientes();
            deudaTotal += saldo + intereses;
        }
        return deudaTotal;
    }

    public void registrarPago(int numeroLote, Pago pago) throws ReglaNegocioException {
        Lote lote = Lote.buscarLote(numeroLote);
        if (lote == null) {
            throw new IllegalArgumentException("No existe un lote con ese numero");
        }
        lote.registrarPago(pago);
    }

    public void mostrarDetalleDeuda(int numeroLote) {
        Lote lote = Lote.buscarLote(numeroLote);
        if (lote == null) {
            throw new IllegalArgumentException("No existe un lote con ese numero");}

        System.out.println("CONSULTA DE DEUDA - LOTE " + numeroLote);
        for (Expensa expensa : lote.obtenerExpensas()) {
            double saldo = expensa.obtenerSaldoPendiente();
            if (saldo > 0) {
                System.out.println(
                        "\nPeriodo: " + expensa.getPeriodo()
                );
                System.out.println(
                        "Saldo pendiente: $" + saldo
                );
                System.out.println(
                        "Intereses: $"
                                + expensa.obtenerInteresesPendientes()
                );
            }
        }
        System.out.println("\nDEUDA TOTAL: $" + consultarDeuda(numeroLote));
    }

}
