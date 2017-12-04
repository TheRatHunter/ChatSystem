package com.fredericboisguerin.insa.chatSystem;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class GUIController {


    public Label labelNomUtilisateur = new Label();
    private static GUIController instance;

    public GUIController() { instance=this; }



    public static GUIController getInstance() {
        return instance;
    }

    public void setNomUtilisateur(String nom){
        if (nom != null)
            labelNomUtilisateur.setText(nom);
        else
            labelNomUtilisateur.setText("ça a pas marché");
    }
}
