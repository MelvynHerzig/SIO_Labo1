/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : LargestFirstLeast
 Auteur(s)   : Herzig Melvyn
 Date        : 11.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.LF;

import coloring.GraphColoring;
import graph.Graph;

import java.util.ArrayList;

/**
 * Implémente la variante "least" de LargestFirst de Welsh et Powell.
 * @author Herzig Melvyn
 * @date 11/03/2021
 */
public class LargestFirstLeast extends ALargestFirst
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

      // Compteur d'utilisation des couleurs
      int[] colorsCounter = new int[colors.length];
      ArrayList<ArrayList<Integer>> colorsOrderedPerUse = new ArrayList<>(graph.getNVertices());
      for(int i  = 0; i < graph.getNVertices(); ++i)
      {
         colorsOrderedPerUse.add(new ArrayList<>());
      }

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
               ++colorsCounter[0];
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

            // Récupération de la couleur disponible.
            // Tri buckets des couleurs selon leur nombre d'occurence
            // La clé est donc le nombre d'occurence de la couleur.
            for(int i = 0; i <= nbColor; ++i)
            {
               colorsOrderedPerUse.get(colorsCounter[i]).add(i+1);
            }

            //Parcours des buckets depuis 1 et finalement 0 utilisations
            boolean colorFound = false;
            for(int i = 0; i < colorsOrderedPerUse.size() && !colorFound; ++i)
            {
               for(int potentialColor : colorsOrderedPerUse.get((i + 1) % colorsOrderedPerUse.size()))
               {
                  if(colors[potentialColor-1] < v)
                  {
                     solution[v-1] = potentialColor;
                     ++colorsCounter[potentialColor - 1];
                     colorFound = true;

                     if((i + 1) % colorsOrderedPerUse.size() == 0) ++nbColor;
                     break;
                  }
               }
            }

            // Réinitialisation des buckets
            for(ArrayList<Integer> al : colorsOrderedPerUse)
               al.clear();
         }
      }
      return new GraphColoring(nbColor, solution);
   }

   @Override
   protected int getPossibleColor()
   {
      return 1;
   }
}
