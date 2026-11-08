package Persistencia;

import personas.Visita;
import java.time.LocalDate;
import java.time.LocalTime;

import java.io.*;

public class ArchivoVisitas {

    private static final String ARCHIVO_VISITAS_VIGENTE = "visitas_vigente.txt";
    private static final String ARCHIVO_VISITAS_ABIERTAS = "visitas_abiertas.txt";

    public void mostrarVisitasVigentes() {
        File archivo = new File(ARCHIVO_VISITAS_VIGENTE);

        if (!archivo.exists()) {
            System.out.println("El archivo de visitas vigentes no existe o está vacío.");
            return;
        }

        // Se abre el archivo para lectura utilizando un bloque try-with-resources
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("\n--- REGISTROS DE VISITAS VIGENTES ---");

            while ((linea = br.readLine()) != null) {
                // Reemplaza los puntos y comas por un formato más legible si lo deseas
                String[] datos = linea.split(";");
                if (datos.length >= 3) {
                    System.out.println("Documento: " + datos[0] + " | Horario: " + datos[1] + " | Lote: " + datos[2]);
                } else {
                    System.out.println(linea); // Muestra la línea tal cual si no cumple el formato
                }
            }
            System.out.println("-------------------------------------\n");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de visitas vigentes: " + e.getMessage());
        }
    }

    public void mostrarVisitasAbiertas() {
        // Usamos la constante que ya tienes declarada arriba en tu archivo
        File archivo = new File(ARCHIVO_VISITAS_ABIERTAS);

        if (!archivo.exists()) {
            System.out.println("El archivo de visitas abiertas no existe o está vacío.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("\n--- REGISTROS DE VISITAS ABIERTAS ---");

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 3) {
                    System.out.println("Documento: " + datos[0] + " | Horario: " + datos[1] + " | Lote: " + datos[2]);
                } else {
                    System.out.println(linea);
                }
            }
            System.out.println("-------------------------------------\n");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de visitas abiertas: " + e.getMessage());
        }
    }

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

    public boolean registrarAutorizacion(String documento, String nombre, LocalDate fecha, LocalTime horaDesde, LocalTime horaHasta, int numeroLote) {

        if (documento.isBlank() || nombre.isBlank()) {
            System.out.println("Los datos no pueden estar vacios.");
            return false;
        }

        if (existeEnVigentes(documento)) {
            System.out.println("Ya existe una autorizacion vigente para ese documento.");
            return false;
        }

        try (FileWriter fw = new FileWriter("visitas_vigente.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(documento + ";" + horaDesde + ";" + numeroLote
                    + ";" + nombre + ";" + fecha + ";" + horaHasta);
            bw.newLine();

            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar la autorizacion: " + e.getMessage());
            return false;
        }
    }

    public boolean buscarDocumentoEnArchivo(String rutaArchivo, String documento) {
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
