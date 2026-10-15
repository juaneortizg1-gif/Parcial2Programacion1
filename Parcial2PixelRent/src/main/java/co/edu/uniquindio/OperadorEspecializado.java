
package co.edu.uniquindio;

public class OperadorEspecializado {
    private final String identificacion;
    private final String nombreCompleto;
    private final String certificacion;
    private final int aniosExperiencia;

    public OperadorEspecializado(String identificacion, String nombreCompleto,
                                 String certificacion, int aniosExperiencia) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.certificacion = certificacion;
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCertificacion() { return certificacion; }
    public int getAniosExperiencia() { return aniosExperiencia; }

    @Override
    public String toString() {
        return nombreCompleto + " (ID " + identificacion + ", " + certificacion + ", "
                + aniosExperiencia + " años)";
    }
}