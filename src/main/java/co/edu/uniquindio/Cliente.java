package co.edu.uniquindio;

public class Cliente {
    private String nombre;
    private String identificacion;
    private String telefono;
    private String correo;
    private String pais;


    public Cliente( String nombre, String identificacion,
                    String telefono, String correo, String pais ){

        this.nombre =nombre;
        this.identificacion= identificacion;
        this.telefono=telefono;
        this.correo=correo;
        this.pais=pais;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre= nombre;
    }
    public String getIdentificacion(){
        return identificacion;
    }
    public void setIdentificacion(String identificacion){
        this.identificacion=identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono=telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String toString() {
        return nombre + " | ID: " + identificacion + " | Tel: " + telefono
                + " | " + correo + " | " + pais;
    }


}

