package com.fredericboisguerin.insa.chatSystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GUIController {


    public Label labelNomUtilisateur = new Label();
    private static GUIController instance;
    public Button boutonEnvoyer;
    public TextArea textInput;
    public AnchorPane panneauDAffichageDesMessages;
    public TextFlow test;
    public Text test2;

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


    public void envoyerTexte(ActionEvent actionEvent) {
        String message = "";
        for (CharSequence cs : textInput.getParagraphs()) {
            message=message+cs.toString()+"\n";
        }
        textInput.clear();
        try {
            Messagerie.getInstance().sendMessage(message, InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void afficherMessage(String message){
        Text text = new Text();
        text.setText(message);
        TextFlow tf = new TextFlow();

    }

}
