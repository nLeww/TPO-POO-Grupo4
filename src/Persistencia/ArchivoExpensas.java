package Persistencia;

import Orden_y_Expensa.Expensa;
import excepciones.ReglaNegocioException;
import lotes.Lote;
import pagos.MedioPago;
import pagos.Pago;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class ArchivoExpensas {
    private static final String ARCHIVO_EXPENSAS = "expensas.txt";
    private static final String ARCHIVO_PAGOS = "pagos.txt";

    public void guardar() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_EXPENSAS))) {
            for (Lote lote : Lote.lotes) { //por cada lote
                for (Expensa expensa : lote.obtenerExpensas()) { //por cada expensa
                    bw.write(lote.getNumero() + ";" + expensa.getPeriodo() + ";" + expensa.getImporte() + ";" + expensa.getCapitalPagado() + ";" + expensa.getInteresesPagados() + ";" + expensa.getInteresesGenerados() + ";" + expensa.getFechaUltimoCalculoInteres());
                    bw.newLine();
                }
            }
        } catch (IOException e) {System.out.println("Error al guardar las expensas: " + e.getMessage());}

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PAGOS))) {
            for (Lote lote : Lote.lotes) {
                for (Pago pago : lote.obtenerPagos()) {
                    bw.write(lote.getNumero() + ";" + pago.getFecha() + ";"
                            + pago.getImporte() + ";" + pago.getMedio());
                    bw.newLine();
                }
            }
        } catch (IOException e) {System.out.println("Error al guardar los pagos: " + e.getMessage());}
    }

    // siempre cargar los lotes primero !!
    public void cargar() {
        cargarExpensas();
        cargarPagos();
    }

    private void cargarExpensas() {
        File archivo = new File(ARCHIVO_EXPENSAS);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                //los p[] son las separaciones
                String[] p = linea.split(";");

                Lote lote = Lote.buscarLote(Integer.parseInt(p[0]));
                if (lote != null) {
                    Expensa expensa = new Expensa(Integer.parseInt(p[1]), Float.parseFloat(p[2]),
                            Double.parseDouble(p[3]), Double.parseDouble(p[4]),
                            Double.parseDouble(p[5]), LocalDate.parse(p[6]));
                    lote.agregarExpensa(expensa);
                }
            }
        } catch (IOException | RuntimeException | ReglaNegocioException e) {System.out.println("Error al leer las expensas: " + e.getMessage());
        } finally {System.out.println("Fin de la lectura de expensas");}
    }

    private void cargarPagos() {
        File archivo = new File(ARCHIVO_PAGOS);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                String[] p = linea.split(";");

                Lote lote = Lote.buscarLote(Integer.parseInt(p[0]));
                if (lote != null) {
                    Pago pago = new Pago(LocalDate.parse(p[1]), Double.parseDouble(p[2]),
                            MedioPago.valueOf(p[3]));
                    lote.cargarPago(pago);
                }
            }
        } catch (IOException | RuntimeException e) {System.out.println("Error al leer los pagos: " + e.getMessage());}
    }
}