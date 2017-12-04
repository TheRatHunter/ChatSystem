package com.fredericboisguerin.insa.chatSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.ComponentOrientation;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.awt.BorderLayout.*;

public class GUI {


    public Label labelNomUtilisateur = new Label();
    public Label helloWorld;
    private static GUI instance;

    public GUI() { }

    public GUI(Stage primaryStage) throws IOException {
        instance=this;
        Parent root = FXMLLoader.load(getClass().getResource("/GUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 850, 400));
        primaryStage.show();
    }

    public static GUI getInstance() {
        return instance;
    }

    public void setNomUtilisateur(String nom){
        labelNomUtilisateur.setText(nom);
    }


    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World");
    }
}
