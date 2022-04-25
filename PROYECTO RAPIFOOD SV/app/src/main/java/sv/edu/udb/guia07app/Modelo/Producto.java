package sv.edu.udb.guia07app.Modelo;

public class Producto {
    String key;
    private String idProducto;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String enStock;
    private double precio;
    private String pImg;

    public Producto(String idProducto, String nombre, String categoria, String descripcion, String enStock, double precio, String pImg) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.enStock = enStock;
        this.precio = precio;
        this.pImg = pImg;
    }

    public Producto() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnStock() {
        return enStock;
    }

    public void setEnStock(String enStock) {
        this.enStock = enStock;
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
}
