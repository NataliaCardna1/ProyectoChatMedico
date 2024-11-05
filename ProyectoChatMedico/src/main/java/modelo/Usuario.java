package src.main.java.modelo;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String rol;
    private String email;
    private String passwordHash;

    public Usuario(int idUsuario, String nombre, String rol, String email, String passwordHash) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
