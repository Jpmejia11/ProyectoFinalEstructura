package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contraseña;
    private boolean esModerador;
    private List<Contenido> contenidosPublicados;
    private List<Usuario> conexiones;
    private List<GrupoEstudio> gruposEstudio;

    public Usuario(String id, String nombre, String correo, String contraseña, boolean esModerador) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.esModerador = esModerador;
        this.contenidosPublicados = new ArrayList<>();
        this.conexiones = new ArrayList<>();
        this.gruposEstudio = new ArrayList<>();
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public boolean esModerador() { return esModerador; }
    public List<Contenido> getContenidosPublicados() { return contenidosPublicados; }
    public List<Usuario> getConexiones() { return conexiones; }
    public List<GrupoEstudio> getGruposEstudio() { return gruposEstudio; }

    // Métodos de funcionalidad
    public void publicarContenido(Contenido contenido) {
        contenidosPublicados.add(contenido);
    }

    public void agregarConexion(Usuario usuario) {
        if (!conexiones.contains(usuario)) {
            conexiones.add(usuario);
            usuario.getConexiones().add(this);
        }
    }

    public void unirseAGrupo(GrupoEstudio grupo) {
        if (!gruposEstudio.contains(grupo)) {
            gruposEstudio.add(grupo);
            grupo.agregarMiembro(this);
        }
    }

    public boolean verificarContraseña(String contraseña) {
        return this.contraseña.equals(contraseña);
    }
}