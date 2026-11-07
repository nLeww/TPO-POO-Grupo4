package lotes;

import Orden_y_Expensa.Expensa;
import excepciones.ReglaNegocioException;
import personas.Habitante;
import personas.Titular;

import java.util.List;
import java.util.ArrayList;

public class Lote {
    private int numero;
    private double superficie;
    private Titular titular;
    private List<Habitante> habitantes;
    private List<Expensa> expensas;
    private boolean Tardio;
    public static List<Lote> lotes = new ArrayList<>(); //Para almacenar los lotes

    public void agregarHabitante(Habitante h) throws ReglaNegocioException {
        if (h==null){throw new IllegalArgumentException("Habitante no puede ser nulo");}
        for (Habitante habitante : habitantes){
            if (habitante.getDocumento() == h.getDocumento()){ throw new ReglaNegocioException("El habitante ya esta registrado");}
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
        expensas = new ArrayList<>();
        Tardio = false;

        lotes.add(this); //lista que contendra todos los lotes

    }

    public static void listarLotes(){
        System.out.println("Lotes: ");
        for (Lote lote : lotes){
            System.out.println(lote);
        }
    }

    public static Lote buscarLote(int numero) {
        for (Lote lote : lotes) {
            if (lote.getNumero() == numero) {
                return lote;
            }
        }
        return null;
    }


    public void agregarExpensa(Expensa expensa) throws ReglaNegocioException {
        if (expensa == null) {
            throw new IllegalArgumentException("La expensa no puede ser nula");
        }

        for (Expensa exp : expensas) {
            if (exp.getPeriodo() == expensa.getPeriodo()) {
                throw new ReglaNegocioException("Ya existe una expensa para ese periodo");
            }
        }
        expensas.add(expensa);
    }

    public List<Expensa> obtenerExpensas() {
        return new ArrayList<>(expensas);
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

