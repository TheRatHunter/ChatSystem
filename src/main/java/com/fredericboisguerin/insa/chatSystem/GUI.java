package com.fredericboisguerin.insa.chatSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.ComponentOrientation;

import static java.awt.BorderLayout.*;

public class GUI extends JFrame {

    private Messagerie messagerieAssociee;

    public GUI() {
        super("Application de Chat");

        this.messagerieAssociee = new Messagerie();


        //Définitions -------------------------------------------------------------------------------------------------

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);

        JPanel globalPane = new JPanel(new GridLayout(2, 1));
        JPanel southPane = new JPanel(new GridLayout(1,2));
        JPanel informationPane = new JPanel(new GridLayout(1, 0));
        JPanel usersPane = new JPanel(new GridLayout(0, 1));
        JPanel conversationPane = new JPanel(new GridLayout(2, 1));
        JPanel bufferPane = new JPanel(new GridLayout(1,2));
        JPanel infoAboutUserPane  = new JPanel(new GridLayout(2,1));

        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(e ->  this.messagerieAssociee.onSendButtonClicked("Coucou"));
        JButton changerPseudoButton = new JButton("Changer");
        changerPseudoButton.addActionListener(e ->  this.messagerieAssociee.onSendButtonClicked("Coucou"));
        JButton parametresButton = new JButton("Paramètres");
        sendButton.addActionListener(e ->  this.onParametresButtonClicked());

        // Mise en place du Panel d'informations (en haut)
        JLabel titreLabel = new JLabel("Système de Chat");
        JLabel pseudoLabel = new JLabel("Pseudo :");

        // Mise en place du Panel des utilisateurs connectés
        JLabel utilisateursConnectesLabel = new JLabel("Utilisateurs connectés :");
        JLabel utilisateursHorsLigneLabel = new JLabel("Utilisateurs hors ligne :");

        usersPane.add(utilisateursConnectesLabel);
        usersPane.add(utilisateursHorsLigneLabel);
        usersPane.setBorder(BorderFactory.createEtchedBorder());

        //Mise en place du Panel de conversation
        JLabel conversationLabel = new JLabel("Conversation :");
        JTextField bufferTextField = new JTextField(10);
        bufferTextField.setToolTipText("Ecrivez ici");





        //Mise en page ------------------------------------------------------------------------------------------------

        infoAboutUserPane.add(pseudoLabel);
        infoAboutUserPane.add(changerPseudoButton);
        informationPane.add(infoAboutUserPane);
        informationPane.add(titreLabel);
        informationPane.add(parametresButton, EAST);
        informationPane.setBorder(BorderFactory.createEtchedBorder());

        bufferPane.add(bufferTextField);
        bufferPane.add(sendButton);


        conversationPane.add(conversationLabel);
        conversationPane.add(bufferPane);
        conversationPane.setBorder(BorderFactory.createEtchedBorder());

        globalPane.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        southPane.add(usersPane);
        southPane.add(conversationPane);

        globalPane.add(informationPane);
        globalPane.add(southPane);



        // Definition de la fenetre globale
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(globalPane);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void afficherMainPage() {
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    public void onParametresButtonClicked() {

    }

    public void afficherConversation() {


    }
}
