package com.fredericboisguerin.insa.chatSystem;

import javafx.application.Platform;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.net.*;
import java.util.*;


public class Messagerie {



    //Déclaration constantes
    private int PORT_ECOUTE_UDP;
    private int PORT_ECOUTE_TCP;
    private int PORT_ENVOI_UDP;
    private int PORT_ENVOI_TCP;
    private static Messagerie instance;
    private Thread UDPlistenThread;
    private Thread TCPlistenThread;

    //Déclatarion variables
    public Utilisateur moi;
    public HashMap<InetAddress, Utilisateur> mapUsersByIP;
    //Utilisée pour la vérification de l'unicité des noms
    public HashMap<InetAddress, String> mapNamesByIP;

    //Constructeur
    public Messagerie (int portEcouteTCP, int portEcouteUDP, int portEnvoiTCP, int portEnvoiUDP){
        this.mapUsersByIP = new HashMap<InetAddress, Utilisateur>();
        this.mapNamesByIP = new HashMap<InetAddress, String>();
        this.instance=this;
        this.PORT_ECOUTE_TCP = portEcouteTCP;
        this.PORT_ECOUTE_UDP = portEcouteUDP;
        this.PORT_ENVOI_TCP = portEnvoiTCP;
        this.PORT_ENVOI_UDP = portEnvoiUDP;

        this.UDPlistenThread = new Thread(() -> {
            try {
                this.listenOnUDPPort();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.TCPlistenThread = new Thread(() -> {
            try {
                this.listenOnTCPPort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setMoi(Utilisateur moiUser) {
        this.moi = moiUser;
    }
    public static Messagerie getInstance() {
        return instance;

    }

    //Lance l'interface graphique et les deux écoutes
    public void go() throws IOException{
        UDPlistenThread.start();
        TCPlistenThread.start();
    }

    public void notifyOthersOfMyDisconnection() {
        String message = "disp : " + String.valueOf(moi.disponible);
        byte[] msg = message.getBytes();
        try {
            InetAddress adresseDeBroadcast = InetAddress.getByName("255.255.255.255");
            DatagramPacket dp = new DatagramPacket(msg, msg.length, adresseDeBroadcast, PORT_ENVOI_UDP);
            DatagramSocket ds = new DatagramSocket();
            ds.send(dp);
            System.out.println("Exit notification datagram sent to all.");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stop() throws IOException{
        for(Map.Entry<InetAddress, Utilisateur> entry : mapUsersByIP.entrySet()) {
            Utilisateur user = entry.getValue();
            user.conv.enregistrerConversation(user.pseudonyme);
        }
        if (moi != null) {
            notifyOthersOfMyDisconnection();
            moi.disponible = false;
        }
        UDPlistenThread.interrupt();
        TCPlistenThread.interrupt();
    }



    //Ecoute de la connection UDP de nouveaux utilisateurs
    public void listenOnUDPPort() throws Exception {
        try{

            byte[] buf = new byte[8196];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            DatagramSocket ds = new DatagramSocket(PORT_ECOUTE_UDP);
            while (true) {
                System.out.println("Ecoute UDP sur le port "+PORT_ECOUTE_UDP+"...");
                ds.receive(dp);
                String msgrecu = new String(dp.getData(), 0, dp.getLength());
                //Si le message n'est pas vide alors on doit ajouter le nom de l'utilisateur
                if (!msgrecu.equals("")) {
                    if (!mapUsersByIP.containsKey(dp.getAddress())) {
                        if (msgrecu.contains("name : ")) {
                            //Si on reçoit un message contenant un nom nouveau on y répond avec notre nom et on l'ajoute
                            System.out.println("Ajout de l'utilisateur " + msgrecu.substring(7));
                            ajouterPersonne(msgrecu.substring(7), dp.getAddress());
                            notifyOneOfMyPresence(dp.getAddress());
                            Platform.runLater((() -> GUIController.getInstance().updateContacts()));
                        }
                    }
                    else if (msgrecu.contains("disp :")){
                        //Si on reçoit un message indiquant la déconnection d'un utilisateur, on le retire de la liste et on sauvegarde notre conversation avec lui
                        mapUsersByIP.get(dp.getAddress()).conv.enregistrerConversation(mapUsersByIP.get(dp.getAddress()).pseudonyme);
                        retirerPersonne(dp.getAddress());
                        Platform.runLater((() -> GUIController.getInstance().updateContacts()));
                        Platform.runLater((() -> GUIController.getInstance().clearConversation()));
                    }
                }
                else  {
                    //Si on reçoit un message vide on informe de notre présence
                    Messagerie.getInstance().notifyOneOfMyPresence(dp.getAddress());
                }
            }


        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    public void getOthers() {
        String message = "";
        byte[] msg = message.getBytes();
        try {
            InetAddress adresseDeBroadcast = InetAddress.getByName("255.255.255.255");
            DatagramPacket dp = new DatagramPacket(msg, msg.length, adresseDeBroadcast, PORT_ENVOI_UDP);
            DatagramSocket ds = new DatagramSocket();
            System.out.println("Request datagram sent.");
            ds.send(dp);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void notifyOthersOfMyPresence() {
        String message = "name : " + moi.pseudonyme;
        byte[] msg = message.getBytes();
        try {
            InetAddress adresseDeBroadcast = InetAddress.getByName("255.255.255.255");
            DatagramPacket dp = new DatagramPacket(msg, msg.length, adresseDeBroadcast, PORT_ENVOI_UDP);
            DatagramSocket ds = new DatagramSocket();
            System.out.println("Presence notification datagram sent to all.");
            ds.send(dp);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void notifyOneOfMyPresence(InetAddress ip) {
        if (moi!=null) {
            String message = "name : " + moi.pseudonyme;
            byte[] msg = message.getBytes();
            try {
                InetAddress adresseCible = ip;
                DatagramPacket dp = new DatagramPacket(msg, msg.length, adresseCible, PORT_ENVOI_UDP);
                DatagramSocket ds = new DatagramSocket();
                System.out.println("Notification datagram sent to one.");
                ds.send(dp);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }



    //Ecouter les messages entrants sur le port TCP
    public void listenOnTCPPort() {
        try {

            ServerSocket socketServeur = new ServerSocket(PORT_ECOUTE_TCP);
            while (true) {
                System.out.println("Ecoute TCP sur le port "+PORT_ECOUTE_TCP+"...");
                Socket socketClient = socketServeur.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

                String temp="";
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.isEmpty()) {
                        break;
                    }
                    temp += line+"\n";
                }

                String message = temp;
                System.out.println("Connexion établie avec " + socketClient.getInetAddress());
                System.out.println("Chaîne reçue : " + message);

                ajouterMessage(socketClient.getInetAddress(), message, false);

                //On actualise la conversation seulement si l'utilisateur courant a changé
                GUIController.getInstance().userCourant=mapUsersByIP.get(socketClient.getInetAddress());
                if (!(GUIController.getInstance().userCourant.equals(GUIController.getInstance().userPrécédent)))
                    Platform.runLater( ( () -> GUIController.getInstance().conversationEnCours.getChildren().clear()));

                GUIController.getInstance().userPrécédent=GUIController.getInstance().userCourant;

                Platform.runLater( ( () -> GUIController.getInstance().afficherMessage(GUIController.getInstance().userCourant.pseudonyme+" :\n"+message, false)));


                socketClient.close();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Envoyer message par TCP
    public void sendMessage(String message, InetAddress ipAddress) {
        try {
            Socket socket = new Socket(ipAddress, PORT_ENVOI_TCP);
            PrintStream out = new PrintStream(socket.getOutputStream());
            System.out.println("Message envoyé : "+message);
            out.println(message);
            ajouterMessage(GUIController.getInstance().userCourant.ipAdress,message,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Ajour d'un message entrant à la conversation
    private void ajouterMessage (InetAddress ip, String message, Boolean deMoi) {
        if (deMoi) {
            if (mapUsersByIP.containsKey(ip)) {
                mapUsersByIP.get(ip).conv.ajouterMessage("Moi", message);
            }
        } else {
            if (mapUsersByIP.containsKey(ip))
                mapUsersByIP.get(ip).conv.ajouterMessage(mapUsersByIP.get(ip).pseudonyme, message);
        }

    }

    //Ajout d'un contact à la liste
    private void ajouterPersonne(String nom, InetAddress ip) {
        mapUsersByIP.put(ip,new Utilisateur(nom, ip));
        mapNamesByIP.put(ip,nom);
    }

    //Retrait d'un contact de la liste
    private void retirerPersonne(InetAddress ip){
        Utilisateur toRemove = mapUsersByIP.get(ip);
        mapUsersByIP.remove(ip, toRemove);
        mapNamesByIP.remove(ip, toRemove.pseudonyme);
    }




}
