
package co.edu.uniquindio;

import java.time.LocalDate;

public class Reserva {
    public static final int MAX_ITEMS = 20;

    private final String codigo;
    private final Cliente cliente;
    private final LocalDate fecha;
    private final TurnoUtilizacion turno;
    private EstadoReserva estado = EstadoReserva.SOLICITADA;
    private OperadorEspecializado operador = null;
    private final ItemReserva[] items = new ItemReserva[MAX_ITEMS];
    private int numItems = 0;
    private String ultimoError = "";

    public Reserva(String codigo, Cliente cliente, LocalDate fecha, TurnoUtilizacion turno) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.fecha = fecha;
        this.turno = turno;
    }

    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public LocalDate getFecha() { return fecha; }
    public TurnoUtilizacion getTurno() { return turno; }
    public EstadoReserva getEstado() { return estado; }
    public OperadorEspecializado getOperador() { return operador; }
    public int getNumItems() { return numItems; }
    public ItemReserva getItem(int i) { return items[i]; }
    public String getUltimoError() { return ultimoError; }

    private boolean fallo(String mensaje) {
        ultimoError = mensaje;
        return false;
    }

    public boolean contieneEquipo(EquipoAudiovisual e) {
        for (int i = 0; i < numItems; i++) {
            if (items[i].getEquipo().getCodigo().equals(e.getCodigo())) return true;
        }
        return false;
    }

    public boolean tieneDron() {
        for (int i = 0; i < numItems; i++) {
            if (items[i].getEquipo() instanceof Dron) return true;
        }
        return false;
    }

    public boolean agregarEquipo(EquipoAudiovisual e, int horas) {
        if (estado != EstadoReserva.SOLICITADA)
            return fallo("Solo se pueden agregar equipos a una reserva en estado SOLICITADA.");
        if (horas <= 0)
            return fallo("Las horas deben ser mayores a cero.");
        if (!e.estaDisponible())
            return fallo("El equipo no está en condiciones de ser utilizado (" + e.getEstado() + ").");
        if (contieneEquipo(e))
            return fallo("El equipo ya forma parte de esta reserva.");
        if (numItems >= MAX_ITEMS)
            return fallo("La reserva alcanzó el máximo de equipos permitido.");
        items[numItems++] = new ItemReserva(e, horas);
        return true;
    }

    public boolean asignarOperador(OperadorEspecializado o) {
        if (estado != EstadoReserva.SOLICITADA)
            return fallo("Solo se puede asignar operador a una reserva SOLICITADA.");
        if (operador != null)
            return fallo("La reserva ya tiene un operador asignado.");
        operador = o;
        return true;
    }

    public double calcularValor() {
        double total = 0;
        for (int i = 0; i < numItems; i++) {
            total += items[i].calcularCosto();
        }
        return total;
    }


    public boolean confirmar() {
        if (estado != EstadoReserva.SOLICITADA)
            return fallo("Solo una reserva SOLICITADA puede pasar a CONFIRMADA.");
        if (numItems == 0)
            return fallo("La reserva debe tener al menos un equipo.");
        for (int i = 0; i < numItems; i++) {
            if (!items[i].getEquipo().estaDisponible())
                return fallo("El equipo " + items[i].getEquipo().getCodigo() + " ya no está disponible.");
        }
        if (tieneDron() && operador == null)
            return fallo("Una reserva con dron requiere un operador asignado.");
        estado = EstadoReserva.CONFIRMADA;
        return true;
    }

    public boolean cancelar() {
        if (estado != EstadoReserva.SOLICITADA && estado != EstadoReserva.CONFIRMADA)
            return fallo("Una reserva " + estado + " no puede cancelarse.");
        estado = EstadoReserva.CANCELADA;
        return true;
    }

    public boolean finalizar() {
        if (estado != EstadoReserva.CONFIRMADA)
            return fallo("Solo una reserva CONFIRMADA puede finalizarse.");
        estado = EstadoReserva.FINALIZADA;
        return true;
    }

    @Override
    public String toString() {
        return "Reserva " + codigo + " | " + cliente.getNombreCompleto() + " | " + fecha + " | "
                + turno + " | " + estado + " | $" + String.format("%,.0f", calcularValor());
    }
}