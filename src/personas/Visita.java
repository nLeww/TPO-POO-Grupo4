package personas;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visita {

    private LocalDate fecha;
    private LocalTime horarioVisita;
    private int loteVisita;


    public Visita(LocalDate fecha, LocalTime horarioVisita, int loteVisita) {
        this.fecha = fecha;
        this.horarioVisita = horarioVisita;
        this.loteVisita = loteVisita;
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
        return loteVisita;
    }

    public void setLoteVisita(int loteVisita) {
        this.loteVisita = loteVisita;
    }

}
