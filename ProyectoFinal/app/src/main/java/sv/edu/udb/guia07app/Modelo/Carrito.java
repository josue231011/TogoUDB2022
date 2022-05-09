package sv.edu.udb.guia07app.Modelo;

public class Carrito {

    String key;
    private String nombre_producto;
    private double precio;
    private String pImg;
    private String correo_usuario;
    private boolean actividad;
    private String direccion_envio;
    private String fecha;

    public Carrito() {
    }

    public Carrito(String nombre_producto, double precio, String pImg, String correo_usuario, boolean actividad, String direccion_envio, String fecha) {
        this.nombre_producto = nombre_producto;
        this.precio = precio;
        this.pImg = pImg;
        this.correo_usuario = correo_usuario;
        this.actividad = actividad;
        this.direccion_envio = direccion_envio;
        this.fecha = fecha;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public boolean isActividad() {
        return actividad;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    public String getDireccion_envio() {
        return direccion_envio;
    }

    public void setDireccion_envio(String direccion_envio) {
        this.direccion_envio = direccion_envio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
