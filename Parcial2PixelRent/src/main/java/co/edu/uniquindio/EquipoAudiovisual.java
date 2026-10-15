package co.edu.uniquindio;

public abstract class EquipoAudiovisual {
    protected int codigo;
    protected String marca;
    protected EstadoEquipo estadoEquipo;

    public EquipoAudiovisual (int codigo, String marca, EstadoEquipo estadoEquipo){

        this.codigo=codigo;
        this.marca=marca;
        this.estadoEquipo=estadoEquipo;

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public EstadoEquipo getEstadoEquipo() {
        return estadoEquipo;
    }

    public void setEstadoEquipo(EstadoEquipo estadoEquipo) {
        this.estadoEquipo = estadoEquipo;
    }
}
