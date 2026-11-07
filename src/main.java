import Dispositivos.CamaraSeguridad;
import Dispositivos.Luminaria;
import Orden_y_Expensa.ControladorExpensas;
import Orden_y_Expensa.ControladorMantenimiento;
import Orden_y_Expensa.Expensa;
import Orden_y_Expensa.OrdenMantenimiento;
import Persistencia.ArchivoVisitas;
import Reportes.ControladorReportes;
import excepciones.ReglaNegocioException;
import lotes.Lote;
import pagos.MedioPago;
import pagos.Pago;
import personal.EmpleadoMantenimiento;
import personal.Especialidad;
import personas.Habitante;
import personas.Titular;
import reclamos.Reclamo;
import reclamos.TipoReclamo;

import java.sql.SQLOutput;
import java.util.Scanner;

import java.time.LocalDate;

public class main {
    public static void main(String[] args) throws ReglaNegocioException {
        ControladorExpensas controlExpensas = new ControladorExpensas();

        Titular t1 = new Titular("Jose","Martinez",22549021);

        Habitante h1 = new Habitante("Martin", "Martinez", 2374738);
        Habitante h2 = new Habitante("Luisa", "Martinez", 11758290);

        EmpleadoMantenimiento emp = new EmpleadoMantenimiento(123,"Juancito",LocalDate.now(), Especialidad.ELECTRICIDAD);

        Lote l1 = new Lote(101,10.0,t1);

        l1.agregarHabitante(h1);
        l1.agregarHabitante(h2);

        Lote l2 = new Lote(102,12,t1);
        l2.obtenerExpensas();

        Expensa exp1 = new Expensa(202610, 100000);
        Expensa exp2 = new Expensa(202611, 100000);

        l1.agregarExpensa(exp1);
        l1.agregarExpensa(exp2);

        controlExpensas.registrarPago(101, new Pago(LocalDate.now(), 150000, MedioPago.EFECTIVO));
        controlExpensas.mostrarDetalleDeuda(101);


        ControladorMantenimiento ctrlMant = new ControladorMantenimiento();

        CamaraSeguridad cam = new CamaraSeguridad(1, "Acceso principal", LocalDate.of(2025, 3, 10), "1080p", 30);
        Luminaria lum = new Luminaria(2, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");
        Luminaria lumB = new Luminaria(3, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");
        Luminaria lumC = new Luminaria(4, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");
        Luminaria lumD = new Luminaria(5, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W");

        try {
            ctrlMant.agregarDispositivo(cam);
            ctrlMant.agregarDispositivo(lum);
            ctrlMant.agregarDispositivo(lumB);
            ctrlMant.agregarDispositivo(lumC);
            ctrlMant.agregarDispositivo(lumD);

            ctrlMant.registrarFalla(1);
            OrdenMantenimiento o1 = ctrlMant.generarOrden(1, emp, LocalDate.now(), "Camara sin imagen");
            System.out.println(o1);

            for (int codigo = 2; codigo <= 5; codigo++) {
                ctrlMant.registrarFalla(codigo);
                System.out.println(ctrlMant.generarOrden(codigo, emp, LocalDate.now(), "Luminaria apagada"));
            }

            o1.cerrar(LocalDate.now(), emp, "Se cambio el cable de red");
            System.out.println(o1);
            ctrlMant.mostrarEstados();
        } catch (ReglaNegocioException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n");

        ControladorReportes reportes = new ControladorReportes(ctrlMant);

        Reclamo seguridad = new Reclamo(23, TipoReclamo.SEGURIDAD, "Porton roto", l1, t1);
        Reclamo convivencia = new Reclamo(24, TipoReclamo.CONVIVENCIA, "Ruidos", l1, t1);

        reportes.reclamosPendientes();
        reportes.morosidadPorLote();
        reportes.equiposFueraDeServicio();


        Scanner scanner = new Scanner(System.in);

        String clave = "12345";
        String usuario = "Ramon";

        System.out.println("Inicio del sistema");

        System.out.println("Menu");

        System.out.println("Ingrese su nombre de usuario y su contrasena (en ese orden)");

        while (true){

            String usuario_ingresa = scanner.nextLine();
            String clave_ingresa = scanner.nextLine();

            if((usuario_ingresa.equals(usuario)) & (clave_ingresa.equals(clave))){
                System.out.println("Ingreso de: " + usuario);
                System.out.println("");
                System.out.println("==========================================");
                System.out.println("=================ACCIONES=================");
                System.out.println("==========================================");
                System.out.println("1. Ver Visitas");
                System.out.println("2. Ver Mantenimiento");
                System.out.println("3. Ver Expensas");
                System.out.println("4. Ver Reclamos");
                System.out.println("5. Administrar Dispositivos");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("1. Ver visitas vigentes");
                        System.out.println("2. Ver visitas pendientes");
                        System.out.println("3. Buscar visita por documento");

                        int eleccion = scanner.nextInt();

                        if (eleccion == 1){

                            System.out.println("Imprimiendo visitas vigentes");
                            ArchivoVisitas archivo = new ArchivoVisitas();
                            archivo.mostrarVisitasVigentes();

                        }
                        if (eleccion == 2){

                            System.out.println("imprimiendo visitas abiertas");
                            ArchivoVisitas archivo = new ArchivoVisitas();
                            archivo.mostrarVisitasAbiertas();

                        }
                        if (eleccion == 3){
                            System.out.println("Ingrese el documento a buscar: ");
                            String documento = scanner.next();
                            System.out.println("Buscando....");
                            ArchivoVisitas archivo = new ArchivoVisitas();
                            archivo.existeEnAbiertas(documento);
                            archivo.mostrarVisitasVigentes();
                        }
                    case 2:
                        System.out.println("1. Ver todos los dispositivos");
                        System.out.println("2. Generar orden de Mantenimiento");

                        int eleccion_2 = scanner.nextInt();

                        if (eleccion_2 == 1){
                            ControladorMantenimiento sistema = new ControladorMantenimiento();

                            System.out.println("=== GENERAR ORDEN DE MANTENIMIENTO ===");


                            System.out.print("Ingrese el código del dispositivo: ");
                            int codigoDispositivo = scanner.nextInt();
                            scanner.nextLine();

                            LocalDate fechaActual = LocalDate.now();

                            EmpleadoMantenimiento empleado = new EmpleadoMantenimiento(456,"Rodrigo",LocalDate.now(), Especialidad.ELECTRICIDAD);


                            System.out.print("Ingrese la descripción de la falla: ");
                            String descripcionFalla = scanner.nextLine();


                            try {
                                System.out.println("\nProcesando la solicitud...");

                                OrdenMantenimiento nuevaOrden = sistema.generarOrden(
                                        codigoDispositivo,
                                        empleado,
                                        fechaActual,
                                        descripcionFalla
                                );


                                System.out.println("¡Orden generada exitosamente!");
                                System.out.println("ID Orden: " + nuevaOrden.toString());

                            } catch (IllegalArgumentException e) {

                                System.out.println("Error de validación: " + e.getMessage());

                            } catch (ReglaNegocioException e) {

                                System.out.println("Error de negocio: " + e.getMessage());

                            } catch (Exception e) {

                                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                            }

                        }
                }


            }

        }

    }







}