/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.TestGraphColoring.java
 Auteur(s)   : Herzig Melvyn
 Date        : 14.03.2021
 -----------------------------------------------------------------------------------
 */

// Résolution
import coloring.GraphColoring;
import coloring.GraphColoringWriter;
import coloring.lf.amout_use.LargestFirstLeast;
import coloring.lf.amout_use.LargestFirstMost;
import coloring.lf.last_use.LargestFirstNewest;

// Graph
import coloring.lf.last_use.LargestFirstOldest;
import graph.*;

import java.io.File;
import java.io.IOException;

/**
 * Classe utilisée pour tester la coloration des graphes.
 * @author Herzig Melvyn
 * @date 20/03/2021
 */
public class TestsGraphColoring
{
   /**
    * Lis le graphe dans le fichier donné et affiche la coloration
    * obtenue à partir de la variante oldest.
    * @param file Fichier du graphe.
    */
   public static void outputColoringOldest(String file)
   {
      try
      {
         Graph g = GraphReader.fromFile(file);

         // Oldest version
         LargestFirstOldest lfo = new LargestFirstOldest();
         System.out.printf("\nOldest\n---------------------------------------------------\n");
         GraphColoringWriter.printSolution(lfo.color(g), System.out);
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
      }
   }

   /**
    * Lis le graphe dans le fichier donné et affiche la coloration
    * obtenue à partir de la variante newest.
    * @param file Fichier du graphe.
    */
   public static void outputColoringNewest(String file)
   {
      try
      {
         Graph g = GraphReader.fromFile(file);

         // Newest version
         LargestFirstNewest lfn = new LargestFirstNewest();
         System.out.printf("\nNewest\n---------------------------------------------------\n");
         GraphColoringWriter.printSolution(lfn.color(g), System.out);
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
      }
   }

   /**
    * Lis le graphe dans le fichier donné et affiche la coloration
    * obtenue à partir de la variante Least.
    * @param file Fichier du graphe.
    */
   public static void outputColoringLeast(String file)
   {
      try
      {
         Graph g = GraphReader.fromFile(file);

         // Least version
         System.out.printf("\nLeast\n---------------------------------------------------\n");
         LargestFirstLeast  lfl = new LargestFirstLeast();
         lfl.color(g);
         //GraphColoringWriter.printSolution(lfl.color(g), System.out);
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
      }
   }

   /**
    * Lis le graphe dans le fichier donné et affiche la coloration
    * obtenue à partir de la variante most.
    * @param file Fichier du graphe.
    */
   public static void outputColoringMost(String file)
   {
      try
      {
         Graph g = GraphReader.fromFile(file);

         // Most version
         System.out.printf("\nMost\n---------------------------------------------------\n");
         LargestFirstMost   lfm = new LargestFirstMost();
         GraphColoringWriter.printSolution(lfm.color(g), System.out);
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
      }
   }

   /**
    * Benchmark les 4 variantes de l'algorithme Largest First de Welsh et Powell.
    * Donne le temps de chaque exécution et le nombre de couleurs trouvée
    * @param folderPath Chemin du dossier contenant les données
    */
   public static void dataBenchmark(String folderPath)
   {
      File folder = new File(folderPath);
      File[] listOfFiles = folder.listFiles();

      if(listOfFiles == null)
      {
         System.out.println("Dossier introuvable.");
         return;
      }

      // Récupération graphes
      try
      {
         for (int i = 0; i < listOfFiles.length; i++)
         {
            if (listOfFiles[i].isFile())
            {
               LargestFirstNewest lfn = new LargestFirstNewest();
               LargestFirstOldest lfo = new LargestFirstOldest();
               LargestFirstMost   lfm = new LargestFirstMost();
               LargestFirstLeast  lfl = new LargestFirstLeast();

               System.out.println("Fichier: " + listOfFiles[i].getName());
               Graph g = GraphReader.fromFile(folderPath.concat(listOfFiles[i].getName()));
               System.out.println( g.getNVertices() + " sommets, " + g.getNEdges() + " arêtes, " + g.getMaxDegree() + " degré max.");

               long startTime, endTime, duration;
               GraphColoring gc;

               // Lancements des mesures
               System.out.printf("---------------------------------------------------\n");

               // Oldest version
               startTime = System.nanoTime();
               gc = lfo.color(g);
               endTime = System.nanoTime();
               duration = (endTime - startTime) / 1000;
               System.out.printf("Oldest: Time: %d || Colors: %d \n", duration, gc.getNumberOfColors());

               // Newest version
               startTime = System.nanoTime();
               gc = lfn.color(g);
               endTime = System.nanoTime();
               duration = (endTime - startTime) / 1000;
               System.out.printf("Newest: Time: %d || Colors: %d \n", duration, gc.getNumberOfColors());

               // Least version
               startTime = System.nanoTime();
               gc = lfl.color(g);
               endTime = System.nanoTime();
               duration = (endTime - startTime) / 1000;
               System.out.printf("Least : Time: %d || Colors: %d \n", duration, gc.getNumberOfColors());

               // Most version
               startTime = System.nanoTime();
               gc = lfm.color(g);
               endTime = System.nanoTime();
               duration = (endTime - startTime) / 1000;
               System.out.printf("Most  : Time: %d || Colors: %d \n", duration, gc.getNumberOfColors());

               System.out.printf("---------------------------------------------------\n\n");
            }
         }
      }
      catch(IOException e)
      {
         System.out.printf(e.getMessage());
      }
   }

   /**
    * Programme d'exécution de la coloration.
    * Permet d'exécuter et d'afficher dans la console la coloration d'un graphe
    * ou lance un benchmark sur tous les fichiers d'un dossier.
    * @param args args 0 = <oldest/newest/least/most> args 1 = <path to file or path to folder>
    */
   public static void main(String[] args)
   {
      if(args.length != 2)
      {
         System.out.println("Use: <oldest/newest/least/most> <path to file containing graph>");
         System.out.println("     or");
         System.out.println("   : <benchmark> <path to folder containing files>");
         return;
      }

      switch (args[0])
      {
         case "oldest"   :
            outputColoringOldest(args[1]);
            break;

         case "newest"   :
            outputColoringNewest(args[1]);
            break;

         case "least"    :
            outputColoringLeast(args[1]);
            break;

         case "most"     :
            outputColoringMost(args[1]);
            break;

         case "benchmark":
            dataBenchmark(args[1]);
            break;

         default:
            System.out.println("Unknown first arg, execution stopped");
      }

      return;
   }
}
