package sv.edu.udb.guia07app.Modelo;

public class Usuario {

    String Key;
    String codigo;
    String nombre;
    String tipoUsuario;
    String img;
    String correo;

    public Usuario() {
    }

    public Usuario(String codigo, String nombre, String tipoUsuario, String img, String correo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
        this.img = img;
        this.correo = correo;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
