package GPSUtils.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.geotools.io.NumberedLineWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgeo.proj4j.CoordinateReferenceSystem;

import GPSUtils.CartesianCoordinate;
import GPSUtils.UTMCoordinate;
import GPSUtils.UTMToWGS84;
import GPSUtils.grid.GridGenerator;

class UTMGridGeneratorTest {
	CartesianCoordinate topLeft;
	CartesianCoordinate topRight; 
	CartesianCoordinate bottomRight;
	CartesianCoordinate bottomLeft;
	BigDecimal latSpacing;
	BigDecimal lngSpacing;
	
	@BeforeEach
	void setUp() throws Exception {
		//E, N
		topLeft = new UTMCoordinate(new BigDecimal(495900),new BigDecimal(5903545), new BigDecimal(100), 29, 'U');
		topRight = new UTMCoordinate(new BigDecimal(496133),new BigDecimal(5903545), new BigDecimal(100), 29, 'U');
		bottomLeft = new UTMCoordinate(new BigDecimal(495900),new BigDecimal(5903089), new BigDecimal(100), 29, 'U');
		bottomRight = new UTMCoordinate(new BigDecimal(496133),new BigDecimal(5903089), new BigDecimal(100), 29, 'U');
		latSpacing = new BigDecimal(100);
		lngSpacing = new BigDecimal(100);
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void testGenerateGridInRectangle() throws Exception {
		List<CartesianCoordinate> gridCoords = GridGenerator.generateGridInRectangle(topLeft, topRight, bottomRight, bottomLeft,
				latSpacing, lngSpacing);
		for(CartesianCoordinate coord: gridCoords) {
			//System.out.println(coord + "\n");
			//System.out.println(UTMToWGS84.convert((UTMCoordinate) coord, "WGS84").latLngRep());
			System.out.println(UTMToWGS84.convertMVTypeScript((UTMCoordinate) coord).latLngRep());
		}
	}
	
	@Test
	void testCompareBigDecimals() {
		assertTrue(GridGenerator.compareBigDecimals(topLeft.getX(), bottomLeft.getX()));
	}
}









