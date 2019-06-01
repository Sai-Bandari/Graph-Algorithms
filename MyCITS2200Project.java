
// Seharsh Srivastava - 22248457
// Sai Prateek Bandari - 22230847

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

//###############################################################################################

public class MyCITS2200Project implements CITS2200Project {

	public Graph graphObject;
	public GraphTransposed graphObjectTransposed;

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
					distanceTo[i] = distanceTo[v] + 1;
					visited[i] = true;
					queue.add(i);
				}

				if (graphObject.getVertex(i).equals(urlTo)) {
					queue.clear();
					distanceFrom_To = distanceTo[i];
					return distanceFrom_To;
				}
			}
		}
		return distanceFrom_To;
	}

	// Method for creating graph centers
	
	
	public String[] getCenters() {

		// Number of vertives
		int nVertices = graphObject.getSize();

		// A array that holds the average shortest path for each vertex
		double[] minEcc = new double[nVertices];

		// An array that holds the values for pages that are the most eccentric
		String[] centers = new String[nVertices];

		// Calculates the distance from a vertex to every other vertex using the
		// shortest path
		// algorithm that we have already implemented.
		for (int i = 0; i < nVertices; i++) {
			int lowestVal[] = new int[nVertices];
			for (int j = 0; j < nVertices; j++) {
				if (i != j) {
					lowestVal[j] = getShortestPath(graphObject.getVertex(i), graphObject.getVertex(j));
				} else {
					lowestVal[j] = INFINITY;
				}
			}

			// Calculates the average shortest path for a singular vertex
			double Average = 0.0;
			double averageCount = 0.0;
			for (int k = 0; k < nVertices; k++) {
				if (lowestVal[k] != INFINITY && lowestVal[k] != -1 && lowestVal[k] != 0) {
					Average += lowestVal[k];
					averageCount++;
				}
			}
			minEcc[i] = (Average / averageCount);
		}

		// After calculating the averages we then looked for the smallest average value
		// And though of it to be the most eccentric
		for (int i = 0; i < nVertices; i++) {
			double newMinimumVal = minEcc[0];
			if (minEcc[i] == newMinimumVal) {
				centers[i] = graphObject.getVertex(i);
			}
			if (minEcc[i] < newMinimumVal) {
				for (int j = 0; j < minEcc.length; j++) {
					centers[j] = null;
				}
				centers[i] = graphObject.getVertex(i);
			}
		}

		// Since our array has been filled with null we cleaned it up
		int count = 0;
		for (int i = 0; i < nVertices; i++) {
			if (centers[i] != null) {
				count++;
			}
		}
		String actualCenters[] = new String[count];
		count = 0;
		for (int i = 0; i < nVertices; i++) {
			if (centers[i] != null) {
				actualCenters[count] = centers[i];
				count++;
			}
		}
		
				
		// returning the actual array with the page of the most eccentricity
		return actualCenters;

	}

	// A recursive function to print DFS starting from v
	public ArrayList<String> DFSmethod(int k, boolean visited[], ArrayList<String> sccc, int rowIndex, int loopCount) {
		// Mark the current node as visited and print it
		visited[k] = true;

		String page = graphObjectTransposed.getVertex(k);

		// Adding the page to an arraylist
		sccc.add(page);

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = graphObjectTransposed.getAdjList(k).iterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n]) {
				DFSmethod(n, visited, sccc, rowIndex++, loopCount);
			}
		}
		return sccc;
	}

	public void FO(int j, boolean visited[], Stack stack) {

		// Mark the current node as visited and print it
		visited[j] = true;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = graphObject.getAdjList(j).iterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n]) {
				FO(n, visited, stack);
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

		// An array list to store the array list of scc pages
		// This will later be coverted into an array
		ArrayList<ArrayList<String>> scc = new ArrayList<ArrayList<String>>();

		boolean visited[] = new boolean[nVertices];

		for (int i = 0; i < nVertices; i++) {
			visited[i] = false;
		}

		for (int i = 0; i < nVertices; i++) {
			if (!visited[i]) {
				FO(i, visited, stack);
			}
		}

		for (int i = 0; i < nVerticesTransposed; i++) {
			visited[i] = false;
		}

		int loopCount = -1;
		int rowIndex = 0;

		while (!stack.empty()) {
			loopCount++;

			// Pop a vertex from stack
			int v = (int) stack.pop();

			// Print Strongly connected component of the popped vertex
			if (!visited[v]) {
				ArrayList<String> sccc = new ArrayList<String>();
				sccc = DFSmethod(v, visited, sccc, rowIndex, loopCount);
				scc.add(sccc);
			}
		}

		// Connecting the arraylist to array
		String[][] array = new String[scc.size()][];
		for (int i = 0; i < scc.size(); i++) {
			ArrayList<String> row = scc.get(i);
			array[i] = row.toArray(new String[row.size()]);
		}

		// returning that array
		return array;

	}

	// Helper method that gets that works recoursiely to find the Hamiltonian path by using every vertex as
	// a starting point
	
	public String[] getHamPath(Graph g, int vertexV, boolean[] visited, List<Integer> path, int nVertices,
			String[] ham) {

		// If all the vertices are visited, then hamiltonian path exists
		if (path.size() == nVertices) {
			// print hamiltonian path
			for (int i : path) {
				System.out.println(g.getVertex(i));
				ham[i] = g.getVertex(i);
			}
			System.out.println();
			return ham;
		}

		// Check if every edge from vertex v leads to a solution
		for (int w : g.adjList.get(vertexV)) {
			// Process only unvisited vertices as hamiltonian path visits each vertex exactly once
			if (!visited[w]) {
				visited[w] = true;
				path.add(w);

				// Checking if adding vertex adjacent vertext lead to a hamiltonian path
				getHamPath(g, w, visited, path, nVertices, ham);

				// Backtracking
				visited[w] = false;
				path.remove(path.size() - 1);
			}
		}
		
		
		return ham;
	}
	
	

	public String[] getHamiltonianPath() {

		// Starting vertex
		int start = 0;

		int nVertices = graphObject.getSize();

		// Add the starting vertex to an arraylist
		List<Integer> path = new ArrayList<>();
		path.add(start);

		// mark start node as visited
		boolean[] visited = new boolean[nVertices];
		visited[start] = true;

		String[] ham = new String[nVertices];

		getHamPath(graphObject, start, visited, path, nVertices, ham);

		for (String i : ham) {
			if (i == null) {
				return new String[0];
			}
		}
		

		return ham;

	}
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

	public GraphTransposed() {
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