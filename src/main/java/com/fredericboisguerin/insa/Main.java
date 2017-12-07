package com.fredericboisguerin.insa;
import com.fredericboisguerin.insa.chatSystem.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Application {
    private Stage primaryStage;
    private Stage connexionStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            this.primaryStage=primaryStage;
            this.connexionStage=new Stage();

            initConnexion();
            Utilisateur moi = new Utilisateur("Lolo", InetAddress.getLocalHost(), 5555);
            Messagerie messagerie = new Messagerie(moi);
            messagerie.go();
            initLayout();
            GUIController.getInstance().setNomUtilisateur(messagerie.moi.pseudonyme);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initConnexion() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ConnectionPanel.fxml"));
            connexionStage.setTitle("Chat System");
            connexionStage.setScene(new Scene(root, 269, 400));
            connexionStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initLayout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUIController.fxml"));
            primaryStage.setTitle("Chat System");
            primaryStage.setScene(new Scene(root, 850, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}