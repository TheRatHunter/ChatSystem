package com.fredericboisguerin.insa.test;

import com.fredericboisguerin.insa.chatSystem.Messagerie;
import com.fredericboisguerin.insa.chatSystem.Utilisateur;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class testEchangeUDP {
    @Test
    public void testUDPlisten() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost());
        Messagerie laPoste = new Messagerie(5555,6666,5557,6667);
        laPoste.listenOnUDPPort();
    }

    @Test
    public void testUDPNotification() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost());
        Messagerie laPoste = new Messagerie(5555,6666,5557,6667);
        laPoste.notifyOthersOfMyPresence();
    }

    @Test
    public void testUDPNotificationAnotherTime() throws Exception {
        Utilisateur moi = new Utilisateur("Mathieu \"Le plus beau\" Raynaud", InetAddress.getLocalHost());
        Messagerie laPoste = new Messagerie(5555,6666,5557,6667);
        laPoste.notifyOthersOfMyPresence();
    }


}
