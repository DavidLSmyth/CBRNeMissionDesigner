package work.assignment.grid.test;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateTranslator;

class GPSCoordinateTranslatorTest {
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
	
	GPSCoordinate NUIGcoord01;
	GPSCoordinate NUIGcoord11;
	GPSCoordinate NUIGcoord21;
	GPSCoordinate NUIGcoord31;
	
	GPSCoordinateTranslator t;
	GPSCoordinateTranslator t1;
	
	@BeforeEach
	void setUp() throws Exception {
		NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
		NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
		NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
		NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		
		coord0 = new GPSCoordinate(0, 0);
		coord1 = new GPSCoordinate(1, 2, 20.20);
		coord2 = new GPSCoordinate(1,-1, 30.90);
		coord3 = new GPSCoordinate(-2,-2, 40.1233);
		coord4 = new GPSCoordinate(-1,2, 30.5);
		
		//lowestLong
		NUIGcoord01 = new GPSCoordinate(53.2815, -9.06215);
		//highestLat
		NUIGcoord11 = new GPSCoordinate(53.28220, -9.0595);
		//highestLong
		NUIGcoord21 = new GPSCoordinate(53.279001, -9.0570580);
		//lowestLat
		NUIGcoord31 = new GPSCoordinate(53.27825, -9.05969);
		t = new GPSCoordinateTranslator(NUIGcoord01, new GPSCoordinate(0,0));
		t1 = new GPSCoordinateTranslator(NUIGcoord01, NUIGcoord11);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	@Test
//	void testGPSCoordinateTranslator() {
//		fail("Not yet implemented");
//		assertEquals(1,1);
//	}

	@Test
	void testTranslate() throws Exception {
		assertEquals(NUIGcoord11.subtract(NUIGcoord01), t.translate(NUIGcoord11));
		assertEquals(NUIGcoord21.subtract(NUIGcoord01), t.translate(NUIGcoord21));
		//assertEquals(NUIGcoord21.subtra)
	}

	@Test
	void testTranslateBack() throws Exception {
		assertEquals(NUIGcoord11, t.translateBack(t.translate(NUIGcoord11)));
		assertEquals(NUIGcoord21, t.translateBack(t.translate(NUIGcoord21)));
		assertEquals(NUIGcoord31, t.translateBack(t.translate(NUIGcoord31)));
		assertEquals(NUIGcoord21, t1.translateBack(t1.translate(NUIGcoord21)));
	}

	@Test
	void testGetLatDelta() {
		assertEquals((NUIGcoord11.getLat() - NUIGcoord01.getLat()), t1.getLatDelta(), 0.00000000001);
	}

	@Test
	void testGetLngDelta() {
		assertEquals((NUIGcoord11.getLng() - NUIGcoord01.getLng()), t1.getLngDelta(), 0.00000000001);
	}

//	@Test
//	void testGetLatDeltaMetres() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetLngDeltaMetres() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testToString() {
//		fail("Not yet implemented");
//	}

}
