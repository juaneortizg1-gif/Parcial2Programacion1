package co.edu.uniquindio;

public class Camara extends EquipoAudiovisual {
    private String resolucion;
    private boolean estabilizador;

    public Camara(int codigo, String marca, EstadoEquipo estadoEquipo,
                  String resolucion, boolean estabilizador){

        super (codigo, marca, estadoEquipo );

        this.resolucion=resolucion;
        this.estabilizador=estabilizador;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public boolean isEstabilizador() {
        return estabilizador;
    }

    public void setEstabilizador(boolean estabilizador) {
        this.estabilizador = estabilizador;
    }
}
