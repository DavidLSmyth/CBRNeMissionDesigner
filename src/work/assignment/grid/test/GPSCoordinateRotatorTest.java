package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateRotator;
import GPSUtils.GPSCoordinateUtils;

class GPSCoordinateRotatorTest {
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
	
	GPSCoordinateRotator r;
	GPSCoordinateRotator r1;
	
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
		NUIGcoord01 = new GPSCoordinate(53.2815000000, -9.062150000);
		//highestLat
		NUIGcoord11 = new GPSCoordinate(53.28220, -9.0595);
		//highestLong
		NUIGcoord21 = new GPSCoordinate(53.279001, -9.0570580);
		//lowestLat
		NUIGcoord31 = new GPSCoordinate(53.27825, -9.05969);
		//r = new GPSCoordinateRotator(NUIGcoord01, 30);
		r1 = new GPSCoordinateRotator(NUIGcoord01, 90);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	@Test
//	void testGPSCoordinateRotator() {
//		fail("Not yet implemented");
//	}

//	 def angle_between_points( p0, p1, p2 ):
//		      a =  math.pow(p1.x-p0.x,2) + math.pow(p1.y-p0.y,2)
//		      b = math.pow(p1.x-p2.x,2) + math.pow(p1.y-p2.y,2)
//		      c = math.pow(p2.x-p0.x,2) + math.pow(p2.y-p0.y,2)
//		      return math.acos((a+b-c)/math.sqrt(4 * a * b))
	@Test
	void testRotateAnticlockwise() throws Exception{
		System.out.println("anticlockwise rotation: " + r1.rotateAnticlockwise(NUIGcoord11));
		System.out.println("clockwise rotation: " + r1.rotateClockwise(NUIGcoord11));
		GPSCoordinate antiClockwiseRot = r1.rotateAnticlockwise(NUIGcoord11);
		System.out.println("acute angle: " + GPSCoordinateUtils.getAcuteAngle(antiClockwiseRot, NUIGcoord01, NUIGcoord11));
		System.out.println("acute angle1: " + GPSCoordinateUtils.getAcuteAngle(NUIGcoord11, NUIGcoord01, NUIGcoord31));
		System.out.println("acute angle2: " + GPSCoordinateUtils.getAcuteAngle(NUIGcoord31, NUIGcoord01, NUIGcoord11));
		System.out.println(antiClockwiseRot.getMetresToOther(NUIGcoord01));
		System.out.println(NUIGcoord11.getMetresToOther(NUIGcoord01));
		//assertEquals(NUIGcoord11.add(new GPSCoordinate(0.0019934958, 0.00188108332)), r1.rotateAnticlockwise(NUIGcoord11));
	}

	@Test
	void testRotateClockwise() throws Exception {
		System.out.println("clockwise rotation: " + r1.rotateClockwise(NUIGcoord11));
		assertEquals(NUIGcoord11.add(new GPSCoordinate(0.0019934958, 0.00188108332)), r1.rotateClockwise(NUIGcoord11));
	}

	@Test
	void testGetTheta() {
		assertEquals(30, r.getTheta());
		assertEquals(45, r1.getTheta());
	}

	@Test
	void testSetTheta() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRotateCoord() {
		assertEquals(NUIGcoord01, r.getRotateCoord());
		assertEquals(NUIGcoord01, r1.getRotateCoord());
	}

	@Test
	void testSetRotateCoord() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
