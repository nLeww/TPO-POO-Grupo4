package Persistencia;

import personas.Visita;
import java.time.LocalDate;
import java.time.LocalTime;

import java.io.*;

public class ArchivoVisitas {

    private static final String ARCHIVO_VISITAS_VIGENTE = "visitas_vigente.txt";
    private static final String ARCHIVO_VISITAS_ABIERTAS = "visitas_abiertas.txt";

    public boolean existeEnVigentes(String documento) {
        return buscarDocumentoEnArchivo(ARCHIVO_VISITAS_VIGENTE, documento);
    }


    public boolean existeEnAbiertas(String documento) {
        return buscarDocumentoEnArchivo(ARCHIVO_VISITAS_ABIERTAS, documento);
    }

    public boolean registrarEnAbiertas(String documento, Visita v) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_VISITAS_ABIERTAS, true))) {
            bw.write(documento + ";" + v.getHorarioVisita() + ";" + v.getLoteVisita());
            bw.newLine();
            return true;

        } catch (IOException e) {
            System.out.println("Error al escribir en visitas abiertas: " + e.getMessage());
            return false;
        }
    }

    private boolean buscarDocumentoEnArchivo(String rutaArchivo, String documento) {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] p = linea.split(";");

                if (p.length > 0 && p[0].trim().equals(documento.trim())) {
                    return true;
                }
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error al leer el archivo " + rutaArchivo + ": " + e.getMessage());
        }
        return false;
    }

    public int obtenerLoteDeVigentes(String documento) {
        File archivo = new File("visitas_vigente.txt");
        if (!archivo.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] p = linea.split(";");

                if (p.length > 0 && p[0].trim().equals(documento.trim())) {

                    if (p.length > 2) {
                        return Integer.parseInt(p[2].trim());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener lote: " + e.getMessage());
        }
        return 0;
    }

}
