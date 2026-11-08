import Dispositivos.CamaraSeguridad;
import Dispositivos.Dispositivo;
import Dispositivos.Luminaria;
import Orden_y_Expensa.ControladorExpensas;
import Orden_y_Expensa.ControladorMantenimiento;
import Orden_y_Expensa.Expensa;
import Orden_y_Expensa.OrdenMantenimiento;
import Persistencia.ArchivoVisitas;
import Persistencia.ArchivoLotes;
import Persistencia.ArchivoExpensas;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Reclamo> reclamos = new ArrayList<>();

    private static ControladorExpensas controlExpensas;
    private static ControladorMantenimiento controlMantenimiento;
    private static EmpleadoMantenimiento empleado;

    public static void main(String[] args) {
        inicializarSistema();

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n========== SISTEMA LOS AROMOS ==========");
            System.out.println("1. Visitas");
            System.out.println("2. Mantenimiento");
            System.out.println("3. Expensas");
            System.out.println("4. Reclamos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");

            int opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> menuVisitas();
                    case 2 -> menuMantenimiento();
                    case 3 -> menuExpensas();
                    case 4 -> menuReclamos();
                    case 5 -> menuReportes();
                    case 0 -> {
                        continuar = false;
                        System.out.println("Saliendo del sistema.");
                    }
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (ReglaNegocioException | IllegalArgumentException e) {
                System.out.println("No se pudo realizar la operacion: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrio un error inesperado: " + e.getMessage());
            }
        }
    }

    // datos de ejemplo / prueba
    private static void inicializarSistema() {
        controlExpensas = new ControladorExpensas();
        controlMantenimiento = new ControladorMantenimiento();

        ArchivoLotes archivoLotes = new ArchivoLotes();
        ArchivoExpensas archivoExpensas = new ArchivoExpensas();
        archivoLotes.cargar();

        boolean datosIniciales = Lote.lotes.isEmpty();
        if (datosIniciales) {
            Titular titular = new Titular("Jose", "Martinez", 22549021);
            Lote lote1 = new Lote(101, 10.0, titular);
            Lote lote2 = new Lote(102, 12.0, titular);

            try {
                lote1.agregarHabitante(new Habitante("Martin", "Martinez", 2374738));
                lote1.agregarHabitante(new Habitante("Luisa", "Martinez", 11758290));
                lote1.agregarExpensa(new Expensa(202610, 100000));
                lote1.agregarExpensa(new Expensa(202611, 100000));
            } catch (ReglaNegocioException e) {
                System.out.println("No se pudieron cargar los datos iniciales: " + e.getMessage());
            }
        }

        archivoExpensas.cargar();

        Lote lotePrueba = Lote.buscarLote(101);
        if (lotePrueba != null && lotePrueba.obtenerExpensas().isEmpty()) {
            try {
                lotePrueba.agregarExpensa(new Expensa(202610, 100000));
                lotePrueba.agregarExpensa(new Expensa(202611, 100000));
            } catch (ReglaNegocioException e) {
                System.out.println("No se pudieron cargar las expensas de prueba: " + e.getMessage());
            }
        }

        if (datosIniciales) {
            archivoLotes.guardar();
            archivoExpensas.guardar();
        }

        empleado = new EmpleadoMantenimiento(
                123, "Juancito", LocalDate.now(), Especialidad.ELECTRICIDAD
        );

        try {
            controlMantenimiento.agregarDispositivo(new CamaraSeguridad(
                    1, "Acceso principal", LocalDate.of(2025, 3, 10), "1080p", 30
            ));
            controlMantenimiento.agregarDispositivo(new Luminaria(
                    2, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W"
            ));
            controlMantenimiento.agregarDispositivo(new Luminaria(
                    3, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W"
            ));
            controlMantenimiento.agregarDispositivo(new Luminaria(
                    4, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W"
            ));
            controlMantenimiento.agregarDispositivo(new Luminaria(
                    5, "Calle interna 1", LocalDate.of(2025, 5, 2), "LED", "50W"
            ));
        } catch (ReglaNegocioException e) {
            System.out.println("No se pudieron cargar los dispositivos iniciales: " + e.getMessage());
        }

        System.out.println("Sistema inicializado. Datos de prueba disponibles: lotes 101 y 102.");
    }

    private static void guardarDatosExpensas() {
        new ArchivoLotes().guardar();
        new ArchivoExpensas().guardar();
    }

    private static void menuVisitas() {
        ArchivoVisitas archivo = new ArchivoVisitas();
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- VISITAS ---");
            System.out.println("1. Ver visitas vigentes");
            System.out.println("2. Ver visitas abiertas");
            System.out.println("3. Buscar documento en visitas vigentes");
            System.out.println("4. Autorizar nueva visita");
            System.out.println("0. Volver");

            switch (leerEntero("Opcion: ")) {
                case 1 -> archivo.mostrarVisitasVigentes();
                case 2 -> archivo.mostrarVisitasAbiertas();
                case 3 -> {
                    String documento = leerTexto("Documento: ");
                    if (archivo.existeEnVigentes(documento)) {
                        System.out.println("La visita figura entre las visitas vigentes. Lote: "
                                + archivo.obtenerLoteDeVigentes(documento));
                    } else if (archivo.existeEnAbiertas(documento)) {
                        System.out.println("La visita figura entre las visitas abiertas.");
                    } else {
                        System.out.println("No se encontro una visita para ese documento.");
                    }
                }
                case 4 -> {
                    System.out.println("\n--- AUTORIZAR NUEVA VISITA ---");

                    String nombre = leerTexto("Nombre del visitante: ");
                    String documento = leerTexto("Documento: ");
                    int numeroLote = leerEntero("Numero de lote: ");

                    try {
                        LocalDate fecha = LocalDate.parse(
                                leerTexto("Fecha (AAAA-MM-DD): ")
                        );

                        LocalTime desde = LocalTime.parse(
                                leerTexto("Hora desde (HH:MM): ")
                        );

                        LocalTime hasta = LocalTime.parse(
                                leerTexto("Hora hasta (HH:MM): ")
                        );

                        if (!hasta.isAfter(desde)) {
                            System.out.println("La hora hasta debe ser posterior a la hora desde.");
                        } else if (Lote.buscarLote(numeroLote) == null) {
                            System.out.println("El lote no existe.");
                        } else {
                            boolean registrado = archivo.registrarAutorizacion(documento, nombre, fecha, desde, hasta, numeroLote);

                            if (registrado) {
                                System.out.println("Visita autorizada correctamente.");
                            }
                        }

                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Formato de fecha u hora incorrecto.");
                    }
                }
                case 0 -> volver = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuMantenimiento() throws ReglaNegocioException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- MANTENIMIENTO ---");
            System.out.println("1. Ver dispositivos y estados");
            System.out.println("2. Registrar falla y generar orden");
            System.out.println("3. Buscar dispositivo");
            System.out.println("0. Volver");

            switch (leerEntero("Opcion: ")) {
                case 1 -> {
                    List<Dispositivo> dispositivos = controlMantenimiento.obtenerDispositivos();
                    if (dispositivos.isEmpty()) {
                        System.out.println("No hay dispositivos registrados.");
                    } else {
                        controlMantenimiento.mostrarEstados();
                    }
                }
                case 2 -> {
                    int codigo = leerEntero("Codigo del dispositivo: ");
                    String descripcion = leerTexto("Descripcion de la falla: ");
                    controlMantenimiento.registrarFalla(codigo);

                    OrdenMantenimiento orden = controlMantenimiento.generarOrden(
                            codigo, empleado, LocalDate.now(), descripcion
                    );
                    System.out.println("Orden generada correctamente: " + orden);
                }
                case 3 -> {
                    int codigo = leerEntero("Codigo del dispositivo: ");
                    Dispositivo dispositivo = controlMantenimiento.buscarDispositivo(codigo);
                    if (dispositivo == null) {
                        System.out.println("No existe un dispositivo con ese codigo.");
                    } else {
                        System.out.println("Dispositivo " + dispositivo.getCodigo()
                                + " - " + dispositivo.getSector()
                                + " - " + dispositivo.getEstado());
                    }
                }
                case 0 -> volver = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuExpensas() throws ReglaNegocioException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- EXPENSAS ---");
            System.out.println("1. Consultar deuda de un lote");
            System.out.println("2. Mostrar detalle de deuda");
            System.out.println("3. Registrar pago");
            System.out.println("0. Volver");

            switch (leerEntero("Opcion: ")) {
                case 1 -> {
                    int numeroLote = leerEntero("Numero de lote: ");
                    System.out.println("Deuda total: $" + controlExpensas.consultarDeuda(numeroLote));
                }
                case 2 -> {
                    int numeroLote = leerEntero("Numero de lote: ");
                    controlExpensas.mostrarDetalleDeuda(numeroLote);
                }
                case 3 -> {
                    int numeroLote = leerEntero("Numero de lote: ");
                    double importe = leerDouble("Importe del pago: ");
                    System.out.println("Medio de pago: EFECTIVO, TRANSFERENCIA o TARJETA");
                    MedioPago medio = MedioPago.valueOf(leerTexto("Medio: ").toUpperCase());

                    controlExpensas.registrarPago(
                            numeroLote, new Pago(LocalDate.now(), importe, medio)
                    );
                    guardarDatosExpensas();
                    System.out.println("Pago registrado correctamente.");
                    System.out.println("Deuda restante: $" + controlExpensas.consultarDeuda(numeroLote));
                }
                case 0 -> volver = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuReclamos() throws ReglaNegocioException {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- RECLAMOS ---");
            System.out.println("1. Registrar reclamo de prueba");
            System.out.println("2. Listar reclamos");
            System.out.println("3. Asignar responsable");
            System.out.println("4. Avanzar estado");
            System.out.println("5. Ver historial");
            System.out.println("0. Volver");

            switch (leerEntero("Opcion: ")) {
                case 1 -> {
                    int numero = leerEntero("Numero de reclamo: ");
                    int numeroLote = leerEntero("Lote (101 o 102): ");
                    Lote lote = Lote.buscarLote(numeroLote);
                    if (lote == null) {
                        System.out.println("No existe ese lote.");
                        break;
                    }

                    System.out.println("Tipo: SEGURIDAD, MANTENIMIENTO, CONVIVENCIA o ADMINISTRATIVO");
                    TipoReclamo tipo = TipoReclamo.valueOf(leerTexto("Tipo: ").toUpperCase());
                    String descripcion = leerTexto("Descripcion: ");

                    Reclamo reclamo = new Reclamo(
                            numero, tipo, descripcion, lote, lote.getTitular()
                    );
                    reclamos.add(reclamo);
                    System.out.println("Reclamo registrado: " + reclamo);
                }
                case 2 -> {
                    if (reclamos.isEmpty()) {
                        System.out.println("No hay reclamos registrados.");
                    } else {
                        for (int i = 0; i < reclamos.size(); i++) {
                            System.out.println((i + 1) + ". " + reclamos.get(i));
                        }
                    }
                }
                case 3 -> {
                    Reclamo reclamo = seleccionarReclamo();
                    if (reclamo != null) {
                        reclamo.asignarResponsable(empleado);
                        System.out.println("Responsable asignado correctamente.");
                    }
                }
                case 4 -> {
                    Reclamo reclamo = seleccionarReclamo();
                    if (reclamo != null) {
                        String observacion = leerTexto("Observacion del cambio: ");
                        reclamo.avanzarEstado(observacion);
                        System.out.println("Nuevo estado: " + reclamo.getEstado());
                    }
                }
                case 5 -> {
                    Reclamo reclamo = seleccionarReclamo();
                    if (reclamo != null) {
                        reclamo.mostrarHistorial();
                    }
                }
                case 0 -> volver = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static Reclamo seleccionarReclamo() {
        if (reclamos.isEmpty()) {
            System.out.println("Todavia no hay reclamos registrados.");
            return null;
        }

        for (int i = 0; i < reclamos.size(); i++) {
            System.out.println((i + 1) + ". " + reclamos.get(i));
        }

        int indice = leerEntero("Numero de reclamo de la lista: ") - 1;
        if (indice < 0 || indice >= reclamos.size()) {
            System.out.println("Seleccion invalida.");
            return null;
        }
        return reclamos.get(indice);
    }

    private static void menuReportes() {
        ControladorReportes reportes = new ControladorReportes(controlMantenimiento);
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- REPORTES ---");
            System.out.println("1. Reclamos pendientes");
            System.out.println("2. Morosidad por lote");
            System.out.println("3. Equipos fuera de servicio");
            System.out.println("0. Volver");

            switch (leerEntero("Opcion: ")) {
                case 1 -> reportes.reclamosPendientes();
                case 2 -> reportes.morosidadPorLote();
                case 3 -> reportes.equiposFueraDeServicio();
                case 0 -> volver = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un importe valido.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}
