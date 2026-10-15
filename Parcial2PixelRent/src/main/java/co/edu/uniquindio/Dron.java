package co.edu.uniquindio;

public class Dron extends EquipoAudiovisual {
    private final int autonomiaMinutos;
    private final boolean bateriaAdicional;

    public Dron(String codigo, String marca, String modelo, EstadoEquipo estado,
                int autonomiaMinutos, boolean bateriaAdicional) {
        super(codigo, marca, modelo, estado);
        this.autonomiaMinutos = autonomiaMinutos;
        this.bateriaAdicional = bateriaAdicional;
    }

    public int getAutonomiaMinutos() { return autonomiaMinutos; }
    public boolean tieneBateriaAdicional() { return bateriaAdicional; }

    @Override
    public double calcularCosto(int horas) {
        double total = 18000.0 * horas;
        if (bateriaAdicional) total += 20000;
        return total;
    }

    @Override
    public String getTipo() { return "Dron"; }
}
