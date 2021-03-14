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
import java.util.ArrayList;

/**
 * Classe utilisée pour tester la coloration des graphes.
 */
public class TestsGraphColoring
{
   /**
    * Lis le graphe dans le fichier donnée et l'affiche dans la sortie standard.
    * @param file Fichier du graphe.s
    */
   public static void outputSingle(String file)
   {
      try
      {
         Graph g = GraphReader.fromFile(file);
         LargestFirstMost lfo = new LargestFirstMost();
         GraphColoringWriter.printSolution(lfo.color(g), System.out);
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
               // Newest version
               startTime = System.nanoTime();
               gc = lfn.color(g);
               endTime = System.nanoTime();
               duration = (endTime - startTime) / 1000;
               System.out.printf("Newest: Time: %d || Colors: %d \n", duration, gc.getNumberOfColors());

               // Oldest version
               startTime = System.nanoTime();
               gc = lfo.color(g);
               endTime = System.nanoTime();
               duration = (endTime - startTime) / 1000;
               System.out.printf("Oldest: Time: %d || Colors: %d \n", duration, gc.getNumberOfColors());

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

   public static void main(String[] args)
   {
      if(args.length != 1)
      {
         System.out.println("Folder with datas expected as argument.");
         System.out.println("Example: \"data/\"");
         return;
      }

      dataBenchmark(args[0]);

      return;
   }
}
