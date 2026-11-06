package reclamos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lotes.Lote;
import personas.Persona;
import personal.Personal;

public class Reclamo {

    private int numero;
    private LocalDateTime fechaCreacion;
    private TipoReclamo tipo;
    private String descripcion;
    private PrioridadReclamo prioridad;
    private EstadoReclamo estado;
    private Lote lote;
    private Persona persona;
    private Personal responsable;
    private List<HistorialReclamo> historial;

    private PrioridadReclamo calcularPrioridad(
            TipoReclamo tipo) {

        return switch (tipo) {
            case SEGURIDAD -> PrioridadReclamo.ALTA;
            case MANTENIMIENTO -> PrioridadReclamo.MEDIA;
            case CONVIVENCIA, ADMINISTRATIVO ->
                    PrioridadReclamo.BAJA;
        };
    }

    public void asignarResponsable(Personal empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException(
                    "El empleado no puede ser nulo");
        }
        if (estado != EstadoReclamo.ABIERTO) {
            throw new IllegalStateException(
                    "Solo se pueden asignar reclamos abiertos");
        }
        this.responsable = empleado;
        this.estado = EstadoReclamo.ASIGNADO;

        registrarCambioEstado("Asignado al empleado " + empleado.getNombre());
    }

    public void avanzarEstado(String desc) {

        if (desc == null || desc.isBlank()) {
            throw new IllegalArgumentException(
                    "La observacion es obligatoria");
        }

        switch (estado) {
            case ABIERTO:
                throw new IllegalStateException(
                        "No hay responsable asignado al reclamo");
            case ASIGNADO:
                estado = EstadoReclamo.EN_CURSO;
                break;
            case EN_CURSO:
                estado = EstadoReclamo.RESUELTO;
                break;
            case RESUELTO:
                estado = EstadoReclamo.CERRADO;
                break;
            case CERRADO:
                throw new IllegalStateException(
                        "El reclamo ya esta cerrado");
        }
        registrarCambioEstado(desc);
    }

    private void registrarCambioEstado(String desc) {
        historial.add(new HistorialReclamo(estado, desc));
    }

    public void mostrarHistorial() {
        for (HistorialReclamo registro : historial) {
            System.out.println(registro);
        }
    }


    public void reabrir(Persona solicitante) {

        if (estado != EstadoReclamo.RESUELTO) {
            throw new IllegalStateException("Solo se pueden reabrir reclamos resueltos");
        }

        if (solicitante == null || !(persona.getDocumento() == solicitante.getDocumento())) { //si el solicitante esta vacio, o el doc de la persona es distinto al del solicitante
            throw new IllegalArgumentException("Solo quien presento el reclamo puede reabrirlo");
        }

        LocalDateTime fechaResolucion = null;

        for (int i = historial.size() - 1; i >= 0; i--) { //Buscamos la fecha que el reclamo quedoc omo resuelto
            HistorialReclamo registro = historial.get(i);

            if (registro.getEstado() == EstadoReclamo.RESUELTO) {
                fechaResolucion = registro.getFecha();
                break;
            }
        }

        if (fechaResolucion == null) { //si no tiene fecha de resolucion no se reabre
            throw new IllegalStateException("No se encontro la fecha de resolucion");
        }

        if (LocalDateTime.now().isAfter( //si es mayor a 5 no se reabre
                fechaResolucion.plusDays(5))) {
            throw new IllegalStateException("El plazo de reapertura ya vencio");
        }

        estado = EstadoReclamo.ASIGNADO;

        registrarCambioEstado("Reclamo reabierto por el vecino");
    }

    //Constructor, Getters y Setters
    public Reclamo(int numero, TipoReclamo tipo, String descripcion, Lote lote, Persona persona) {

        if (numero <= 0|| tipo == null || lote == null || persona == null || descripcion.isBlank()) {
            throw new IllegalArgumentException(
                    "Detalles de reclamo invalidos");
        }
        this.numero = numero;
        this.fechaCreacion = LocalDateTime.now();
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.prioridad = calcularPrioridad(tipo);

        this.estado = EstadoReclamo.ABIERTO;
        this.lote = lote;
        this.persona = persona;
        this.responsable = null;

        this.historial = new ArrayList<>();
        registrarCambioEstado("Reclamo creado");
    }

    //Getters y Setters
    public int getNumero() {
        return numero;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public TipoReclamo getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public PrioridadReclamo getPrioridad() {
        return prioridad;
    }

    public EstadoReclamo getEstado() {
        return estado;
    }

    public Lote getLote() {
        return lote;
    }

    public Persona getPersona() {
        return persona;
    }

    public Personal getResponsable() {
        return responsable;
    }

    public List<HistorialReclamo> getHistorial() {
        return new ArrayList<>(historial);
    }

    @Override
    public String toString() {
        return numero + " " +  tipo + " " + prioridad + " " + estado;
    }

}
