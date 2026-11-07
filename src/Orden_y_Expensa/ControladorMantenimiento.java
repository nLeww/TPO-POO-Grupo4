package Orden_y_Expensa;

import Dispositivos.Dispositivo;
import Dispositivos.EstadoDispositivo;
import Dispositivos.PrioridadOrden;
import excepciones.ReglaNegocioException;
import personal.EmpleadoMantenimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorMantenimiento {
    private List<Dispositivo> dispositivos;
    private List<OrdenMantenimiento> ordenes;

    public ControladorMantenimiento() {
        dispositivos = new ArrayList<>();
        ordenes = new ArrayList<>();
    }

    public void agregarDispositivo(Dispositivo dispositivo) throws ReglaNegocioException {
        if (dispositivo == null) {
            throw new IllegalArgumentException("El dispositivo no puede ser nulo");
        }
        if (buscarDispositivo(dispositivo.getCodigo()) != null) {
            throw new ReglaNegocioException("Ya existe un dispositivo con ese codigo");
        }
        dispositivos.add(dispositivo);
    }

    public Dispositivo buscarDispositivo(int codigo) {
        for (Dispositivo d : dispositivos) {
            if (d.getCodigo() == codigo) {
                return d;
            }
        }
        return null;
    }

    public void registrarFalla(int codigo) throws ReglaNegocioException {
        Dispositivo d = buscarDispositivo(codigo);
        if (d == null) {
            throw new IllegalArgumentException("No existe un dispositivo con ese codigo");
        }
        if (d.getEstado() != EstadoDispositivo.OPERATIVO) {
            throw new ReglaNegocioException("El dispositivo ya tiene una falla registrada");
        }
        d.cambiarEstado(EstadoDispositivo.CON_FALLA);
    }

    public OrdenMantenimiento generarOrden(int codigo, EmpleadoMantenimiento empleado, LocalDate fecha, String descripcion) throws ReglaNegocioException {
        Dispositivo d = buscarDispositivo(codigo);
        if (d == null) {
            throw new IllegalArgumentException("No existe un dispositivo con ese codigo");
        }
        if (d.getEstado() != EstadoDispositivo.CON_FALLA) {
            throw new ReglaNegocioException("Solo se puede generar una orden para un dispositivo con falla");
        }

        PrioridadOrden prioridad = d.calcularPrioridadOrden(dispositivos);
        OrdenMantenimiento orden = new OrdenMantenimiento(d, empleado, fecha, prioridad, descripcion);

        d.cambiarEstado(EstadoDispositivo.EN_REPARACION);
        ordenes.add(orden);
        return orden;
    }

    public void mostrarEstados() {
        for (Dispositivo d : dispositivos) {
            System.out.println(d.getCodigo() + " " + d.getSector() + " " + d.getEstado());
        }
    }
}