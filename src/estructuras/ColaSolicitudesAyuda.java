package estructuras;

import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class ColaSolicitudesAyuda {
    public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
        private Usuario solicitante;
        private String tema;
        private int nivelUrgencia;
        private LocalDateTime fecha;
        private String descripcion;

        public SolicitudAyuda(Usuario solicitante, String tema, int nivelUrgencia, String descripcion) {
            this.solicitante = solicitante;
            this.tema = tema;
            this.nivelUrgencia = nivelUrgencia;
            this.descripcion = descripcion;
            this.fecha = LocalDateTime.now();
        }

        // Getters
        public Usuario getSolicitante() { return solicitante; }
        public String getTema() { return tema; }
        public int getNivelUrgencia() { return nivelUrgencia; }
        public LocalDateTime getFecha() { return fecha; }
        public String getDescripcion() { return descripcion; }

        @Override
        public int compareTo(SolicitudAyuda otra) {
            // Primero comparamos por nivel de urgencia (mayor urgencia primero)
            int comparacionUrgencia = Integer.compare(otra.nivelUrgencia, this.nivelUrgencia);
            if (comparacionUrgencia != 0) {
                return comparacionUrgencia;
            }
            // Si la urgencia es igual, comparamos por fecha (más antigua primero)
            return this.fecha.compareTo(otra.fecha);
        }
    }

    private PriorityQueue<SolicitudAyuda> cola;

    public ColaSolicitudesAyuda() {
        this.cola = new PriorityQueue<>();
    }

    public void agregarSolicitud(Usuario solicitante, String tema, int nivelUrgencia, String descripcion) {
        cola.offer(new SolicitudAyuda(solicitante, tema, nivelUrgencia, descripcion));
    }

    public SolicitudAyuda obtenerSiguienteSolicitud() {
        return cola.poll();
    }

    public SolicitudAyuda verSiguienteSolicitud() {
        return cola.peek();
    }

    public boolean haySolicitudes() {
        return !cola.isEmpty();
    }

    public int cantidadSolicitudes() {
        return cola.size();
    }
}