package co.edu.uniquindio;

import java.time.LocalDate;

public class Reserva {
    private int codigoReserva;
    private LocalDate fecha;
    private TurnoUtilizacion turno;
    private EstadoReserva estado;

    public Reserva(int codigoReserva, LocalDate fecha,
                   TurnoUtilizacion turno, EstadoReserva estado) {
        this.codigoReserva = codigoReserva;
        this.fecha = fecha;
        this.turno = turno;
        this.estado = estado;


    }

    public int getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(int codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TurnoUtilizacion getTurno() {
        return turno;
    }

    public void setTurno(TurnoUtilizacion turno) {
        this.turno = turno;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
