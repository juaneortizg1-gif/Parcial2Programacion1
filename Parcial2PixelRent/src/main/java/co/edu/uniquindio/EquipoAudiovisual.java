package co.edu.uniquindio;

public abstract class EquipoAudiovisual {
    private final String codigo;
    private final String marca;
    private final String modelo;
    private EstadoEquipo estado;

    public EquipoAudiovisual(String codigo, String marca, String modelo, EstadoEquipo estado) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
    }

    public String getCodigo() { return codigo; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public EstadoEquipo getEstado() { return estado; }
    public void setEstado(EstadoEquipo estado) { this.estado = estado; }

    public boolean estaDisponible() {
        return estado == EstadoEquipo.DISPONIBLE;
    }

    public abstract double calcularCosto(int horas);
    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + " [" + codigo + "] " + marca + " " + modelo + " - " + estado;
    }
}