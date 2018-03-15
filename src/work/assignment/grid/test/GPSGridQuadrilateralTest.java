package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

class GPSGridQuadrilateralTest {
	
	GPSGridQuadrilateral nuigQuad;
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	
	@BeforeEach
	void setUp() throws Exception {
		NUIGcoord0 = new GPSCoordinate(53.2779115341, -9.0597334278);
		NUIGcoord1 = new GPSCoordinate(53.2812554869, -9.0627998557);
		NUIGcoord2 = new GPSCoordinate(53.2823423226, -9.0594844171);
		NUIGcoord3 = new GPSCoordinate(53.2789984548, -9.0564179892);
		
		nuigQuad = new GPSGridQuadrilateral(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetLowestLong() {
		assertEquals(NUIGcoord1, nuigQuad.getLowestLong());
	}

	@Test
	void testGetHighestLong() {
		assertEquals(NUIGcoord3, nuigQuad.getHighestLong());
	}

	@Test
	void testGetHighestLat() {
		assertEquals(NUIGcoord2, nuigQuad.getHighestLat());
	}

	@Test
	void testGetLowestLat() {
		assertEquals(NUIGcoord0, nuigQuad.getLowestLat());
	}

//	@Test
//	void testToString() {
//		fail("Not yet implemented");
//	}

}
