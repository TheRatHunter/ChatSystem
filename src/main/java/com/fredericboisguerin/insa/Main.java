package com.fredericboisguerin.insa;
import com.fredericboisguerin.insa.chatSystem.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {

        try {
            Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost(), 5555);
            Messagerie messagerie = new Messagerie(moi);
            messagerie.go();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
