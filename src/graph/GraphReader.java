package graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Henrik Akesson
 */
public final class GraphReader
{
   /**
    * Error message used if number of lines does not match number of edges.
    */
   private static final String ILLEGAL_N_LINES = "Illegal number of lines (%d lines, expected %d)";
   /**
    * Error message used if a line contains an insufficient number of tokens.
    */
   private static final String ILLEGAL_N_TOKENS = "Invalid number of tokens in line (got %d, expected %d)";
   /**
    * Error message used if an edge is declared with an invalid vertex id.
    */
   private static final String INVALID_VERTEX_ID = "Invalid vertex id (got %d, should be in [%d, %d])";

   private GraphReader()
   {
   }

   private static boolean notInRange(final int value, final int max)
   {
      return value > max || value < 1;
   }

   public static Graph fromInputStreamReader(final InputStreamReader inputStreamReader) throws IOException
   {
      try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
      {
         String line = bufferedReader.readLine();
         if (line == null)
         {
            return null;
         }
         String[] tokens = line.split(" ");
         final Graph graph = new Graph(Integer.parseInt(tokens[0]));
         final int nEdges = Integer.parseInt(tokens[1]);
         for (int i = 0; i < nEdges; i++)
         {
            line = bufferedReader.readLine();
            if (line == null)
            {
               throw new IllegalArgumentException(String.format(ILLEGAL_N_LINES, i - 1, nEdges));
            }
            tokens = line.split(" ");
            if (tokens.length != 2)
            {
               throw new IllegalArgumentException(String.format(ILLEGAL_N_TOKENS, 2, tokens.length));
            }
            final int from = Integer.parseInt(tokens[0]);
            final int to = Integer.parseInt(tokens[1]);
            if (notInRange(from, nEdges))
            {
               throw new IllegalArgumentException(String.format(INVALID_VERTEX_ID, from, 1, nEdges));
            }
            if (notInRange(to, nEdges))
            {
               throw new IllegalArgumentException(String.format(INVALID_VERTEX_ID, to, 1, nEdges));
            }
            graph.addEdge(from, to);
         }
         line = bufferedReader.readLine();
         if (line != null)
         {
            throw new IllegalArgumentException(String.format(ILLEGAL_N_LINES, nEdges + 2, nEdges + 1));
         }
         return graph;
      }
   }

   public static Graph fromFile(final String filename) throws IOException
   {
      return fromInputStreamReader(new FileReader(filename));
   }

}
