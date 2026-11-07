package Dispositivos;

import java.time.LocalDate;
import java.util.List;

public abstract class Dispositivo {
    public int Codigo;
    String sector;
    private LocalDate fechaInstalacion;
    private EstadoDispositivo estado;

    public abstract PrioridadOrden calcularPrioridadOrden(List<Dispositivo> dispositivos);



    public Dispositivo(int codigo, String sector, LocalDate fechaInstalacion) {
        if (codigo <= 0 || sector == null || sector.isBlank() || fechaInstalacion == null) {throw new IllegalArgumentException("Datos de dispositivo invalidos");}
        Codigo = codigo;
        this.sector = sector;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = EstadoDispositivo.OPERATIVO;

    }

    public void cambiarEstado(EstadoDispositivo nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = nuevoEstado;
    }


    public EstadoDispositivo getEstado() {return estado;}

    public LocalDate getFechaInstalacion() {return fechaInstalacion;}

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
