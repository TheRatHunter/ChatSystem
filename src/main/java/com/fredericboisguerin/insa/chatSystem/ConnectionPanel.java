package com.fredericboisguerin.insa.chatSystem;

import com.fredericboisguerin.insa.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConnectionPanel {

    public Button buttonConnexion;
    public TextField champPseudo;
    public boolean pseudoOK = false;
    private static ConnectionPanel instance;
    public String pseudo = "null";

    public ConnectionPanel() { instance=this; }

    public static ConnectionPanel getInstance() {
        return instance;
    }

    public void onOKButtonClicked(ActionEvent actionEvent) {
        String pseudoPropose = champPseudo.getCharacters().toString();
        if ((!pseudoPropose.isEmpty()) && (pseudoPropose.length()<20) && (!Messagerie.getInstance().mapUsersByIP.containsValue(pseudoPropose)) ) {
            pseudoOK=true;
            pseudo = pseudoPropose ;
            champPseudo.setStyle("-fx-background-color: #ffffff;");

        } else {
            champPseudo.setStyle("-fx-background-color: #FE5151;");
            champPseudo.clear();
        }

    }

    public boolean pseudoOK() { return pseudoOK; }


}
