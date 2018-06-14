package mission.resolver.map.utils;


import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import GPSUtils.GPSCoordinate;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.alg.tour.TwoApproxMetricTSP;
//import org.jgrapht.alg.matching.
import org.jgrapht.*;

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
	public Graph<GPSCoordinate,DefaultWeightedEdge> createCompleteGraphFromGPSGrid(Iterable<GPSCoordinate> coordinates) throws Exception {
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
			outerCounter++;
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
	
	public double getTPSChristofidesSolutionCost() throws Exception {
		return TSPChristofidesSolution().getWeight();
	}
	public double getTPSChristofidesSolutionLength() throws Exception {
		//Verifies that the christofides solution uses all nodes!
		return TSPChristofidesSolution().getLength();
	}
	
	public List<GPSCoordinate> getTPSChristofidesSolutionVertices() throws Exception {
		return TSPChristofidesSolution().getVertexList();
	}
	
	private GraphPath<GPSCoordinate, DefaultWeightedEdge> TSPChristofidesSolution() throws Exception{
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
		SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> mst = (SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge>) findMinimumSpanningTree(completeGraph);
		
		//2). Let O be the set of vertices with odd degree in T
		Set<GPSCoordinate> o = getOddDegreeVertices(mst);
		// By the handshaking lemma, O has an even number of vertices.
		assert o.size() % 2 == 0;
		//3). Find a minimum-weight perfect matching M in the induced subgraph given by the vertices from O.
		SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> minWeightMatching = matching(o, mst);
		
		//4). Combine the edges of M and T to form a connected multigraph H in which each vertex has even degree.
		WeightedMultigraph<GPSCoordinate, DefaultWeightedEdge> combinedGraph = combineGraphs(mst, minWeightMatching);
//		assert Graphs.addGraph(minWeightMatching, mst);
		
		//5). Form an Eulerian circuit in H.
		HierholzerEulerianCycle<GPSCoordinate, DefaultWeightedEdge> cycle = new HierholzerEulerianCycle<GPSCoordinate, DefaultWeightedEdge>();
		GraphPath<GPSCoordinate, DefaultWeightedEdge> eulerianCycle = cycle.getEulerianCycle(minWeightMatching);
		assert cycle.isEulerian(eulerianCycle.getGraph());
		
		//6). Make the circuit found in previous step into a Hamiltonian circuit by skipping repeated vertices (shortcutting).
		TwoApproxMetricTSP<GPSCoordinate, DefaultWeightedEdge> tspSoln = new TwoApproxMetricTSP<GPSCoordinate, DefaultWeightedEdge>();
		return tspSoln.getTour(eulerianCycle.getGraph());
		 
		 
		//CompleteGraphGenerator<V, E> generator = new CompleteGraphGenerator<>(coordinates.size());
		//return coordinates;
		
	}
	
	private WeightedMultigraph<GPSCoordinate, DefaultWeightedEdge> combineGraphs(
			SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> graph1,
			SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> graph2
    ) {
        // Tworzymy nowy graf
        WeightedMultigraph<GPSCoordinate, DefaultWeightedEdge> combinedGraph = new WeightedMultigraph<GPSCoordinate,
        		DefaultWeightedEdge>(DefaultWeightedEdge.class);
        // kopiujemy wszystkie wierzcholki z pierwszego
        for (GPSCoordinate vertex : graph1.vertexSet()) {
            combinedGraph.addVertex(vertex);
        }
        // oraz wszystkie krawedzie
        for (DefaultWeightedEdge edge : graph1.edgeSet()) {
            combinedGraph.addEdge(graph1.getEdgeSource(edge), graph1.getEdgeTarget(edge), edge);
        }
        // A teraz wszystkie krawedzie z drugiego grafu (mogą się powtarzać)
        for (DefaultWeightedEdge edge : graph2.edgeSet()) {
            combinedGraph.addEdge(graph2.getEdgeSource(edge), graph2.getEdgeTarget(edge), edge);
        }

        // Zwracamy polaczony graf
        return combinedGraph;
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
	
	private SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> matching(Set<GPSCoordinate> voddVertices,
			SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> graph) {
		LinkedList<DefaultWeightedEdge> matches = new LinkedList<DefaultWeightedEdge>();

		Set<GPSCoordinate> vertices = new HashSet<GPSCoordinate>();
		for (GPSCoordinate vertex : graph.vertexSet()) {
			vertices.add(vertex);
		}

		// HashMap<GPSCoordinate, LinkedList<DefaultWeightedEdge>> adjList =
		// g.getAdjList();
		int numberOfMatches = voddVertices.size() / 2;

		HashMap<GPSCoordinate, LinkedList<DefaultWeightedEdge>> trash = new HashMap<GPSCoordinate, LinkedList<DefaultWeightedEdge>>();
		// Lecimy po wszystkich wierzcholkach
		for (GPSCoordinate vertex : vertices) {

			Set<DefaultWeightedEdge> edges = graph.edgesOf(vertex);
			outer: for (DefaultWeightedEdge e : edges) {

				// wrzucamy wierzcholki krawedzi do listy wierzcholkow
				LinkedList<GPSCoordinate> verticesOfEdge = new LinkedList<GPSCoordinate>();
				verticesOfEdge.add((GPSCoordinate) graph.getEdgeSource(e));
						//getSourceVertex());
				verticesOfEdge.add((GPSCoordinate) graph.getEdgeTarget(e));
						//e.getTargetVertex()
				for (GPSCoordinate vertexOfEdge : verticesOfEdge) {

					if (!voddVertices.contains(vertexOfEdge)) {
						// zapisujemy do wywalenia
						if (trash.get(vertexOfEdge) == null) {
							trash.put(vertexOfEdge, new LinkedList<DefaultWeightedEdge>());
						}
						trash.get(vertexOfEdge).add(e);
						continue outer;
					}
				}
			}
		}

		// Usuwamy wszystkie znalezione krawędzie
		Iterator<Entry<GPSCoordinate, LinkedList<DefaultWeightedEdge>>> iterator = trash.entrySet().iterator();
		Set<DefaultWeightedEdge> edges = new HashSet<DefaultWeightedEdge>();

		for (DefaultWeightedEdge e : graph.edgeSet()) {
			edges.add(e);
		}

		while (iterator.hasNext()) {
			Map.Entry<GPSCoordinate, LinkedList<DefaultWeightedEdge>> pair = iterator.next();
			GPSCoordinate key = (GPSCoordinate) (pair.getKey());
			LinkedList<DefaultWeightedEdge> edgeList = (LinkedList<DefaultWeightedEdge>) (pair.getValue());

			List<DefaultWeightedEdge> toRemove = new LinkedList<DefaultWeightedEdge>();

			if (!voddVertices.contains(key)) {
				// Usuwamy wierzcholek
				voddVertices.remove(key);
				// I wszystkie z nim krawedzie
				for (DefaultWeightedEdge e : edgeList) {
					if (graph.getEdgeSource(e).equals(key) || graph.getEdgeTarget(e).equals(key)) {
					//if (e.getSourceVertex().equals(key) || e.getTargetVertex().equals(key)) {
						toRemove.add(e);
					}
				}
			} else {
				for (DefaultWeightedEdge e : edgeList) {
					if (graph.getEdgeSource(e).equals(key) || graph.getEdgeTarget(e).equals(key)) {
					//if (e.getSourceVertex().equals(key) || e.getTargetVertex().equals(key)) {
						toRemove.add(e);
					}
				}
			}

			for (DefaultWeightedEdge edge : toRemove) {
				edgeList.remove(edge);
			}
		}

		// tak dlugo, jak mamy mniej krawedzi od spodziewanej liczby dopasowan
		while (matches.size() < numberOfMatches) {
			// znajdujemy krawedz o najnizszej wadze

			// Tworzymy nowy graf z nowymi wierzcholkami i krawedziami
			SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> newGraph = new SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge>(
					DefaultWeightedEdge.class);

			for (GPSCoordinate v : vertices) {
				newGraph.addVertex(v);
			}

			for (DefaultWeightedEdge edge : edges) {
				newGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
				//newGraph.addEdge((GPSCoordinate) edge.getSourceVertex(), (GPSCoordinate) edge.getTargetVertex(), edge);
			}

			DefaultWeightedEdge emin = new DefaultWeightedEdge();
			graph.setEdgeWeight(emin, Double.MAX_VALUE);
			//emin.setWeight(Double.MAX_VALUE);

			for (GPSCoordinate vertex : vertices) {

				Set<DefaultWeightedEdge> edgesOfVertex = newGraph.edgesOf(vertex);
				outer: for (DefaultWeightedEdge e : edgesOfVertex) {
					// ignore edge if it is not incident on any of the given vertices

					// wrzucamy wierzcholki krawedzi do listy wierzcholkow
					LinkedList<GPSCoordinate> verticesOfEdge = new LinkedList<GPSCoordinate>();
					verticesOfEdge.add(newGraph.getEdgeSource(e));
					verticesOfEdge.add(newGraph.getEdgeTarget(e));
//					verticesOfEdge.add((GPSCoordinate) e.getSourceVertex());
//					verticesOfEdge.add((GPSCoordinate) e.getTargetVertex());

					for (GPSCoordinate vertexOfEdge : verticesOfEdge) {
						if (!voddVertices.contains(vertexOfEdge)) {
							continue outer;
						}
					}

					if (newGraph.getEdgeWeight(e) < newGraph.getEdgeWeight(emin)) {
						emin = e;
					}
//					if (e.getWeight() < emin.getWeight()) {
//						emin = e;
//					}
				}
			}

			// dodajemy krawedz do zwracanego grafu
			matches.add(emin);

			HashMap<GPSCoordinate, LinkedList<DefaultWeightedEdge>> deathRow = new HashMap<GPSCoordinate, LinkedList<DefaultWeightedEdge>>();

			// usuwamy wszystkie krawędzie incydentne z wierzcholkami
			for (GPSCoordinate vertex : vertices) {
				Set<DefaultWeightedEdge> edgesOfVertex = newGraph.edgesOf(vertex);

				for (DefaultWeightedEdge e : edgesOfVertex) {
					// wrzucamy wierzcholki krawedzi do listy wierzcholkow
					LinkedList<GPSCoordinate> verticesOfMinEdge = new LinkedList<GPSCoordinate>();
					verticesOfMinEdge.add(newGraph.getEdgeSource(emin));
					verticesOfMinEdge.add(newGraph.getEdgeTarget(emin));
//					verticesOfMinEdge.add((GPSCoordinate) emin.getSourceVertex());
//					verticesOfMinEdge.add((GPSCoordinate) emin.getTargetVertex());

					for (GPSCoordinate v : verticesOfMinEdge) {
						LinkedList<GPSCoordinate> verticesOfEdge = new LinkedList<GPSCoordinate>();
						verticesOfEdge.add(newGraph.getEdgeSource(e));
						verticesOfEdge.add(newGraph.getEdgeTarget(e));
//						verticesOfEdge.add((GPSCoordinate) e.getSourceVertex());
//						verticesOfEdge.add((GPSCoordinate) e.getTargetVertex());

						if (verticesOfEdge.contains(v)) {
							// zapisujemy do usuniecia
							if (deathRow.get(vertex) == null) {
								deathRow.put(vertex, new LinkedList<DefaultWeightedEdge>());
							}
							deathRow.get(vertex).add(e);
						}
					}
				}
			}

			// usuwamy wszystkie oznaczone krawedzie
			Iterator<Entry<GPSCoordinate, LinkedList<DefaultWeightedEdge>>> iterator2 = deathRow.entrySet().iterator();
			while (iterator2.hasNext()) {
				Map.Entry<GPSCoordinate, LinkedList<DefaultWeightedEdge>> pair = iterator2.next();
				LinkedList<DefaultWeightedEdge> edgesOfVertex = pair.getValue();
				for (DefaultWeightedEdge e : edgesOfVertex) {
					edges.remove(e);
				}
			}
		}

		// Tworzymy ostateczny graf z nowymi wierzcholkami i krawedziami
		SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge> returnGraph = new SimpleWeightedGraph<GPSCoordinate, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		for (GPSCoordinate vertex : vertices) {
			returnGraph.addVertex(vertex);
		}

		for (DefaultWeightedEdge edge : matches) {
			// Tworzymy nowe obiekty krawedzi, gdyz inaczej jgrapht moze myslec ze dwa razy
			// dodalismy taką samą
			// krawędź
			DefaultWeightedEdge newEdge = new DefaultWeightedEdge();
			//newEdge.setWeight(edge.getWeight());
			returnGraph.setEdgeWeight(newEdge, graph.getEdgeWeight(edge));
			returnGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge),
					newEdge);
//			returnGraph.addEdge((GPSCoordinate) edge.getSourceVertex(), (GPSCoordinate) edge.getTargetVertex(),
//					newEdge);
		}

		return returnGraph;
	}
}
