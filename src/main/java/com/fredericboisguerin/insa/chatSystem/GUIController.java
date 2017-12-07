package com.fredericboisguerin.insa.chatSystem;

import java.net.InetAddress;
import java.net.UnknownHostException;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class GUIController {


    public Label labelNomUtilisateur = new Label();
    public TextFlow conversationEnCours = new TextFlow();
    public TextArea textInput = new TextArea();
    public Button sendButton = new Button();

    private static GUIController instance;

    public GUIController() { instance=this; }



    public static GUIController getInstance() {
        return instance;
    }

    public void setNomUtilisateur(String nom){
        if (nom != null)
            labelNomUtilisateur.setText(nom);
        else
            labelNomUtilisateur.setText("Aucun nom trouve pour l'utilisateur");
    }

    public void onSendButtonClicked (){


        //Conversion de l'input en String
        String message = "";

        for (CharSequence cs : textInput.getParagraphs()) {
            message=message+cs.toString()+"\n";
        }

        try {
            Messagerie.getInstance().sendMessage(message, InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        message = "Moi :\n"+message;

        Text text = new Text (message);
        conversationEnCours.setTextAlignment(TextAlignment.RIGHT);
        textInput.clear();

        conversationEnCours.getChildren().addAll(text);


    }


    public void afficherMessageRecu(String message){
        String messageAEnvoyer ="Reçu :\n"+message;
        Text text = new Text(messageAEnvoyer);
        conversationEnCours.setTextAlignment(TextAlignment.LEFT);
        conversationEnCours.getChildren().addAll(text);

    }
}
