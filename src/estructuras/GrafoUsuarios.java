package estructuras;

import modelo.Usuario;
import java.util.*;

public class GrafoUsuarios {
    private Map<Usuario, Set<Usuario>> adyacencias;

    public GrafoUsuarios() {
        this.adyacencias = new HashMap<>();
    }

    public void agregarUsuario(Usuario usuario) {
        if (!adyacencias.containsKey(usuario)) {
            adyacencias.put(usuario, new HashSet<>());
        }
    }

    public void agregarConexion(Usuario usuario1, Usuario usuario2) {
        agregarUsuario(usuario1);
        agregarUsuario(usuario2);
        adyacencias.get(usuario1).add(usuario2);
        adyacencias.get(usuario2).add(usuario1);
    }

    public List<Usuario> obtenerRutaMasCorta(Usuario origen, Usuario destino) {
        Map<Usuario, Usuario> padres = new HashMap<>();
        Map<Usuario, Integer> distancias = new HashMap<>();
        PriorityQueue<Usuario> cola = new PriorityQueue<>(
            (a, b) -> distancias.get(a) - distancias.get(b));

        // Inicialización
        for (Usuario u : adyacencias.keySet()) {
            distancias.put(u, Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);
        cola.offer(origen);

        // Algoritmo de Dijkstra
        while (!cola.isEmpty()) {
            Usuario actual = cola.poll();
            if (actual.equals(destino)) break;

            for (Usuario vecino : adyacencias.get(actual)) {
                int nuevaDistancia = distancias.get(actual) + 1;
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    padres.put(vecino, actual);
                    cola.offer(vecino);
                }
            }
        }

        // Reconstruir el camino
        if (!padres.containsKey(destino)) return new ArrayList<>();
        List<Usuario> ruta = new ArrayList<>();
        Usuario actual = destino;
        while (actual != null) {
            ruta.add(0, actual);
            actual = padres.get(actual);
        }
        return ruta;
    }

    public List<Usuario> obtenerSugerenciasConexiones(Usuario usuario) {
        Set<Usuario> sugerencias = new HashSet<>();
        Set<Usuario> conexionesDirectas = adyacencias.get(usuario);
        
        for (Usuario conexion : conexionesDirectas) {
            for (Usuario conexionDeConexion : adyacencias.get(conexion)) {
                if (!conexionesDirectas.contains(conexionDeConexion) && !conexionDeConexion.equals(usuario)) {
                    sugerencias.add(conexionDeConexion);
                }
            }
        }
        
        return new ArrayList<>(sugerencias);
    }

    public Set<Usuario> obtenerConexiones(Usuario usuario) {
        return adyacencias.getOrDefault(usuario, new HashSet<>());
    }

    public Map<Usuario, Integer> obtenerUsuariosMasConectados() {
        Map<Usuario, Integer> conexionesPorUsuario = new HashMap<>();
        for (Map.Entry<Usuario, Set<Usuario>> entry : adyacencias.entrySet()) {
            conexionesPorUsuario.put(entry.getKey(), entry.getValue().size());
        }
        return conexionesPorUsuario;
    }
}