
package co.edu.uniquindio;

public class KitIluminacion extends EquipoAudiovisual {
    private final int cantidadLuces;
    private final boolean controlInalambrico;

    public KitIluminacion(String codigo, String marca, String modelo, EstadoEquipo estado,
                          int cantidadLuces, boolean controlInalambrico) {
        super(codigo, marca, modelo, estado);
        this.cantidadLuces = cantidadLuces;
        this.controlInalambrico = controlInalambrico;
    }

    public int getCantidadLuces() { return cantidadLuces; }
    public boolean tieneControlInalambrico() { return controlInalambrico; }

    @Override
    public double calcularCosto(int horas) {
        double total = 10000.0 * horas;
        if (controlInalambrico) total += 12000;
        return total;
    }

    @Override
    public String getTipo() { return "Kit de iluminación"; }
}
