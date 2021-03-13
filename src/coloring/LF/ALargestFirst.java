/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : ALargestFirst
 Auteur(s)   : Herzig Melvyn
 Date        : 11.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.LF;

import coloring.GraphColoring;
import coloring.IGraphColorer;
import graph.Graph;
import java.util.ArrayList;

/**
 * Classe Abstraite qui défini les méthodes communes aux différentes
 * variantes de l'algorithmes LargestFirst de Welsh et Powell.
 * @author Herzig Melvyn
 * @date 11/03/2021
 */
public abstract class ALargestFirst implements IGraphColorer
{
   /**
    * Buckets pour trier les sommets selon lors degrés
    */
   protected ArrayList<ArrayList<Integer>> buckets;

   /**
    * Couleur par sommet
    */
   protected int[] solution;

   /**
    * Tableau résumant le stockage des couleurs
    * Chaque index correspond à une couleur,
    * Si le sommet 4 touche les couleurs 1,2 elle alors les index 1 et 2
    * seront à 4.
    * correspond au dernier sommet avec cette couleur.
    */
   protected int[] colors;


   /**
    * Constructeur sans paramètre.
    */
   protected ALargestFirst()
   {
      buckets = null;
      solution = null;

      System.out.println(getPossibleColor());
   }

   /**
    * Trie le graphe g reçu en paramètre par ordre croissant des sommets
    * et selon l'ordre lexicographique.
    * @param g Graphe à trier.
    */
   protected void sortGraph(final Graph g)
   {
      // Initialisation des buckets entre 0 et N-1
      buckets = new ArrayList<>(g.getMaxDegree()+1);
      for(int i = 0; i < g.getMaxDegree()+1; ++i)
      {
         buckets.add(new ArrayList<>());
      }

      // La clé d'un sommet est son degré
      for(int v = 1; v <= g.getNVertices(); ++v)
      {
         int degree = g.getAdjacencyList(v).size();
         buckets.get(degree).add(v);
      }
   }

   /**
    * Initialise les structure pour la méthode color.
    * @param g Graphe utilisé dans solve.
    */
   private void init(final Graph g)
   {
      solution = new int[g.getNVertices()];
      colors = new int[g.getMaxDegree()+1];
   }

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
            for(int i = 0; i < colors.length; ++i)
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
         }
      }
      return new GraphColoring(nbColor, solution);
   }

   /**
    * Détermine laa prochaine couleur possible.
    * @return
    */
   protected abstract int getPossibleColor();
}
