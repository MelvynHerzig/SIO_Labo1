

// Résolution
import coloring.GraphColoringWriter;
import coloring.LF.LargestFirstLeast;
import coloring.LF.LargestFirstMost;
import coloring.LF.LargestFirstNewest;
import coloring.LF.LargestFirstOldest;

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
         LargestFirstMost lfo = new LargestFirstMost();
         GraphColoringWriter.printSolution(lfo.color(g), System.out);
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
      }
      return;
   }
}
