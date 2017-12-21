package com.fredericboisguerin.insa.chatSystem;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;


public class Conversation {

    // ATTRIBUTS

    private Utilisateur utilisateurDistant;
    public ArrayList<StringTuple> sauvegarde;

    // CONSTRUCTEUR

    public Conversation(Utilisateur utilisateur) {

        try {
            this.utilisateurDistant = utilisateur;
            this.sauvegarde = recupererHistorique(utilisateur);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // METHODES
    public ArrayList<StringTuple> recupererHistorique(Utilisateur utilisateurDistant) throws IOException {
        //Temporaire
        return FileManager.getConv(utilisateurDistant.pseudonyme);
    }

    public void ajouterMessage(String emetteur, String msg) {
        //On crée un tuple contenant l'emetteur du message et le message lui-meme, et on le sauvegarde dans la conversation
        StringTuple temp = new StringTuple(emetteur,msg);
        sauvegarde.add(temp);
        System.out.println("Enregistrement du message :"+msg);
    }

    public void enregistrerConversation (String userDistant) {
        FileManager.saveConv(userDistant, sauvegarde);
    }

}
