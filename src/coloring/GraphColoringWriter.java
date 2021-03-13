package coloring;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Henrik Akesson
 */
public final class GraphColoringWriter
{
   private GraphColoringWriter()
   {
   }

   /**
    * Print a {@link GraphColoring} to a file.
    *
    * @param coloring Coloring solution to write.
    * @param filename Name of file to write solution to.
    * @throws IOException If anything goes wrong while writing.
    */
   public static void printSolutionToFile(final GraphColoring coloring, final String filename) throws IOException
   {
      printSolution(coloring, new FileOutputStream(filename));
   }

   /**
    * Print a {@link GraphColoring} solution to a given output stream.
    *
    * @param graphColoring Coloring solution to write.
    * @param outputStream  {@link OutputStream} to write solution to.
    * @throws IOException If anything goes wrong while writing.
    */
   public static void printSolution(final GraphColoring graphColoring,
                                    final OutputStream outputStream) throws IOException
   {
      try (OutputStreamWriter writer = new OutputStreamWriter(outputStream))
      {
         writer.write(String.format("%d %d%n", graphColoring.getSolution().length, graphColoring.getNumberOfColors()));
         final int[] solution = graphColoring.getSolution();
         for (int i = 0; i < solution.length; i++)
         {
            writer.write(String.format("%d %d%n", i + 1, solution[i]));
         }
      }
   }

}
