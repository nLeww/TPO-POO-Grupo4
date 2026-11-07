package Orden_y_Expensa;

import java.time.LocalDateTime;
import Persistencia.ArchivoVisitas;
import personas.Visita;

public class ControladorVisitas {

    public void registrarIngreso(String documento) {

        boolean tieneAutorizacion = buscarAutorizacionVigente(documento);
        if (!tieneAutorizacion) {
            mostrarError("Ingreso rechazado: No existe autorización vigente para el documento " + documento);
            return;
        }


        boolean yaEstaIngresado = buscarIngresoAbierto(documento);
        if (yaEstaIngresado) {
            mostrarError("Ingreso rechazado: La visita ya se encuentra registrada dentro del establecimiento.");
            return;
        }


        ArchivoVisitas manager = new ArchivoVisitas();
        int numeroLoteReal = manager.obtenerLoteDeVigentes(documento);


        boolean exito = crearIngreso(documento, LocalDateTime.now(), numeroLoteReal);

    }

    private boolean buscarAutorizacionVigente(String documento) {
        ArchivoVisitas managerArchivos = new ArchivoVisitas();
        return managerArchivos.existeEnVigentes(documento);

    }

    private boolean buscarIngresoAbierto(String documento) {
        ArchivoVisitas managerArchivos = new ArchivoVisitas();
        return managerArchivos.existeEnAbiertas(documento);
    }


    private boolean crearIngreso(String documento, LocalDateTime fechaHora, int numero) {
        try {
            Visita nuevaVisita = new Visita(fechaHora.toLocalDate(), fechaHora.toLocalTime(), numero);

            Visita.visitas.add(nuevaVisita);

            ArchivoVisitas archivo = new ArchivoVisitas();
            return archivo.registrarEnAbiertas(documento, nuevaVisita);

        } catch (Exception e) {
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    private void mostrarMensajeExitoso(String mensaje) {
        System.out.println("[SISTEMA] " + mensaje);
    }
}