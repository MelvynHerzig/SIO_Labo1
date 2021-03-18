/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.amount_use.LargestFirstAmount.java
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
 * Classe Abstraite qui défini les stuctures supplémentaires et méthodes
 * communes pour les variantes de l'algorithmes LargestFirst Least et Most
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
    * Initialise les structures supplémentaires et appel la résolution
    * de la classe parente.
    * @param g Graphe simple à colorer.
    * @return Retourne un GrapheColoring contenant la solution trouvée.
    */
   public GraphColoring color(Graph g)
   {
      init(g);

      if(g.getNVertices() != 0)
      {
         ++colorsCounter[0];
         adjustBucketsColors(1);
      }

      return super.color(g);
   }

   /**
    * Initialise les structures pour la méthode color.
    * @param g Graphe utilisé.
    */
   private void init(Graph g)
   {
      colorsCounter = new int[g.getMaxDegree()+1];

      //Il y a entre 0 et N utilisations possibles pour les couleurs.
      bucketsColorsUse = new ArrayList<>(g.getNVertices());
      for(int i  = 0; i < g.getNVertices(); ++i)
      {
         bucketsColorsUse.add(new ArrayList<>());
      }

      // Placement des couleurs (à 0 pour le moment)
      for(int color = 1; color <= colorsCounter.length; ++color)
      {
         bucketsColorsUse.get(0).add(color);
      }
   }

   protected void adjustBucketsColors(int color)
   {
      // On retire la couleur de son ancien bucket
      bucketsColorsUse.get(colorsCounter[color-1] - 1).remove(Integer.valueOf(color));

      // On l'ajoute dans le bon bucket
      // On cherche la première plus grande couleur
      int i;
      for(i = 0; i < bucketsColorsUse.get(colorsCounter[color-1]).size(); ++i)
      {
         if(bucketsColorsUse.get(colorsCounter[color-1]).get(i) > color)
         {
            break;
         }
      }

      // On ajoute dans le bucket à la bonne position.
      bucketsColorsUse.get(colorsCounter[color-1]).add(i, color);
   }

   /**
    * Essaie d'ajouter la couleur color au sommet vertex
    * @param color Couleur à ajouter.
    * @param vertex Sommet cible.
    * @return vrai en cas de succès sinon false.
    */
   protected boolean tryAddColor(int color, int vertex)
   {
      if(adjacentColors[color-1] != vertex)
      {
         solution[vertex-1] = color;

         if(colorsCounter[color - 1] == 0) ++nbDiffrentColors;

         ++colorsCounter[color - 1];

         // Mise à jour
         adjustBucketsColors(color);

         return true;
      }
      return false;
   }
}
