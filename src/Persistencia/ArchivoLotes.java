package Persistencia;

import excepciones.ReglaNegocioException;
import lotes.Lote;
import personas.Habitante;
import personas.Persona;
import personas.Titular;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoLotes {
    private static String ARCHIVO_LOTES = "lotes.txt";
    private static String ARCHIVO_HABITANTES = "habitantes.txt";

    public void guardar() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_LOTES))) {
            for (Lote lote : Lote.lotes) {
                //guarda los datos del lote separados por ;
                bw.write(lote.getNumero() + ";" + lote.getSuperficie() + ";" + datosPersona(lote.getTitular()));
                //cada lote es una linea
                bw.newLine();
            }
        } catch (IOException e) {System.out.println("Error al guardar los lotes: " + e.getMessage());}

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_HABITANTES))) {
            for (Lote lote : Lote.lotes) {
                for (Habitante h : lote.getHabitantes()) {
                    bw.write(lote.getNumero() + ";" + datosPersona(h));
                    bw.newLine();
                }
            }
        } catch (IOException e) {System.out.println("Error al guardar los habitantes: " + e.getMessage());}
    }

    public void cargar() {
        Lote.lotes.clear();   // evitamos cargar lotes por encima de los de memoria
        cargarLotes();
        cargarHabitantes();
    }

    private void cargarLotes() {
        File archivo = new File(ARCHIVO_LOTES);
        if (!archivo.exists()) {
            System.out.println("No hay lotes guardados");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                String[] p = linea.split(";"); //separamos los datos del lote
                // un string de lote seria p[0] ; p[1] ; p[2] y asi
                // como se guarda en string usamos parse para convertir al tipo de dato relevante
                Titular titular = new Titular(p[3], p[4], Integer.parseInt(p[2]), Integer.parseInt(p[5]), leerCorreo(p[6]));
                new Lote(Integer.parseInt(p[0]), Double.parseDouble(p[1]), titular); //usamos leer correo asi si es null lo cambiamos por un simbolo -
            }
        } catch (IOException | RuntimeException e) {System.out.println("Error al leer los lotes: " + e.getMessage());
        } finally {System.out.println("Fin de la lectura de lotes");
        }
    }

    private void cargarHabitantes() {
        File archivo = new File(ARCHIVO_HABITANTES);
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
                    lote.agregarHabitante(new Habitante(p[2], p[3], Integer.parseInt(p[1]),
                            Integer.parseInt(p[4]), leerCorreo(p[5])));
                }
            }
        } catch (IOException | RuntimeException | ReglaNegocioException e) {System.out.println("Error al leer los habitantes: " + e.getMessage());}
    }

    private String datosPersona(Persona p) {
        String correo = p.getCorreo_electronico();
        if (correo == null) {
            correo = "-";
        }
        return p.getDocumento() + ";" + p.getNombre() + ";" + p.getApellido() + ";"
                + p.getTelefono() + ";" + correo;
    }

    private String leerCorreo(String texto) {
        if (texto.equals("-")) {
            return null;
        }
        return texto;
    }
}