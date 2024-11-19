package sockets;

import modelo.Usuario;

import java.io.Serializable;
import java.net.Socket;

public class UsuarioConectado implements Serializable {

    private Usuario usuario;
    private Socket socket;

    public UsuarioConectado(Usuario usuario, Socket socket) {
        this.usuario = usuario;
        this.socket = socket;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


}
