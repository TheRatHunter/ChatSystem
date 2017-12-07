package com.fredericboisguerin.insa.chatSystem;

import java.net.InetAddress;

public class Utilisateur {

    public String pseudonyme;
    public InetAddress ipAdress;
    public boolean disponible;
    public Conversation conv;

    public Utilisateur(String pseudo, InetAddress ip) {
        this.pseudonyme = pseudo;
        this.ipAdress = ip;
        this.disponible = true;
        this.conv = new Conversation(this);
    }

}
