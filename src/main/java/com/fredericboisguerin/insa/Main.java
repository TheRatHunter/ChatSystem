package com.fredericboisguerin.insa;
import com.fredericboisguerin.insa.chatSystem.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        try {



            GUI monGUI = new GUI(primaryStage);
            Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost(), 5555);
            Messagerie messagerie = new Messagerie(moi);
            messagerie.go();
            System.out.println(messagerie.moi.pseudonyme);
            GUI.getInstance().setNomUtilisateur(messagerie.moi.pseudonyme);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}