package travellingSalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import javax.sound.sampled.LineListener;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.GabowStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateUtils;
import GPSUtils.grid.GPSPolygonGrid;
import agent.Agent;
import mission.resolver.map.MapMissionStrategy;
import tree.Tree;
import tree.TreeNode;

public class PrimTSPSolution implements MapMissionStrategy{
	
	GPSPolygonGrid traversalGrid;
	GraphManager graphManager;
	

	public PrimTSPSolution(GPSPolygonGrid traversalGrid) {
		setTraversalGrid(traversalGrid);
		setGraphManager(new GraphManager(getTraversalGrid()));
	}
	
	
	// A utility function to find the vertex with minimum cost
    // value to add to the partially constructed mst, from the set of vertices not yet included in MST
    int getMinAddVertexToTreeCost(double addVertexToTreeCost[], Boolean verticesNotYetIncluded[], int V)
    {
        // Initialize min value
        double min = Double.MAX_VALUE;
        int min_index=-1;
 
        //find the vertex of least weight not yet included in the mst by 
        //iterating over all vertices not yet included in mst
        for (int v = 0; v < V; v++) {
        	//if the vertex has not yet been included in the mst and 
            if (verticesNotYetIncluded[v] == false && addVertexToTreeCost[v] < min)
            {
                min = addVertexToTreeCost[v];
                min_index = v;
            }
        }
        return min_index;
    }
    
    /**
     * Calculates the lower bound of the cost of the travelling salesman problem given a minimal 
     * spanning tree calculated by prim's algorithm.
     * 
     * Algorithm taken from here: https://www.geeksforgeeks.org/travelling-salesman-problem-set-2-approximate-using-mst/
     * 
     * Algorithm steps:
		1)	Let 1 be the starting and ending point for salesman.
		2)	Construct MST from with 1 as root using Prim’s Algorithm.
		3)	List vertices visited in preorder walk of the constructed MST and add 1 at the end.
     * @param graph is the graph of costs to travel from node row to node col
     * @param mst holds the parent of each node. To get the parent node (and edge) for node 5, 
     * compute mst[4]
     * @param size is the number of nodes in the graph and mst
     * @return
     */
    public double getMSTPrimLowerBoundCost(double[][] graph, int[] parent, int size, List<GPSCoordinate> vertices) {
    	double returnValue = 0.0;
    	//need to pre-order traverse mst
    	//first set up a childMap - a map that gives a list of children of the 
    	//vertex indexed i

    	//the list of integers represent the ith node in the list of vertices
    	Map<Integer, List<Integer>> childMap = getChildMap(parent, size);
    	Stack<Integer> preOrderStack = new Stack<Integer>();
    	//push the 0_th node onto the stack
    	preOrderStack.push(0);
    	
    	Integer currentNode;
    	Integer prevNode = 0;
    	//keep popping elements from the stack 
    	while(preOrderStack.size() != 0) {
    		for(Integer i: childMap.get(0)) {
        		preOrderStack.push(i);
        	}
    	}
    	
    	
    	
    	//iterate over all nodes in the tree
    	for (int i = 1; i < size; i++) {
    		returnValue += graph[i][parent[i]];
    	}
//        System.out.println(parent[i]+" - "+ i+"    "+
//                graph[i][parent[i]]);
    	return returnValue;
    }
    
    protected Map<Integer, List<Integer>> getChildMap(int[] parent, int size) {
	    Map<Integer, List<Integer>> childMap = new HashMap<Integer, List<Integer>>();
	    
	    //0th node doesn't have a child (is the root of the tree)
		childMap.put(-1, Arrays.asList(0));
		
		//iterate over all of the parents in the parent array 
		for(int counter = 1; counter < size; counter ++) {
			if(childMap.get(parent[counter]) != null) {
				childMap.put(parent[counter], Arrays.asList(counter));
			}
			else {
				List<Integer> children = childMap.get(parent[counter]);
				children.add(counter);
				childMap.put(parent[counter], children);
			}
		}
		return childMap;
    }
   
    protected double[][] createGraph(List<GPSCoordinate> vertices) throws Exception{
    	double[][] returnGraph = new double[vertices.size()][vertices.size()];
    	
    	//iterate over all vertices
    	for(int fromIndex = 0; fromIndex < vertices.size(); fromIndex++) {
    		for (int toIndex = 0; toIndex < vertices.size(); toIndex++) {
    			if(fromIndex == toIndex) {
    				//Cost of going from vertex rowIndex to colIndex is 0 if they are the same vertex
    				returnGraph[fromIndex][toIndex] = 0.0;
    			}
    			else {
    				returnGraph[fromIndex][toIndex] = GPSCoordinateUtils.getDistanceMetresBetweenWGS84(vertices.get(fromIndex), vertices.get(toIndex));
    			}
    		}
    	}
		return returnGraph;
    }
    
    //Node not yet added, node already added
    protected List<GPSCoordinate> getNodeToAdd(List<GPSCoordinate> nodesNotYetAdded, List<GPSCoordinate> nodesAlreadyAdded) throws Exception {
    	List<GPSCoordinate> returnList = Arrays.asList(nodesNotYetAdded.get(0), nodesAlreadyAdded.get(0));
    	double minCostToAdd = nodesAlreadyAdded.get(0).getMetresToOther(nodesNotYetAdded.get(0));
    	for(GPSCoordinate coord1: nodesNotYetAdded) {
    		for(GPSCoordinate coord2: nodesAlreadyAdded) {
    			double currentCostToAdd = coord1.getMetresToOther(coord2);
    			if(currentCostToAdd < minCostToAdd) {
    				returnList = Arrays.asList(coord1, coord2);
    				minCostToAdd = currentCostToAdd;
    			}
    		}
    	}
    	System.out.println("Returning node not yet added: " + returnList.get(1) + " and node to add to: " + returnList.get(0));
    	return returnList;
    }
    
    protected Tree<GPSCoordinate> getMSTPrimTree(List<GPSCoordinate> vertices) throws Exception {
    	
    	TreeNode<GPSCoordinate> root = new TreeNode<GPSCoordinate>();
    	int noVertices = vertices.size();
    	
    	
    	List<GPSCoordinate> nodesNotYetAdded = vertices;
    	List<GPSCoordinate> nodesAlreadyAdded = new ArrayList<GPSCoordinate>();
    	
    	
//    	for(int vertexCounter = 0; vertexCounter < noVertices; vertexCounter++) {
//    		visitedNodes[vertexCounter] = false;
//    	}
    	//insert the 0_th vertex as the root of the tree.
    	Tree<GPSCoordinate> returnTree = new Tree<GPSCoordinate>(vertices.get(0));
    	System.out.println("initialised tree with " + vertices.get(0));
    	nodesNotYetAdded.remove(vertices.get(0));
    	nodesAlreadyAdded.add(vertices.get(0));
    	//while there are still nodes left to visit
    	while(nodesNotYetAdded.size() != 0) {
    		List<GPSCoordinate> x = getNodeToAdd(nodesNotYetAdded, nodesAlreadyAdded);
    		
    		TreeNode<GPSCoordinate> a = returnTree.find(x.get(1));
    		a.addChild(new TreeNode<GPSCoordinate> (x.get(0)));
    		nodesNotYetAdded.remove(x.get(0));
    		nodesAlreadyAdded.add(x.get(0));
    	}
    	
    	return returnTree;
    }
    
    public List<GPSCoordinate> getMSTHamiltonianTour(List<GPSCoordinate> vertices) throws Exception{
    	return getMSTPrimTree(vertices).preOrderTraverse();
    }
    
    
	/**
	 * Code adapted from https://www.geeksforgeeks.org/greedy-algorithms-set-5-prims-minimum-spanning-tree-mst-2/
	 * @param vertices
	 * @throws Exception 
	 */
	public double[][] getMSTPrim(List<GPSCoordinate> vertices) throws Exception {        
		//create the fully connected graph
		//should automatically be initalised to 0: https://www.geeksforgeeks.org/default-array-values-in-java/
		double[][] graph = createGraph(vertices);
		
		//Array to store constructed MST
		//initialise a tree with the same number of vertices as in the original graph
		int primMST[] = new int[vertices.size()];
        	
        //Key values used to pick minimum weight edge in cut
		//i.e. for each vertex in verticesNotIncluded, gives the weight that 
		//adding that vertex would cost to be added to the graph
        double addVertexToTreeCost[] = new double [vertices.size()];
        
        // To represent set of vertices not yet included in MST
        //Boolean mstSet[] = new Boolean[V];
        Boolean verticesNotYetIncluded[] = new Boolean[vertices.size()];
 
        int V = vertices.size();
        
        // Initialize all keys as INFINITE
        // Innitialize all vertices as not visited
        for (int i = 0; i < V; i++)
        {
        	addVertexToTreeCost[i] = Integer.MAX_VALUE;
            verticesNotYetIncluded[i] = false;
        }
        
     // Always include first 1st vertex in MST.
        addVertexToTreeCost[0] = 0;     // Make key 0 so that this vertex is
                        // picked as first vertex
        primMST[0] = -1; // First node is always root of MST
        
     // The MST will have V vertices (from original graph)
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum key vertex from the set of vertices
            // not yet included in MST. U_th vertex is the next vertex to add to the graph
            int u = getMinAddVertexToTreeCost(addVertexToTreeCost, verticesNotYetIncluded, V);
 
            // Add the picked vertex to the MST Set
            verticesNotYetIncluded[u] = true;
 
            // Update key value (cost of adding a node to the partially
            //completed mst and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++) {
 
                // graph[u][v] is non zero only for adjacent vertices of m
                // verticesNotYetIncluded[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && verticesNotYetIncluded[v] == false &&
                    graph[u][v] < addVertexToTreeCost[v])
                {
                	//u is now the parent of v
                	primMST[v]  = u;
                	//update the key value of v
                	addVertexToTreeCost[v] = graph[u][v];
                }
            }
        }
        return graph;
	}
	
	public Set<DefaultWeightedEdge> kruskalMST(final Graph<GPSCoordinate, DefaultWeightedEdge> graph) {
        UnionFind<GPSCoordinate> forest = new UnionFind<GPSCoordinate>(graph.vertexSet());
        ArrayList<DefaultWeightedEdge> allEdges = new ArrayList<DefaultWeightedEdge>(graph.edgeSet());
        Collections.sort(
                allEdges,
                new Comparator<DefaultWeightedEdge>() {
                    public int compare(DefaultWeightedEdge edge1, DefaultWeightedEdge edge2) {
                        return Double.valueOf(graph.getEdgeWeight(edge1)).compareTo(
                                graph.getEdgeWeight(edge2));
                    }
                });
        Set<DefaultWeightedEdge> edgeList = new HashSet<DefaultWeightedEdge>();

        for (DefaultWeightedEdge edge : allEdges) {
        	GPSCoordinate source = graph.getEdgeSource(edge);
        	GPSCoordinate target = graph.getEdgeTarget(edge);
            if (forest.find(source).equals(forest.find(target))) {
                continue;
            }

            forest.union(source, target);
            edgeList.add(edge);
        }

        return edgeList;
	}
	
	public Graph<GPSCoordinate, DefaultWeightedEdge> getKruskalTSPSolution(Graph<GPSCoordinate, DefaultWeightedEdge> graph) throws Exception{
		//this gives a minimal weight spanning tree, but need a graph in order to do anything useful
		//create a graph from the spanning tree
		Set<DefaultWeightedEdge> mstEdges = kruskalMST(graph);
		Graph<GPSCoordinate, DefaultWeightedEdge> mstGraph = new SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        List<GPSCoordinate> vertices = new ArrayList<GPSCoordinate>();
        for (GPSCoordinate vertex : graph.vertexSet()) {
            mstGraph.addVertex(vertex);
        }
        for (DefaultWeightedEdge edge : mstEdges) {
          mstGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
        }
        return mstGraph;
	}
	
	/**
	 * 
	 * @return the cost of the Kruskal minimum spanning tree solution to the travelling salesman problem
	 * @throws Exception
	 */
	public double getKruskalTSPSolutionCost() throws Exception {
		Graph<GPSCoordinate, DefaultWeightedEdge> kruskalSoln = getKruskalTSPSolution(graphManager.createCompleteGraphFromGPSGrid(traversalGrid.generateContainedGPSCoordinates()));
		double solnCost = 0;
		DepthFirstIterator<GPSCoordinate, DefaultWeightedEdge> graphIterator  = new DepthFirstIterator<GPSCoordinate, DefaultWeightedEdge>(kruskalSoln);
		
		//store first vertex as the agent must return to it once they have finished traversing the other nodes
		GPSCoordinate firstVertex = graphIterator.next();
		//prev is initialised to first, then 'shuffles' along behind nextvertex
		GPSCoordinate prevVertex = firstVertex;
		GPSCoordinate nextVertex;
		int counter = 0;
		do {
			counter += 1;
			System.out.println("Counted " + counter + " edges so far");
			System.out.println("Soln cost so far: " + solnCost);
			
			try {
				nextVertex = graphIterator.next();
			}
			//Assertion throws an error, not sure why
			catch(Error exception) {
				System.out.println(exception);
				System.out.println("Couldn't get next element");
				nextVertex = firstVertex;
			}
			
			System.out.println("Adding weight of prev to next: " + prevVertex.toString() + nextVertex.toString());
			try {
				solnCost += kruskalSoln.getEdgeWeight(kruskalSoln.getEdge(prevVertex, nextVertex));
			}
			catch(Exception e) {
				//next vertex null means leaf?
				System.out.println(e);
			}
			prevVertex = nextVertex;
		}
		while(graphIterator.hasNext());
		solnCost += kruskalSoln.getEdgeWeight(kruskalSoln.getEdge(prevVertex, firstVertex));
		return solnCost;
	}
	
	public GPSPolygonGrid getTraversalGrid() {
		return traversalGrid;
	}

	public void setTraversalGrid(GPSPolygonGrid traversalGrid) {
		this.traversalGrid = traversalGrid;
	}
	protected GraphManager getGraphManager() {
		return graphManager;
	}

	protected void setGraphManager(GraphManager graphManager) {
		this.graphManager = graphManager;
	}


	@Override
	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping() throws Exception {
		// Use this to develop agent routes from PrimMST, cost will be given for free by analyser
		return null;
	}
}
