import java.util
import java.util.ArrayList;
import java.util.LinkedList;*





public interface CITS2200Project {
	/**
	 * Adds an edge to the Wikipedia page graph. If the pages do not
	 * already exist in the graph, they will be added to the graph.
	 * 
	 * @param urlFrom the URL which has a link to urlTo.
	 * @param urlTo the URL which urlFrom has a link to.
	 */
	//We must first create a graph 

	class Graph{
		private int[][] edgeMatrix;
		private int numberOfVertices;


		private Graph(int paramInt) {
			if (paramInt < 1) {
				this.numberOfVertices = 0; 
			}

			else { 
				this.numberOfVertices = paramInt;
			}

			this.edgeMatrix = new int[this.numberOfVertices][this.numberOfVertices];
		}





		public void addEdge(String urlFrom, String urlTo) {
			if (0 <= paramInt1 && 0 <= paramInt2 && paramInt1 <= this.numberOfVertices && paramInt2 <= this.numberOfVertices) {
				if (paramInt3 < 1) { paramInt3 = 0; }
				else if (!this.weighted) { paramInt3 = 1; }
				this.edgeMatrix[paramInt1][paramInt2] = paramInt3;
				if (!this.directed) this.edgeMatrix[paramInt2][paramInt1] = paramInt3; 
			} else {
				throw new RuntimeException("Vertex out of bounds");
			} 



		}


		/**
		 * Finds the shorest path in number of links between two pages.
		 * If there is no path, returns -1.
		 * 
		 * @param urlFrom the URL where the path should start.
		 * @param urlTo the URL where the path should end.
		 * @return the legnth of the shorest path in number of links followed.
		 */

		public int getShortestPath(String urlFrom, String urlTo) {
			//Number of vertices in the graph is stored
			int nVertex = g.getNumberOfVertices();

			int[][] Weight = g.getEdgeMatrix();

			int[] dist = new int[nVertex];

			int[] travelled = new int[nVertex];


			Arrays.fill(travelled, 0);

			Arrays.fill(dist, -1);



			PriorityQueue<Edge> q = new PriorityQueue<Edge>();

			q.add((PathImp<E>.Edge)new Edge(source, 0));

			dist[source] = 0;

			while (!q.isEmpty()) {	

				int vertex = q.remove().vertex;

				if (travelled[vertex] != 0) {

					continue;

				}

				travelled[vertex] = 1;

				for (int i = 0; i < nVertex; i++) {

					int weight = Weight[vertex][i];

					if (weight != 0 && travelled[i] != 1) {

						if (dist[i] > dist[vertex] + weight || dist[i] == -1) {

							dist[i] = dist[vertex] + weight;

							q.add((PathImp<E>.Edge)new Edge(i,dist[i]));
						}
					}
				}
			}


			return dist;
		}
	}

	/**
	 * Finds all the centers of the page graph. The order of pages
	 * in the output does not matter. Any order is correct as long as
	 * all the centers are in the array, and no pages that aren't centers
	 * are in the array.
	 * 
	 * @return an array containing all the URLs that correspond to pages that are centers.
	 */
	public String[] getCenters();

	/**
	 * Finds all the strongly connected components of the page graph.
	 * Every strongly connected component can be represented as an array 
	 * containing the page URLs in the component. The return value is thus an array
	 * of strongly connected components. The order of elements in these arrays
	 * does not matter. Any output that contains all the strongly connected
	 * components is considered correct.
	 * 
	 * @return an array containing every strongly connected component.
	 */
	public String[][] getStronglyConnectedComponents();

	/**
	 * Finds a Hamiltonian path in the page graph. There may be many
	 * possible Hamiltonian paths. Any of these paths is a correct output.
	 * This method should never be called on a graph with more than 20
	 * vertices. If there is no Hamiltonian path, this method will
	 * return an empty array. The output array should contain the URLs of pages
	 * in a Hamiltonian path. The order matters, as the elements of the
	 * array represent this path in sequence. So the element [0] is the start
	 * of the path, and [1] is the next page, and so on.
	 * 
	 * @return a Hamiltonian path of the page graph.
	 */
	public String[] getHamiltonianPath();
}

