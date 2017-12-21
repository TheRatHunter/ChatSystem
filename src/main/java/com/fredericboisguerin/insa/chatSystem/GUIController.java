package com.fredericboisguerin.insa.chatSystem;

import java.net.InetAddress;
import java.util.Map;


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
            afficherMessage("Moi :\n"+message, true);
            textInput.clear();
        } else {
            textInput.clear();
            conversationEnCours.getChildren().clear();
            Text text = new Text("ERREUR : Aucun interlocuteur de sélectionné.");
            conversationEnCours.getChildren().addAll(text);
        }

    }


    public void afficherMessage(String message, boolean deMoi){
        afficherConversation(userCourant);
        String messageRecu = message;
        Text text = new Text(messageRecu);
        //Le text alignment n'a pas l'air de marcher dans le textflow : besoin de changer de classe de container ?
        if (deMoi) {
            //Appliquer CSS messages de l'utilisateur local
            text.setStyle("margin-right: 0; -fx-text-alignment: left; -fx-font-size: 14; -fx-fill: slategray;");
        } else {
            //Appliquer CSS messages de l'utilisateur distant
            text.setStyle("-fx-text-alignment: right; -fx-font-size: 14; -fx-fill: darkblue;");
        }
        conversationEnCours.setStyle("padding: 5 5 5 5;");
        conversationEnCours.getChildren().addAll(text);
        conversationEnCours.requestFocus();
    }

    public void afficherConversation(Utilisateur userDistant){
        userCourant = userDistant;
        nomUserCourant.setText(userCourant.pseudonyme);
        //Rajouter code pour l'import depuis le CSV ici
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

    public void clearConversation() {
        conversationEnCours.getChildren().clear();
        userCourant=null;
        nomUserCourant.setText("Aucun utilisateur sélectionné");
        System.out.println("Fenêtre nettoyée.");
    }
}
