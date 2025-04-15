package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import servicios.SistemaRedSocial;

public class RedSocialEducativaApp extends Application {
    private Stage primaryStage;
    private SistemaRedSocial sistema;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.sistema = new SistemaRedSocial();

        // Configurar la ventana principal
        primaryStage.setTitle("Red Social Educativa");
        
        // Mostrar la vista de login
        mostrarLogin();
        
        primaryStage.show();
    }

    public void mostrarLogin() {
        LoginView loginView = new LoginView(this, sistema);
        Scene scene = new Scene(loginView, 800, 600);
        primaryStage.setScene(scene);
    }

    public void mostrarPanelPrincipal() {
        PanelPrincipalView panelView = new PanelPrincipalView(this, sistema);
        Scene scene = new Scene(panelView, 1024, 768);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}