package Reportes;

import Dispositivos.Dispositivo;
import Dispositivos.EstadoDispositivo;
import Orden_y_Expensa.ControladorMantenimiento;
import lotes.Lote;
import reclamos.EstadoReclamo;
import reclamos.Reclamo;

import java.util.ArrayList;
import java.util.List;

public class ControladorReportes {
    private ControladorMantenimiento mantenimiento;

    public ControladorReportes(ControladorMantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public List<Reclamo> reclamosPendientes() {
        List<Reclamo> pendientes = new ArrayList<>();

        for (Lote lote : Lote.lotes) {
            for (Reclamo r : lote.obtenerReclamos()) {
                if (r.getEstado() != EstadoReclamo.RESUELTO && r.getEstado() != EstadoReclamo.CERRADO) {
                    pendientes.add(r);
                }
            }
        }

        System.out.println("REPORTE: RECLAMOS PENDIENTES");
        for (Reclamo r : pendientes) {
            System.out.println(r + " - " + r.getFechaCreacion());
        }
        return pendientes;
    }

    public List<Lote> morosidadPorLote() {
        List<Lote> conDeuda = new ArrayList<>();

        for (Lote lote : Lote.lotes) {
            if (lote.calcularDeudaTotal() > 0) {
                conDeuda.add(lote);
            }
        }

        System.out.println("REPORTE: MOROSIDAD POR LOTE");
        for (Lote l : conDeuda) {
            System.out.println("Lote " + l.getNumero() + " - " + l.getTitular()
                    + " - periodos impagos: " + l.contarPeriodosImpagos()
                    + " - deuda: $" + l.calcularDeudaTotal()
                    + " - moroso: " + l.estaMoroso());
        }
        return conDeuda;
    }

    public List<Dispositivo> equiposFueraDeServicio() {
        List<Dispositivo> fuera = new ArrayList<>();

        for (Dispositivo d : mantenimiento.obtenerDispositivos()) {
            if (d.getEstado() != EstadoDispositivo.OPERATIVO) {
                fuera.add(d);
            }
        }

        System.out.println("REPORTE: EQUIPOS FUERA DE SERVICIO");
        for (Dispositivo d : fuera) {
            System.out.println(d.getCodigo() + " - " + d.getSector() + " - " + d.getEstado());
        }
        return fuera;
    }
}