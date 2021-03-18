/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.last_use.LargestFirstOldest.java
 Auteur(s)   : Herzig Melvyn
 Date        : 11.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.last_use;

/**
 * Implémente la variante "oldest" de LargestFirst de Welsh et Powell.
 * Elle définit le parcours des couleurs possibles dans l'ordre croissant.
 * @author Herzig Melvyn
 * @date 13/03/2021
 */
public class LargestFirstOldest extends ALargestFirstTime
{

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   @Override
   protected void setPossibleColor(int v)
   {

      // De la première couleur jusqu'a une dernière pas encore utilisée.
      // Ainsi on est sur d'avoir au moins un résultat
      for(int color = 1; color <= nbDiffrentColors + 1; ++color)
      {
         if(tryAddColor(color, v))
            break;
      }
   }
}
