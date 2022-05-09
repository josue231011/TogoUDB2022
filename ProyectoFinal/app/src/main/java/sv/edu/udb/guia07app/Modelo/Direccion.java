package sv.edu.udb.guia07app.Modelo;

public class Direccion {

    String key;
    private String nombre_direccion;
    private String direccion;
    private String correo;

    public Direccion() {
    }

    public Direccion(String nombre_direccion, String direccion, String correo) {
        this.nombre_direccion = nombre_direccion;
        this.direccion = direccion;
        this.correo = correo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre_direccion() {
        return nombre_direccion;
    }

    public void setNombre_direccion(String nombre_direccion) {
        this.nombre_direccion = nombre_direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
