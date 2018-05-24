package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;
import work.assignment.grid.rectangle.GPSGridRectangle;
import work.assignment.grid.rectangle.StandardGPSGridRectangle;
import work.assignment.grid.rectangle.StandardisedGridRectangleMapper;

class RegularTraversalGridQuadTest {
	
	GPSGridQuadrilateral nuigQuad;
	RegularTraversalGridQuad nuigGrid;

	GPSGridQuadrilateral nuigQuad1;
	RegularTraversalGridQuad nuigGrid1;
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	
	GPSCoordinate NUIGcoord0a;
	GPSCoordinate NUIGcoord1a;
	GPSCoordinate NUIGcoord2a;
	GPSCoordinate NUIGcoord3a;
	
	@BeforeEach
	void setUp() throws Exception {
		
		NUIGcoord0 = new GPSCoordinate(53.2779115341, -9.0597334278);
		NUIGcoord1 = new GPSCoordinate(53.2812554869, -9.0627998557);
		NUIGcoord2 = new GPSCoordinate(53.2823423226, -9.0594844171);
		NUIGcoord3 = new GPSCoordinate(53.2789984548, -9.0564179892);

		
		//[[[-9.0600289846,53.2778808184],[-9.0619335669,53.27879866],[-9.0589139914,53.2810387215],[-9.0570094091,53.2801209281],[-9.0600289846,53.2778808184]]]	
		NUIGcoord0a = new GPSCoordinate(53.2778808184, -9.0600289846);
		NUIGcoord1a = new GPSCoordinate(53.27879866, -9.0619335669);
		NUIGcoord2a = new GPSCoordinate(53.2810387215, -9.0589139914);
		NUIGcoord3a = new GPSCoordinate(53.2801209281, -9.0570094091);
		
		nuigQuad = new GPSGridQuadrilateral(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3);
		nuigGrid = new RegularTraversalGridQuad(nuigQuad, 20, 25, 20);

		nuigQuad1 = new GPSGridQuadrilateral(NUIGcoord0a, NUIGcoord1a, NUIGcoord2a, NUIGcoord3a);
		nuigGrid1 = new RegularTraversalGridQuad(nuigQuad1, 30, 25, 30);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetNoPoints() {
		int noLatPoints = (int) Math.ceil(nuigQuad.getLowestLat().getMetresToOther(nuigQuad.getHighestLong()) / nuigGrid.getLatSpacingMetres());
		int noLongPoints = (int) Math.ceil(nuigQuad.getLowestLat().getMetresToOther(nuigQuad.getLowestLong()) / nuigGrid.getLngSpacingMetres());
		System.out.println("test no lat points: " + noLatPoints + "test no long points: " + noLongPoints);
		assertEquals(noLatPoints * noLongPoints, 
		nuigGrid.getNoPoints());
		
		noLatPoints = (int) Math.ceil(nuigQuad1.getLowestLat().getMetresToOther(nuigQuad1.getHighestLong()) / nuigGrid1.getLatSpacingMetres());
		noLongPoints = (int) Math.ceil(nuigQuad1.getLowestLat().getMetresToOther(nuigQuad1.getLowestLong()) / nuigGrid1.getLngSpacingMetres());
		System.out.println("test no lat points: " + noLatPoints + "test no long points: " + noLongPoints);
		assertEquals(noLatPoints * noLongPoints, 
		nuigGrid1.getNoPoints());
	}

	@Test
	void testRegularTraversalGridQuad() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNoLngPoints() {
		int noLongPoints = (int) Math.ceil(nuigQuad.getLowestLat().getMetresToOther(nuigQuad.getLowestLong()) / nuigGrid.getLngSpacingMetres());
		assertEquals(noLongPoints, nuigGrid.getNoLngPoints());
	}
//
//	@Test
//	void testSetNoLngPoints() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetNoLatPoints() {
		int noLatPoints = (int) Math.ceil(nuigQuad.getLowestLat().getMetresToOther(nuigQuad.getHighestLong()) / nuigGrid.getLatSpacingMetres());
		assertEquals(noLatPoints, nuigGrid.getNoLatPoints());
	}

//	@Test
//	void testSetNoLatPoints() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetLngSpacingMetres() {
		assertEquals(20, nuigGrid.getLngSpacingMetres());
	}

	@Test
	void testSetLngSpacingMetres() {
		nuigGrid.setLngSpacingMetres(10);
		assertEquals(10, nuigGrid.getLngSpacingMetres());
	}

	@Test
	void testGetLatSpacingMetres() {
		assertEquals(25, nuigGrid.getLatSpacingMetres());
	}

	@Test
	void testSetLatSpacingMetres() {
		nuigGrid.setLatSpacingMetres(10);
		assertEquals(10, nuigGrid.getLatSpacingMetres());
	}

	@Test
	void testGetAltitude() {
		assertEquals(20, nuigGrid.getAltitude());
	}
//
//	@Test
//	void testSetAltitude() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetLatPointLngPoint() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBoundingRectangle() {
		fail("Not yet implemented");
	}

//	@Test
//	void testSetBoundingRectangle() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetGridPoints() {
		System.out.println("\n\n\n\n Grid points for grid A: ");
		for(GPSCoordinate c: nuigGrid.getPoints()) {
			System.out.println(c);
		}

		System.out.println("\n\n\n\n\n\n\n Grid points for grid B: ");
		for(GPSCoordinate c: nuigGrid1.getPoints()) {
			System.out.println(c);
		}
		
	}

	@Test
	void testGetLatSpacingDegrees() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLngSpacingDegrees() {
		fail("Not yet implemented");
	}

}
