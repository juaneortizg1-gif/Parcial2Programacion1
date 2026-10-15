package co.edu.uniquindio;

public class Desarrollador {
    private int codigo;
    private NivelProgramador nivel;
    private String equipoTrabajo;
    private int maxProyectos;
    private double tarifaDia;
    private EstadoDesarrollador disponibilidad;


    public Desarrollador(int codigo, NivelProgramador nivel, String equipoTrabajo,
                         int maxProyectos, double tarifaDia){
        this.codigo= codigo;
        this.nivel= nivel;
        this.equipoTrabajo=equipoTrabajo;
        this.maxProyectos=maxProyectos;
        this.tarifaDia=tarifaDia;
        this.disponibilidad = EstadoDesarrollador.DISPONIBLE;
    }

    public int getCodigo() {
        return codigo;
    }

    public NivelProgramador getNivel() {
        return nivel;
    }

    public void setNivel(NivelProgramador nivel) {
        this.nivel = nivel;
    }

    public String getEquipoTrabajo() {
        return equipoTrabajo;
    }

    public void setEquipoTrabajo(String equipoTrabajo) {
        this.equipoTrabajo = equipoTrabajo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getMaxProyectos() {
        return maxProyectos;
    }

    public double getTarifaDia() {
        return tarifaDia;
    }

    public void setTarifaDia(double tarifaDia) {
        this.tarifaDia = tarifaDia;
    }

    public EstadoDesarrollador getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(EstadoDesarrollador disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setMaxProyectos(int maxProyectos) {
        this.maxProyectos = maxProyectos;
    }
    public String toString() {
        return "Dev #" + codigo + " | " + nivel + " | Equipo: " + equipoTrabajo
                + " | Máx. proyectos: " + maxProyectos + " | $" + tarifaDia
                + "/día | " + disponibilidad;
    }
}

