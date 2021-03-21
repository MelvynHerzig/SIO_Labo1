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
import java.util.Arrays;

/**
 * Classe Abstraite qui défini les méthodes communes aux différentes
 * variantes de l'algorithmes LargestFirst de Welsh et Powell.
 * @author Herzig Melvyn
 * @date 11/03/2021
 */
public abstract class ALargestFirst implements IGraphColorer
{
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
    * Définit la limite utilsée de adjacentColors.
    */
   protected int nbDiffrentColors;

   /**
    * Constructeur sans paramètre.
    */
   protected ALargestFirst()
   {
      solution = null;
      adjacentColors = null;
      nbDiffrentColors = 0;
   }

   /**
    * Crée le graphe coloré pour le graphe simple graph.
    * L'appel à setPossibleColor n'est pas effectué pour le premier
    * sommet.
    * @param g Graphe simple à colorer.
    * @return Retourne un GrapheColoring contenant la solution trouvée.
    */
   public GraphColoring color(Graph g)
   {
      init(g);
      ArrayList<ArrayList<Integer>> bucketsVerticesDegree = sortGraph(g);

      // Couleur du sommet adjacent;
      int adjacentColor;

      // Pour chaque bucket (depuis celui des plus grands degrés)
      for(int b = bucketsVerticesDegree.size() - 1; b >= 0; --b)
      {
         // Pour chaque sommet dans l'ordre lexicographique
         for(int v : bucketsVerticesDegree.get(b))
         {
            //Première itération, on passe la recherche
            if(nbDiffrentColors == 0)
            {
               solution[v-1] = 1;
               ++nbDiffrentColors;
               continue;
            }

            // Récupération des couleurs voisines
            for(int adjacentVertex : g.getAdjacencyList(v))
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
    * Trie le graphe g reçu en paramètre par ordre croissant des sommets
    * et selon l'ordre lexicographique.
    * @param g Graphe à trier.
    */
   private ArrayList<ArrayList<Integer>> sortGraph(Graph g)
   {
      ArrayList<ArrayList<Integer>> bucketsVerticesDegree = new ArrayList<>(g.getMaxDegree()+1);

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

      return bucketsVerticesDegree;
   }

   /**
    * Initialise les structures pour la méthode color.
    * @param g Graphe utilisé.
    */
   private void init(Graph g)
   {
      solution = new int[g.getNVertices()];
      adjacentColors = new int[g.getMaxDegree()+1];
      nbDiffrentColors = 0;
   }

   /**
    * Recherche et initialise la prochaine couleur pour v.
    * @param v Sommet à chercher la prochaine couleur.
    */
   protected abstract void setPossibleColor(int v);
}
