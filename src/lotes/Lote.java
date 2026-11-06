package lotes;

import personas.Habitante;
import personas.Titular;

import java.util.List;
import java.util.ArrayList;

public class Lote {
    private int numero;
    private double superficie;
    private Titular titular;
    private List<Habitante> habitantes;

    public void agregarHabitante(Habitante h){
        if (h==null){throw new IllegalArgumentException("personas.Habitante no puede ser nulo");}
        for (Habitante habitante : habitantes){
            if (habitante.getDocumento() == h.getDocumento()){ throw new IllegalArgumentException("El habitante ya esta registrado");}
        }
        habitantes.add(h);
    }

    //Constructor, Getters y Setters
    public Lote(int numero, double superficie, Titular titular) {

        if (numero <= 0 || titular == null || superficie <= 0) {throw new IllegalArgumentException("Datos de lotes.Lote Invalidos");}

        this.numero = numero;
        this.superficie = superficie;
        this.titular = titular;
        habitantes = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public List<Habitante> getHabitantes() {
        return new ArrayList<>(habitantes);
    }
}
