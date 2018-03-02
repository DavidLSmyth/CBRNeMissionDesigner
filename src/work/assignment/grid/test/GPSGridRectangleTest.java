package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSGridRectangle;

class GPSGridRectangleTest {
	GPSGridRectangle r1;
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	
	@BeforeEach
	void setUp() throws Exception {
		NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
		NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
		NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
		NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		
		r1 = new GPSGridRectangle(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3);  
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetHighestLowest() {
		assertEquals(NUIGcoord2, r1.getHighestLat());
		assertEquals(NUIGcoord3, r1.getHighestLong());
		assertEquals(NUIGcoord1, r1.getLowestLong());
		assertEquals(NUIGcoord0, r1.getLowestLat());
	}

	@Test
	void testGPSGridRectangle() throws Exception {
	}

	@Test
	void testEnsureRectangle() throws Exception {
		assertTrue(r1.ensureRectangle());
		GPSCoordinate spoof = new GPSCoordinate(55.0, -9);
		GPSGridRectangle r2 = new GPSGridRectangle(NUIGcoord0,NUIGcoord1,NUIGcoord2, spoof);
		assertFalse(r2.ensureRectangle());
	}

	@Test
	void testGetCorners() {
		ArrayList<ArrayList<GPSCoordinate>> corners = r1.getCorners();
		ArrayList<GPSCoordinate> corner1 = new ArrayList<GPSCoordinate>();
		corner1.add(r1.getHighestLong());
		corner1.add(r1.getHighestLat());
		corner1.add(r1.getLowestLong());
		assertEquals(r1.getCorners().get(0), corner1);
	}

	@Test
	void testGetBoxWidthMetres() {
		assertEquals(600.0, r1.getBoxWidthMetres(), 200);
	}

	@Test
	void testGetStandardizedGPSGridRectangle() throws Exception {
		//GPSGridRectangle standardisedNUIGRect = r1.getStandardizedGPSGridRectangle();
		//assertEquals(0, standardisedNUIGRect.getLowestLat().getLat());
	}

	@Test
	void testGetStandardizedGPSGridTranslator() throws Exception {
		//53.2803630515, -9.0628107958
		assertEquals(-53.2803630515, r1.getStandardizedGPSGridTranslator().getLatDelta(), 0.0000000001);
		assertEquals(9.0628107958, r1.getStandardizedGPSGridTranslator().getLngDelta(), 0.0000000001);
		assertEquals(NUIGcoord0.getLat()-53.2803630515, r1.getStandardizedGPSGridTranslator().translate(NUIGcoord0).getLat(), 0.0000000001);
		assertEquals(NUIGcoord0.getLng()+9.0628107958, r1.getStandardizedGPSGridTranslator().translate(NUIGcoord0).getLng(), 0.0000000001);
//		System.out.println(r1.getStandardizedGPSGridTranslator().translate(NUIGcoord0));
//		System.out.println(r1.getStandardizedGPSGridTranslator().translate(NUIGcoord1));
//		System.out.println(r1.getStandardizedGPSGridTranslator().translate(NUIGcoord2));
//		System.out.println(r1.getStandardizedGPSGridTranslator().translate(NUIGcoord3));
	}

	@Test
	void testGetStandardizedGPSGridRotator() throws Exception {
		NUIGcoord0 = r1.getStandardizedGPSGridTranslator().translate(NUIGcoord0);
		NUIGcoord1 = r1.getStandardizedGPSGridTranslator().translate(NUIGcoord1);
		NUIGcoord2 = r1.getStandardizedGPSGridTranslator().translate(NUIGcoord2);
		NUIGcoord3 = r1.getStandardizedGPSGridTranslator().translate(NUIGcoord3);
		
		assertEquals(270, r1.getStandardizedGPSGridRotator().getTheta(), 0.5);
//		System.out.println(r1.getStandardizedGPSGridRotator().rotateClockwise(NUIGcoord0));
//		System.out.println(r1.getStandardizedGPSGridRotator().rotateClockwise(NUIGcoord1));
//		System.out.println(r1.getStandardizedGPSGridRotator().rotateClockwise(NUIGcoord2));
//		System.out.println(r1.getStandardizedGPSGridRotator().rotateClockwise(NUIGcoord3));
	}
	
	@Test
	void getHighestLat() {
		assertEquals(53.2803808514, NUIGcoord2.getLat(), 0.000001);
	}
	
	@Test
	void getHighestLong() {
		
	}
	
	@Test
	void getLowestLong() {
		
	}
	
	@Test
	void getLowestLat() {
		
	}

}
