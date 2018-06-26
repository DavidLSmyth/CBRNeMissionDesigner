package mission.resolver.map.utils.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.relation.RelationService;
import javax.swing.text.Utilities;

import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import GPSUtils.TestUtils;
import GPSUtils.grid.GPSPolygon;
import GPSUtils.grid.GPSPolygonGrid;
import mission.resolver.map.utils.Christofides;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

class ChristofidesTest {
	
	TestUtils utils;
	GPSPolygon galwayPoly;
	GPSPolygonGrid galwayGrid;
	Christofides christofides;
	List<GPSCoordinate> galwayGridPoints;
	SimpleGraph<GPSCoordinate, DefaultWeightedEdge> graph;
	
	Set<GPSCoordinate> traversalGridSet;
	List<GPSCoordinate> traversalGridList;
	int traversalGridSetSize;
	Set<GPSCoordinate> vertexSet;
	int vertexSetSize;
	
	{
		try {
			utils = new TestUtils(1500, 1500);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		galwayPoly = utils.getNUIGPoly();
		galwayGrid = utils.getNUIGGrid();
	}
	@BeforeEach
	void setUp() throws Exception {
//		NUIGcoord0 = new GPSCoordinate(53.2791748417, -9.0644775368);
//        NUIGcoord1 = new GPSCoordinate(53.2801009832, -9.0648776011);
//        NUIGcoord2 = new GPSCoordinate(53.2805257224, -9.0621271428);
//        NUIGcoord3 = new GPSCoordinate(53.27959959, -9.0617270785);
//        
		
//        nuigQuad = new GPSPolygon(Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3));
//        //GPSPolygon polygon, double latSpacing, double lngSpacing)
//		nuigGrid = new GPSPolygonGrid(nuigQuad, 100, 100);
//		utils = new TestUtils(1000, 1000);
//		galwayPoly = utils.getNUIGPoly();
//		galwayGrid = utils.getNUIGGrid();
		christofides = new Christofides(galwayGrid);
		galwayGridPoints = galwayGrid.generateContainedGPSCoordinates();
		graph = (SimpleGraph<GPSCoordinate, DefaultWeightedEdge>) 
				christofides.createCompleteGraphFromGPSGrid(galwayGridPoints);
		
		traversalGridSet = new HashSet<GPSCoordinate>(galwayGridPoints);
		traversalGridList = galwayGridPoints;
		traversalGridSetSize = traversalGridSet.size();
		vertexSet = new HashSet<GPSCoordinate>(graph.vertexSet());
		vertexSetSize = vertexSet.size();
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
	void testTSPChristofidesCreateGraphFromGPSTraversalGrid() throws Exception {
		
		System.out.println("Vertex set size: " + vertexSetSize);
		//System.out.println(vertexSet);
		
		//assertEquals(traversalGridSet, vertexSet);
		
		//System.out.println("Traversal grid list: " + traversalGridList);
		//check degree of nodse correct
		assertEquals(traversalGridSet.size() - 1, graph.degreeOf(traversalGridList.get(0)));
		//check that there is one edge from node0 to node1
		assertEquals(1, graph.getAllEdges(traversalGridList.get(0), traversalGridList.get(1)).size());
		//check that the graph is complete
		assertEquals(traversalGridSetSize * (traversalGridSetSize-1) /2, graph.edgeSet().size());
		
		for(GPSCoordinate coord: galwayGridPoints) {
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
	void testTSPChristofidesLowerBoundCost() throws Exception {
		System.out.println(christofides.getTPSChristofidesSolutionCost());
	}

	@Test
	void testTSPChristofidesSolution() throws Exception {
		//Test that the created graph behaves as expected
		fail("Not yet implemented");
	}

}
