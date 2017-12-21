package com.fredericboisguerin.insa.test;

import com.fredericboisguerin.insa.chatSystem.Messagerie;
import com.fredericboisguerin.insa.chatSystem.Utilisateur;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class testEchange {

    /*Les tests nous ont permis de vérifier nos échanges de messages simples,
     mais ne marchent plus maintenant que l'application s'est étoffée */

    /*
    @Test
    public void testlisten() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost());
        Messagerie laPoste = new Messagerie(5557,6667,5555,6666);
        laPoste.go();
        while(true);
    }

    @Test
    public void testUDPNotification() throws Exception {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost());
        Messagerie laPoste = new Messagerie(5555,6666,5557,6667);
        laPoste.setMoi(moi);
        laPoste.notifyOthersOfMyPresence();
    }

    @Test
    public void testUDPNotificationAnotherTime() throws Exception {
        Utilisateur moi = new Utilisateur("Mathieu \"Le plus beau\" Raynaud", InetAddress.getLocalHost());
        Messagerie laPoste = new Messagerie(5555,6666,5557,6667);
        laPoste.setMoi(moi);
        laPoste.notifyOthersOfMyPresence();
    }

    @Test
    public void testTCPsend() throws IOException {
        Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost());
        Messagerie UPS = new Messagerie(5555,6666,5557,6667);
        UPS.setMoi(moi);
        UPS.sendMessage("Yo !", InetAddress.getLocalHost());
    }
    */


}
