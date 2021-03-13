/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : LargestFirstOldest
 Auteur(s)   : Herzig Melvyn
 Date        : 11.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.last_use;

import coloring.lf.ALargestFirst;

/**
 * Implémente la variante "oldest" de LargestFirst de Welsh et Powell.
 * Elle définit le parcours des couleurs possibles dans l'ordre croissant.
 * @author Herzig Melvyn
 * @date 13/03/2021
 */
public class LargestFirstOldest extends ALargestFirst
{

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   @Override
   protected void setPossibleColor(int v)
   {

      for(int i = 0; i < adjacentColors.length; ++i)
      {
         //Si une couleur non adjacent est trouvée
         if(adjacentColors[i] < v)
         {
            solution[v-1] = i + 1;
            // Mise à jour nombre de couleurs
            if( solution[v-1] > nbDiffrentColors) nbDiffrentColors = solution[v-1];
            break;
         }
      }

      // Si aucune couleur n'a été trouvée, ajout d'une nouvelle
      if(solution[v-1] == 0)
         solution[v-1] = ++nbDiffrentColors;
   }
}
