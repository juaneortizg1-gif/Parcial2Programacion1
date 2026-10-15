package co.edu.uniquindio;

public class OperadorEspecializado {
    private String id;
    private String nombre;
    private String certificacion;
    private int aniosExperiencia;

    public OperadorEspecializado (String id, String nombre, String certificacion,
                                  int aniosExperiencia){
        this.id=id;
        this.nombre=nombre;
        this.certificacion=certificacion;
        this.aniosExperiencia=aniosExperiencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }
}

