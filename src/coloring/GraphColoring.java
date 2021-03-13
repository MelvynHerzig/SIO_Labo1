package coloring;

public class GraphColoring {
  /** Number of used colors in solution. */
  private final int numberOfColors;
  /** Color by vertex. */
  private final int[] solution;

  /**
   * Create an instance of GraphColoring.
   *
   * @param numberOfColors Number of colors used in solution.
   * @param solution Array containing colors used by vertex (solution[0] contains the color given to vertex 1 etc).
   */
  public GraphColoring(final int numberOfColors, final int[] solution) {
    this.numberOfColors = numberOfColors;
    this.solution = solution;
  }

  /** @return Number of colors in solution. */
  public final int getNumberOfColors() {
    return numberOfColors;
  }

  /** @return Color by vertex. */
  public final int[] getSolution() {
    return solution;
  }

}
