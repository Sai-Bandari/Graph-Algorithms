
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

				if (graphObject.getVertex(i).equals(urlTo) ) {
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
		String[] centers =  new String[nVertices];
		
		
		// Calculates the distance from a vertex to every other vertex using the shortest path 
		// algorithm that we have already implemented.
		for (int i = 0; i < nVertices; i++) {
			int lowestVal[] = new int[nVertices];
			for (int j = 0; j < nVertices; j++) {
				if ( i != j ) {
					lowestVal[j] = getShortestPath(graphObject.getVertex(i), graphObject.getVertex(j));
				}
				else {
					lowestVal[j] = INFINITY;
				}
			}
			
			// Calculates the average shortest path for a singular vertex 
			double Average = 0.0;
			double averageCount = 0.0;
			for (int k = 0; k < nVertices; k++ ) {
				if (lowestVal[k] != INFINITY) {
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
	public void DFSmethod(int k, boolean visited[], String[][]scc, int rowIndex, int loopCount) {
		// Mark the current node as visited and print it
		visited[k] = true;

		String page = graphObjectTransposed.getVertex(k);
		System.out.print(page + " ");

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = graphObjectTransposed.getAdjList(k).iterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n]) {
				DFSmethod(n, visited, scc, rowIndex++, loopCount);
			}
		}
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
	
	
	// Method for removing null from our 2d array
	public static String[][] removeNull( String[][] arr2d) {
        //
        ArrayList<ArrayList<String>> list2d = new ArrayList<ArrayList<String>>();
        //
        for(String[] arr1d: arr2d){
            ArrayList<String> list1d = new ArrayList<String>();
            for(String s: arr1d){
                if(s != null && s.length() > 0) {
                    list1d.add(s);
                }
            }
            // Removing empty 2d array
            if(list1d.size()>0){
                list2d.add(list1d);
            }
        }
        String[][] cleanArr = new String[list2d.size()][];
        int next = 0;
        for(ArrayList<String> list1d: list2d){
            cleanArr[next++] = list1d.toArray(new String[list1d.size()]);
        }
        return cleanArr;
    }

	public String[][] getStronglyConnectedComponents() {

		int nVertices = graphObject.getSize();

		int nVerticesTransposed = graphObjectTransposed.getSize();

		Stack stack = new Stack();
		
		String[][] scc = new String[nVertices+1][nVertices+1];

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
			int v = (int)stack.pop();

			// Print Strongly connected component of the popped vertex
			if (!visited[v]) {
				DFSmethod(v, visited, scc, rowIndex, loopCount);
				System.out.println();
			}
		}
		
		// Commented out another way to remove nulls from our array
//		for(int i = 0; i < scc.length; i++) {
//		    String[] inner = scc[i];
//		    List<String> list = new ArrayList<String>(inner.length);
//		    for(int j = 0; j < inner.length; j++){
//		        if(inner[j] != null){
//		            list.add(inner[j]);
//		        }
//		    }
//		    scc[i] = list.toArray(new String[list.size()]);
//		}
//		List<String[]> outerList = new ArrayList<String[]>(scc.length);
//		for(int i = 0; i < scc.length; i++) {
//			String[] inner = scc[i];
//		    if (inner != null) {
//		        List<String> list = new ArrayList<String>(inner.length);
//		        for(int j=0; j < inner.length; j++){
//		            if(inner[j] != null){
//		                list.add(inner[j]);
//		            }
//		        }
//		        outerList.add(list.toArray(new String[list.size()]));
//		    }
//		}
//		scc = outerList.toArray(new String[outerList.size()][]);
		
		return removeNull(scc);

	}

	public String[] getHamiltonianPath() {
		String z[] = new String[1];
		z[0] = "Hello";
		return z;
		
		// TODO:
//		int nVertices = graphObject.getSize();
//
//		int a = (int)Math.pow(2, nVertices);
//
//		// Initialising an emepty array that will egt populated by the path.
//		String[] pathArray = new String[nVertices];
//		for (int i = 0; i < nVertices; i++) {
//			pathArray[i] = "";
//		}
//
//		boolean visited[] = new boolean[nVertices];
//
//		int[][] dp = new int[a][nVertices];
//
//		for (int i = 0; i < a; i++) {
//			for (int j = 0; j < nVertices; j++) {
//				dp[i][j] = INFINITY;
//			}
//		}
//
//		for (int i = 0; i < nVertices; i++) {
//			dp[a][i] = 0;
//		}
//
//		for (int m = 0; m < a; m++) {
//			for (int i = 0; i < nVertices; i++) {
//				if ((m & a) != 0) {
//					for (int j = 0; j < nVertices; j++) {
//						if ((m & a) != 0) {
//							dp[m][i] = Math.min(dp[m][i],dp[m ^ (a)][j] + graphObject.getDist(j, i));
//						}
//					}
//				}
//			}
//		}
//		
//		int numberOfVerticesInPath = 0;
//		for (int i = 0; i < nVertices; i++) {
//			if (dp[a][numberOfVerticesInPath] < )
//		}
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
