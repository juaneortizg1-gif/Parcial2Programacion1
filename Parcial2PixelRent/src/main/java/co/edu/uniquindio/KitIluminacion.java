package co.edu.uniquindio;

public class KitIluminacion extends EquipoAudiovisual {

    private int cantidadLuces;
    private boolean controlInalambrico;

    public KitIluminacion(int codigo, String marca, EstadoEquipo estadoEquipo,
                          int cantidadLuces, boolean controlInalambrico) {

        super(codigo, marca, estadoEquipo);

        this.cantidadLuces=cantidadLuces;
        this.controlInalambrico=controlInalambrico;
    }

    public int getCantidadLuces() {
        return cantidadLuces;
    }

    public void setCantidadLuces(int cantidadLuces) {
        this.cantidadLuces = cantidadLuces;
    }

    public boolean isControlInalambrico() {
        return controlInalambrico;
    }

    public void setControlInalambrico(boolean controlInalambrico) {
        this.controlInalambrico = controlInalambrico;
    }
}

