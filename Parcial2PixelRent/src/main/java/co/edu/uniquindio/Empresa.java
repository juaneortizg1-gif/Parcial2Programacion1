package co.edu.uniquindio;

public class Empresa {
    private String nombre;
    private  String nit;
    private  String telefono;

    public Empresa(String nombre, String nit, String telefono){
        this.nombre=nombre;
        this.nit=nit;
        this.telefono=telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

