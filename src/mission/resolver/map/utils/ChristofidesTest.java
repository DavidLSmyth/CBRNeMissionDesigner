package mission.resolver.map.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.management.relation.RelationService;

import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

class ChristofidesTest {
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	
	GPSGridQuadrilateral nuigQuad;
	RegularTraversalGridQuad nuigGrid;

	@BeforeEach
	void setUp() throws Exception {
		NUIGcoord0 = new GPSCoordinate(53.2791748417, -9.0644775368);
        NUIGcoord1 = new GPSCoordinate(53.2801009832, -9.0648776011);
        NUIGcoord2 = new GPSCoordinate(53.2805257224, -9.0621271428);
        NUIGcoord3 = new GPSCoordinate(53.27959959, -9.0617270785);
        
        nuigQuad = new GPSGridQuadrilateral(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3);
		nuigGrid = new RegularTraversalGridQuad(nuigQuad, 100, 100, 20);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testChristofides() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTraversalGrid() {
		fail("Not yet implemented");
	}

	@Test
	void testSetTraversalGrid() {
		fail("Not yet implemented");
	}

	@Test
	void testTSPChristofidesUpperBoundCost() {
		fail("Not yet implemented");
	}
	@Test
	void testTSPChristofidesCreateGraphFromGPSTraversalGrid() {
		Christofides christofides = new Christofides(nuigGrid);
		SimpleGraph<GPSCoordinate, DefaultWeightedEdge> graph = (SimpleGraph<GPSCoordinate, DefaultWeightedEdge>) christofides.createCompleteGraphFromGPSGrid(nuigGrid.getGridPoints());
		Set traversalGridSet = new HashSet<>(nuigGrid.getGridPoints());
		ArrayList<GPSCoordinate> traversalGridList = nuigGrid.getGridPoints();
		int traversalGridSetSize = traversalGridSet.size();
		Set vertexSet = new HashSet<>(graph.vertexSet());
		int vertexSetSize = vertexSet.size();
		assertEquals(traversalGridSet, vertexSet);
		assertEquals(traversalGridSet.size() - 1, graph.degreeOf(traversalGridList.get(0)));
		assertEquals(1, graph.getAllEdges(traversalGridList.get(0), traversalGridList.get(1)).size());
		assertEquals(traversalGridSetSize * (traversalGridSetSize-1) /2, graph.edgeSet().size());
		for(GPSCoordinate coord: nuigGrid.getGridPoints()) {
			assertEquals(traversalGridSetSize - 1, graph.degreeOf(coord));
		}
		//graph.remove
		System.out.println("Arbitrary edge: " + graph.getEdge(traversalGridList.get(0), traversalGridList.get(1)));
		System.out.println(graph.getEdge(traversalGridList.get(0), traversalGridList.get(1)));
		
		//Is this a bug?
		//assertEquals(traversalGridList.get(0).getMetresToOther(traversalGridList.get(1)), graph.getEdgeWeight(graph.getEdge(traversalGridList.get(0), traversalGridList.get(1))));
		DefaultWeightedEdge edge = graph.getEdge(traversalGridList.get(0), traversalGridList.get(1));
		if(edge != null) {
			//allow tolerance of 0.1mm
			assertEquals(traversalGridList.get(0).getMetresToOther(traversalGridList.get(1)), graph.getEdgeWeight(edge), 0.0001);
		}
		else {
			fail("Edge is null, cannot get weight");
			//throw new Exception("Edge doesn't exist in graph");
		}
		graph.removeEdge(traversalGridList.get(0), traversalGridList.get(1));
		assertEquals(traversalGridSet.size() - 2, graph.degreeOf(traversalGridList.get(0)));
		//assertEquals(graph.);
	}

	@Test
	void testTSPChristofidesLowerBoundCost() {
		fail("Not yet implemented");
	}

	@Test
	void testTSPChristofidesSolution() throws Exception {
		//Test that the created graph behaves as expected
		fail("Not yet implemented");
	}

}
