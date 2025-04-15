package modelo;

import TipoContenido.TipoContenido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Contenido implements Comparable<Contenido> {
    private String id;
    private String titulo;
    private String tema;
    private String descripcion;
    private Usuario autor;
    private LocalDateTime fechaPublicacion;
    private List<Valoracion> valoraciones;
    private TipoContenido tipoContenido; // Agregar este atributo si es necesario

    // Constructor principal
    public Contenido(String id, String titulo, String tema, String descripcion, Usuario autor) {
        this.id = id;
        this.titulo = titulo;
        this.tema = tema;
        this.descripcion = descripcion;
        this.autor = autor;
        this.fechaPublicacion = LocalDateTime.now();
        this.valoraciones = new ArrayList<>();
    }

    // Constructor que incluye TipoContenido y otros parámetros
    public Contenido(String id, String titulo, String tema, TipoContenido tipoContenido, String url, Usuario autor, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.tema = tema;
        this.tipoContenido = tipoContenido; // Inicializar el nuevo atributo
        this.descripcion = descripcion;
        this.autor = autor;
        this.fechaPublicacion = LocalDateTime.now();
        this.valoraciones = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTema() {
        return tema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void agregarValoracion(Valoracion valoracion) {
        valoraciones.add(valoracion);
    }

    public double getPromedioValoraciones() {
        if (valoraciones.isEmpty()) return 0.0;
        return valoraciones.stream()
                .mapToInt(Valoracion::getPuntuacion)
                .average()
                .orElse(0.0);
    }

    @Override
    public int compareTo(Contenido otro) {
        // Ordenar primero por tema
        int comparacionTema = this.tema.compareTo(otro.tema);
        if (comparacionTema != 0) {
            return comparacionTema;
        }
        // Si el tema es igual, ordenar por fecha (más reciente primero)
        return otro.fechaPublicacion.compareTo(this.fechaPublicacion);
    }
}
