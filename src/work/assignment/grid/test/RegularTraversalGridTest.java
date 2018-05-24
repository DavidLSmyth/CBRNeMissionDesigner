package work.assignment.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateUtils;
import work.assignment.AgentType;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;
import work.assignment.grid.rectangle.GPSGridRectangle;
import work.assignment.grid.rectangle.RegularTraversalGrid;
import work.assignment.grid.rectangle.StandardGPSGridRectangle;
import work.assignment.grid.rectangle.StandardisedGridRectangleMapper;

class RegularTraversalGridTest {

	GPSGridQuadrilateral nuig;
	StandardGPSGridRectangle standard;
	
	StandardisedGridRectangleMapper mapper;
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	RegularTraversalGrid g1;
	RegularTraversalGridQuad g2;
	
	@BeforeEach
	void setUp() throws Exception {
		//bottomleft
//		NUIGcoord0 = new GPSCoordinate(53.2781237886, -9.0627913362);
//		//origin
//		NUIGcoord1 = new GPSCoordinate(53.2803630515, -9.0628107958);
//		NUIGcoord2 = new GPSCoordinate(53.2803808514, -9.057081263);
//		NUIGcoord3 = new GPSCoordinate(53.2781415894, -9.0570618034);
		
		NUIGcoord0 = new GPSCoordinate(53.2779115341, -9.0597334278);
		NUIGcoord1 = new GPSCoordinate(53.2812554869, -9.0627998557);
		NUIGcoord2 = new GPSCoordinate(53.2823423226, -9.0594844171);
		NUIGcoord3 = new GPSCoordinate(53.2789984548, -9.0564179892);
		//NUIGcoord4 = new GPSCoordinate(53.2823423226, -9.0594844171);
		
		//		,[],[-9.0594844171,53.2823423226],[-9.0564179892,53.2789984548],[-9.0597334278,53.2779115341]] )
				
		nuig = new GPSGridQuadrilateral(NUIGcoord1, NUIGcoord0, NUIGcoord2, NUIGcoord3);
//		System.out.println("Box height: " + nuig.getBoxHeightMetres());
//		//250.6016826838075
//		System.out.println("Box width: " + nuig.getBoxWidthMetres());
//		//490.90646103607935
//		mapper = new StandardisedGridRectangleMapper(nuig);
//
//		standard = new StandardGPSGridRectangle(mapper.convertToStandard(NUIGcoord0), 
//				mapper.convertToStandard(NUIGcoord1),
//				mapper.convertToStandard(NUIGcoord2),
//				mapper.convertToStandard(NUIGcoord3)); 
//		System.out.println("standardized box height: " + standard.getBoxHeightMetres());
//		System.out.println("standardized box width: " + standard.getBoxWidthMetres());
		//20m lat spacing, 25m long spacing
		
//		g1 = new RegularTraversalGrid(standard, 20, 20, 20);
//		g2 = new RegularTraversalGrid(standard, 50, 50, 20);
		
		g2 = new RegularTraversalGridQuad(nuig, 50, 50, 20);
		
		System.out.println(g1.getBoundingRectangle().getBoxHeightMetres());
		//636.1431821827633 metres
		System.out.println(g1.getBoundingRectangle().getBoxWidthMetres());
		//193.9493914218654 metres
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRegularTraversalGrid() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}
	
	@Test
	void generatePoints() throws Exception {
		System.out.println("Generating points");
		for(GPSCoordinate p: g2.getPoints()) {
			System.out.println(mapper.convertToOriginal(p).getLat() + ", " + mapper.convertToOriginal(p).getLng());
		}
		System.out.println("Generating unstandardized points");
		for(GPSCoordinate p: g2.getPoints()) {
			System.out.println(p.getLat() + ", " + p.getLng());
		}
		
		
	}
	
	@Test
	void testGetNoLngPoints() {
			assertEquals(10, g1.getNoLngPoints());
	}

	@Test
	void testGetNoLatPoints() {
		assertEquals(32, g1.getNoLatPoints());
	}


	@Test
	void testGetLngSpacing() {
		assertEquals(50, g2.getLngSpacingMetres());
		assertEquals(20, g1.getLngSpacingMetres());
//		assertEquals(GPSCoordinateUtils.convertMetresLongToDegrees(50, g2.getBoundingRectangle()), g2.getLngSpacingDegrees());
//		assertEquals(GPSCoordinateUtils.convertMetresLongToDegrees(20), g1.getLngSpacingDegrees());
	}

	@Test
	void testGetLatSpacing() {
		assertEquals(50, g2.getLatSpacingMetres());
		assertEquals(20, g1.getLatSpacingMetres());
//		assertEquals(GPSCoordinateUtils.convertMetresLatToDegrees(50), g2.getLatSpacingDegrees());
//		assertEquals(GPSCoordinateUtils.convertMetresLatToDegrees(20), g1.getLatSpacingDegrees());
	}
	
	@Test
	void testGetAltitude() {
		assertEquals(20, g2.getAltitude());
	}
//	@Test
//	void testSetAltitude() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetLatPointLongPoint() throws Exception {
		assertEquals(new GPSCoordinate(0, 0, 20.0), g1.getLatPointLngPoint(0,0));
		assertEquals(new GPSCoordinate(0, g1.getLngSpacingDegrees(), 20.0), g1.getLatPointLngPoint(0, 1));
		assertEquals(new GPSCoordinate(g1.getLatSpacingDegrees(), 0, 20.0), g1.getLatPointLngPoint(1, 0));
	}
	
	@Test
	void testGetBottomRight() {
		
	}
	@Test
	void testGetTopRight() {
		
	}
	@Test
	void testGetTopLeft() {
//		StandardGPSGridRectangle r = g1.getBoundingRectangle();
		//assertEquals()
	}
	
	@Test
	void testGetBoundingRectangle() throws Exception {
//		StandardGPSGridRectangle r = g1.getBoundingRectangle();
//		System.out.println("Bounding rectangle corners: " + r.getCorners());
//		assertEquals(new GPSCoordinate(0, GPSCoordinate.convertMetresLongToDegrees(nuig.getLowestLong().getLngMetresToOther(nuig.getLowestLat()))),
//											r.getBottomRight());
//		assertEquals(r.getBoxHeightMetres(), nuig.getBoxHeightMetres(), 50);
//		assertEquals(r.getBoxWidthMetres(), nuig.getBoxWidthMetres(), 50);
		
	}

	@Test
	void testGetGridPoints() {
		ArrayList<GPSCoordinate> gpoints = g1.getGridPoints();
		System.out.println(gpoints);
	}

	@Test
	void testTraversalGridIntArrayListOfGPSCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCost() {
		System.out.println(g1.getLatPointLngPoint(0, 0));
		System.out.println(g1.getLatPointLngPoint(0, 1));
		System.out.println(g1.getLatPointLngPoint(1, 0));
		for(int i = 0; i < 35; i++) {
			System.out.println("getting " + i/g1.getNoLngPoints() + ", " + i % g1.getNoLngPoints());
			System.out.println(g1.getLatPointLngPoint(i/g1.getNoLngPoints(), i % g1.getNoLngPoints()));
		}
		//change this to use the cost class.
//		assertEquals(g1.getLngSpacingMetres(), RegularTraversalGrid.getCost(g1.getLatPointLngPoint(0, 0), g1.getLatPointLngPoint(0, 1), AgentType.AIR_VEHICLE));
//		assertEquals(g1.getLatSpacingMetres(), RegularTraversalGrid.getCost(g1.getLatPointLngPoint(0, 0), g1.getLatPointLngPoint(1, 0), AgentType.AIR_VEHICLE));
//		assertEquals(g1.getLatSpacingMetres() * 2, RegularTraversalGrid.getCost(g1.getLatPointLngPoint(0, 0), g1.getLatPointLngPoint(2, 0), AgentType.AIR_VEHICLE));
//		assertEquals(Math.sqrt(Math.pow(2 * g1.getLatSpacingMetres(), 2) + Math.pow(2 * g1.getLngSpacingMetres(), 2)),
//				RegularTraversalGrid.getCost(g1.getLatPointLngPoint(0, 0), g1.getLatPointLngPoint(2, 2), AgentType.AIR_VEHICLE));
	}


	@Test
	void testGetPathCost() {
		ArrayList<GPSCoordinate> testPath = new ArrayList<GPSCoordinate>();
		testPath.add(g1.getLatPointLngPoint(0, 0));
		testPath.add(g1.getLatPointLngPoint(1, 0));
		testPath.add(g1.getLatPointLngPoint(5, 0));
		testPath.add(g1.getLatPointLngPoint(0, 3));
		testPath.add(g1.getLatPointLngPoint(0, 0));
		//change this to use the cost class
//		assertEquals(g1.getLatPointLngPoint(0, 0).getMetresToOther(g1.getLatPointLngPoint(1, 0)) + 
//				g1.getLatPointLngPoint(1, 0).getMetresToOther(g1.getLatPointLngPoint(5, 0)) +
//				g1.getLatPointLngPoint(5, 0).getMetresToOther(g1.getLatPointLngPoint(0, 3)) +
//				g1.getLatPointLngPoint(0, 3).getMetresToOther(g1.getLatPointLngPoint(0, 0)),				
//				RegularTraversalGrid.getPathCost(testPath, AgentType.AIR_VEHICLE));	
	}

	@Test
	void testGetNoPoints() {
		assertEquals(320, g1.getNoPoints());
	}



	@Test
	void testGetPoints() {
		fail("Not yet implemented");
	}

}
