package com.fredericboisguerin.insa.test;

import com.fredericboisguerin.insa.chatSystem.Messagerie;

import java.io.IOException;
import java.net.InetAddress;

import com.fredericboisguerin.insa.chatSystem.Utilisateur;
import org.junit.Test;

public class TestEchangeTCP {


    @Test
    public void testTCPlisten() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost());
        Messagerie laPoste = new Messagerie(5555,6666,5557,6667);
        laPoste.listenOnTCPPort();
    }

    @Test
    public void testTCPsend() throws IOException {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost());
        Messagerie UPS = new Messagerie(5555,6666,5557,6667);
        UPS.sendMessage("Yo !", InetAddress.getLocalHost());
    }

}
