package servicios;

import estructuras.ArbolContenidos;
import estructuras.ColaSolicitudesAyuda;
import estructuras.GrafoUsuarios;
import modelo.*;

import java.util.*;

public class SistemaRedSocial {
    private Map<String, Usuario> usuarios;
    private GrafoUsuarios grafoUsuarios;
    private ArbolContenidos arbolContenidos;
    private ColaSolicitudesAyuda colaSolicitudes;
    private List<GrupoEstudio> gruposEstudio;

    public SistemaRedSocial() {
        this.usuarios = new HashMap<>();
        this.grafoUsuarios = new GrafoUsuarios();
        this.arbolContenidos = new ArbolContenidos();
        this.colaSolicitudes = new ColaSolicitudesAyuda();
        this.gruposEstudio = new ArrayList<>();
    }

    // Gestión de usuarios
    public Usuario registrarUsuario(String id, String nombre, String correo, String contraseña, boolean esModerador) {
        if (usuarios.containsKey(id)) {
            throw new IllegalArgumentException("El ID de usuario ya existe");
        }
        Usuario usuario = new Usuario(id, nombre, correo, contraseña, esModerador);
        usuarios.put(id, usuario);
        grafoUsuarios.agregarUsuario(usuario);
        return usuario;
    }

    public Usuario autenticarUsuario(String correo, String contraseña) {
        return usuarios.values().stream()
                .filter(u -> u.getCorreo().equals(correo) && u.verificarContraseña(contraseña))
                .findFirst()
                .orElse(null);
    }

    // Gestión de contenidos
    public void publicarContenido(Contenido contenido) {
        arbolContenidos.insertar(contenido);
        contenido.getAutor().publicarContenido(contenido);
    }

    public List<Contenido> buscarContenidosPorTema(String tema) {
        return arbolContenidos.buscarPorTema(tema);
    }

    public void valorarContenido(Contenido contenido, Usuario usuario, int puntuacion, String comentario) {
        Valoracion valoracion = new Valoracion(usuario, puntuacion, comentario);
        contenido.agregarValoracion(valoracion);
        
        // Crear conexión si valoran contenidos similares
        for (Valoracion otraValoracion : contenido.getValoraciones()) {
            if (!otraValoracion.getUsuario().equals(usuario)) {
                grafoUsuarios.agregarConexion(usuario, otraValoracion.getUsuario());
            }
        }
    }

    // Gestión de solicitudes de ayuda
    public void crearSolicitudAyuda(Usuario solicitante, String tema, int nivelUrgencia, String descripcion) {
        colaSolicitudes.agregarSolicitud(solicitante, tema, nivelUrgencia, descripcion);
    }

    public ColaSolicitudesAyuda.SolicitudAyuda obtenerSiguienteSolicitud() {
        return colaSolicitudes.obtenerSiguienteSolicitud();
    }

    // Gestión de grupos de estudio
    public GrupoEstudio crearGrupoEstudio(String id, String nombre, String tema) {
        GrupoEstudio grupo = new GrupoEstudio(id, nombre, tema);
        gruposEstudio.add(grupo);
        return grupo;
    }

    public void agregarUsuarioAGrupo(Usuario usuario, GrupoEstudio grupo) {
        grupo.agregarMiembro(usuario);
        usuario.unirseAGrupo(grupo);
        
        // Crear conexiones entre miembros del grupo
        for (Usuario miembro : grupo.getMiembros()) {
            if (!miembro.equals(usuario)) {
                grafoUsuarios.agregarConexion(usuario, miembro);
            }
        }
    }

    // Recomendaciones y análisis
    public List<Usuario> obtenerSugerenciasConexiones(Usuario usuario) {
        return grafoUsuarios.obtenerSugerenciasConexiones(usuario);
    }

    public List<GrupoEstudio> sugerirGruposEstudio(Usuario usuario) {
        return gruposEstudio.stream()
                .filter(g -> !g.contieneMiembro(usuario) && 
                           g.getMiembros().stream()
                               .anyMatch(m -> grafoUsuarios.obtenerConexiones(usuario).contains(m)))
                .toList();
    }

    public List<Contenido> obtenerContenidosMasValorados() {
        return arbolContenidos.obtenerContenidosMasValorados();
    }

    public Map<Usuario, Integer> obtenerUsuariosMasConectados() {
        return grafoUsuarios.obtenerUsuariosMasConectados();
    }

    public List<Usuario> obtenerRutaEntreUsuarios(Usuario origen, Usuario destino) {
        return grafoUsuarios.obtenerRutaMasCorta(origen, destino);
    }
}