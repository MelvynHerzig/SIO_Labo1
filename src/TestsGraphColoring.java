

// Résolution
import coloring.GraphColoringWriter;
import coloring.lf.last_use.LargestFirstNewest;

// Graph
import graph.*;

import java.io.IOException;


public class TestsGraphColoring
{
   public static void main(String[] args)
   {
      try
      {
         String graphPathName = "EX_SERIE1_4_a.txt";
         Graph g = GraphReader.fromFile(graphPathName);
         LargestFirstNewest lfo = new LargestFirstNewest();
         GraphColoringWriter.printSolution(lfo.color(g), System.out);
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
      }
      return;
   }
}
