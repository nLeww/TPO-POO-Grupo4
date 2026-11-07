package personas;

import personal.EmpleadoMantenimiento;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Visita {

    private LocalDate fecha;
    private LocalTime horarioVisita;
    private int numero;

    public static ArrayList<Visita> visitas = new ArrayList<>();


    public Visita(LocalDate fecha, LocalTime horarioVisita, int numero) {
        this.fecha = fecha;
        this.horarioVisita = horarioVisita;
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHorarioVisita() {
        return horarioVisita;
    }

    public void setHorarioVisita(LocalTime horarioVisita) {
        this.horarioVisita = horarioVisita;
    }

    public int getLoteVisita() {
        return numero;
    }

    public void setnumero(int numero) {this.numero = numero;}

}
