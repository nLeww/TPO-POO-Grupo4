package lotes;

import Orden_y_Expensa.Expensa;
import personas.Habitante;
import personas.Titular;

import java.util.List;
import java.util.ArrayList;

public class Lote {
    private int numero;
    private double superficie;
    private Titular titular;
    private List<Habitante> habitantes;
    private boolean Tardio;
    public static List<Lote> lotes = new ArrayList<>(); //Para almacenar los lotes

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
        Tardio = false;

        lotes.add(this); //lista que contendra todos los lotes

    }
public static void getListaLotes(){
        
}


    public int getNumero() {
        return numero;
    }

    public double getSuperficie() {
        return superficie;
    }

    public Titular getTitular() {
        return titular;
    }

    public List<Habitante> getHabitantes() {
        return new ArrayList<>(habitantes);
    }

    public void setEstadoTardio(boolean Tardio) {
        this.Tardio = Tardio;
    }

    @Override
    public String toString() {
        return  " " + numero + " " + superficie + " " + titular + " " + habitantes;
    }
}

