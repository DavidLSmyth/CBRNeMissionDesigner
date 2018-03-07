package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinateUtils;

class GPSCoordinateUtilsTest {

//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//	}

	@Test
	void testConvertLatDegreeDifferenceToMetres() {
		assertEquals(111000, GPSCoordinateUtils.convertLatDegreeDifferenceToMetres(1, 2), 500);
		assertEquals(11100, GPSCoordinateUtils.convertLatDegreeDifferenceToMetres(53, 53.1), 100);
	}

	@Test
	void testConvertLongDegreeDifferenceToMetres() {
		assertEquals(85000, GPSCoordinateUtils.convertLongDegreeDifferenceToMetres(1, 2, 40), 500);
		assertEquals(6713.7, GPSCoordinateUtils.convertLongDegreeDifferenceToMetres(-9, -9.1, 53.14), 100);
	}
	
//	@Test
//	void testgetLenMetresOneDegreeLat() {
//		assertEquals(111000, GPSCoordinateUtils.getLenMetresOneDegreeLat(40), 500);
//	}
//	
//	@Test
//	void testgetLenMetresOneDegreeLong() {
//		assertEquals(85000, GPSCoordinateUtils.getLenMetresOneDegreeLong(40), 500);
//	}

	@Test
	void testConvertMetresLatToDegrees() {
		assertEquals(800.0/111286, GPSCoordinateUtils.convertMetresLatToDegrees(800, 53.14), 800.0/67137.0 * 0.005);
	}

	@Test
	void testConvertMetresLongToDegrees() {
		assertEquals(500.0/67137.0, GPSCoordinateUtils.convertMetresLongToDegrees(500, 53.14), 500.0/67137.0 * 0.005);
	}

}
