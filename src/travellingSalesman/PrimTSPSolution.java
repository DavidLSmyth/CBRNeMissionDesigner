package travellingSalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import GPSUtils.GPSCoordinate;
import GPSUtils.grid.GPSPolygonGrid;

public class PrimTSPSolution {
	
	GPSPolygonGrid traversalGrid;
	GraphManager graphManager;
	

	public PrimTSPSolution(GPSPolygonGrid traversalGrid) {
		setTraversalGrid(traversalGrid);
		setGraphManager(new GraphManager(getTraversalGrid()));
	}
	
	
	// A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(double key[], Boolean verticesNotYetIncluded[], int V)
    {
        // Initialize min value
        double min = Double.MAX_VALUE;
        int min_index=-1;
 
        //find the vertex of least weight not yet included in the mst by 
        //iterating over all vertices not yet included in mst
        for (int v = 0; v < V; v++) {
        	//if the vertex has not yet been included in the mst and 
            if (verticesNotYetIncluded[v] == false && key[v] < min)
            {
                min = key[v];
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
     * @param graph
     * @return
     */
    public double getMSTPrimLowerBoundCost(double[][] graph) {
    	
    }
    
	/**
	 * Code adapted from https://www.geeksforgeeks.org/greedy-algorithms-set-5-prims-minimum-spanning-tree-mst-2/
	 * @param vertices
	 */
	public double[][] getMSTPrim(List<GPSCoordinate> vertices) {
		//Array to store constructed MST
		//create the fully connected graph
		double[][] graph = new double[vertices.size()][vertices.size()];
		
		//initialise a tree with the same number of vertices as in the original graph
		double primMST[] = new double[vertices.size()];
        	
        //Key values used to pick minimum weight edge in cut
		//i.e. for each vertex in verticesNotIncluded, gives the weight that 
		//adding that vertex would cost to be added to the graph
        double key[] = new double [vertices.size()];
        
        // To represent set of vertices not yet included in MST
        //Boolean mstSet[] = new Boolean[V];
        Boolean verticesNotYetIncluded[] = new Boolean[vertices.size()];
 
        int V = vertices.size();
        
        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++)
        {
            key[i] = Integer.MAX_VALUE;
            verticesNotYetIncluded[i] = false;
        }
        
     // The MST will have V vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, verticesNotYetIncluded, V);
 
            // Add the picked vertex to the MST Set
            verticesNotYetIncluded[u] = true;
 
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++) {
 
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v]!=0 && verticesNotYetIncluded[v] == false &&
                    graph[u][v] <  key[v])
                {
                	primMST[v]  = u;
                    key[v] = graph[u][v];
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
}
