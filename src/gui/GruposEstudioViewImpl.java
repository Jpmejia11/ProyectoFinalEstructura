package gui;

import javafx.scene.Node;
import modelo.Usuario;
import servicios.SistemaRedSocial;

public class GruposEstudioViewImpl extends GruposEstudioView {
    public GruposEstudioViewImpl(SistemaRedSocial sistema, Usuario usuarioActual) {
        super(sistema, usuarioActual);
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
