package com.fredericboisguerin.insa.chatSystem;

import java.util.ArrayList;

public class Conversation {

    // ATTRIBUTS

    private Utilisateur utilisateurDistant;

    private ArrayList<String> conversation;

    // CONSTRUCTEUR

    public Conversation(Utilisateur utilisateur){
        this.utilisateurDistant = utilisateur;
        this.conversation = recupererHistorique(utilisateur);
    }

    // METHODES

    public void fermerConversation(){
        enregistrerConversation();
    }

    public ArrayList<String> recupererHistorique(Utilisateur utilisateurDistant){
        ArrayList<String> hist = new ArrayList<String>();

        return hist;
    }

    public void enregistrerConversation (){
        ArrayList<String> conv = new ArrayList<String>();
    }

}
