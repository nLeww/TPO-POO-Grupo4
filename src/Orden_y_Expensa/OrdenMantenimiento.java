package Orden_y_Expensa;

import Dispositivos.Dispositivo;
import Dispositivos.EstadoDispositivo;
import Dispositivos.PrioridadOrden;
import excepciones.ReglaNegocioException;
import personal.EmpleadoMantenimiento;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;

public class OrdenMantenimiento {
    private Dispositivo dispositivo;
    EmpleadoMantenimiento Empleado_Asignado;
    LocalDate fecha_apertura;
    PrioridadOrden Prioridad;
    String Descripcion;
    private LocalDate fechaCierre;
    private EmpleadoMantenimiento tecnico;
    private String descripcionReparacion;



    public OrdenMantenimiento(Dispositivo dispositivo, EmpleadoMantenimiento empleado_Asignado, LocalDate fecha_apertura, PrioridadOrden prioridad, String descripcion) {
        if (dispositivo == null || empleado_Asignado == null || fecha_apertura == null || prioridad == null || descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("Datos de orden invalidos");}
        this.dispositivo = dispositivo;
        this.Empleado_Asignado = empleado_Asignado;
        this.fecha_apertura = fecha_apertura;
        this.Prioridad = prioridad;
        this.Descripcion = descripcion;
    }

    public void cerrar(LocalDate fecha, EmpleadoMantenimiento tecnico, String descripcionReparacion)
            throws ReglaNegocioException {

        if (fechaCierre != null) {
            throw new ReglaNegocioException("La orden ya esta cerrada");
        }
        if (fecha == null || tecnico == null
                || descripcionReparacion == null || descripcionReparacion.isBlank()) {
            throw new ReglaNegocioException(
                    "No se puede cerrar la orden sin fecha, tecnico y descripcion de la reparacion");
        }
        if (fecha.isBefore(fecha_apertura)) {
            throw new ReglaNegocioException("La fecha de cierre no puede ser anterior a la apertura");
        }

        this.fechaCierre = fecha;
        this.tecnico = tecnico;
        this.descripcionReparacion = descripcionReparacion;
        dispositivo.cambiarEstado(EstadoDispositivo.OPERATIVO);
    }

    public boolean estaCerrada() {
        return fechaCierre != null;
    }

    public EmpleadoMantenimiento getEmpleado_Asignado() {
        return Empleado_Asignado;
    }

    public void setEmpleado_Asignado(EmpleadoMantenimiento empleado_Asignado) {
        Empleado_Asignado = empleado_Asignado;
    }

    public LocalDate getFecha_apertura() {
        return fecha_apertura;
    }

    public void setFecha_apertura(LocalDate fecha_apertura) {
        this.fecha_apertura = fecha_apertura;
    }

    public PrioridadOrden getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(PrioridadOrden prioridad) {
        Prioridad = prioridad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public LocalDate getFecha_cierre() {
        return fechaCierre;
    }

    public void Registrar_Orden(EmpleadoMantenimiento empleado_Asignado, Date fecha_apertura, String prioridad, String descripcion, Date fecha_cierre){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Registro_de_Ordenes.txt", true))) {

        bw.write(empleado_Asignado + "/" + fecha_apertura + "/" + prioridad + "/" + descripcion + "/" + fecha_cierre);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void LeerOrdenes(){
        try(BufferedReader br = new BufferedReader(new FileReader("Registro_de_Ordenes.txt"))){

            String linea;

            while ((linea = br.readLine()) != null){
                System.out.println(linea);
            }

        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
    @Override
    public String toString() {
        String estado = "ABIERTA";
        if (estaCerrada()) {
            estado = "CERRADA";
        }
        return "dispositivo " + dispositivo.getCodigo() + " " + dispositivo.getSector() + "  " + Prioridad + " " + estado;
    }
}
