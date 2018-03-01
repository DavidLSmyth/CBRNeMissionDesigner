package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSGridRectangle;

class GPSGridRectangleTest {

	@BeforeEach
	void setUp() throws Exception {
		GPSCoordinate NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
		GPSCoordinate NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
		GPSCoordinate NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
		GPSCoordinate NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		GPSCoordinate NUIGcoord4 = new GPSCoordinate(53.2781237886, -9.0627913362);
		
		GPSGridRectangle r1 = new GPSGridRectangle(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3);  
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGPSGridRectangle() {
		fail("Not yet implemented");
	}

	@Test
	void testEnsureRectangle() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCorners() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBoxWidthMetres() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStandardizedGPSGridRectangle() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStandardizedGPSGridTranslator() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStandardizedGPSGridRotator() {
		fail("Not yet implemented");
	}

}
