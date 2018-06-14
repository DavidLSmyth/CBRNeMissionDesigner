package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateCosts;
import work.assignment.environmentalfactors.WindFactor;

class GPSCoordinateCostsTest {
	GPSCoordinate c1; 
	GPSCoordinate c2;
	WindFactor windfactor;
	
	@BeforeEach
	void setUp() throws Exception {
		c1 = new GPSCoordinate(53.2779115341, -9.0597334278);
		c2 = new GPSCoordinate(53.2812554869, -9.0627998557);
		windfactor = new WindFactor(-2, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetDistanceCost() throws Exception {
		//424m according to vincenty
		assertEquals(424, GPSCoordinateCosts.getDistanceCost(c1, c2), 10);
		
	}

	@Test
	void testGetLatDistanceCost() throws Exception {
		assertEquals(372.15, GPSCoordinateCosts.getLatDistanceCost(c1, c2), 1);
		
	}

	@Test
	void testGetLongDistanceCost() throws Exception {
		assertEquals(204.55, GPSCoordinateCosts.getLongDistanceCost(c1, c2), 1);
	}

	@Test
	void testGetTimeCostDoubleGPSCoordinateGPSCoordinate() throws Exception {
		assertEquals(42.4, GPSCoordinateCosts.getTimeCost(10, c1, c2), 0.5);
	}

	@Test
	void testGetTimeCostDoubleWindFactorGPSCoordinateGPSCoordinate() throws Exception {
		//-2,1
		//55 comes from:
		//       /|
		//  424 /_|  372
		//      204
		
		//       /|
		//   10 /_|  8.77
		//      4.82
		
		
		//  8.77 - 2 = 6.77
		//  4.82 + 1 = 5.82
		//sqrt(6.77**2 + 5.82**2) is effective velocity
		assertEquals(55, GPSCoordinateCosts.getTimeCost(10, windfactor, c1, c2), 1);
		assertEquals(42.4, GPSCoordinateCosts.getTimeCost(10, new WindFactor(0,0), c1, c2), 0.5);
	}

	@Test
	void testGetBatteryCost() {
		fail("Not yet implemented");
	}

}
