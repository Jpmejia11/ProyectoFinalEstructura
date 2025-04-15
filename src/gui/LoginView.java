package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Usuario;
import servicios.SistemaRedSocial;

public class LoginView extends VBox {
    private RedSocialEducativaApp app;
    private SistemaRedSocial sistema;
    private TextField correoField;
    private PasswordField contraseñaField;

    public LoginView(RedSocialEducativaApp app, SistemaRedSocial sistema) {
        this.app = app;
        this.sistema = sistema;
        
        setSpacing(10);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);

        // Título
        Label titulo = new Label("Red Social Educativa");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label correoLabel = new Label("Correo:");
        correoField = new TextField();
        correoField.setPromptText("Ingrese su correo");

        Label contraseñaLabel = new Label("Contraseña:");
        contraseñaField = new PasswordField();
        contraseñaField.setPromptText("Ingrese su contraseña");

        grid.add(correoLabel, 0, 0);
        grid.add(correoField, 1, 0);
        grid.add(contraseñaLabel, 0, 1);
        grid.add(contraseñaField, 1, 1);

        // Botones
        HBox botonesBox = new HBox(10);
        botonesBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Iniciar Sesión");
        Button registroButton = new Button("Registrarse");

        loginButton.setOnAction(e -> iniciarSesion());
        registroButton.setOnAction(e -> mostrarRegistro());

        botonesBox.getChildren().addAll(loginButton, registroButton);

        // Agregar todo al VBox
        getChildren().addAll(titulo, grid, botonesBox);

        // Crear usuario de prueba si no existe
        if (sistema.autenticarUsuario("admin@edu.com", "admin123") == null) {
            sistema.registrarUsuario("admin", "Administrador", "admin@edu.com", "admin123", true);
        }
    }

    private void iniciarSesion() {
        String correo = correoField.getText();
        String contraseña = contraseñaField.getText();

        Usuario usuario = sistema.autenticarUsuario(correo, contraseña);
        if (usuario != null) {
            app.mostrarPanelPrincipal();
        } else {
            mostrarError("Error de autenticación", "Correo o contraseña incorrectos");
        }
    }

    private void mostrarRegistro() {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Registro de Usuario");
        dialog.setHeaderText("Complete los datos para registrarse");

        // Botones
        ButtonType registrarButtonType = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registrarButtonType, ButtonType.CANCEL);

        // Campos del formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        TextField nombreField = new TextField();
        TextField correoField = new TextField();
        PasswordField contraseñaField = new PasswordField();

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombreField, 1, 1);
        grid.add(new Label("Correo:"), 0, 2);
        grid.add(correoField, 1, 2);
        grid.add(new Label("Contraseña:"), 0, 3);
        grid.add(contraseñaField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registrarButtonType) {
                try {
                    return sistema.registrarUsuario(
                        idField.getText(),
                        nombreField.getText(),
                        correoField.getText(),
                        contraseñaField.getText(),
                        false
                    );
                } catch (IllegalArgumentException e) {
                    mostrarError("Error de registro", e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(usuario -> {
            if (usuario != null) {
                mostrarInfo("Registro exitoso", "Usuario registrado correctamente");
            }
        });
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}