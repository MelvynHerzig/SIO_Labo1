/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.amount_use.LargestFirstLeast.java
 Auteur(s)   : Herzig Melvyn
 Date        : 13.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.amout_use;

/**
 * Implémente la variante "most" de LargestFirst de Welsh et Powell.
 * Elle définit que les couleurs utilisées sont en prioritésles couleurs
 * qui sont déjà employées le moins.
 * @author Herzig Melvyn
 * @date 13/03/2021
 */
public class LargestFirstLeast extends ALargestFirstAmount
{
   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   @Override
   protected void setPossibleColor(int v)
   {
      //Parcours croissant des buckets depuis 1
      for(int i = 0; i < bucketsColorsUse.size() && solution[v-1] == 0; ++i)
      {
         for(int potentialColor : bucketsColorsUse.get((i + 1) % bucketsColorsUse.size()))
         {
            if(tryAddColor(potentialColor, v)) break;
         }
      }
   }
}
