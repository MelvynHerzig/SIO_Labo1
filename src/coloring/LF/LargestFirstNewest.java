/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : LF.LargestFirstNewest
 Auteur(s)   : Herzig Melvyn
 Date        : 11.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.LF;

import coloring.GraphColoring;
import graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémente la variante "oldest" de LargestFirst de Welsh et Powell.
 * @author Herzig Melvyn
 * @date 11/03/2021
 */
public class LargestFirstNewest extends ALargestFirst
{

   /**
    * Crée le graphe coloré pour le graphe simple graph.
    * @param graph Graphe simple à colorer.
    * @return Retourne un GrapheColoring contenant la solution trouvée.
    */
   public GraphColoring color(Graph graph)
   {
      init(graph);
      sortGraph(graph);

      // Couleur des sommets adjacents;
      int color;

      // Nombre de couleurs utilisées
      int nbColor = 0;

      // Pour chaque bucket (depuis celui des plus grands degrés)
      for(int b = buckets.size() - 1; b >= 0; --b)
      {
         // Pour chaque sommet dans l'ordre lexicographique
         for(int v : buckets.get(b))
         {
            //Premier sommet, vérifications inutles
            if(nbColor == 0)
            {
               solution[v-1] = ++nbColor;
               continue;
            }

            for(int adjacentVertex : graph.getAdjacencyList(v))
            {
               //Récupération de la couleurs du voisin.
               color = solution[adjacentVertex-1];
               //Si il a un couleur
               if(color > 0)
               {
                  colors[color-1] = v;
               }
            }
            // Récupération de la première couleure disponible
            int i;
            for (i = nbColor - 1; i >= 0; --i)
            {
               //Si une couleur non adjacent est trouvée
               if(colors[i] < v)
               {
                  solution[v-1] = i + 1;
                  // Mise à jour nombre de couleurs
                  if( solution[v-1] > nbColor) nbColor = solution[v-1];
                  break;
               }
            }
            // Si aucune couleur a été trouvée , on ajoute une nouvelle.
            if(i == -1 && colors[0] == v)
            {
               solution[v-1] = ++nbColor;
            }
         }
      }
      return new GraphColoring(nbColor, solution);
   }

   @Override
   protected int getPossibleColor()
   {
      return 5;
   }
}
