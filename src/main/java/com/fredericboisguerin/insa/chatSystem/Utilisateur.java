package com.fredericboisguerin.insa.chatSystem;

import java.net.InetAddress;

public class Utilisateur {

    public String pseudonyme;
    public InetAddress ipAdress;
    public int port;
    public boolean disponible;
    public Conversation conv;

    public Utilisateur(String pseudo, InetAddress ip, int port) {
        this.pseudonyme = pseudo;
        this.ipAdress = ip;
        this.disponible = true;
        this.port = port;
        this.conv = new Conversation(this);
    }

}
