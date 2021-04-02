/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.last_use.LargestFirstNewest.java
 Auteur(s)   : Herzig Melvyn
 Date        : 11.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.last_use;

/**
 * Implémente la variante "newest" de LargestFirst de Welsh et Powell.
 * Elle définit le parcours des couleurs possibles dans l'ordre décroissant.
 * @author Herzig Melvyn
 * @date 13/03/2021
 */
public class LargestFirstNewest extends ALargestFirstTime
{
   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   @Override
   protected void setPossibleColor(int v)
   {
      for(int offset = 1; offset <= nbDiffrentColors + 1; ++offset)
      {
         // On parcourt les couleurs de la dernière à la première puis la (dernière + 1)
         int color = (nbDiffrentColors + 1) - (offset % (nbDiffrentColors + 1));

         if(tryAddColor(color, v))
            break;
      }
   }
}
