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
    * Mémorise le nombre d'utilisation de la couleurs la plus utilisée.
    * Permet de ne pas itérer sur les buckets finaux vides.
    */
   protected int mostUsedAmount;

   /**
    * Constructeur.
    */
   protected ALargestFirstAmount()
   {
      bucketsColorsUse = null;
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
         adjustBucketsColors(1, 1);
      }

      return super.color(g);
   }

   /**
    * Initialise les structures pour la méthode color.
    * @param g Graphe utilisé.
    */
   private void init(Graph g)
   {
      mostUsedAmount = 0;

      //Il y a entre 0 et N utilisations possibles pour les couleurs.
      bucketsColorsUse = new ArrayList<>(g.getNVertices()+1);
      for(int i  = 0; i < g.getNVertices(); ++i)
      {
         bucketsColorsUse.add(new ArrayList<>());
      }

      // Placement des couleurs (à 0 pour le moment)
      for(int color = 1; color <= g.getMaxDegree()+1; ++color)
      {
         bucketsColorsUse.get(0).add(color);
      }
   }

   protected void adjustBucketsColors(int color, int newUsageCount)
   {
      // On retire la couleur de son ancien bucket
      bucketsColorsUse.get(newUsageCount-1).remove(Integer.valueOf(color));

      // On l'ajoute dans le bon bucket
      // On cherche la première plus grande couleur
      int i;
      for(i = 0; i < bucketsColorsUse.get(newUsageCount).size(); ++i)
      {
         if(bucketsColorsUse.get(newUsageCount).get(i) > color)
         {
            break;
         }
      }

      // On ajoute dans le bucket à la bonne position.
      bucketsColorsUse.get(newUsageCount).add(i, color);

      // Mise à jour du montant de la plus grosse utilisation.
      mostUsedAmount = (mostUsedAmount < newUsageCount) ? newUsageCount : mostUsedAmount;
   }

   /**
    * Essaie d'ajouter la couleur color au sommet vertex
    * @param color Couleur à ajouter.
    * @param colorUseCount Nombre d'utilisations actuelles de la couleur.
    * @param vertex Sommet cible.
    * @return vrai en cas de succès sinon false.
    */
   protected boolean tryAddColor(int color, int colorUseCount,int vertex)
   {
      if(adjacentColors[color-1] != vertex)
      {
         solution[vertex-1] = color;

         if( color > nbDiffrentColors)
            ++nbDiffrentColors;

         // Mise à jour
         adjustBucketsColors(color, colorUseCount+1);

         return true;
      }
      return false;
   }
}
