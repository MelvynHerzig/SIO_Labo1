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

/**
 * Classe Abstraite qui défini les stuctures supplémentaires et méthodes
 * communes pour les variantes de l'algorithmes LargestFirst Least et Most
 * @author Herzig Melvyn
 * @date 14/03/2021
 */
public abstract class ALargestFirstAmount extends ALargestFirst
{
   /**
    * Stocke pour chaque couleur son nombre d'utilisations.
    */
   protected int[] amoutColorsUsed;

   /**
    * A chaque assignaton d'une couleur pour un noeud.
    * Stocke la meilleure couleur potentiellement assignable.
    */
   protected int  potentialColor;

   /**
    * Constructeur.
    */
   protected ALargestFirstAmount()
   {
      amoutColorsUsed = null;
   }

   /**
    * Initialise les structures pour la méthode color.
    * @param g Graphe utilisé.
    */
   private void init(Graph g)
   {
      potentialColor = 0;
      amoutColorsUsed = new int[g.getMaxDegree()+1];
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
         ++amoutColorsUsed[0];
      }

      return super.color(g);
   }

   /**
    * Essaie d'améliorer potentialColor par color pour le sommet vertex
    * @param color Couleur possiblement meilleur que potentialColor.
    * @param vertex Sommet courrant.
    */
   protected void tryImprovePotentialColor(int color,int vertex)
   {
      // Si la couleur est adjacente pas besoin d'améliorer potentialColor.
      if(adjacentColors[color-1] == vertex)
      {
         return;
      }

      // aucune couleur n'est encore potentielle
      if (potentialColor == 0 || isColorBetterThanPotential(color))
      {
         potentialColor = color;
      }
   }

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   @Override
   protected void setPossibleColor(int v)
   {
      potentialColor = 0;

      // De la première couleur jusqu'a la dernière couleur.
      for(int color = 1; color <= nbDiffrentColors; ++color)
      {
         tryImprovePotentialColor(color, v);
      }

      // Si aucune couleur n'est trouvle comme potentielle.
      // On ajoute une nouvelle couleurs
      if(potentialColor == 0)
      {
         potentialColor = nbDiffrentColors +1;
         ++nbDiffrentColors;
      }

      solution[v-1] = potentialColor;
      ++amoutColorsUsed[potentialColor-1];
   }

   /**
    * Confirme si une couleur est un meilleur choix.
    * C'est à dire, est-elle moins utilisée que la couleur précédente trouvée.
    * @param color couleur à verifier.
    * @return Vrai si la couleur est préférable à utiliser que potentialColor.
    */
   abstract boolean isColorBetterThanPotential(int color);
}
