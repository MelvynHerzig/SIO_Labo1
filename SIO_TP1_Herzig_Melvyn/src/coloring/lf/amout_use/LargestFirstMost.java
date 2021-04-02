/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.amount_use.LargestFirstMost.java
 Auteur(s)   : Herzig Melvyn
 Date        : 13.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf.amout_use;

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
    * Confirme si une couleur est un meilleur choix.
    * C'est à dire, est-elle plus utilisée que la couleur précédente trouvée.
    * @param color couleur à verifier.
    * @return Vrai si la couleur est plus utilisée que potentialColor.
    */
   boolean isColorBetterThanPotential(int color)
   {
      return amoutColorsUsed[potentialColor - 1] < amoutColorsUsed[color - 1];
   }
}
