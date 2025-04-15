package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import modelo.Contenido;
import modelo.GrupoEstudio;
import modelo.Usuario;
import servicios.SistemaRedSocial;

public class PanelPrincipalView extends BorderPane {
    private RedSocialEducativaApp app;
    private SistemaRedSocial sistema;
    private TabPane tabPane;

    public PanelPrincipalView(RedSocialEducativaApp app, SistemaRedSocial sistema) {
        this.app = app;
        this.sistema = sistema;

        // Crear menú superior
        MenuBar menuBar = crearMenuBar();
        setTop(menuBar);

        // Crear pestañas principales
        tabPane = new TabPane();
        
        Tab contenidosTab = new Tab("Contenidos", crearContenidosView());
        Tab gruposTab = new Tab("Grupos de Estudio", crearGruposView());
        Tab ayudaTab = new Tab("Solicitudes de Ayuda", crearAyudaView());
        
        tabPane.getTabs().addAll(contenidosTab, gruposTab, ayudaTab);
        setCenter(tabPane);
    }

    private MenuBar crearMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Menú de Usuario
        Menu usuarioMenu = new Menu("Usuario");
        MenuItem perfilItem = new MenuItem("Ver Perfil");
        MenuItem cerrarSesionItem = new MenuItem("Cerrar Sesión");
        
        cerrarSesionItem.setOnAction(e -> app.mostrarLogin());
        
        usuarioMenu.getItems().addAll(perfilItem, cerrarSesionItem);

        menuBar.getMenus().add(usuarioMenu);
        return menuBar;
    }

    private VBox crearContenidosView() {
        VBox contenidosBox = new VBox(10);
        contenidosBox.setPadding(new Insets(10));

        Button nuevoContenidoBtn = new Button("Nuevo Contenido");
        TextField buscarField = new TextField();
        buscarField.setPromptText("Buscar por tema...");
        ListView<Contenido> contenidosList = new ListView<>();

        nuevoContenidoBtn.setOnAction(e -> mostrarDialogoNuevoContenido());
        buscarField.setOnAction(e -> {
            String tema = buscarField.getText();
            contenidosList.getItems().setAll(sistema.buscarContenidosPorTema(tema));
        });

        contenidosBox.getChildren().addAll(nuevoContenidoBtn, buscarField, contenidosList);
        return contenidosBox;
    }

    private VBox crearGruposView() {
        VBox gruposBox = new VBox(10);
        gruposBox.setPadding(new Insets(10));

        Button nuevoGrupoBtn = new Button("Crear Grupo");
        ListView<GrupoEstudio> gruposList = new ListView<>();

        nuevoGrupoBtn.setOnAction(e -> mostrarDialogoNuevoGrupo());

        gruposBox.getChildren().addAll(nuevoGrupoBtn, gruposList);
        return gruposBox;
    }

    private VBox crearAyudaView() {
        VBox ayudaBox = new VBox(10);
        ayudaBox.setPadding(new Insets(10));

        Button nuevaSolicitudBtn = new Button("Nueva Solicitud");
        ListView<String> solicitudesList = new ListView<>();

        nuevaSolicitudBtn.setOnAction(e -> mostrarDialogoNuevaSolicitud());

        ayudaBox.getChildren().addAll(nuevaSolicitudBtn, solicitudesList);
        return ayudaBox;
    }

    private void mostrarDialogoNuevoContenido() {
        Dialog<Contenido> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Contenido");
        dialog.setHeaderText("Ingrese los datos del contenido");

        ButtonType publicarButtonType = new ButtonType("Publicar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(publicarButtonType, ButtonType.CANCEL);

        // TODO: Implementar formulario de nuevo contenido
    }

    private void mostrarDialogoNuevoGrupo() {
        Dialog<GrupoEstudio> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Grupo de Estudio");
        dialog.setHeaderText("Ingrese los datos del grupo");

        ButtonType crearButtonType = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);

        // TODO: Implementar formulario de nuevo grupo
    }

    private void mostrarDialogoNuevaSolicitud() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Nueva Solicitud de Ayuda");
        dialog.setHeaderText("Ingrese los detalles de su solicitud");

        ButtonType enviarButtonType = new ButtonType("Enviar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(enviarButtonType, ButtonType.CANCEL);

        // TODO: Implementar formulario de nueva solicitud
    }
}