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
import coloring.IGraphColorer;
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
         GraphColoringWriter.printSolution(lfl.color(g), System.out);
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
    * Mesure le temps d'exécution d'une coloration.
    * @param testName Nom du test.
    * @param colorer Heuristique de coloration.
    * @param graph Graphe a colorer.
    */
   public static void mesureTime(String testName, IGraphColorer colorer, Graph graph)
   {
      long totalTime = 0;

      long startTime = System.nanoTime();
      GraphColoring gc = colorer.color(graph);
      long endTime = System.nanoTime();
      totalTime += ((endTime - startTime)/1000);

      System.out.printf(testName + ": Time: %d || Colors: %d \n", totalTime , gc.getNumberOfColors());
   }

   /**
    * Benchmark les 4 variantes de l'algorithme Largest First de Welsh et Powell.
    * Donne le temps de chaque exécution et le nombre de couleurs trouvées
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
               LargestFirstOldest lfo = new LargestFirstOldest();
               LargestFirstNewest lfn = new LargestFirstNewest();
               LargestFirstLeast  lfl = new LargestFirstLeast();
               LargestFirstMost   lfm = new LargestFirstMost();

               System.out.println("Fichier: " + listOfFiles[i].getName());
               Graph g = GraphReader.fromFile(folderPath.concat(listOfFiles[i].getName()));
               System.out.println( g.getNVertices() + " sommets, " + g.getNEdges() + " arêtes, " + g.getMaxDegree() + " degré max.");

               long startTime, endTime, duration;
               GraphColoring gc;

               // Lancements des mesures
               System.out.printf("---------------------------------------------------\n");

               // Oldest version
               mesureTime("Oldest", lfo, g);

               // Newest version
               mesureTime("Newest", lfn, g);

               // Least version
               mesureTime("Least ", lfl, g);

               // Most version
               mesureTime("most  ", lfm, g);

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
