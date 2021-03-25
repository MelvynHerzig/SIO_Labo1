/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.amount_use.LargestFirstMost.java
 Auteur(s)   : Herzig Melvyn
 Date        : 13.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.amout_use;

import java.util.ArrayList;

/**
 * Implémente la variante "most" de LargestFirst de Welsh et Powell.
 * Elle définit que les couleurs utilisées sont en priorité les couleurs
 * qui sont déjà employées le plus.
 * @author Herzig Melvyn
 * @date 13/03/2021
 */
public class LargestFirstMost extends ALargestFirstAmount
{

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   @Override
   protected void setPossibleColor(int v)
   {
      //Parcours décroissant des buckets.
      for(int i = mostUsedAmount; i >= 0 && solution[v-1] == 0; --i)
      {
         for(int potentialColor : bucketsColorsUse.get(i))
         {
            if(tryAddColor(potentialColor, i, v)) break;
         }
      }
   }
}
