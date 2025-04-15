package modelo;

import java.time.LocalDateTime;

public class Valoracion {
    private Usuario usuario;
    private int puntuacion;
    private String comentario;
    private LocalDateTime fecha;

    public Valoracion(Usuario usuario, int puntuacion, String comentario) {
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5");
        }
        this.usuario = usuario;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = LocalDateTime.now();
    }

    // Getters
    public Usuario getUsuario() { return usuario; }
    public int getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
    public LocalDateTime getFecha() { return fecha; }
}