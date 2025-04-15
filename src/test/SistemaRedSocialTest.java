package test;

import modelo.*;
import servicios.SistemaRedSocial;
import TipoContenido.TipoContenido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaRedSocialTest {
    private SistemaRedSocial sistema;
    private Usuario usuario1;
    private Usuario usuario2;
    private Contenido contenido1;

    @BeforeEach
    void setUp() {
        sistema = new SistemaRedSocial();
        usuario1 = sistema.registrarUsuario("1", "Juan Pérez", "juan@test.com", "123456", false);
        usuario2 = sistema.registrarUsuario("2", "Ana García", "ana@test.com", "123456", false);
        contenido1 = new Contenido("1", "Matemáticas Básicas", "Curso de matemáticas",
                                 TipoContenido.DOCUMENTO, "url", usuario1, "Matemáticas");
    }

    @Test
    void testRegistroUsuario() {
        Usuario usuario = sistema.registrarUsuario("3", "Test User", "test@test.com", "password", false);
        assertNotNull(usuario);
        assertEquals("Test User", usuario.getNombre());
    }

    @Test
    void testAutenticacionUsuario() {
        Usuario autenticado = sistema.autenticarUsuario("juan@test.com", "123456");
        assertNotNull(autenticado);
        assertEquals(usuario1.getId(), autenticado.getId());
    }

    @Test
    void testPublicacionContenido() {
        sistema.publicarContenido(contenido1);
        var contenidos = sistema.buscarContenidosPorTema("Matemáticas");
        assertFalse(contenidos.isEmpty());
        assertEquals(contenido1, contenidos.get(0));
    }

    @Test
    void testValoracionContenido() {
        sistema.publicarContenido(contenido1);
        sistema.valorarContenido(contenido1, usuario2, 5, "Excelente contenido");
        assertEquals(5.0, contenido1.getPromedioValoraciones());
    }

    @Test
    void testCreacionGrupoEstudio() {
        GrupoEstudio grupo = sistema.crearGrupoEstudio("1", "Grupo Matemáticas", "Matemáticas");
        sistema.agregarUsuarioAGrupo(usuario1, grupo);
        sistema.agregarUsuarioAGrupo(usuario2, grupo);
        assertTrue(grupo.contieneMiembro(usuario1));
        assertTrue(grupo.contieneMiembro(usuario2));
    }

    @Test
    void testSolicitudAyuda() {
        sistema.crearSolicitudAyuda(usuario1, "Matemáticas", 3, "Necesito ayuda con álgebra");
        var solicitud = sistema.obtenerSiguienteSolicitud();
        assertNotNull(solicitud);
        assertEquals(usuario1, solicitud.getSolicitante());
    }

    @Test
    void testConexionesEntreUsuarios() {
        sistema.publicarContenido(contenido1);
        sistema.valorarContenido(contenido1, usuario2, 5, "Excelente");
        var sugerencias = sistema.obtenerSugerenciasConexiones(usuario1);
        assertTrue(sugerencias.contains(usuario2));
    }
}
