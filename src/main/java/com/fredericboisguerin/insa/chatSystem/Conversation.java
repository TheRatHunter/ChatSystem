package com.fredericboisguerin.insa.chatSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;


public class Conversation {

    // ATTRIBUTS

    private Utilisateur utilisateurDistant;
    private ArrayList<String> conversation;

    // CONSTRUCTEUR

    public Conversation(Utilisateur utilisateur) {
        try {
            this.utilisateurDistant = utilisateur;
            this.conversation = recupererHistorique(utilisateur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // METHODES

    public void fermerConversation(){
        enregistrerConversation();
    }

    public ArrayList<String> recupererHistorique(Utilisateur utilisateurDistant) throws IOException {
        ArrayList<String> hist = new ArrayList<String>();
        CSVReader reader = new CSVReader(new FileReader("contacts.csv"));
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
        }
        return hist;
    }

    public void enregistrerConversation (){
        ArrayList<String> conv = new ArrayList<String>();
    }

}
