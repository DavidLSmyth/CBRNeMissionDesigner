package GPSUtils.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Polygon;
import java.math.BigDecimal;

import org.apache.commons.math3.analysis.solvers.UnivariateSolverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import GPSUtils.TestUtils;
import GPSUtils.grid.GPSPolygon;
import au.com.objectix.jgridshift.Util;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;

class GPSPolygonTest {
	
	TestUtils utils;
	GPSPolygon poly;
//	GPSCoordinate p1;
//	GPSCoordinate p2;
//	GPSCoordinate p3;
//	GPSCoordinate p4;
//	GPSCoordinate p5;
//	GPSCoordinate p6;
	
	@BeforeEach
	void setUp() throws Exception {
		utils = new TestUtils();
		poly = utils.getNUIGPoly();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGPSPolygon() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBoundingQuadrilateral() throws Exception {
		/*	
		//highest lat	
		p1 = new GPSCoordinate(53.355684, -9.034788, 3.112233445566);
		//lowest long
		p2 = new GPSCoordinate(53.318084, -9.135740, 3.112233445566);
		//lowest lat
		p3 = new GPSCoordinate(53.265427, -9.108212, 100.0);
		
		p4 = new GPSCoordinate(53.268804, -9.037489, 3.11223344556600001);
		//highest long
		p5 = new GPSCoordinate(53.305931, -8.989646, 8.11223344556);
		
		p6 = new GPSCoordinate(53.316051, -9.018173, 3.112233445);
		 */
		assertEquals(new GPSGridQuadrilateral(
				//low lat, low long
				new GPSCoordinate(53.265427, -9.135740),
				//low lat, high long
				new GPSCoordinate(53.265427, -8.989646),
				//high lat, high long
				new GPSCoordinate(53.355684, -8.989646),
				//high lat, low long
				new GPSCoordinate(53.355684, -9.135740)),
				poly.getBoundingQuadrilateral());
//		for(GPSCoordinate coord: poly.getBoundingQuadrilateral().getCornerPoints()) {
//			System.out.println(coord);
//		}
	}

	@Test
	void testGetBoundary() {
		fail("Not yet implemented");
	}

	@Test
	void testGetHighestLat() throws Exception {
		assertEquals(new BigDecimal(53.355684), poly.getBoundingQuadrilateral().getHighestLat());
	}

	@Test
	void testGetHighestLng() throws Exception {
		assertEquals(new BigDecimal(-8.989646), poly.getBoundingQuadrilateral().getHighestLong());
	}

	@Test
	void testGetLowestLng() throws Exception {
		assertEquals(new BigDecimal(-9.135740), poly.getBoundingQuadrilateral().getLowestLong());
	}

	@Test
	void testGetLowestLat() throws Exception {
		assertEquals(new BigDecimal(53.265427), poly.getBoundingQuadrilateral().getLowestLat());
	}

	@Test
	void testSetBoundary() {
		fail("Not yet implemented");
	}

}
