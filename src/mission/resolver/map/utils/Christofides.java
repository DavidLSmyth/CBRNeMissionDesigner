package mission.resolver.map.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Comparator;
import java.util.HashMap;

import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.alg.interfaces.TSPAlgorithm;
import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
//import org.jgrapht.graph.AdvancedWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
//import org.jgrapht.alg.matching.
import org.jgrapht.*;

//import pl.wat.tal.common.AbstractAlgorithm;
//import pl.wat.tal.common.Algorithm;
//import pl.wat.tal.misc.TSPResult;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class Christofides {
	
	RegularTraversalGridQuad traversalGrid;
	
	public Christofides(RegularTraversalGridQuad traversalGrid) {
		setTraversalGrid(traversalGrid);
	}

	public RegularTraversalGridQuad getTraversalGrid() {
		return traversalGrid;
	}


	public void setTraversalGrid(RegularTraversalGridQuad traversalGrid) {
		this.traversalGrid = traversalGrid;
	}
	
	public double TSPChristofidesUpperBoundCost() {
		return 0;
	}
	
	public double TSPChristofidesLowerBoundCost() {
		return 0;
	}
	
	//should be private
	public Graph<GPSCoordinate,DefaultWeightedEdge> createCompleteGraphFromGPSGrid(Iterable<GPSCoordinate> coordinates) {
		//ArrayList<GPSCoordinate> coordinates = traversalGrid.getGridPoints();
		Graph<GPSCoordinate,DefaultWeightedEdge> simpleGraph = new SimpleWeightedGraph<GPSCoordinate,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		for(GPSCoordinate coord: coordinates) {
			simpleGraph.addVertex(coord);
		}
		int outerCounter = 0;
		for(GPSCoordinate coord1: coordinates) {
			int innerCounter = 0;
			for(GPSCoordinate coord2: coordinates) {
				//&& !coord1.equals(coord2)
				if(innerCounter > outerCounter) {
					simpleGraph.addEdge(coord1, coord2, new DefaultWeightedEdge());
					simpleGraph.setEdgeWeight(simpleGraph.getEdge(coord1, coord2), coord1.getMetresToOther(coord2));
					//simpleGraph.add
				}
				innerCounter++;
			}
//			for(int innerCounter = outerCounter; innerCounter < coordinates.size(); innerCounter++) {
//				GPSCoordinate coord2 = coordinates.get(innerCounter);
//				
//			}
		}
		return simpleGraph;
	}
	
	//private
	public Set<GPSCoordinate> getOddDegreeVertices(SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> mst) {
		
		Set<GPSCoordinate> returnSet = new HashSet<GPSCoordinate>();		
		for(GPSCoordinate coord: mst.vertexSet()) {
			if(mst.degreeOf(coord) % 2 == 1) {
				returnSet.add(coord);
			}
		}
		return returnSet;
	}
	
	public ArrayList<GPSCoordinate> TSPChristofidesSolution(GPSCoordinate startPos){
		/*
		 * The Christofides algorithm is an algorithm for finding approximate solutions
		 *  to the travelling salesman problem, on instances where the distances form a 
		 *  metric space (they are symmetric and obey the triangle inequality).
		 *  [1] It is an approximation algorithm that guarantees that its solutions will be 
		 *  within a factor of 3/2 of the optimal solution length, and is named after Nicos Christofides,
		 *   who published it in 1976.[2] As of 2017, this is the best approximation ratio that has been 
		 *   proven for the traveling salesman problem on general metric spaces, although better approximations 
		 *   are known for some special cases.
		 *   
		 *   Let G = (V,w) be an instance of the travelling salesman problem. That is, G is a complete graph on the set V of vertices, and the function w assigns a nonnegative real weight to every edge of G. According to the triangle inequality, for every three vertices u, v, and x, it should be the case that w(uv) + w(vx) ≥ w(ux).

			Then the algorithm can be described in pseudocode as follows.[1]
			
			1). Create a minimum spanning tree T of G.
			2). Let O be the set of vertices with odd degree in T. By the handshaking lemma, O has an even number of vertices.
			3). Find a minimum-weight perfect matching M in the induced subgraph given by the vertices from O.
			4). Combine the edges of M and T to form a connected multigraph H in which each vertex has even degree.
			5). Form an Eulerian circuit in H.
			6). Make the circuit found in previous step into a Hamiltonian circuit by skipping repeated vertices (shortcutting).
		 */
		ArrayList<GPSCoordinate> coordinates = traversalGrid.getGridPoints();
		/*
		* A simple graph. A simple graph is an undirected graph for which at most one edge connects any two
		 * vertices, and loops are not permitted
		 * 
		 */
		
		Graph<GPSCoordinate, DefaultWeightedEdge> completeGraph = createCompleteGraphFromGPSGrid(traversalGrid.getGridPoints());
		
		//1) Create a minimum spanning tree T of G.
		//KruskalMinimumSpanningTree does not expose the graph or vertices/edges in the graph, so just take
		//the source from here: https://github.com/jgrapht/jgrapht/blob/c8949472c13412580911857789c9dc1d444e1cc9/jgrapht-core/src/main/java/org/jgrapht/alg/spanning/KruskalMinimumSpanningTree.java
		//SpanningTree<DefaultWeightedEdge> mst = new KruskalMinimumSpanningTree<GPSCoordinate, DefaultWeightedEdge>(completeGraph).getSpanningTree();
		SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> mst = findMinimumSpanningTree(completeGraph);
		
		//2). Let O be the set of vertices with odd degree in T
		Set<GPSCoordinate> o = getOddDegreeVertices(mst);
		// By the handshaking lemma, O has an even number of vertices.
		assert o.size() % 2 == 0;
		//3). Find a minimum-weight perfect matching M in the induced subgraph given by the vertices from O.
		//mst.
		
		
		//CompleteGraphGenerator<V, E> generator = new CompleteGraphGenerator<>(coordinates.size());
		return coordinates;
		
	}
	
	public Set<DefaultWeightedEdge> getMinimumWeightPerfectMatching(Graph<GPSCoordinate, DefaultWeightedEdge> g) {
		return null;
		//A perfect matching (a.k.a. 1-factor) is a matching which matches all vertices of the graph. 
		//That is, every vertex of the graph is incident to exactly one edge of the matching.
		//In the mathematical discipline of graph theory, a matching or independent edge set 
		//in a graph is a set of edges without common vertices. In some matchings, all the vertices 
		//may be incident with some edge of the matching, but this is not required and can only occur
		//if the number of vertices is even.
	}
	
	//private
	public Graph<GPSCoordinate, DefaultWeightedEdge> findMinimumSpanningTree(Graph<GPSCoordinate, DefaultWeightedEdge> graph) {
		//create spanning tree graph from set of edges found by Kruskal
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
        
        //create complete subgraph using only the edges of the minimum spanning tree
        //return createCompleteGraphFromGPSGrid(mstGraph.vertexSet());

//        SimpleWeightedGraph<GPSCoordinate,DefaultWeightedEdge> returnMST = new SimpleWeightedGraph<GPSCoordinate,
//        		DefaultWeightedEdge>(DefaultWeightedEdge.class);
//        //All nodes must be included in a spanning tree by definition
//        for (GPSCoordinate vertex : graph.vertexSet()) {
//            returnMST.addVertex(vertex);
//        }
//        // Add edges found by the kruskal algorithm
//        for (DefaultWeightedEdge edge : mstEdges) {
//            returnMST.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
//        }
//        return returnMST;
    }
	
	//private
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
}
