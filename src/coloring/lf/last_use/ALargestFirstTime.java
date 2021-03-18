/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.last_use.ALargestFirstTime.java
 Auteur(s)   : Herzig Melvyn
 Date        : 18.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.last_use;

import coloring.lf.ALargestFirst;

/**
 * Classe Abstraite qui défini les méthodes communes
 * pour les variantes de l'algorithmes LargestFirst Oldest et Newest
 * @author Herzig Melvyn
 * @date 18/03/2021
 */
public abstract class ALargestFirstTime extends ALargestFirst
{
   /**
    * Essaie d'ajouter la couleur color au sommet vertex
    * @param color Couleur à ajouter.
    * @param vertex Sommet cible.
    * @return vrai en cas de succès sinon false.
    */
   protected boolean tryAddColor(int color, int vertex)
   {
      //Si une couleur non adjacent est trouvée
      if(adjacentColors[color-1] != vertex)
      {
         solution[vertex-1] = color;

         // Mise à jour nombre de couleurs
         if( color > nbDiffrentColors)
            ++nbDiffrentColors;

         return true;
      }
      return false;
   }
}
