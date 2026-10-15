
package co.edu.uniquindio;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Empresa empresa = new Empresa();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> registrarSede();
                case 2 -> registrarEquipo();
                case 3 -> registrarCliente();
                case 4 -> registrarOperador();
                case 5 -> crearReserva();
                case 6 -> agregarEquipoAReserva();
                case 7 -> asignarOperador();
                case 8 -> cambiarEstado();
                case 9 -> verEquiposDisponibles();
                case 10 -> verReservasCliente();
                case 11 -> verValorReserva();
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== PIXELRENT =====");
        System.out.println("1. Registrar sede");
        System.out.println("2. Registrar equipo");
        System.out.println("3. Registrar cliente");
        System.out.println("4. Registrar operador");
        System.out.println("5. Crear reserva");
        System.out.println("6. Agregar equipo a reserva");
        System.out.println("7. Asignar operador a reserva");
        System.out.println("8. Cambiar estado de reserva");
        System.out.println("9. Ver equipos disponibles (fecha y turno)");
        System.out.println("10. Ver reservas confirmadas de un cliente");
        System.out.println("11. Ver valor de una reserva");
        System.out.println("0. Salir");
    }

    private static void registrarSede() {
        String codigo = leerTexto("Código de la sede: ");
        String nombre = leerTexto("Nombre: ");
        String direccion = leerTexto("Dirección: ");
        mostrarResultado(empresa.registrarSede(new Sede(codigo, nombre, direccion)), "Sede registrada.");
    }

    private static void registrarEquipo() {
        System.out.println("Tipo: 1. Cámara  2. Dron  3. Kit de iluminación");
        int tipo = leerEntero("Opción: ");
        String codigo = leerTexto("Código del equipo: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        EstadoEquipo estado = leerEstadoEquipo();

        EquipoAudiovisual equipo;
        switch (tipo) {
            case 1 -> {
                String resolucion = leerTexto("Resolución máxima: ");
                boolean estabilizador = leerSiNo("¿Tiene estabilizador? (s/n): ");
                equipo = new Camara(codigo, marca, modelo, estado, resolucion, estabilizador);
            }
            case 2 -> {
                int autonomia = leerEntero("Autonomía en minutos: ");
                boolean bateria = leerSiNo("¿Tiene batería adicional? (s/n): ");
                equipo = new Dron(codigo, marca, modelo, estado, autonomia, bateria);
            }
            case 3 -> {
                int luces = leerEntero("Cantidad de luces: ");
                boolean control = leerSiNo("¿Tiene control inalámbrico? (s/n): ");
                equipo = new KitIluminacion(codigo, marca, modelo, estado, luces, control);
            }
            default -> {
                System.out.println("Tipo inválido.");
                return;
            }
        }

        String codigoSede = leerTexto("Código de la sede donde está el equipo: ");
        mostrarResultado(empresa.registrarEquipo(equipo, codigoSede), "Equipo registrado.");
    }

    private static void registrarCliente() {
        String nombre = leerTexto("Nombre completo: ");
        String identificacion = leerTexto("Identificación: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        mostrarResultado(empresa.registrarCliente(new Cliente(nombre, identificacion, telefono, correo)),
                "Cliente registrado.");
    }

    private static void registrarOperador() {
        String identificacion = leerTexto("Identificación: ");
        String nombre = leerTexto("Nombre completo: ");
        String certificacion = leerTexto("Certificación: ");
        int anios = leerEntero("Años de experiencia: ");
        mostrarResultado(empresa.registrarOperador(
                        new OperadorEspecializado(identificacion, nombre, certificacion, anios)),
                "Operador registrado.");
    }

    private static void crearReserva() {
        String codigo = leerTexto("Código de la reserva: ");
        String idCliente = leerTexto("Identificación del cliente: ");
        LocalDate fecha = leerFecha();
        TurnoUtilizacion turno = leerTurno();
        if (fecha == null || turno == null) {
            System.out.println("Fecha o turno inválidos.");
            return;
        }
        mostrarResultado(empresa.registrarReserva(codigo, idCliente, fecha, turno),
                "Reserva creada en estado SOLICITADA.");
    }

    private static void agregarEquipoAReserva() {
        String codReserva = leerTexto("Código de la reserva: ");
        String codEquipo = leerTexto("Código del equipo: ");
        int horas = leerEntero("Horas de uso: ");
        mostrarResultado(empresa.agregarEquipoAReserva(codReserva, codEquipo, horas),
                "Equipo agregado a la reserva.");
    }

    private static void asignarOperador() {
        String codReserva = leerTexto("Código de la reserva: ");
        String idOperador = leerTexto("Identificación del operador: ");
        mostrarResultado(empresa.asignarOperador(codReserva, idOperador), "Operador asignado.");
    }

    private static void cambiarEstado() {
        String codReserva = leerTexto("Código de la reserva: ");
        System.out.println("Nuevo estado: 1. CONFIRMADA  2. CANCELADA  3. FINALIZADA");
        int opcion = leerEntero("Opción: ");
        EstadoReserva nuevo = switch (opcion) {
            case 1 -> EstadoReserva.CONFIRMADA;
            case 2 -> EstadoReserva.CANCELADA;
            case 3 -> EstadoReserva.FINALIZADA;
            default -> null;
        };
        if (nuevo == null) {
            System.out.println("Opción inválida.");
            return;
        }
        mostrarResultado(empresa.cambiarEstado(codReserva, nuevo), "Estado actualizado a " + nuevo + ".");
    }

    private static void verEquiposDisponibles() {
        LocalDate fecha = leerFecha();
        TurnoUtilizacion turno = leerTurno();
        if (fecha == null || turno == null) {
            System.out.println("Fecha o turno inválidos.");
            return;
        }
        EquipoAudiovisual[] disponibles = empresa.equiposDisponibles(fecha, turno);
        if (disponibles.length == 0) {
            System.out.println("No hay equipos disponibles para esa fecha y turno.");
            return;
        }
        for (EquipoAudiovisual e : disponibles) {
            System.out.println(e);
        }
    }

    private static void verReservasCliente() {
        String idCliente = leerTexto("Identificación del cliente: ");
        Reserva[] reservas = empresa.reservasConfirmadasDeCliente(idCliente);
        if (reservas.length == 0) {
            System.out.println("El cliente no tiene reservas confirmadas.");
            return;
        }
        for (Reserva r : reservas) {
            System.out.println(r);
        }
    }

    private static void verValorReserva() {
        String codReserva = leerTexto("Código de la reserva: ");
        double valor = empresa.valorReserva(codReserva);
        if (valor < 0) {
            System.out.println("La reserva no existe.");
        } else {
            System.out.printf("Valor total: $%,.0f%n", valor);
        }
    }

    // ---------- Utilidades ----------

    private static void mostrarResultado(boolean ok, String exito) {
        System.out.println(ok ? exito : empresa.getUltimoMensaje());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero.");
            }
        }
    }

    private static boolean leerSiNo(String mensaje) {
        return leerTexto(mensaje).equalsIgnoreCase("s");
    }

    private static LocalDate leerFecha() {
        try {
            return LocalDate.parse(leerTexto("Fecha (aaaa-mm-dd): "));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static TurnoUtilizacion leerTurno() {
        System.out.println("Turno: 1. Mañana  2. Tarde  3. Noche");
        return switch (leerEntero("Opción: ")) {
            case 1 -> TurnoUtilizacion.MANIANA;
            case 2 -> TurnoUtilizacion.TARDE;
            case 3 -> TurnoUtilizacion.NOCHE;
            default -> null;
        };
    }

    private static EstadoEquipo leerEstadoEquipo() {
        System.out.println("Estado: 1. Disponible  2. Mantenimiento  3. Fuera de servicio");
        return switch (leerEntero("Opción: ")) {
            case 2 -> EstadoEquipo.MANTENIMIENTO;
            case 3 -> EstadoEquipo.FUERA_DE_SERVICIO;
            default -> EstadoEquipo.DISPONIBLE;
        };
    }
}