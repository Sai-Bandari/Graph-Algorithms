
// Seharsh Srivastava - 22248457
// Sai Prateek Bandari - 22230847

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//###############################################################################################

public class MyCits2200Project implements CITS2200Project {

	public Graph graphObject;
	public Graph graphObjectTransposed;

	public static final int INFINITY = Integer.MAX_VALUE / 2;

	public MyCITS2200Project() {
		graphObject = new Graph();
		graphObjectTransposed = new GraphTransposed();
	}

	public void addEdge(String urlFrom, String urlTo) {
		graphObject.Graph_addEdge(urlFrom, urlTo);
		graphObjectTransposed.Graph_addEdge(urlTo, urlFrom);
	}

	public int getShortestPath(String urlFrom, String urlTo) {
		// Size of adjacency list
		int nVertices = graphObject.getSize();

		// Initalising distance between urlFrom and urlTo
		int distanceFrom_To = -1;

		// Initialising whether the url's exist
		boolean foundUrlFrom = false;
		boolean foundUrlTo = false;

		// Initalising the index value of from
		int from = -1;

		// Checking to see if the arguements provided exist
		for (int i = 0; i < nVertices; i++) {
			if (urlFrom.equals(graphObject.getVertex(i))) {
				from = i;
				foundUrlFrom = true;
			}
			if (urlTo.equals(graphObject.getVertex(i))) {
				foundUrlTo = true;
			}

			// We have found that they exist hence stop searching
			if (foundUrlFrom && foundUrlTo) {
				break;
			}
		}

		// If any one of the url's provided do not exist then return -1
		if (!foundUrlFrom || !foundUrlTo) {
			return distanceFrom_To;
		}

		// If urlFrom = urlTo then distance is 0
		if (urlFrom == urlTo) {
			distanceFrom_To = 0;
			return distanceFrom_To;
		}

		Queue<Integer> queue = new LinkedList<Integer>();

		boolean visited[] = new boolean[nVertices];
		int distanceTo[] = new int[nVertices];

		// We have already visited ourselves
		visited[from] = true;

		queue.add(from);

		for (int i = 0; i < nVertices; i++) {
			distanceTo[i] = INFINITY;
		}
		// Distance to itself is 0
		distanceTo[from] = 0;

		// Standard Breadth First Search Algorithm
		while (!queue.isEmpty()) {

			int v = queue.poll();

			for (int i : graphObject.getAdjList(v)) {
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

	// A recursive function to print DFS starting from v
	public void DFSUtil(int k, boolean visited[]) {
		// Mark the current node as visited and print it
		visited[k] = true;
		System.out.print(k + " ");

		int n;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = graphObjectTransposed.getAdjList(j).iterator();
		while (i.hasNext()) {
			n = i.next();
			if (!visited[n]) {
				DFSUtil(n, visited);
			}
		}
	}

	public void fillOrder(int j, boolean visited[], Stack stack) {

		// Mark the current node as visited and print it
		visited[j] = true;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = graphObject.getAdjList(j).iterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n]) {
				fillOrder(n, visited, stack);
			}
		}

		// All vertices reachable from v are processed by now,
		// push v to Stack
		stack.push(new Integer(j));
	}

	public String[][] getStronglyConnectedComponents() {

		int nVertices = graphObject.getSize();

		int nVerticesTransposed = graphObjectTransposed.getSize();

		Stack stack = new Stack();

		boolean visited[] = new boolean[V];

		for (int i = 0; i < nVertices; i++) {
			visited[i] = false;
		}

		for (int i = 0; i < nVertices; i++) {
			if (!visited[i]) {
				fillOrder(i, visited, stack);
			}
		}

		for (int i = 0; i < nVerticesTransposed; i++) {
			visited[i] = false;
		}

		while (!stack.empty()) {
			// Pop a vertex from stack
			int v = (int) stack.pop();

			// Print Strongly connected component of the popped vertex
			if (!visited[v]) {
				DFSUtil(v, visited);
				System.out.println();
			}
		}

	}

	public String[] getHamiltonianPath() {

		int nVertices = graphObject.getSize();

		int a = (int) Math.pow(2, nVertices);

		// Initialising an emepty array that will egt populated by the path.
		String[] pathArray = new String[nVertices];
		for (int i = 0; i < nVertices; i++) {
			pathArray[i] = "";
		}

		boolean visited[] = new boolean[nVertices];

		int[][] dp = new int[a][nVertices];

		for (int i = 0; i < a; i++) {
			for (int j = 0; j < nVertices; j++) {
				dp[i][j] = INFINITY;
			}
		}

		for (int i = 0; i < n; i++) {
			dp[(int) Math.pow(2, i)][i] = 0;
		}

		for (int m = 0; m < a; m++) {
			for (int i = 0; i < nVertices; i++) {
				if ((m & (int) Math.pow(2, i)) != 0) {
					for (int j = 0; j < nVertices; j++) {
						if ((m & (int) Math.pow(2, j)) != 0) {
							dp[m][i] = Math.min(dp[m][i],
									dp[m ^ ((int) Math.pow(2, i))][j] + graphObject.getDist(j, i));
						}
					}
				}
			}
		}

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

class Graph {

	// Adjacency list of the edges in the graph
	// Basically a list of all Adjacent vertices
	public ArrayList<LinkedList<Integer>> adjList;

	// List of all the vertices
	public LinkedList<String> vertexRowList;

	private static final int INFINITY = Integer.MAX_VALUE / 2;

	public Graph() {
		adjList = new ArrayList<LinkedList<Integer>>();
		vertexRowList = new LinkedList<String>();
	}

	// Adds an edge to the Wikipedia page graph.
	// If the pages do not already exist in the graph, they will be added to the
	// graph.
	// Using Graph_urlFrom and Graph_urlTo to differentiate that this is in graph
	// class.
	// Purely to help with searching of variables.
	public void Graph_addEdge(String Graph_urlFrom, String Graph_urlTo) {

		// Setting locations / indice to -1 because any positive value would mean that
		// are in the list.

		// Initialising Location / Indice or the 'FROM' url
		int locationFrom = -1;

		// Initialising Location / Indice of the 'TO' url
		int locationTo = -1;

		// Checking if the page already exists in the adjcency list
		// Doing this by comparing if the first value of ever list is the same as input.
		for (int i = 0; i < vertexRowList.size(); i++) {
			if (vertexRowList.get(i).equals(Graph_urlFrom)) {
				locationFrom = i;
			}

			if (vertexRowList.get(i).equals(Graph_urlTo)) {
				locationTo = i;
			}

			// Neither url exits in the
			if (locationTo != -1 && locationFrom != -1) {
				break;
			}
		}

		// If the "from" vertex is not present in the graph, creates it
		if (locationFrom == -1) {
			// Adding the vertext to the list of vertices
			vertexRowList.add(Graph_urlFrom);

			// Appending to the array lift of linked lists
			LinkedList<Integer> fromList = new LinkedList<Integer>();
			locationFrom = adjList.size();
			fromList.add(locationFrom);
			adjList.add(fromList);
		}

		// If the "to" vertex is present in the graph, adds an edge from the "from"
		// vertex to it
		// else it creates the "to" vertex and then adds an edge from the "from" vertex
		// to it
		if (locationTo != -1) {
			adjList.get(locationFrom).add(locationTo);
		}

		else {
			LinkedList<Integer> toList = new LinkedList<Integer>();

			locationTo = adjList.size();
			toList.add(locationTo);
			adjList.get(locationFrom).add(locationTo);
			adjList.add(toList);
			vertexRowList.add(Graph_urlTo);
		}
	}

	// A method to return the index when the vertex is passed as a parameter,
	// if not present, returns -1
	public int getIndex(String pageName) {
		for (int i = 0; i < vertexRowList.size(); i++) {
			if (vertexRowList.get(i).equals(pageName)) {
				return i;
			}
		}
		return -1;
	}

	// A method that returns 1 if the parameter "b" is present in the row of the
	// adjacency list of the parent "a",
	// else it returns INFINITY
	public int getDist(int a, int b) {
		if (adjList.get(a).contains(b)) {
			return 1;
		}
		return INFINITY;
	}

	public String getVertex(int i) {
		return vertexRowList.get(i);
	}

	public LinkedList<Integer> getAdjList(int i) {
		return adjList.get(i);
	}

	public int getSize() {
		return vertexRowList.size();
	}
}

// #############################################################################################

class GraphTransposed {

	// Adjacency list of the edges in the graph
	// Basically a list of all Adjacent vertices
	public ArrayList<LinkedList<Integer>> adjList;

	// List of all the vertices
	public LinkedList<String> vertexRowList;

	private static final int INFINITY = Integer.MAX_VALUE / 2;

	public Graph() {
		adjList = new ArrayList<LinkedList<Integer>>();
		vertexRowList = new LinkedList<String>();
	}

	// Adds an edge to the Wikipedia page graph.
	// If the pages do not already exist in the graph, they will be added to the
	// graph.
	// Using Graph_urlFrom and Graph_urlTo to differentiate that this is in graph
	// class.
	// Purely to help with searching of variables.
	public void Graph_addEdge(String Graph_urlFrom, String Graph_urlTo) {

		// Setting locations / indice to -1 because any positive value would mean that
		// are in the list.

		// Initialising Location / Indice or the 'FROM' url
		int locationFrom = -1;

		// Initialising Location / Indice of the 'TO' url
		int locationTo = -1;

		// Checking if the page already exists in the adjcency list
		// Doing this by comparing if the first value of ever list is the same as input.
		for (int i = 0; i < vertexRowList.size(); i++) {
			if (vertexRowList.get(i).equals(Graph_urlFrom)) {
				locationFrom = i;
			}

			if (vertexRowList.get(i).equals(Graph_urlTo)) {
				locationTo = i;
			}

			// Neither url exits in the
			if (locationTo != -1 && locationFrom != -1) {
				break;
			}
		}

		// If the "from" vertex is not present in the graph, creates it
		if (locationFrom == -1) {
			// Adding the vertext to the list of vertices
			vertexRowList.add(Graph_urlFrom);

			// Appending to the array lift of linked lists
			LinkedList<Integer> fromList = new LinkedList<Integer>();
			locationFrom = adjList.size();
			fromList.add(locationFrom);
			adjList.add(fromList);
		}

		// If the "to" vertex is present in the graph, adds an edge from the "from"
		// vertex to it
		// else it creates the "to" vertex and then adds an edge from the "from" vertex
		// to it
		if (locationTo != -1) {
			adjList.get(locationFrom).add(locationTo);
		}

		else {
			LinkedList<Integer> toList = new LinkedList<Integer>();

			locationTo = adjList.size();
			toList.add(locationTo);
			adjList.get(locationFrom).add(locationTo);
			adjList.add(toList);
			vertexRowList.add(Graph_urlTo);
		}
	}

	// A method to return the index when the vertex is passed as a parameter,
	// if not present, returns -1
	public int getIndex(String pageName) {
		for (int i = 0; i < vertexRowList.size(); i++) {
			if (vertexRowList.get(i).equals(pageName)) {
				return i;
			}
		}
		return -1;
	}

	// A method that returns 1 if the parameter "b" is present in the row of the
	// adjacency list of the parent "a",
	// else it returns INFINITY
	public int getDist(int a, int b) {
		if (adjList.get(a).contains(b)) {
			return 1;
		}
		return INFINITY;
	}

	public String getVertex(int i) {
		return vertexRowList.get(i);
	}

	public LinkedList<Integer> getAdjList(int i) {
		return adjList.get(i);
	}

	public int getSize() {
		return vertexRowList.size();
	}
}