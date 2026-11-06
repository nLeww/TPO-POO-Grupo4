package reclamos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class HistorialReclamo {
    private LocalDateTime fecha;
    private EstadoReclamo estado;
    private String observacion;


    //constructor, Getters y Setters
    public HistorialReclamo(EstadoReclamo estado, String observacion) {

        if (estado == null || observacion == null || observacion.isBlank()) {
            throw new IllegalArgumentException("Faltan datos del reclamo");
        }
        this.fecha = LocalDateTime.now();
        this.estado = estado;
        this.observacion = observacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoReclamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoReclamo estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return fecha +" " + estado + " " + observacion;
    }

    public void ResgitrarReclamo(EstadoReclamo estado, String observacion){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Registro_de_Reclamos.txt", true))) {

            bw.write(estado + "/" + observacion + "/" + fecha );

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

