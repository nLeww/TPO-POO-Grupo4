package personal;

import java.time.LocalDate;

public abstract class Personal {
    private int legajo;
    private String nombre;
    private LocalDate fechaIngreso;


    //Constructor, Getters y Setters
    public Personal(int legajo, String nombre, LocalDate fechaIngreso) {

        if (legajo <= 0 || nombre == null || fechaIngreso == null)
            throw new IllegalArgumentException("Datos de personal invalidos");

        this.legajo = legajo;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString(){
        return legajo+ " " + nombre + " " + fechaIngreso;
    }
}
