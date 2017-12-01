package com.fredericboisguerin.insa.test;

import com.fredericboisguerin.insa.chatSystem.Messagerie;
import com.fredericboisguerin.insa.chatSystem.Utilisateur;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class testEchangeUDP {
    @Test
    public void testUDPlisten() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost(), 5555);
        Messagerie laPoste = new Messagerie(moi);
        laPoste.listenOnUDPPort();
    }

    @Test
    public void testUDPNotification() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost(), 5555);
        Messagerie laPoste = new Messagerie(moi);
        laPoste.notifyOthersOfMyPresence();
    }

    @Test
    public void testUDPNotificationAnotherTime() throws Exception {
        Utilisateur moi = new Utilisateur("Mathieu \"Le plus beau\" Raynaud", InetAddress.getLocalHost(), 5555);
        Messagerie laPoste = new Messagerie(moi);
        laPoste.notifyOthersOfMyPresence();
    }


}
