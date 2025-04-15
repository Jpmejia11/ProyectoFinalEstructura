package modelo;

import java.util.ArrayList;
import java.util.List;

public class GrupoEstudio {
    private String id;
    private String nombre;
    private String tema;
    private List<Usuario> miembros;
    private List<Contenido> contenidos;

    public GrupoEstudio(String id, String nombre, String tema) {
        this.id = id;
        this.nombre = nombre;
        this.tema = tema;
        this.miembros = new ArrayList<>();
        this.contenidos = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTema() { return tema; }
    public List<Usuario> getMiembros() { return miembros; }
    public List<Contenido> getContenidos() { return contenidos; }

    public void agregarMiembro(Usuario usuario) {
        if (!miembros.contains(usuario)) {
            miembros.add(usuario);
        }
    }

    public void agregarContenido(Contenido contenido) {
        if (!contenidos.contains(contenido)) {
            contenidos.add(contenido);
        }
    }

    public boolean contieneMiembro(Usuario usuario) {
        return miembros.contains(usuario);
    }
}