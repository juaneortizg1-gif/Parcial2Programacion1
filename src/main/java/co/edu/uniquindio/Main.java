package co.edu.uniquindio;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {
    static DevPlus devPlus = new DevPlus("DevPlus", "900123456-1",
            "Calle 10 # 5-20", "3001234567", "www.devplus.com");

    public static void main(String[] args) {

            String[] menu = {
                    "1. Registrar cliente",
                    "2. Registrar desarrollador",
                    "3. Registrar servicio adicional",
                    "4. Crear proyecto",
                    "5. Agregar desarrollador a proyecto",
                    "6. Agregar servicio a proyecto",
                    "7. Cambiar estado del proyecto (confirmar, iniciar, finalizar, cancelar)",
                    "8. Consultar cliente por teléfono (número perfecto)",
                    "9. Ingresos por fecha de solicitud",
                    "10. Ver listados",
                    "11. Salir"
            };

            while (true) {
                Object opcion = elegir("Seleccione una opción:", menu);
                if (opcion == null) break;
                String numero = opcion.toString().split("\\.")[0];

                switch (numero) {
                    case "1" -> registrarCliente();
                    case "2" -> registrarDesarrollador();
                    case "3" -> registrarServicio();
                    case "4" -> crearProyecto();
                    case "5" -> agregarDesarrollador();
                    case "6" -> agregarServicio();
                    case "7" -> cambiarEstado();
                    case "8" -> mostrar(devPlus.consultarClientePorTelefono(leerTexto("Teléfono del cliente:")));
                    case "9" -> ingresosPorFecha();
                    case "10" -> verListados();
                    case "11" -> { mostrar("Hasta luego."); return; }
                }
            }
        }

        //----------------------------------------------------------------------------------------

        static void registrarCliente() {
            String nombre = leerTexto("Nombre completo o razón social:");
            String id = leerTexto("Documento de identidad o NIT:");
            String tel = leerTexto("Telefono: ");
            String correo = leerTexto("Correo electrónico:");
            String pais = leerTexto("País de procedencia:");

            if (nombre.isEmpty() || id.isEmpty() || tel.isEmpty()) {
                mostrar("Nombre, identificación y teléfono son obligatorios.");
                return;
            }
            mostrar(devPlus.registrarCliente(new Cliente(nombre, id, tel, correo, pais)));
        }

        static void registrarDesarrollador() {
            int codigo = leerEntero("Código del desarrollador:");
            NivelProgramador nivel = (NivelProgramador) elegir("Nivel:", NivelProgramador.values());
            String equipo = leerTexto("Equipo de trabajo:");
            int max = leerEntero("Cantidad máxima de proyectos simultáneos:");
            double tarifa = leerDecimal("Tarifa por día:");

            if (codigo < 0 || nivel == null || max < 1 || tarifa < 0) {
                mostrar("Datos inválidos. Intente de nuevo.");
                return;
            }
            Desarrollador d = new Desarrollador(codigo, nivel, equipo, max, tarifa);
            int enCapacitacion = JOptionPane.showConfirmDialog(null, "¿Está en capacitación?", "DevPlus",
                    JOptionPane.YES_NO_OPTION);
            if (enCapacitacion == JOptionPane.YES_OPTION) {
                d.setDisponibilidad(EstadoDesarrollador.EN_CAPACITACION);
            }
            mostrar(devPlus.registrarDesarrollador(d));
        }

        static void registrarServicio() {
            int codigo = leerEntero("Código del servicio:");
            String nombre = leerTexto("Nombre (ej. Soporte técnico, Migración de datos):");
            String descripcion = leerTexto("Descripción:");
            double precio = leerDecimal("Precio:");

            if (codigo < 0 || nombre.isEmpty() || precio < 0) {
                mostrar("Datos inválidos. Intente de nuevo.");
                return;
            }
            mostrar(devPlus.registrarServicio(new ServicioAdicional(codigo, nombre, descripcion, precio)));
        }

        static void crearProyecto() {
            Cliente cliente = devPlus.buscarCliente(leerTexto("Identificación del cliente:"));
            if (cliente == null) {
                mostrar("No existe un cliente con esa identificación.");
                return;
            }
            int codigo = leerEntero("Código del proyecto:");
            LocalDate solicitud = leerFecha("Fecha de solicitud");
            LocalDate inicio = leerFecha("Fecha de inicio");
            LocalDate entrega = leerFecha("Fecha de entrega");
            MetodoDePago metodo = (MetodoDePago) elegir("Método de pago:", MetodoDePago.values());

            if (codigo < 0 || solicitud == null || inicio == null || entrega == null || metodo == null) {
                mostrar("Datos inválidos. Revise el código y el formato de las fechas.");
                return;
            }
            mostrar(devPlus.registrarProyecto(new Proyecto(codigo, solicitud, inicio, entrega, metodo), cliente));
        }

        static void agregarDesarrollador() {
            Proyecto p = devPlus.buscarProyecto(leerEntero("Código del proyecto:"));
            Desarrollador d = devPlus.buscarDesarrollador(leerEntero("Código del desarrollador:"));
            mostrar(p == null || d == null ? "Proyecto o desarrollador no encontrado."
                    : devPlus.agregarDesarrolladorAProyecto(p, d));
        }

        static void agregarServicio() {
            Proyecto p = devPlus.buscarProyecto(leerEntero("Código del proyecto:"));
            ServicioAdicional s = devPlus.buscarServicio(leerEntero("Código del servicio:"));
            mostrar(p == null || s == null ? "Proyecto o servicio no encontrado."
                    : devPlus.agregarServicioAProyecto(p, s));
        }

        static void cambiarEstado() {
            Proyecto p = devPlus.buscarProyecto(leerEntero("Código del proyecto:"));
            if (p == null) {
                mostrar("Proyecto no encontrado.");
                return;
            }
            EstadoProyecto nuevo = (EstadoProyecto) elegir(
                    "Estado actual: " + p.getEstado() + "\nNuevo estado:", EstadoProyecto.values());
            if (nuevo != null) {
                mostrar(devPlus.cambiarEstadoProyecto(p, nuevo));
            }
        }

        static void ingresosPorFecha() {
            LocalDate fecha = leerFecha("Fecha a consultar");
            mostrar(fecha == null ? "Fecha inválida."
                    : "Ingresos de los proyectos solicitados el " + fecha + ":\n$"
                    + devPlus.calcularIngresosPorFecha(fecha));
        }

        static void verListados() {
            String[] listas = {"Clientes", "Desarrolladores", "Servicios adicionales", "Proyectos"};
            Object eleccion = elegir("¿Qué desea ver?", listas);
            if (eleccion == null)
                return;

            String texto = switch (eleccion.toString()) {
                case "Clientes" -> devPlus.listarClientes();
                case "Desarrolladores" -> devPlus.listarDesarrolladores();
                case "Servicios adicionales" -> devPlus.listarServicios();
                default -> devPlus.listarProyectos();
            };
            JTextArea area = new JTextArea(texto, 15, 60);
            area.setEditable(false);
            JOptionPane.showMessageDialog(null, new JScrollPane(area), eleccion.toString(), JOptionPane.PLAIN_MESSAGE);
        }

        // ------------------------------------------------------------------------------------------

        static String leerTexto(String mensaje) {
            String valor = JOptionPane.showInputDialog(null, mensaje, "DevPlus", JOptionPane.QUESTION_MESSAGE);
            return valor == null ? "" : valor.trim();
        }

        static int leerEntero(String mensaje) {
            try { return Integer.parseInt(leerTexto(mensaje)); }
            catch (NumberFormatException e) { return -1; }
        }

        static double leerDecimal(String mensaje) {
            try { return Double.parseDouble(leerTexto(mensaje)); }
            catch (NumberFormatException e) { return -1; }
        }

        static LocalDate leerFecha(String mensaje) {
            try { return LocalDate.parse(leerTexto(mensaje + " (AAAA-MM-DD):")); }
            catch (DateTimeParseException e) { return null; }
        }

        static Object elegir(String mensaje, Object[] opciones) {
            return JOptionPane.showInputDialog(null, mensaje, "DevPlus",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        }

        static void mostrar(String mensaje) {
            JOptionPane.showMessageDialog(null, mensaje, "DevPlus", JOptionPane.INFORMATION_MESSAGE);
        }

    }
