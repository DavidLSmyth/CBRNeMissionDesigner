package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSGridRectangle;
import work.assignment.grid.StandardGPSGridRectangle;
import work.assignment.grid.StandardisedGridRectangleMapper;

class StandardGPSGridRectangleTest {
	GPSCoordinate p1;
	GPSCoordinate p2;
	GPSCoordinate p3;
	GPSCoordinate p4;
	StandardGPSGridRectangle testRect;
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	
	GPSCoordinate NUIGcoord01;
	GPSCoordinate NUIGcoord11;
	GPSCoordinate NUIGcoord21;
	GPSCoordinate NUIGcoord31;
	
	StandardGPSGridRectangle nuigTestRect;
	StandardGPSGridRectangle nuigTestRect1;
	GPSGridRectangle nuig;
	GPSGridRectangle nuig1;
	
	//write tests for this tomorrow: 
	//53.27825, -9.05969
	//53.2815, -9.06215
	//53.28220, -9.0595
	//53.279001, -9.0570580
	
	@BeforeEach
	void setUp() throws Exception {
		//origin
		p1 = new GPSCoordinate(0.0000000001, 0.00000001);
		//topLeft
		p2 = new GPSCoordinate(0.05, 0.00000023546);
		//bottomRight
		p3 = new GPSCoordinate(0.0000000001, 0.02287297);
		//topRight
		p4 = new GPSCoordinate(0.0500012364, 0.023045);
		
//		53.278, -9.0627913362
//		53.28061, -9.064
//		53.2815, -9.0582
//		53.27899, -9.0570618034
		
		testRect = new StandardGPSGridRectangle(p1, p3, p2, p4);
		//lowestLat
		NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
		//lowestLong
		NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
		//highestLat
		NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
		//highestLong
		NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		
		NUIGcoord01 = new GPSCoordinate(53.2815, -9.06215);
		//lowestLong
		NUIGcoord11 = new GPSCoordinate(53.28220, -9.0595);
		//highestLat
		NUIGcoord21 = new GPSCoordinate(53.279001, -9.0570580);
		//highestLong
		NUIGcoord31 = new GPSCoordinate(53.27825, -9.05969);
		
		nuig = new GPSGridRectangle(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3);
		System.out.println("initialising slanted rect");
		nuig1 = new GPSGridRectangle(NUIGcoord01, NUIGcoord11, NUIGcoord21, NUIGcoord31);
		
		nuigTestRect = nuig.getStandardizedGPSGridRectangle();
		nuigTestRect1 = nuig1.getStandardizedGPSGridRectangle();
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void testGetCorners() throws Exception {
		boolean cornersCorrect = true;
		StandardisedGridRectangleMapper m = new StandardisedGridRectangleMapper(nuig);
		System.out.println("\n\n\n Corners: \n\n\n" + nuigTestRect.getCorners());
//		
//		for(ArrayList<GPSCoordinate> corner: nuigTestRect.getCorners()) {
//			//lowestLat, lowestLong, highestLat
//			if(!corner.equals(new ArrayList<GPSCoordinate>(Arrays.asList(m.convertToStandard(NUIGcoord0), m.convertToStandard(NUIGcoord1), m.convertToStandard(NUIGcoord2)))) &&
//			//lowestLong, lowestLat, highestLong
//			   !corner.equals(new ArrayList<GPSCoordinate>(Arrays.asList(m.convertToStandard(NUIGcoord1), m.convertToStandard(NUIGcoord0), m.convertToStandard(NUIGcoord3)))) &&
//			//lowestLong, highestLat, highestLong
//			   !corner.equals(new ArrayList<GPSCoordinate>(Arrays.asList(m.convertToStandard(NUIGcoord1), m.convertToStandard(NUIGcoord2), m.convertToStandard(NUIGcoord3)))) &&
//			////lowestLat, highestLong, highestLat
//			   !corner.equals(new ArrayList<GPSCoordinate>(Arrays.asList(m.convertToStandard(NUIGcoord0), m.convertToStandard(NUIGcoord3), m.convertToStandard(NUIGcoord2))))
//				){
//				cornersCorrect = false;
//				
//				System.out.println("unmatching corner: " + corner);
//			}
//		}
//		assertTrue(cornersCorrect);
	}

	@Test
	void testVerifyOriginInRect() throws Exception {
		p1 = new GPSCoordinate(0.08, 0.00000001);
		try {
			testRect = new StandardGPSGridRectangle(p1, p3, p2, p4);
		}
		catch (Exception e) {
			assertEquals("Origin must be in rectangle", e.getMessage());
		}
		assertTrue(nuigTestRect1.verifyOriginInRect());
	}

	@Test
	void testVerifyPointsPositive() throws Exception {
		p1 = new GPSCoordinate(0.0000000001, 0.00000001);
		//topLeft
		p2 = new GPSCoordinate(-0.05, -0.00000023546);
		//bottomRight
		p3 = new GPSCoordinate(0.0000000001,- 0.02287297);
		//topRight
		p4 = new GPSCoordinate(-0.0500012364, 0.023045);
		try {
			testRect = new StandardGPSGridRectangle(p1, p3, p2, p4);
		}
		catch (Exception e) {
			assertEquals("All points must be positive", e.getMessage());
		}
	}

	@Test
	void testCreateCorner() {
		ArrayList<GPSCoordinate> testCorners = new ArrayList<GPSCoordinate>();
		testCorners.add(p1);
		testCorners.add(p2);
		testCorners.add(p3);
		assertEquals(testCorners, StandardGPSGridRectangle.createCorner(p1, p2, p3));
	}

	@Test
	void testVerifyRightAngles() throws Exception {
		p1 = new GPSCoordinate(0.00000000000003, 0.00000001);
		p2 = new GPSCoordinate(5,2);
		try {
			testRect = new StandardGPSGridRectangle(p1, p3, p2, p4);
		}
		catch (Exception e) {
			assertEquals("All angles must be 90 degrees", e.getMessage());
		}
		
	}

}
