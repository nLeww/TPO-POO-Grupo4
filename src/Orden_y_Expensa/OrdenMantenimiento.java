package Orden_y_Expensa;

import personal.EmpleadoMantenimiento;

import java.io.*;
import java.util.Date;

public class OrdenMantenimiento {
    EmpleadoMantenimiento Empleado_Asignado;
    Date fecha_apertura;
    String Prioridad;
    String Descripcion;
    Date fecha_cierre;

    public OrdenMantenimiento(EmpleadoMantenimiento empleado_Asignado, Date fecha_apertura, String prioridad, String descripcion, Date fecha_cierre) {
        Empleado_Asignado = empleado_Asignado;
        this.fecha_apertura = fecha_apertura;
        Prioridad = prioridad;
        Descripcion = descripcion;
        this.fecha_cierre = fecha_cierre;
    }


    public EmpleadoMantenimiento getEmpleado_Asignado() {
        return Empleado_Asignado;
    }

    public void setEmpleado_Asignado(EmpleadoMantenimiento empleado_Asignado) {
        Empleado_Asignado = empleado_Asignado;
    }

    public Date getFecha_apertura() {
        return fecha_apertura;
    }

    public void setFecha_apertura(Date fecha_apertura) {
        this.fecha_apertura = fecha_apertura;
    }

    public String getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(String prioridad) {
        Prioridad = prioridad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Date getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Date fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
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
}
