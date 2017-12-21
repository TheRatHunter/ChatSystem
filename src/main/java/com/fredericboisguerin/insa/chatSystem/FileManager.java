package com.fredericboisguerin.insa.chatSystem;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

import static java.io.File.separator;
import static java.io.File.separatorChar;

public class FileManager{

    public FileManager() { }


     public static void saveConv(String utilisateur, ArrayList<StringTuple> conversation) {
           try {
               System.out.println("Sauvegarde d'une conversation");
               // Nous prenons comme convention que chaque utilisateur distant a un fichier nomme sonNom.txt dans le dossier data ou toute la conversation est sauvegarde
               String pathname = new File("").getAbsolutePath();
               File convFile = new File(pathname +"/conversations/"+ utilisateur+".txt");
               convFile.createNewFile();
               FileWriter stylo = new FileWriter(convFile);
               for(StringTuple message : conversation) {
                   String aEcrire = (message.nom + " SAID := " + message.msg + "\n");
                   stylo.write(aEcrire+"\n");
                   System.out.println("Ecriture d'un message dans le fichier : "+message.nom + " SAID := " + message.msg + "\n");
               }
               stylo.close();
           } catch (Exception e){
               e.printStackTrace();
           }
       }

       public static ArrayList<StringTuple> getConv(String utilisateur) {
            return new ArrayList<StringTuple>();
       }

}
