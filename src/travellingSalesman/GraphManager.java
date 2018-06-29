package travellingSalesman;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import GPSUtils.GPSCoordinate;
import GPSUtils.grid.GPSPolygonGrid;

/**
 * A class to manage a graph with GPSCoordinate nodes (for now!)
 * Provides a way to get useful components from a graph of GPS nodes, for
 * example spanning trees etc.
 * @author 13383861
 *
 */
public class GraphManager {
	GPSPolygonGrid traversalGrid;
	
	public GraphManager(GPSPolygonGrid nuigGrid) {
		setTraversalGrid(nuigGrid);
	}

	public GPSPolygonGrid getTraversalGrid() {
		return traversalGrid;
	}
	
	public SpanningTree<DefaultWeightedEdge> getMST() throws Exception{
		Graph<GPSCoordinate,DefaultWeightedEdge> g = createCompleteGraphFromGPSGrid(traversalGrid.generateContainedGPSCoordinates());
		PrimMinimumSpanningTree<GPSCoordinate, DefaultWeightedEdge> mst = new PrimMinimumSpanningTree<GPSCoordinate, DefaultWeightedEdge> (g);
		return mst.getSpanningTree();
	}


	public void setTraversalGrid(GPSPolygonGrid grid) {
		this.traversalGrid = grid;
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
		}
		return simpleGraph;
	}
}
