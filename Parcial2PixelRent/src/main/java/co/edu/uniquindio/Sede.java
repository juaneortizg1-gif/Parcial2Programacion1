
package co.edu.uniquindio;

public class Sede {
    public static final int MAX_EQUIPOS = 50;

    private final String codigo;
    private final String nombre;
    private final String direccion;
    private final EquipoAudiovisual[] equipos = new EquipoAudiovisual[MAX_EQUIPOS];
    private int numEquipos = 0;

    public Sede(String codigo, String nombre, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public int getNumEquipos() { return numEquipos; }

    public boolean asignarEquipo(EquipoAudiovisual e) {
        if (numEquipos >= MAX_EQUIPOS) return false;
        equipos[numEquipos++] = e;
        return true;
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + nombre + " - " + direccion;
    }
}