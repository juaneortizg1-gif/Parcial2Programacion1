package co.edu.uniquindio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Proyecto {
    private int codigo;
    private LocalDate fechaSoli;
    private LocalDate fechaIni;
    private LocalDate entrega;
    private EstadoProyecto estado;
    private MetodoDePago metodoPago;
    private double valorTotal;
    private Cliente cliente;

    private Desarrollador[] desarrolladores;
    private int cantidadDesarrolladores;
    private ServicioAdicional[] servicios;
    private int cantidadServicios;

    public Proyecto(int codigo, LocalDate fechaSoli, LocalDate fechaIni,
                    LocalDate entrega, MetodoDePago metodoPago) {
        this.codigo = codigo;
        this.fechaSoli = fechaSoli;
        this.fechaIni = fechaIni;
        this.entrega = entrega;
        this.metodoPago = metodoPago;
        this.estado = EstadoProyecto.PENDIENTE;
        this.valorTotal = 0;
        this.desarrolladores = new Desarrollador[10];
        this.cantidadDesarrolladores = 0;
        this.servicios = new ServicioAdicional[10];
        this.cantidadServicios = 0;
    }

    // ---------- Getters y setters ----------
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public LocalDate getFechaSoli() { return fechaSoli; }
    public void setFechaSoli(LocalDate fechaSoli) { this.fechaSoli = fechaSoli; }

    public LocalDate getFechaIni() { return fechaIni; }
    public void setFechaIni(LocalDate fechaIni) { this.fechaIni = fechaIni; }

    public LocalDate getEntrega() { return entrega; }
    public void setEntrega(LocalDate entrega) { this.entrega = entrega; }

    public EstadoProyecto getEstado() { return estado; }
    public void setEstado(EstadoProyecto estado) { this.estado = estado; }

    public MetodoDePago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoDePago metodoPago) { this.metodoPago = metodoPago; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Desarrollador[] getDesarrolladores() { return desarrolladores; }
    public int getCantidadDesarrolladores() { return cantidadDesarrolladores; }
    public ServicioAdicional[] getServicios() { return servicios; }
    public int getCantidadServicios() { return cantidadServicios; }

    // ------------------------------------------------------------------------------------------
    public boolean contieneDesarrollador(Desarrollador d) {
        for (int i = 0; i < cantidadDesarrolladores; i++) {
            if (desarrolladores[i] == d) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------------------------
    public boolean agregarDesarrollador(Desarrollador d) {
        if (cantidadDesarrolladores == desarrolladores.length || contieneDesarrollador(d)) {
            return false;
        }
        desarrolladores[cantidadDesarrolladores] = d;
        cantidadDesarrolladores++;
        return true;
    }

    // ------------------------------------------------------------------------------------------
    public boolean agregarServicio(ServicioAdicional s) {
        if (cantidadServicios == servicios.length || !s.isDisponibilidad()) {
            return false;
        }
        servicios[cantidadServicios] = s;
        cantidadServicios++;
        return true;
    }

    //
    public long calcularDias() {
        return ChronoUnit.DAYS.between(fechaIni, entrega) + 1;
    }

    //------------------------------------------------------------------------------------------
    public double calcularValorTotal(boolean clienteFrecuente, double porcentajeDescuento) {
        double sumaTarifas = 0;
        for (int i = 0; i < cantidadDesarrolladores; i++) {
            sumaTarifas += desarrolladores[i].getTarifaDia();
        }
        double costoDesarrollo = sumaTarifas * calcularDias();

        double costoServicios = 0;
        for (int i = 0; i < cantidadServicios; i++) {
            costoServicios += servicios[i].getPrecio();
        }

        double subtotal = costoDesarrollo + costoServicios;
        double descuento = 0;
        if (clienteFrecuente) {
            descuento = subtotal * porcentajeDescuento;
        }
        valorTotal = subtotal - descuento;
        return valorTotal;
    }

    public boolean estaActivo() {
        return estado == EstadoProyecto.CONFIRMADO || estado == EstadoProyecto.EN_CURSO;
    }


    public boolean seCruzaCon(LocalDate ini, LocalDate fin) {
        return !(fin.isBefore(fechaIni) || ini.isAfter(entrega));
    }

    public String toString() {
        String nombreCliente = (cliente == null) ? "-" : cliente.getNombre();
        return "Proyecto #" + codigo + " | Cliente: " + nombreCliente
                + " | Solicitud: " + fechaSoli + " | " + fechaIni + " a " + entrega
                + " | " + estado + " | " + metodoPago
                + " | Devs: " + cantidadDesarrolladores
                + " | Servicios: " + cantidadServicios
                + " | Total: $" + valorTotal;
    }
}