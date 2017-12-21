package com.fredericboisguerin.insa.chatSystem;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ConnectionPanel {

    public Button buttonConnexion;
    public TextField champPseudo;
    public boolean pseudoOK = false;
    private static ConnectionPanel instance;
    public String pseudo = "null";
    public Text texteConnexion;

    public ConnectionPanel() { instance=this; }

    public static ConnectionPanel getInstance() {
        return instance;
    }

    public void onOKButtonClicked(ActionEvent actionEvent) {
        String pseudoPropose = champPseudo.getCharacters().toString();
        if ((!pseudoPropose.isEmpty()) && (pseudoPropose.length()<30) && (!Messagerie.getInstance().mapNamesByIP.containsValue(pseudoPropose)) ) {
            pseudoOK=true;
            pseudo = pseudoPropose ;
            champPseudo.setStyle("-fx-background-color: #ffffff;");


        } else {
            champPseudo.setStyle("-fx-background-color: #FE5151;");
            if (pseudoPropose.isEmpty()) texteConnexion.setText("Veuillez choisir un pseudo non vide :");
            if (pseudoPropose.length()>=30) texteConnexion.setText("Veuillez choisir un pseudo plus court :");
            if (Messagerie.getInstance().mapNamesByIP.containsValue(pseudoPropose)) texteConnexion.setText("Pseudo déjà pris. Réessayez :");
            champPseudo.clear();
        }

    }

    public boolean pseudoOK() { return pseudoOK; }


}
