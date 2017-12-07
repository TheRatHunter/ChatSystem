package com.fredericboisguerin.insa.chatSystem;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


import javafx.event.EventHandler;
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
    public TextFlow contacts;
    public Text nomUserCourant;
    public Utilisateur userCourant;
    public Utilisateur userPrécédent;

    public GUIController() { instance=this; userPrécédent=null; }



    public static GUIController getInstance() { return instance; }

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

        if (userCourant!=null) {
            //Si l'utilisateur a changé on nettoie la fenêtre
            if ( !(userCourant.equals(userPrécédent)))
                conversationEnCours.getChildren().clear();
            userPrécédent=userCourant;

            Messagerie.getInstance().sendMessage(message, userCourant.ipAdress);
            message = "Moi :\n" + message;
            Text text = new Text(message);
            textInput.clear();
            conversationEnCours.getChildren().addAll(text);
        } else {
            textInput.clear();
            conversationEnCours.getChildren().clear();
            Text text = new Text("ERREUR : Aucun interlocuteur de sélectionné.");
            conversationEnCours.getChildren().addAll(text);
        }






    }


    public void afficherMessageRecu(String message){
        afficherConversation(userCourant);
        String messageAEnvoyer ="Reçu :\n"+message;
        Text text = new Text(messageAEnvoyer);
        conversationEnCours.getChildren().addAll(text);

    }

    public void afficherConversation(Utilisateur userDistant){
        userCourant = userDistant;
        nomUserCourant.setText(userCourant.pseudonyme);
    }

    public void updateContacts() {
        contacts.getChildren().clear();
        for(Map.Entry<InetAddress, Utilisateur> entry : Messagerie.getInstance().mapUsersByIP.entrySet()) {
            Utilisateur user = entry.getValue();
            Text text = new Text(user.pseudonyme+"  ");
            contacts.getChildren().addAll(text);
            Button btn = new Button("Parler");
            btn.setTextAlignment(TextAlignment.RIGHT);
            btn.setOnAction( (javafx.event.ActionEvent e) -> afficherConversation(user));
            btn.setStyle("float: right;");
            contacts.getChildren().addAll(btn);
            Text retourCharriot = new Text("\n");
            contacts.getChildren().addAll(retourCharriot);
        }

    }
}
