package com.fredericboisguerin.insa.chatSystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;


public class Messagerie {

    final int portEcouteUDP = 5555;
    public Utilisateur moi;
    public ArrayList<Utilisateur> listOfContactsWithConversations ;

    public Messagerie (){
        listOfContactsWithConversations = new ArrayList<Utilisateur>();
    }

    public void go() {
            GUI monBeauGUI = new GUI();
            monBeauGUI.afficherMainPage();

            Thread UDPlistenThread = new Thread(() -> {
                try {
                    this.listenOnUDPPort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread TCPlistenThread = new Thread(() -> {
                try {
                    this.listenOnTCPPort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    public void onSendButtonClicked(String str, Utilisateur distantUser) {
        Thread envoiMessageThread = new Thread(() -> {
            try {
                sendMessage(str, distantUser.ipAdress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendMessage(String message, InetAddress ipAddress) throws IOException {
        //Envoyer message par TCP
    }

    public void ListenForNewUsers(int port, IncomingMessageListener incomingMessageListener) throws Exception {
        Thread ecouteConnexionUtilisateurThread = new Thread(() -> {
            try {
                listenOnUDPPort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void listenOnUDPPort() throws Exception {
        try{

            byte[] buf = new byte[8196];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            DatagramSocket ds = new DatagramSocket(portEcouteUDP);
            System.out.println("I'm listening, boss.");
            while (true) {
                ds.receive(dp);
                String msgrecu = new String(dp.getData(), 0, dp.getLength());
                Thread ajoutPersonneThread = new Thread(() -> {
                    try {
                        ajouterPersonne(msgrecu, dp.getAddress(), dp.getPort());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

    }

    private void ajouterPersonne(String nom, InetAddress ip, int port) {
        listOfContactsWithConversations.add(new Utilisateur(nom, ip, port));
    }


    public void listenOnTCPPort() throws Exception {


    }

    private void ajouterMessage (String nom,InetAddress ip) {


    }



}
