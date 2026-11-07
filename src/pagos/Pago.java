package pagos;

import java.time.LocalDate;

public class Pago {
    private LocalDate fecha;
    private double importe;
    private MedioPago medio;


    public Pago(LocalDate fecha, double importe, MedioPago medio) {
        if (fecha == null || medio == null || importe <= 0) {
            throw new IllegalArgumentException("Datos de pago invalidos");
        }
        this.fecha = fecha;
        this.importe = importe;
        this.medio = medio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getImporte() {
        return importe;
    }

    public MedioPago getMedio() {
        return medio;
    }

    @Override
    public String toString() {
        return fecha + " $" + importe + " " + medio;
    }
}