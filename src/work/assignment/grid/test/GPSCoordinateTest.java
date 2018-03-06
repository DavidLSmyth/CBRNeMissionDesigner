package work.assignment.grid.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateRotator;
import work.assignment.grid.GPSCoordinateTranslator;

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
	
	Double nullDouble = null;
	Integer nullInteger = null;
	
	@BeforeEach
	void setUp() throws Exception {
		//Coordinates are lat, long
		NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
		NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
		NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
		NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		
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
		try {
			new GPSCoordinate(500, 1000, null); 
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
	void testGetLatMetresToOther() {
		assertEquals(111000, coord0.getLatMetresToOther(coord1), 111000 * 0.01);
		assertEquals(300, NUIGcoord0.getLatMetresToOther(NUIGcoord1), 100);
	}

	@Test
	void testGetLngMetresToOther() {
		assertEquals(85000 * 2, coord0.getLngMetresToOther(coord1), 85000 * 0.01);
		assertEquals(500, NUIGcoord0.getLngMetresToOther(NUIGcoord3), 200);
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
		
		try {
			coord0.setLat(1000);
		}
		catch(Exception e) {
			assertEquals("Latitude" + " must lie in the range (" + GPSCoordinate.getLowerLatBound() + ", " + GPSCoordinate.getUpperLatBound() + ")", e.getMessage());
		}
		
		try {
			coord0.setLat(-200);
		}
		catch(Exception e) {
			assertEquals("Latitude" + " must lie in the range (" + GPSCoordinate.getLowerLatBound() + ", " + GPSCoordinate.getUpperLatBound() + ")", e.getMessage());
		}		
	}

	@Test
	void testGetLng() {
		assertEquals(NUIGcoord0.getLng(), -9.0627913362);
		assertEquals(NUIGcoord1.getLng(), -9.0628107958);
		
		assertEquals(coord0.getLng(), 0);
		assertEquals(coord1.getLng(), 2);
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
		
		try {
			coord0.setLng(1000);
		}
		catch(Exception e) {
			assertEquals("Longitude" + " must lie in the range (" + GPSCoordinate.getLowerLngBound() + ", " + GPSCoordinate.getUpperLngBound() + ")", e.getMessage());
		}
		
		try {
			coord0.setLng(-200);
		}
		catch(Exception e) {
			assertEquals("Longitude" + " must lie in the range (" + GPSCoordinate.getLowerLngBound() + ", " + GPSCoordinate.getUpperLngBound() + ")", e.getMessage());
		}		
	}

	@Test
	void testGetAlt() {
		assertEquals(null, coord0.getAlt());
		assertEquals(Double.valueOf(20.20), coord1.getAlt());
		assertEquals(Double.valueOf(30.90), coord2.getAlt());
	}

	@Test
	void testSetAlt() throws NumberFormatException, Exception {
		coord0.setAlt(0);
		//coord1.setAlt(null);
		coord2.setAlt(2);
		coord3.setAlt(20.56);
		assertEquals(Double.valueOf(0.0), coord0.getAlt());
		//assertEquals(null, coord1.getAlt());
		assertEquals(Double.valueOf(2), coord2.getAlt());
		assertEquals(Double.valueOf(20.56), coord3.getAlt());
		
		try {
			coord0.setAlt(Integer.valueOf(-5));
		}
		catch(Exception e) {
			assertEquals("Altitude" + " must lie in the range (" + GPSCoordinate.getLowerAltBound() + ", " + GPSCoordinate.getUpperAltBound() + ")", e.getMessage());
		}
		try {
			coord1.setAlt(Double.valueOf(-10.5));
		}
		catch(Exception e) {
			assertEquals("Altitude" + " must lie in the range (" + GPSCoordinate.getLowerAltBound() + ", " + GPSCoordinate.getUpperAltBound() + ")", e.getMessage());
		}
		
		coord0.setAlt(nullInteger);
		assertEquals(null, coord0.getAlt());
		
	}

	@Test
	void testGetTranslatorFromThisTo() throws Exception {
		GPSCoordinateTranslator t = coord1.getTranslatorFromThisTo(coord0);
		
		assertEquals(-2, t.getLngDelta());
		assertEquals(-1, t.getLatDelta());
		
		assertEquals(2 * 85000, t.getLngDeltaMetres(), 85000 * 0.01);
		assertNotEquals(2 * 10, t.getLngDeltaMetres(), 85000 * 0.01);
		
		assertEquals(1 * 111000, t.getLatDeltaMetres(), 111000 * 0.01);
		assertNotEquals(1 * 10000, t.getLngDeltaMetres(), 111000 * 0.01);
		
		coord2 = t.translate(coord2);
		assertEquals(0, coord2.getLat());
		assertEquals(-3, coord2.getLng());
		
		GPSCoordinate coord10 = new GPSCoordinate(2, 4);
		assertEquals(1, t.translate(coord10).getLat());
		assertEquals(2, t.translate(coord10).getLng());
		
	}
	
	@Test
	void testToString() {
		assertEquals("(0.0, 0.0)", coord0.toString());
		assertEquals("(1.0, 2.0, 20.2)", coord1.toString());
	}

	@Test
	void testEquals() throws Exception {
		GPSCoordinate c1 = new GPSCoordinate(3.123456789876, 5.9876543212, 3.112233445566);
		GPSCoordinate c2 = new GPSCoordinate(3.123456789876, 5.9876543212, 3.112233445566);
		GPSCoordinate c3 = new GPSCoordinate(3.1234567898760000001, 5.9876543212000001, 3.11223344556600001);
		GPSCoordinate c4 = new GPSCoordinate(3.1234567898760000001, -5.9876543212000001, 3.11223344556600001);
		GPSCoordinate c5 = new GPSCoordinate(3.123458, -5.98765439, 3.11223344556);
		GPSCoordinate c6 = new GPSCoordinate(-3.123458, -5.98765439, 3.112233445);
		GPSCoordinate c7 = new GPSCoordinate(-3.1234582, -5.9876544, 3.11223344);
		assertEquals(c1, c2);
		assertEquals(c2, c3);
		assertEquals(c4, c5);
		assertEquals(c6, c7);
		assertNotEquals(c1, coord1);
		assertNotEquals(NUIGcoord0, NUIGcoord1);
		assertNotEquals(NUIGcoord0, NUIGcoord2);
		assertNotEquals(NUIGcoord0, NUIGcoord3);
		assertNotEquals(NUIGcoord2, NUIGcoord3);
	}

	@Test
	void testGetRotatorAboutThis() throws Exception {
		GPSCoordinateRotator r = coord0.getRotatorAboutThis(45);
		coord2 = r.rotateAnticlockwise(coord2);
		assertEquals(45, r.getTheta());
		assertEquals(coord0, r.getRotateCoord());
		assertEquals(0, coord2.getLat(), Math.pow(10, -5));
		assertEquals(-Math.sqrt(2), coord2.getLng(), Math.sqrt(2) * 0.001);
		
		GPSCoordinate coord10 = new GPSCoordinate(5,5);
		GPSCoordinate coord11 = new GPSCoordinate(1,1);
		
		GPSCoordinateRotator r1 = coord11.getRotatorAboutThis(180);
		GPSCoordinate coord12 = r1.rotateAnticlockwise(coord10);
		assertEquals(new GPSCoordinate(-3, -3, null), coord12);
		
		GPSCoordinateRotator r2 = coord11.getRotatorAboutThis(90);
		GPSCoordinate coord13 = r2.rotateAnticlockwise(coord10);
		assertEquals(new GPSCoordinate(5, -3, null), coord13);
		
		GPSCoordinateRotator r3 = coord11.getRotatorAboutThis(270);
		GPSCoordinate coord14 = r3.rotateAnticlockwise(coord10);
		assertEquals(new GPSCoordinate(-3, 5, null), coord14);
		
		GPSCoordinateRotator r4 = coord11.getRotatorAboutThis(10);
		GPSCoordinate coord15 = r4.rotateAnticlockwise(coord10);
		assertEquals(new GPSCoordinate(4.63382372272 + 1, 3.24463830138 + 1, null), coord15);
		
		GPSCoordinateRotator r5 = coord11.getRotatorAboutThis(10);
		GPSCoordinate coord16 = r5.rotateClockwise(coord10);
		assertEquals(new GPSCoordinate(3.24463830138 + 1, 4.63382372272 + 1, null), coord16);
		
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
		assertEquals(-2.0, coord1.getLng());
		assertEquals(9.0627913362, NUIGcoord0.getLng());
	}

	@Test
	void testGetAngleRelativeToOriginXAxis() {
		
//		coord0 = new GPSCoordinate(0, 0);
//		coord1 = new GPSCoordinate(1, 2, 20.20);
//		coord2 = new GPSCoordinate(1,-1, 30.90);
//		coord3 = new GPSCoordinate(-2,-2, 40.1233);
//		coord4 = new GPSCoordinate(-1,2, 30.5);
		
		assertEquals(135.0, coord2.getAngleRelativeToOriginXAxis(), Math.pow(10, -4));
		assertEquals(26.5650512, coord1.getAngleRelativeToOriginXAxis(), Math.pow(10, -4));
		assertEquals(225.0, coord3.getAngleRelativeToOriginXAxis(), Math.pow(10, -4));
		assertEquals(333.4349488, coord4.getAngleRelativeToOriginXAxis(), Math.pow(10, -4));
	}

	@Test
	void testGetAcuteAngle() {
		try {
			assertEquals(108.4349488, GPSCoordinate.getAcuteAngle(coord2, coord0, coord1), 108 * 0.0000001);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		try {
			assertEquals(90.0, GPSCoordinate.getAcuteAngle(coord2, coord0, coord3), 90 * 0.0000001);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		try {
			assertEquals(161.5650512, GPSCoordinate.getAcuteAngle(coord2, coord0, coord4), 161.5650512 * 0.0000001);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		try {
			assertEquals(90, GPSCoordinate.getAcuteAngle(NUIGcoord0, NUIGcoord1, NUIGcoord2), 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	
	@Test
	void verifyAlt() {
		assertTrue(GPSCoordinate.verifyAlt(Integer.valueOf(2000)));
		assertTrue(GPSCoordinate.verifyAlt(Double.valueOf(2000.20)));
		Double nullValueDouble = null;
		assertTrue(GPSCoordinate.verifyAlt(nullValueDouble));
		Integer nullValueInt = null;
		assertTrue(GPSCoordinate.verifyAlt(nullValueInt));
		
		assertFalse(GPSCoordinate.verifyAlt(Integer.valueOf(-20)));
		assertFalse(GPSCoordinate.verifyAlt(Integer.valueOf(2000000000)));
		assertFalse(GPSCoordinate.verifyAlt(Double.valueOf(2000000000.12364645)));
	}
	
	@Test
	void verifyLat() {
		assertTrue(GPSCoordinate.verifyLat(10));
		assertTrue(GPSCoordinate.verifyLat(Double.valueOf(Double.valueOf(-20.30))));
		assertFalse(GPSCoordinate.verifyLat(Double.valueOf(200.20)));
		assertFalse(GPSCoordinate.verifyLat(Double.valueOf(-400.1234)));
	}
	
	@Test
	void verifyLng() {
		assertTrue(GPSCoordinate.verifyLng(10));
		assertTrue(GPSCoordinate.verifyLng(Double.valueOf(Double.valueOf(-20.30))));
		assertFalse(GPSCoordinate.verifyLng(Double.valueOf(200.20)));
		assertFalse(GPSCoordinate.verifyLng(Double.valueOf(-400.1234)));
	}

}
