/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : LargestFirstMost
 Auteur(s)   : Herzig Melvyn
 Date        : 13.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.amout_use;

import coloring.lf.ALargestFirst;
import java.util.ArrayList;

/**
 * Implémente la variante "most" de LargestFirst de Welsh et Powell.
 * Elle définit que les couleurs utilisées sont en priorité les couleurs
 * qui sont déjà employées le plus.
 * @author Herzig Melvyn
 * @date 13/03/2021
 */
public class LargestFirstMost extends ALargestFirst
{

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   @Override
   protected void setPossibleColor(int v)
   {
      // Récupération de la couleur disponible.
      // Tri buckets des couleurs selon leur nombre d'occurence
      // La clé est donc le nombre d'occurence de la couleur.
      for(int i = 0; i <= nbColor; ++i)
      {
         colorsOrderedPerUse.get(colorsCounter[i]).add(i+1);
      }

      //Parcours des buckets depuis 1 et finalement 0 utilisations
      boolean colorFound = false;
      for(int i = colorsOrderedPerUse.size() - 1; i >= 0 && !colorFound; --i)
      {
         for(int potentialColor : colorsOrderedPerUse.get(i))
         {
            if(colors[potentialColor-1] < v)
            {
               solution[v-1] = potentialColor;
               ++colorsCounter[potentialColor - 1];
               colorFound = true;

               if(i == 0) ++nbColor;
               break;
            }
         }
      }

      // Réinitialisation des buckets
      for(ArrayList<Integer> al : colorsOrderedPerUse)
         al.clear();
   }
}