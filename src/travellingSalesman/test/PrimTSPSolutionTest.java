package travellingSalesman.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Polygon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.TestUtils;
import GPSUtils.grid.GPSPolygon;
import GPSUtils.grid.GPSPolygonGrid;
import travellingSalesman.PrimTSPSolution;

class PrimTSPSolutionTest {
	
	TestUtils testUtils; 
	GPSPolygon poly;
	GPSPolygonGrid polyGrid;
	PrimTSPSolution soln;
	
	@BeforeEach
	void setUp() throws Exception {
		testUtils = new TestUtils(2000, 2000);
		poly = testUtils.getNUIGPoly();
		polyGrid = testUtils.getNUIGGrid();
		soln = new PrimTSPSolution(polyGrid);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPrimTSPSolution() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPrimTSPSolutionCost() throws Exception {
		System.out.println(soln.getMSTHamiltonianTour(polyGrid.generateContainedGPSCoordinates()));
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
	void testGetGraphManager() {
		fail("Not yet implemented");
	}

	@Test
	void testSetGraphManager() {
		fail("Not yet implemented");
	}

}
