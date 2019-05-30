
// Seharsh Srivastava - 22248457
// Sai Prateek Bandari - 22230847

import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {

	// Adjacency list of the edges in the graph
	// Basically a list of all Adjacent vertices
	public ArrayList<LinkedList<Integer>> listOfEdges;

	// List of all the vertices
	public LinkedList<String> listOfVertices;

	
	private static final int INFINITY = Integer.MAX_VALUE;

	public Graph() {
		listOfEdges = new ArrayList<LinkedList<Integer>>();
		listOfVertices = new LinkedList<String>();
	}

	// Adds an edge to the Wikipedia page graph.
	// If the pages do not already exist in the graph, they will be added to the graph.
	// Using Graph_urlFrom and Graph_urlTo to differentiate that this is in graph class.
	// Purely to help with searching of variables.
	public void Graph_addEdge(String Graph_urlFrom, String Graph_urlTo) {

		// Setting locations / indice to -1 because any positive value would mean that
		// are in the list.
		
		// Initialising Location / Indice or the 'FROM' url
		int locationFrom = -1;

		// Initialising Location / Indice of the 'TO' url
		int locationTo = -1;

		for (int i = 0; i < listOfVertices.size(); i++) {
			if (listOfVertices.get(i).equals(Graph_urlFrom)) {
				locationFrom = i;
			}

			if (listOfVertices.get(i).equals(Graph_urlTo)) {
				locationTo = i;
			}

			// The location of both urls have been found.
			// No need to continue looking.
			if (locationTo != -1 && locationFrom != -1) {
				break;
			}
		}

		// If the "from" vertex is not present in the graph, creates it
		if (locationFrom == -1) {
			// Adding the vertext to the list of vertices
			listOfVertices.add(Graph_urlFrom);

			// Appending to the array lift of linked lists
			LinkedList<Integer> fromList = new LinkedList<Integer>();
			locationFrom = listOfEdges.size();
			fromList.add(locationFrom);
			listOfEdges.add(fromList);
		}

		// If the "to" vertex is present in the graph, adds an edge from the "from"
		// vertex to it
		// else it creates the "to" vertex and then adds an edge from the "from" vertex
		// to it
		if (locationTo != -1) {
			listOfEdges.get(locationFrom).add(locationTo);
		}

		else {
			LinkedList<Integer> toList = new LinkedList<Integer>();

			locationTo = listOfEdges.size();
			toList.add(locationTo);
			listOfEdges.get(locationFrom).add(locationTo);
			listOfEdges.add(toList);
			listOfVertices.add(Graph_urlTo);
		}
	}

	// A method to return the index when the vertex is passed as a parameter,
	// if not present, returns -1
	public int getIndexOf(String str) {
		for (int i = 0; i < listOfVertices.size(); i++)
			if (listOfVertices.get(i).equals(str))
				return i;
		return -1;
	}

	// A method that returns 1 if the parameter "b" is present in the row of the
	// adjacency list of the parent "a",
	// else it returns INFINITY
	public int getdist(int a, int b) {
		if (listOfEdges.get(a).contains(b))
			return 1;
		return INFINITY;
	}

	public String getVertex(int i) {
		return listOfVertices.get(i);
	}

	// ArrayList<LinkedList<Integer>> getEdge() //
	// { return p; }

	public LinkedList<Integer> getRow(int i) {
		return listOfEdges.get(i);
	}

	public int getsize() {
		return listOfVertices.size();
	}

	public static Graph transpose(Graph G) {
		Graph RG = new Graph();

		for (int i = 0; i < G.size(); i++) {
			LinkedList<Integer> temp = new LinkedList<Integer>();

			temp.add(i);
			RG.listOfEdges.add(temp);
			RG.listOfVertices.add(G.listOfVertices.get(i));
		}

		for (LinkedList<Integer> x : G.listOfEdges)
			for (int child : x)
				if (child != x.get(0))
					RG.Graph_addEdge(G.getVertex(child), G.getVertex(x.get(0)));

		return RG;
	}
}

//###############################################################################################

public class MyCits2200Project implements CITS2200Project {

	private Graph graphObject;

	private static final int INFINITY = Integer.MAX_VALUE;

	public MyCITS2200Project() {
		graphObject = new Graph();
	}

	public void addEdge(String urlFrom, String urlTo) {
		graphObject.Graph_addEdge(urlFrom, urlTo);
	}

	public int getShortestPath(String urlFrom, String urlTo) {
		// Size of adjacency list
		int nVertices = graphObject.getsize();

		// Initalising distance between urlFrom and urlTo
		int distanceFrom_To = -1;

		// Initialising whether the url's exist
		boolean foundUrlFrom = false;
		boolean foundUrlTo = false;

		int from = -1;

		// Checking to see if the arguements provided exist
		for (int i = 0; i < nVertices; i++) {
			if (urlFrom.eqquals(graphObject.getVertex(i))) {
				foundUrlFrom = true;
			}
			if (urlTo.equals(graphObject.getVertex(i))) {
				from = i;
				foundUrlTo = true;
			}

			// We have found that they exist hence stop searching
			if(foundUrlFrom && foundUrlTo) {
				break;
			}
		}

		// If any one of the url's provided do not exist then return -1
		if (!foundUrlFrom || !foundUrlTo) {
			return -1;
		}

		if (urlFrom == urlTo) {
			distanceFrom_To = 0;
			return distanceFrom_To;
		}

		Queue<Integer> queue = new LinkedList<Integer>();
		boolean visited[] = new boolean[nVertices];
		int distanceTo[] = new int[nVertices];

		distanceTo[from] = 0;
		visited[from] = true;

		queue.add(from);

		for (int i = 0; i < nVertices; i++) {
			distanceTo[i] = INFINITY;
		}

		while (!queue.isEmpty()) {

			int v = queue.poll();

			for (int i : graphObject.getRow(v)) {
				if (!visited[i]) {
					distanceTo[i] = distanceTo[i] + 1;
					visited[i] = true;
					queue.add(i);
				}

				if (graphObject.getVertex(i).equals(urlTO)) {
					distanceFrom_To = distanceTo[i];
					return distanceFrom_To;
				}
			}
		}
		return distanceFrom_To;
	}

	public String[] getCenters() {
	}

	public String[][] getStronglyConnectedComponents() {
	}

	public String[] getHamiltonianPath() {

		nVertices = graphObject.getsize();
		String[] pathArray = new String[nVertices];

		boolean visited[] = new boolean[nVertices];

	}
}

public interface CITS2200Project {

	/**
	 * Adds an edge to the Wikipedia page graph. If the pages do not already exist
	 * in the graph, they will be added to the graph.
	 * 
	 * @param urlFrom the URL which has a link to urlTo.
	 * @param urlTo   the URL which urlFrom has a link to.
	 */
	public void addEdge(String urlFrom, String urlTo);

	/**
	 * Finds the shorest path in number of links between two pages. If there is no
	 * path, returns -1.
	 * 
	 * @param urlFrom the URL where the path should start.
	 * @param urlTo   the URL where the path should end.
	 * @return the legnth of the shorest path in number of links followed.
	 */
	public int getShortestPath(String urlFrom, String urlTo);

	/**
	 * Finds all the centers of the page graph. The order of pages in the output
	 * does not matter. Any order is correct as long as all the centers are in the
	 * array, and no pages that aren't centers are in the array.
	 * 
	 * @return an array containing all the URLs that correspond to pages that are
	 *         centers.
	 */
	public String[] getCenters();

	/**
	 * Finds all the strongly connected components of the page graph. Every strongly
	 * connected component can be represented as an array containing the page URLs
	 * in the component. The return value is thus an array of strongly connected
	 * components. The order of elements in these arrays does not matter. Any output
	 * that contains all the strongly connected components is considered correct.
	 * 
	 * @return an array containing every strongly connected component.
	 */
	public String[][] getStronglyConnectedComponents();

	/**
	 * Finds a Hamiltonian path in the page graph. There may be many possible
	 * Hamiltonian paths. Any of these paths is a correct output. This method should
	 * never be called on a graph with more than 20 vertices. If there is no
	 * Hamiltonian path, this method will return an empty array. The output array
	 * should contain the URLs of pages in a Hamiltonian path. The order matters, as
	 * the elements of the array represent this path in sequence. So the element [0]
	 * is the start of the path, and [1] is the next page, and so on.
	 * 
	 * @return a Hamiltonian path of the page graph.
	 */
	public String[] getHamiltonianPath();
}