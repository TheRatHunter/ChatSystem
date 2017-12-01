package com.fredericboisguerin.insa.test;

import com.fredericboisguerin.insa.chatSystem.Messagerie;

import java.io.IOException;
import java.net.InetAddress;
import org.junit.Test;

public class TestEchangeTCP {

    @Test
    public void testTCPlisten() throws Exception {
        Messagerie laPoste = new Messagerie();
        laPoste.listenOnTCPPort();
    }

    @Test
    public void testTCPsend() throws IOException {
        Messagerie UPS = new Messagerie();
        UPS.sendMessage("Yo !", InetAddress.getLocalHost());
    }

}
