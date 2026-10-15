package co.edu.uniquindio;

public class Dron extends EquipoAudiovisual{
    private int autonomiaVuelo;
    private boolean bateriaAdicional;

    public Dron (int codigo, String marca, EstadoEquipo estadoEquipo, int autonomiaVuelo,boolean bateriaAdicional){

        super (codigo, marca, estadoEquipo);

        this.autonomiaVuelo=autonomiaVuelo;
        this.bateriaAdicional=bateriaAdicional;
    }

    public int getAutonomiaVuelo() {
        return autonomiaVuelo;
    }

    public void setAutonomiaVuelo(int autonomiaVuelo) {
        this.autonomiaVuelo = autonomiaVuelo;
    }

    public boolean isBateriaAdicional() {
        return bateriaAdicional;
    }

    public void setBateriaAdicional(boolean bateriaAdicional) {
        this.bateriaAdicional = bateriaAdicional;
    }
}

