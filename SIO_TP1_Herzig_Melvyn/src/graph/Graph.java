package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Henrik Akesson
 */
public final class Graph
{
   /**
    * Adjacency lists.
    */
   private final List<List<Integer>> adjacencyLists;
   /**
    * Number of edges.
    */
   private int nEdges;
   /**
    * Max degree of graph.
    */
   private int maxDegree;

   /**
    * Constructs a new graph with given number of vertices.
    *
    * @param vertices Number of vertices of the graph.
    */
   public Graph(final int vertices)
   {
      if (vertices < 1)
      {
         throw new IllegalArgumentException("Invalid number of vertices, must be >= 1");
      }
      adjacencyLists = new ArrayList<>();
      for (int i = 0; i < vertices; i++)
      {
         adjacencyLists.add(new ArrayList<>());
      }
   }

   /**
    * @return Number of vertices.
    */
   public int getNVertices()
   {
      return adjacencyLists.size();
   }

   /**
    * Add an edge between vertices with given ids. Indices must be between 1 and nEdges and must be different.
    *
    * @param index1 Id of first vertex.
    * @param index2 Id of second vertex.
    */
   public void addEdge(final int index1, final int index2)
   {
      if (index1 < 1 || index1 > adjacencyLists.size() || index2 < 1 || index2 > adjacencyLists.size())
      {
         throw new IllegalArgumentException("Invalid indices");
      }
      if (index1 == index2)
      {
         throw new IllegalArgumentException("Cannot have an edge from and to the same vertex");
      }
      final List<Integer> adjacencyList1 = adjacencyLists.get(index1 - 1);
      final List<Integer> adjacencyList2 = adjacencyLists.get(index2 - 1);
      adjacencyList1.add(index2);
      adjacencyList2.add(index1);
      if (adjacencyList1.size() > maxDegree)
      {
         maxDegree = adjacencyList1.size();
      }
      if (adjacencyList2.size() > maxDegree)
      {
         maxDegree = adjacencyList2.size();
      }
      nEdges++;
   }

   /**
    * @return Current number of edges of this graph.
    */
   public int getNEdges()
   {
      return nEdges;
   }

   /**
    * @return Maximum degree of graph.
    */
   public int getMaxDegree()
   {
      return maxDegree;
   }

   /**
    * @param vertex Vertex index.
    * @return Adjacency list of given vertex.
    */
   public List<Integer> getAdjacencyList(final int vertex)
   {
      return adjacencyLists.get(vertex - 1);
   }

}
