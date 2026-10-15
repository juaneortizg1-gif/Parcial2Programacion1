package co.edu.uniquindio;

public class Camara extends EquipoAudiovisual {
    private final String resolucionMaxima;
    private final boolean tieneEstabilizador;

    public Camara(String codigo, String marca, String modelo, EstadoEquipo estado,
                  String resolucionMaxima, boolean tieneEstabilizador) {
        super(codigo, marca, modelo, estado);
        this.resolucionMaxima = resolucionMaxima;
        this.tieneEstabilizador = tieneEstabilizador;
    }

    public String getResolucionMaxima() { return resolucionMaxima; }
    public boolean tieneEstabilizador() { return tieneEstabilizador; }

    @Override
    public double calcularCosto(int horas) {
        double total = 12000.0 * horas;
        if (tieneEstabilizador) total += 15000;
        return total;
    }

    @Override
    public String getTipo() { return "Cámara"; }
}