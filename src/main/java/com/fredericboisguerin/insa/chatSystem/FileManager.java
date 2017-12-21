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
                   String aEcrire = (message.nom + " SAID := " + message.msg + "\r\n");
                   stylo.write(aEcrire);
                   System.out.println("Ecriture d'un message dans le fichier : "+message.nom + " SAID := " + message.msg);
               }
               stylo.close();
           } catch (Exception e){
               e.printStackTrace();
           }
       }

       public static ArrayList<StringTuple> getConv(String utilisateur) {
           ArrayList<StringTuple> resultat = new ArrayList<StringTuple>();
           String pathname = new File("").getAbsolutePath();
           File convFile = new File(pathname +"/conversations/"+ utilisateur+".txt");
           if (convFile.exists()) {
               //lecture du fichier texte
               System.out.println("Récupération de l'historique des messages.");
               try{
                   InputStream ips=new FileInputStream(pathname +"/conversations/"+ utilisateur+".txt");
                   InputStreamReader ipsr=new InputStreamReader(ips);
                   BufferedReader br=new BufferedReader(ipsr);
                   String ligne;
                   while ((ligne=br.readLine())!=null){
                       if (!ligne.equals("")) {
                           System.out.println(ligne);
                           int indexSepatateur = ligne.indexOf(" SAID");
                           System.out.println(indexSepatateur);
                           String nom = ligne.substring(0,indexSepatateur);
                           String msg = ligne.substring(indexSepatateur+9, ligne.length());
                           System.out.println(nom+ " --- "+msg);
                           StringTuple temp = new StringTuple(nom,msg+"\n");
                           resultat.add(temp);
                           System.out.println("Message récupéré.");
                           br.skip(1);
                       }
                   }
                   br.close();
               }
               catch (Exception e){
                   System.out.println(e.toString());
               }
           }
           return resultat;
       }

}
