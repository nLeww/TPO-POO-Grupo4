package lotes;

import Orden_y_Expensa.Expensa;
import excepciones.ReglaNegocioException;
import pagos.Pago;
import personas.Habitante;
import personas.Titular;

import java.util.List;
import java.util.ArrayList;
import reclamos.Reclamo;

public class Lote {
    private int numero;
    private double superficie;
    private Titular titular;
    private List<Habitante> habitantes;
    private List<Expensa> expensas;
    private boolean Tardio;
    public static List<Lote> lotes = new ArrayList<>(); //Para almacenar los lotes
    private List<Pago> pagos;
    private List<Reclamo> reclamos;

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
        pagos = new ArrayList<>();
        reclamos = new ArrayList<>();
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

    public double calcularDeudaTotal() {
        double total = 0;
        for (Expensa e : expensas) {
            total += e.obtenerSaldoPendiente() + e.obtenerInteresesPendientes();
        }
        return total;
    }

    private Expensa buscarMasAntiguaConDeuda() {
        Expensa masAntigua = null;

        for (Expensa e : expensas) {
            double deuda = e.obtenerSaldoPendiente() + e.obtenerInteresesPendientes();

            if (deuda > 0) {
                if (masAntigua == null) {
                    masAntigua = e;
                } else if (e.getPeriodo() < masAntigua.getPeriodo()) {
                    masAntigua = e;
                }
            }
        }
        return masAntigua;
    }

    public void registrarPago(Pago pago) throws ReglaNegocioException {
        if (pago == null) {
            throw new IllegalArgumentException("El pago no puede ser nulo");
        }
        if (pago.getImporte() > calcularDeudaTotal()) {
            throw new ReglaNegocioException("El pago supera la deuda pendiente del lote");
        }

        double restante = pago.getImporte();

        while (restante > 0) {
            Expensa masAntigua = buscarMasAntiguaConDeuda();
            if (masAntigua == null) {
                break;
            }
            restante = masAntigua.registrarPago(restante);
        }
        pagos.add(pago);
    }

    public void agregarReclamo(Reclamo reclamo) {
        reclamos.add(reclamo);
    }

    public List<Reclamo> obtenerReclamos() {
        return new ArrayList<>(reclamos);
    }

    public boolean actualizarMorosidad() {
        int impagos = contarPeriodosImpagos();

        if (!Tardio && impagos > 3) {
            Tardio = true;
            return true;
        }
        if (Tardio && impagos == 0) {
            Tardio = false;
        }
        return false;
    }

    public boolean estaMoroso() {
        return Tardio;
    }

    public int contarPeriodosImpagos() {
        int cantidad = 0;
        for (Expensa e : expensas) {
            if (e.estaVencida() && e.obtenerSaldoPendiente() > 0) {
                cantidad++;
            }
        }
        return cantidad;
    }
    public void verificarMorosidad(int numeroLote) {
        Lote lote = Lote.buscarLote(numeroLote);
        if (lote == null) {
            throw new IllegalArgumentException("No existe un lote con ese numero");
        }

        boolean nuevoMoroso = lote.actualizarMorosidad();

        if (nuevoMoroso) {
            System.out.println("NOTIFICACION FORMAL a " + lote.getTitular() + ": el lote " + numeroLote + " tiene mas de 3 periodos impagos.");
        }
    }

    public List<Expensa> obtenerExpensas() {
        return new ArrayList<>(expensas);
    }

    public void cargarPago(Pago pago) {
        pagos.add(pago);
    }

    public List<Pago> obtenerPagos() {return new ArrayList<>(pagos);}

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

