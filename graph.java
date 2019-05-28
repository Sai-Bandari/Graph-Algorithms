
//Implementation for graph from the cits2200 package
public class Graph
{
  private int[][] edgeMatrix;
  private int numberOfVertices;
  private boolean directed;
  private boolean weighted;
  
  private Graph(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramInt < 1) { this.numberOfVertices = 0; }
    else { this.numberOfVertices = paramInt; }
     this.edgeMatrix = new int[this.numberOfVertices][this.numberOfVertices];
    this.weighted = paramBoolean1;
    this.directed = paramBoolean2;
  }
  //Implementation of add edge from cits2200 package
  
  private void addEdge(int paramInt1, int paramInt2, int paramInt3) {
	    if (0 <= paramInt1 && 0 <= paramInt2 && paramInt1 <= this.numberOfVertices && paramInt2 <= this.numberOfVertices) {
	      if (paramInt3 < 1) { paramInt3 = 0; }
	      else if (!this.weighted) { paramInt3 = 1; }
	       this.edgeMatrix[paramInt1][paramInt2] = paramInt3;
	      if (!this.directed) this.edgeMatrix[paramInt2][paramInt1] = paramInt3; 
	    } else {
	      throw new RuntimeException("Vertex out of bounds");
	    } 
	  }