
package co.edu.uniquindio;

public class ItemReserva {
    private final EquipoAudiovisual equipo;
    private final int horas;

    public ItemReserva(EquipoAudiovisual equipo, int horas) {
        this.equipo = equipo;
        this.horas = horas;
    }

    public EquipoAudiovisual getEquipo() { return equipo; }
    public int getHoras() { return horas; }

    public double calcularCosto() {
        return equipo.calcularCosto(horas);
    }
}