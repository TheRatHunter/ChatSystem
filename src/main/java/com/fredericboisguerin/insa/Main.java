package com.fredericboisguerin.insa;
import com.fredericboisguerin.insa.chatSystem.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.InetAddress;


public class Main extends Application {
    private Stage primaryStage;
    private Stage connexionStage;
    //Pour tester sur un seul PC : laisser les ports comme ça pour lancer la première fenêtre et les échanger avant de lancer la seconde !
    //2 utilisateurs maximum sur un unique PC car on mappe les utilisateurs par leur @IP
    private final int portEcouteUDP = 5551;
    private final int portEcouteTCP = 4441;
    private final int portEnvoiUDP = 5550;
    private final int portEnvoiTCP = 4440;

    private Thread gestionApp;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
            this.primaryStage=primaryStage;
            this.connexionStage=new Stage();
            initConnexion();
            initLayout();
            connexionStage.show();
            /*
            On doit intégrer la gestion de notre application dans un nouveau Thread
            car la fonction start ne doit pas être bloquante
             */
            gestionApp = new Thread ( () -> launchMainApp() );
            gestionApp.start();
    }

    public void initConnexion() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ConnectionPanel.fxml"));
            connexionStage.setTitle("Chat System");
            connexionStage.setScene(new Scene(root, 269, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initLayout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUIController.fxml"));
            Platform.runLater( ( () -> primaryStage.setTitle("Chat System")));
            Platform.runLater( ( () ->primaryStage.setScene(new Scene(root, 850, 400))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeApp() {
        try {
            Messagerie.getInstance().stop();
            System.out.println("Processus terminé.");
            gestionApp.interrupt();
            Runtime.getRuntime().exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchMainApp() {
        try {
            connexionStage.setOnCloseRequest( (WindowEvent e) -> closeApp() );
            Messagerie messagerie = new Messagerie(portEcouteTCP,portEcouteUDP,portEnvoiTCP,portEnvoiUDP);
            Messagerie.getInstance().go();
            Messagerie.getInstance().getOthers();
            boolean done = false;
            while (!done) {
                if (ConnectionPanel.getInstance().pseudoOK) {
                    Platform.runLater( ( () -> connexionStage.hide()));
                    Platform.runLater( ( () -> primaryStage.show()));
                    done=true;
                } else {
                    try { Thread.sleep(200); } catch (InterruptedException e) { }
                }
            }
            Utilisateur moi = null;
            primaryStage.setOnCloseRequest( (WindowEvent e) -> closeApp() );
            moi = new Utilisateur(ConnectionPanel.getInstance().pseudo, InetAddress.getLocalHost());
            Messagerie.getInstance().setMoi(moi);
            initLayout();
            GUIController.getInstance().updateContacts();
            GUIController.getInstance().setNomUtilisateur(messagerie.moi.pseudonyme);
            Messagerie.getInstance().notifyOthersOfMyPresence();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}