package com.fredericboisguerin.insa;
import com.fredericboisguerin.insa.chatSystem.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class Main extends Application {
    private Stage primaryStage;
    private Stage connexionStage;
    private int portEcouteUDP = 5555;
    private int portEcouteTCP = 6666;
    private int portEnvoiUDP = 5557;
    private int portEnvoiTCP = 6667;

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
            Thread gestionApp = new Thread ( () -> launchMainApp() );

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

    public void launchMainApp() {
        Messagerie messagerie = new Messagerie(portEcouteTCP,portEcouteUDP,portEnvoiTCP,portEnvoiUDP);
        Messagerie.getInstance().getOthers();
        boolean done = false;
        while (!done) {
            if (ConnectionPanel.getInstance().pseudoOK) {
                Platform.runLater( ( () -> connexionStage.hide()));
                Platform.runLater( ( () -> primaryStage.show()));
                done=true;
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Utilisateur moi = null;
        try {
            moi = new Utilisateur(ConnectionPanel.getInstance().pseudo, InetAddress.getLocalHost());
            Messagerie.getInstance().setMoi(moi);
            initLayout();
            GUIController.getInstance().setNomUtilisateur(messagerie.moi.pseudonyme);
            messagerie.go();
            Messagerie.getInstance().notifyOthersOfMyPresence();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}