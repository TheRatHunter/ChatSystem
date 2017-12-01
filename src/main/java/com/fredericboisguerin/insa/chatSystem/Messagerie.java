package com.fredericboisguerin.insa.chatSystem;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Messagerie {

    //Déclaration constantes
    final int portEcouteUDP = 5555;
    final int portEcouteTCP = 6666;

    //Déclatarion variables
    public Utilisateur moi;
    public HashMap<InetAddress, Utilisateur> mapUsersByIP;

    //Constructeur
    public Messagerie (){
        mapUsersByIP = new HashMap<InetAddress, Utilisateur>();
    }

    //Lance l'interface graphique et les deux écoutes
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

    //Envoie un message quand le bouton envoyer est actionné
    public void onSendButtonClicked(String str, Utilisateur distantUser) {
        Thread envoiMessageThread = new Thread(() -> {
            try {
                sendMessage(str, distantUser.ipAdress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //Envoyer message par TCP
    public void sendMessage(String message, InetAddress ipAddress) throws IOException {
        try {
            Socket socket = new Socket(ipAddress, portEcouteTCP);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Ecoute de la connection de nouveaux utilisateurs
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

    //Ajout d'un contact à la liste
    private void ajouterPersonne(String nom, InetAddress ip, int port) {
        mapUsersByIP.put(ip,new Utilisateur(nom, ip, port));
    }

    //Ecouter les messages entrants sur le port TCP
    public void listenOnTCPPort() throws Exception {

        try {
            ServerSocket socketServeur = new ServerSocket(portEcouteTCP);
            System.out.println("Ecoute de messages entrants...");
            while (true) {
                Socket socketClient = socketServeur.accept();
                String message = new String("");
                BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                PrintStream out = new PrintStream(socketClient.getOutputStream());
                message = in.readLine();
                System.out.println("Connexion établie avec " + socketClient.getInetAddress());
                System.out.println("Chaîne reçue : " + message + "\n");
                ajouterMessage(socketClient.getInetAddress(), message);
                socketClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Ajour d'un message entrant à la conversation
    private void ajouterMessage (InetAddress ip, String message) {
        mapUsersByIP.get(ip).conv.ajouterMessage(message);

    }



}
