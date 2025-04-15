package estructuras;

import modelo.Contenido;
import java.util.ArrayList;
import java.util.List;

public class ArbolContenidos {
    private class Nodo {
        Contenido contenido;
        Nodo izquierdo;
        Nodo derecho;

        Nodo(Contenido contenido) {
            this.contenido = contenido;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private Nodo raiz;

    public ArbolContenidos() {
        this.raiz = null;
    }

    public void insertar(Contenido contenido) {
        raiz = insertarRecursivo(raiz, contenido);
    }

    private Nodo insertarRecursivo(Nodo nodo, Contenido contenido) {
        if (nodo == null) {
            return new Nodo(contenido);
        }

        int comparacion = contenido.compareTo(nodo.contenido);
        if (comparacion < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, contenido);
        } else if (comparacion > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, contenido);
        }

        return nodo;
    }

    public List<Contenido> buscarPorTema(String tema) {
        List<Contenido> resultados = new ArrayList<>();
        buscarPorTemaRecursivo(raiz, tema.toLowerCase(), resultados);
        return resultados;
    }

    private void buscarPorTemaRecursivo(Nodo nodo, String tema, List<Contenido> resultados) {
        if (nodo == null) return;

        if (nodo.contenido.getTema().toLowerCase().contains(tema)) {
            resultados.add(nodo.contenido);
        }
        buscarPorTemaRecursivo(nodo.izquierdo, tema, resultados);
        buscarPorTemaRecursivo(nodo.derecho, tema, resultados);
    }

    public List<Contenido> obtenerTodos() {
        List<Contenido> contenidos = new ArrayList<>();
        recorridoInorden(raiz, contenidos);
        return contenidos;
    }

    private void recorridoInorden(Nodo nodo, List<Contenido> contenidos) {
        if (nodo == null) return;
        recorridoInorden(nodo.izquierdo, contenidos);
        contenidos.add(nodo.contenido);
        recorridoInorden(nodo.derecho, contenidos);
    }

    public List<Contenido> obtenerContenidosMasValorados() {
        List<Contenido> todos = obtenerTodos();
        todos.sort((c1, c2) -> Double.compare(c2.getPromedioValoraciones(), c1.getPromedioValoraciones()));
        return todos;
    }
}