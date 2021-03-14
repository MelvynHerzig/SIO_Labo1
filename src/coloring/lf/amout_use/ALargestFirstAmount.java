/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.ALargestFirstAmount.java
 Auteur(s)   : Herzig Melvyn
 Date        : 13.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.amout_use;

import coloring.GraphColoring;
import coloring.lf.ALargestFirst;
import graph.Graph;

import java.util.ArrayList;

/**
 * Classe Abstraite qui défini les stuctures supplémentaires pour
 * variantes de l'algorithmes LargestFirst Least et Most
 * @author Herzig Melvyn
 * @date 14/03/2021
 */
public abstract class ALargestFirstAmount extends ALargestFirst
{
   /**
    * Buckets pour trier les couleurs selon leur nombre d'utilisation.
    */
   protected ArrayList<ArrayList<Integer>> bucketsColorsUse;

   /**
    * Compte pour chaque couleur son nombre d'occurences
    */
   protected int[] colorsCounter;

   /**
    * Constructeur.
    */
   protected ALargestFirstAmount()
   {
      bucketsColorsUse = null;
      colorsCounter = null;
   }

   /**
    * Initialise les structures pour la méthode color.
    * @param g Graphe utilisé.
    */
   private void init(final Graph g)
   {
      colorsCounter = new int[g.getMaxDegree()+1];

      //Il y a entre 0 et N utilisations possibles pour les couleurs.
      bucketsColorsUse = new ArrayList<>(g.getNVertices());
      for(int i  = 0; i < g.getNVertices(); ++i)
      {
         bucketsColorsUse.add(new ArrayList<>());
      }
   }

   /**
    * Initialise les structures supplémentaires et appel la résolution
    * de la classe parente.
    * @param graph Graphe simple à colorer.
    * @return Retourne un GrapheColoring contenant la solution trouvée.
    */
   public GraphColoring color(Graph graph)
   {
      init(graph);

      return super.color(graph);
   }
}
