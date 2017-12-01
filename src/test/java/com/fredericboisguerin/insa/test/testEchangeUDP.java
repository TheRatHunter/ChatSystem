package com.fredericboisguerin.insa.test;

import com.fredericboisguerin.insa.chatSystem.Messagerie;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class testEchangeUDP {
    @Test
    public void testUDPlisten() throws Exception {
        Messagerie laPoste = new Messagerie();
        laPoste.listenOnUDPPort();
    }


}
