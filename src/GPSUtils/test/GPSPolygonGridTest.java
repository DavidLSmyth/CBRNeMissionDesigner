package GPSUtils.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import GPSUtils.TestUtils;
import GPSUtils.grid.GPSPolygon;
import GPSUtils.grid.GPSPolygonGrid;


class GPSPolygonGridTest {
	
	TestUtils utils;
	GPSPolygon poly;
		
	@BeforeEach
	void setUp() throws Exception {
		utils = new TestUtils();
		poly = utils.getPolygon();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGPSPolygonGrid() throws Exception {
		GPSPolygonGrid polyGrid = new GPSPolygonGrid(poly, 600, 400);
		System.out.println(polyGrid.getPolygon().getBoundary());
		System.out.println("\n\n" + polyGrid.getPolygon().getBoundingQuadrilateral().getCornerPoints());
		List<GPSCoordinate> grid = polyGrid.generateContainedGPSCoordinates();
		for(GPSCoordinate coord: grid) {
			//System.out.println(coord);
		}
	}

	@Test
	void testGetHeight() {
		fail("Not yet implemented");
	}

	@Test
	void testGetWidth() {
		fail("Not yet implemented");
	}

	@Test
	void testGenerateContainedGPSCoordinates() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPolygon() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNoGridPoints() {
		fail("Not yet implemented");
	}

	@Test
	void testSetPolygon() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLatSpacing() {
		fail("Not yet implemented");
	}

	@Test
	void testSetLatSpacing() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLngSpacing() {
		fail("Not yet implemented");
	}

	@Test
	void testSetLngSpacing() {
		fail("Not yet implemented");
	}

}
