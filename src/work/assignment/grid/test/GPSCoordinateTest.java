package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;

class GPSCoordinateTest {
	
	GPSCoordinate coord0;
	GPSCoordinate coord1;
	GPSCoordinate coord2;
	GPSCoordinate coord3;
	GPSCoordinate coord4;
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	GPSCoordinate NUIGcoord4;
	
	@BeforeEach
	void setUp() throws Exception {
		//Coordinates are lat, long
		NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
		NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
		NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
		NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		NUIGcoord4 = new GPSCoordinate(53.2781237886, -9.0627913362);
		
		coord0 = new GPSCoordinate(0, 0);
		coord1 = new GPSCoordinate(1, 2, 20.20);
		coord2 = new GPSCoordinate(1,-1, 30.90);
		coord3 = new GPSCoordinate(-2,-2, 40.1233);
		coord4 = new GPSCoordinate(-1,2, 30.5);

	}

	//[[[-9.0627913362,53.2781237886],[-9.0628107958,53.2803630515],[-9.057081263,53.2803808514],[-9.0570618034,53.2781415894],[-9.0627913362,53.2781237886]]]
			
	@AfterClass
	void tearDownClass() throws Exception {
	}

	@Test
	void testGPSCoordinateDoubleDouble() {
		try {
			new GPSCoordinate(500, 1000, 10.1); 
		}
		catch (Exception e){
			assertEquals("Latitude must lie in the range (-85.0, 85.0)", e.getMessage());
		}
		try {
			new GPSCoordinate(50, 1000, 10.1); 
		}
		catch (Exception e){
			assertEquals("Longitude must lie in the range (-180.0, 180.0)", e.getMessage());
		}
		
	}

	@Test
	void testGPSCoordinateDoubleDoubleDouble() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLatMetresToOther() {
		assertEquals(111000, coord0.getLatMetresToOther(coord1), 111000 * 0.01);
	}

	@Test
	void testGetLngMetresToOther() {
		assertEquals(85000 * 2, coord0.getLngMetresToOther(coord1), 85000 * 0.01);
	}

	@Test
	void testGetQuadrant() {
		assertEquals(0, coord1.getQuadrant());
		assertEquals(1, coord2.getQuadrant());
		assertEquals(2, coord3.getQuadrant());
		assertEquals(3, coord4.getQuadrant());
	}

	@Test
	void testGetLat() {
		assertEquals(NUIGcoord0.getLat(), 53.2781237886);
		assertEquals(NUIGcoord1.getLat(), 53.2803630515);
		
		assertEquals(coord0.getLat(), 0);
		assertEquals(coord1.getLat(), 1);
		assertEquals(coord2.getLat(), 1);
		assertEquals(coord3.getLat(), -2);
		assertEquals(coord4.getLat(), -1);
	}

	@Test
	void testSetLat() throws Exception {
		coord0.setLat(20.12345);
		coord1.setLat(-20.123345);
		coord2.setLat(0);
		
		assertEquals(coord0.getLat(), 20.12345);
		assertEquals(coord1.getLat(), -20.123345);
		assertEquals(coord2.getLat(), 0);
		
		
	}

	@Test
	void testGetLng() {
		assertEquals(NUIGcoord0.getLng(), -9.0627913362);
		assertEquals(NUIGcoord1.getLng(), -9.0628107958);
		
		assertEquals(coord0.getLng(), 0);
		assertEquals(coord1.getLng(), 1);
		assertEquals(coord2.getLng(), -1);
		assertEquals(coord3.getLng(), -2);
		assertEquals(coord4.getLng(), 2);
	}

	@Test
	void testSetLng() throws Exception {
		coord0.setLng(20.12345);
		coord1.setLng(-20.123345);
		coord2.setLng(0);
		
		assertEquals(20.12345, coord0.getLng());
		assertEquals(-20.123345, coord1.getLng());
		assertEquals(0, coord2.getLng());
	}

	@Test
	void testGetAlt() {
		assertEquals(null, coord0.getAlt());
		assertEquals(Double.valueOf(20.20), coord1.getAlt());
		assertEquals(Double.valueOf(30.90), coord2.getAlt());
	}

	@Test
	void testSetAlt() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTranslatorFromThisTo() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRotatorAboutThis() {
		fail("Not yet implemented");
	}

	@Test
	void testReflectLat() {
		coord0.reflectLat();
		coord1.reflectLat();
		NUIGcoord0.reflectLat();
		assertEquals(-0.0, coord0.getLat());
		assertEquals(-1.0, coord1.getLat());
		assertEquals(-53.2781237886, NUIGcoord0.getLat());
	}

	@Test
	void testReflectLng() {
		coord0.reflectLng();
		coord1.reflectLng();
		NUIGcoord0.reflectLng();
		assertEquals(-0.0, coord0.getLng());
		assertEquals(-1.0, coord1.getLng());
		assertEquals(-53.2781237886, NUIGcoord0.getLng());
	}

	@Test
	void testGetAngleRelativeToOriginXAxis() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAngle() {
		fail("Not yet implemented");
	}

}
