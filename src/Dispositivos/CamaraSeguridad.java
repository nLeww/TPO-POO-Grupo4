package Dispositivos;

import java.time.LocalDate;
import java.util.List;

public class CamaraSeguridad extends Dispositivo{

    public String Resolucion;

    public int DiasGrabados;

    public CamaraSeguridad(int codigo, String sector, LocalDate fechaInstalacion) {
        super(codigo, sector, fechaInstalacion);
    }

    public CamaraSeguridad(int codigo, String sector,LocalDate fechaInstalacion, String resolucion, int diasGrabados) {
        super(codigo, sector, fechaInstalacion);
        Resolucion = resolucion;
        DiasGrabados = diasGrabados;
    }

    @Override
    public PrioridadOrden calcularPrioridadOrden(List<Dispositivo> dispositivos) {
        return PrioridadOrden.ALTA;
    }

    public String getResolucion() {
        return Resolucion;
    }

    public void setResolucion(String resolucion) {
        Resolucion = resolucion;
    }

    public int getDiasGrabados() {
        return DiasGrabados;
    }

    public void setDiasGrabados(int diasGrabados) {
        DiasGrabados = diasGrabados;
    }
}
