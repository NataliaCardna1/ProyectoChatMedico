package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private String idUsuario;
    private String nombre;
    private String password;
    private String email;

    public Usuario(String idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {return password;}

    public void setPassword(String password){this.password = password;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}