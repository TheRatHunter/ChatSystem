package com.fredericboisguerin.insa.chatSystem;
public class Utilisateur {

    private String pseudonyme;

    public Utilisateur(String pseudo) {
        this.pseudonyme = pseudo;
    }

    public void setPseudonyme(String pseudo){
        this.pseudonyme = pseudo;
    }

    public String getPseudonyme() {
        return this.pseudonyme;
    }

}
