/*
 -----------------------------------------------------------------------------------
 Cours       : Simultation et optimisation (SIO) Labo 1
 Fichier     : coloring.lf.ALargestFirst.java
 Auteur(s)   : Herzig Melvyn
 Date        : 13.03.2021
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
   protected ArrayList<ArrayList<Integer>> bucketsVerticesDegree;

   /**
    * Couleurs par sommet
    */
   protected int[] solution;

   /**
    * Tableau résumant le stockage des couleurs
    * Chaque index + 1 correspond au numéro des couleurs.
    * Si le sommet 4 touche les couleurs 1,2 elle alors les index 0 et 1
    * seront à 4.
    */
   protected int[] adjacentColors;

   /**
    * Nombre de couleurs utilisées jusqu'a présent.
    * Définit la limite utilse de colors.
    */
   protected int nbDiffrentColors;

   /**
    * Constructeur sans paramètre.
    */
   protected ALargestFirst()
   {
      bucketsVerticesDegree = null;
      solution = null;
      adjacentColors = null;
      nbDiffrentColors = 0;
   }

   /**
    * Trie le graphe g reçu en paramètre par ordre croissant des sommets
    * et selon l'ordre lexicographique.
    * @param g Graphe à trier.
    */
   private void sortGraph(final Graph g)
   {
      // Initialisation des buckets entre 0 et N-1
      for(int i = 0; i < g.getMaxDegree()+1; ++i)
      {
         bucketsVerticesDegree.add(new ArrayList<>());
      }

      // La clé d'un sommet est son degré
      for(int v = 1; v <= g.getNVertices(); ++v)
      {
         int degree = g.getAdjacencyList(v).size();
         bucketsVerticesDegree.get(degree).add(v);
      }
   }

   /**
    * Initialise les structures pour la méthode color.
    * @param g Graphe utilisé.
    */
   private void init(final Graph g)
   {
      bucketsVerticesDegree = new ArrayList<>(g.getMaxDegree()+1);
      solution = new int[g.getNVertices()];
      adjacentColors = new int[g.getMaxDegree()+1];
      nbDiffrentColors = 0;
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

      // Couleur du sommet adjacent;
      int adjacentColor;

      // Pour chaque bucket (depuis celui des plus grands degrés)
      for(int b = bucketsVerticesDegree.size() - 1; b >= 0; --b)
      {
         // Pour chaque sommet dans l'ordre lexicographique
         for(int v : bucketsVerticesDegree.get(b))
         {
            // Récupération des couleurs voisines
            for(int adjacentVertex : graph.getAdjacencyList(v))
            {
               //Récupération de la couleurs du voisin.
               adjacentColor = solution[adjacentVertex-1];
               //Si il a une couleur
               if(adjacentColor > 0)
               {
                  adjacentColors[adjacentColor-1] = v;
               }
            }
            // Cherche et initialise les différentes variables en fonction de
            // l'agorithme utilisé.
            setPossibleColor(v);
         }
      }
      return new GraphColoring(nbDiffrentColors, solution);
   }

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   protected abstract void setPossibleColor(int v);
}
