/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : ALargestFirst
 Auteur(s)   : Herzig Melvyn
 Date        : 11.03.2021
 -----------------------------------------------------------------------------------
 */

package coloring.lf;

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
    * Buckets pour trier les couleurs selon leur nombre d'utilisation.
    */
   protected ArrayList<ArrayList<Integer>> colorsOrderedPerUse;

   /**
    * Couleur par sommet
    */
   protected int[] solution;

   /**
    * Tableau résumant le stockage des couleurs
    * Chaque index correspond à une couleur,
    * Si le sommet 4 touche les couleurs 1,2 elle alors les index 1 et 2
    * seront à 4.
    */
   protected int[] colors;
   /**
     * Nombre de couleurs utilisées. Définit la limite de colors
     */
   protected int nbColor;

   /**
    * Compte pour chaque couleur son nombre d'occurence
    */
   protected int[] colorsCounter;

   /**
    * Constructeur sans paramètre.
    */
   protected ALargestFirst()
   {
      buckets = null;
      solution = null;
   }

   /**
    * Trie le graphe g reçu en paramètre par ordre croissant des sommets
    * et selon l'ordre lexicographique.
    * @param g Graphe à trier.
    */
   private void sortGraph(final Graph g)
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

      // Nombre de couleurs utilisées
      nbColor = 0;

      // Compteur d'utilisation des couleurs
      colorsCounter = new int[colors.length];
      colorsOrderedPerUse = new ArrayList<>(g.getNVertices());
      for(int i  = 0; i < g.getNVertices(); ++i)
      {
         colorsOrderedPerUse.add(new ArrayList<>());
      }
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
            // Cherche et initialise les différente variables en fonction de
            // l'agorithme utilisé.
            setPossibleColor(v);

         }
      }
      return new GraphColoring(nbColor, solution);
   }

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   protected abstract void setPossibleColor(int v);
}
