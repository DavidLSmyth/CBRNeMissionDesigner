package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateTranslator;
import work.assignment.grid.rectangle.GPSGridRectangle;
import work.assignment.grid.rectangle.StandardGPSGridRectangle;
import work.assignment.grid.rectangle.StandardisedGridRectangleMapper;

class StandardisedGridRectangleMapperTest {

	GPSGridRectangle r1;
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	StandardisedGridRectangleMapper m;
	StandardGPSGridRectangle standardRect;
	
	@BeforeEach
	void setUp() throws Exception {
		//lowestLat
		NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
		//lowestLong
		NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
		NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
		NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		
		r1 = new GPSGridRectangle(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3);
		m = new StandardisedGridRectangleMapper(r1);
		
		standardRect = new StandardGPSGridRectangle(m.convertToStandard(NUIGcoord0),
				m.convertToStandard(NUIGcoord1),
				m.convertToStandard(NUIGcoord2),
				m.convertToStandard(NUIGcoord3));
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testTranslateToStandard() throws Exception {
		GPSCoordinateTranslator t = r1.getStandardizedGPSGridTranslator();
		assertEquals(9.0628, t.getLngDelta(), 0.1);
		assertEquals(-53.278124, t.getLatDelta(), 0.1);
		assertEquals(t.translate(r1.getLowestLong()), new GPSCoordinate(0,0));
		
		
		assertEquals(new GPSCoordinate(0,0.002271), m.convertToStandard(NUIGcoord0)); 
		assertEquals(new GPSCoordinate(0,0), m.convertToStandard(NUIGcoord1));
		assertEquals(new GPSCoordinate(0.005729471145,0), m.convertToStandard(NUIGcoord2));
		assertEquals(new GPSCoordinate(0.005729471145,0.0022713361215817474), m.convertToStandard(NUIGcoord3));
	}
	
	@Test
	void testTranslateToOriginal() throws Exception {
		assertEquals(NUIGcoord0, m.convertToOriginal(new GPSCoordinate(0,0.002239347452125299))); 
		assertEquals(NUIGcoord1, m.convertToOriginal(new GPSCoordinate(0,0))); 
		assertEquals(NUIGcoord3, m.convertToOriginal(new GPSCoordinate(0.005729471145766105,0.0022713361215817474))); 
		assertEquals(NUIGcoord2, m.convertToOriginal(new GPSCoordinate(0.005729471153586988,3.1989569424317155E-5))); 
		
		assertEquals(NUIGcoord0, m.convertToOriginal(m.convertToStandard(NUIGcoord0)));
		assertEquals(NUIGcoord1, m.convertToOriginal(m.convertToStandard(NUIGcoord1)));
		assertEquals(NUIGcoord2, m.convertToOriginal(m.convertToStandard(NUIGcoord2)));
		assertEquals(NUIGcoord3, m.convertToOriginal(m.convertToStandard(NUIGcoord3)));
	}
	
	@Test
	void testGetTranslate() {
		//getLowestLong().getTranslatorFromThisTo(new GPSCoordinate(0,0))
		assertEquals(-53.2803630515, m.getTranslate().getLatDelta());
		assertEquals(9.0628107958, m.getTranslate().getLngDelta());
	}

//	@Test
//	void testSetTranslate() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetRotate() throws Exception {
		assertEquals(270.5, m.getRotate().getTheta(), 0.1);
		assertEquals(new GPSCoordinate(0,0), m.getRotate().getRotateCoord());
	}

//	@Test
//	void testSetRotate() {
//		fail("Not yet implemented");
//	}


}
