package co.edu.uniquindio;

import java.time.LocalDate;

public class Empresa {
    private static final int MAX = 200;

    private final Sede[] sedes = new Sede[MAX];
    private final EquipoAudiovisual[] equipos = new EquipoAudiovisual[MAX];
    private final Cliente[] clientes = new Cliente[MAX];
    private final OperadorEspecializado[] operadores = new OperadorEspecializado[MAX];
    private final Reserva[] reservas = new Reserva[MAX];
    private int nSedes, nEquipos, nClientes, nOperadores, nReservas;

    private String ultimoMensaje = "";

    public String getUltimoMensaje() { return ultimoMensaje; }

    private boolean fallo(String mensaje) {
        ultimoMensaje = mensaje;
        return false;
    }

    public Sede buscarSede(String codigo) {
        for (int i = 0; i < nSedes; i++) if (sedes[i].getCodigo().equals(codigo)) return sedes[i];
        return null;
    }

    public EquipoAudiovisual buscarEquipo(String codigo) {
        for (int i = 0; i < nEquipos; i++) if (equipos[i].getCodigo().equals(codigo)) return equipos[i];
        return null;
    }

    public Cliente buscarCliente(String identificacion) {
        for (int i = 0; i < nClientes; i++)
            if (clientes[i].getIdentificacion().equals(identificacion)) return clientes[i];
        return null;
    }

    public OperadorEspecializado buscarOperador(String identificacion) {
        for (int i = 0; i < nOperadores; i++)
            if (operadores[i].getIdentificacion().equals(identificacion)) return operadores[i];
        return null;
    }

    public Reserva buscarReserva(String codigo) {
        for (int i = 0; i < nReservas; i++) if (reservas[i].getCodigo().equals(codigo)) return reservas[i];
        return null;
    }

    public boolean registrarSede(Sede s) {
        if (buscarSede(s.getCodigo()) != null) return fallo("Ya existe una sede con ese código.");
        if (nSedes >= MAX) return fallo("Capacidad de sedes llena.");
        sedes[nSedes++] = s;
        return true;
    }

    public boolean registrarEquipo(EquipoAudiovisual e, String codigoSede) {
        if (buscarEquipo(e.getCodigo()) != null) return fallo("Ya existe un equipo con ese código.");
        Sede sede = buscarSede(codigoSede);
        if (sede == null) return fallo("La sede indicada no existe.");
        if (nEquipos >= MAX) return fallo("Capacidad de equipos llena.");
        if (!sede.asignarEquipo(e)) return fallo("La sede no admite más equipos.");
        equipos[nEquipos++] = e;
        return true;
    }

    public boolean registrarCliente(Cliente c) {
        if (buscarCliente(c.getIdentificacion()) != null)
            return fallo("Ya existe un cliente con esa identificación.");
        if (nClientes >= MAX) return fallo("Capacidad de clientes llena.");
        clientes[nClientes++] = c;
        return true;
    }

    public boolean registrarOperador(OperadorEspecializado o) {
        if (buscarOperador(o.getIdentificacion()) != null)
            return fallo("Ya existe un operador con esa identificación.");
        if (nOperadores >= MAX) return fallo("Capacidad de operadores llena.");
        operadores[nOperadores++] = o;
        return true;
    }

    public boolean registrarReserva(String codigo, String idCliente, LocalDate fecha, TurnoUtilizacion turno) {
        if (buscarReserva(codigo) != null) return fallo("Ya existe una reserva con ese código.");
        Cliente c = buscarCliente(idCliente);
        if (c == null) return fallo("El cliente no está registrado.");
        if (nReservas >= MAX) return fallo("Capacidad de reservas llena.");
        reservas[nReservas++] = new Reserva(codigo, c, fecha, turno);
        return true;
    }


    public boolean agregarEquipoAReserva(String codReserva, String codEquipo, int horas) {
        Reserva r = buscarReserva(codReserva);
        if (r == null) return fallo("La reserva no existe.");
        EquipoAudiovisual e = buscarEquipo(codEquipo);
        if (e == null) return fallo("El equipo no existe.");
        if (!r.agregarEquipo(e, horas)) return fallo(r.getUltimoError());
        return true;
    }

    public boolean asignarOperador(String codReserva, String idOperador) {
        Reserva r = buscarReserva(codReserva);
        if (r == null) return fallo("La reserva no existe.");
        OperadorEspecializado o = buscarOperador(idOperador);
        if (o == null) return fallo("El operador no existe.");
        if (!r.asignarOperador(o)) return fallo(r.getUltimoError());
        return true;
    }

    public boolean cambiarEstado(String codReserva, EstadoReserva nuevo) {
        Reserva r = buscarReserva(codReserva);
        if (r == null) return fallo("La reserva no existe.");
        boolean ok;
        switch (nuevo) {
            case CONFIRMADA:
                for (int i = 0; i < r.getNumItems(); i++) {
                    EquipoAudiovisual e = r.getItem(i).getEquipo();
                    if (equipoOcupado(e, r.getFecha(), r.getTurno(), r)) {
                        return fallo("El equipo " + e.getCodigo()
                                + " ya está en otra reserva confirmada para esa fecha y turno.");
                    }
                }
                ok = r.confirmar();
                break;
            case CANCELADA:
                ok = r.cancelar();
                break;
            case FINALIZADA:
                ok = r.finalizar();
                break;
            default:
                return fallo("Una reserva no puede volver al estado SOLICITADA.");
        }
        return ok || fallo(r.getUltimoError());
    }

    private boolean equipoOcupado(EquipoAudiovisual e, LocalDate fecha, TurnoUtilizacion turno, Reserva excluir) {
        for (int i = 0; i < nReservas; i++) {
            Reserva r = reservas[i];
            if (r == excluir) continue;
            if (r.getEstado() == EstadoReserva.CONFIRMADA
                    && r.getFecha().equals(fecha)
                    && r.getTurno() == turno
                    && r.contieneEquipo(e)) {
                return true;
            }
        }
        return false;
    }

    public EquipoAudiovisual[] equiposDisponibles(LocalDate fecha, TurnoUtilizacion turno) {
        int n = 0;
        for (int i = 0; i < nEquipos; i++) {
            if (equipos[i].estaDisponible() && !equipoOcupado(equipos[i], fecha, turno, null)) {
                n++;
            }
        }
        EquipoAudiovisual[] resultado = new EquipoAudiovisual[n];
        int k = 0;
        for (int i = 0; i < nEquipos; i++) {
            if (equipos[i].estaDisponible() && !equipoOcupado(equipos[i], fecha, turno, null)) {
                resultado[k++] = equipos[i];
            }
        }
        return resultado;
    }

    public Reserva[] reservasConfirmadasDeCliente(String identificacion) {
        int n = 0;
        for (int i = 0; i < nReservas; i++) {
            if (reservas[i].getEstado() == EstadoReserva.CONFIRMADA
                    && reservas[i].getCliente().getIdentificacion().equals(identificacion)) {
                n++;
            }
        }
        Reserva[] resultado = new Reserva[n];
        int k = 0;
        for (int i = 0; i < nReservas; i++) {
            if (reservas[i].getEstado() == EstadoReserva.CONFIRMADA
                    && reservas[i].getCliente().getIdentificacion().equals(identificacion)) {
                resultado[k++] = reservas[i];
            }
        }
        return resultado;
    }

    public double valorReserva(String codReserva) {
        Reserva r = buscarReserva(codReserva);
        return r == null ? -1 : r.calcularValor();
    }
}
