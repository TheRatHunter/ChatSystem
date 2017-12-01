package com.fredericboisguerin.insa.test;

import com.fredericboisguerin.insa.chatSystem.Messagerie;

import java.io.IOException;
import java.net.InetAddress;

import com.fredericboisguerin.insa.chatSystem.Utilisateur;
import org.junit.Test;

public class TestEchangeTCP {

    @Test
    public void testTCPlisten() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost(), 5555);
        Messagerie laPoste = new Messagerie(moi);
        laPoste.listenOnTCPPort();
    }

    @Test
    public void testTCPsend() throws IOException {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost(), 5555);
        Messagerie UPS = new Messagerie(moi);
        UPS.sendMessage("Yo !", InetAddress.getLocalHost());
    }

}
