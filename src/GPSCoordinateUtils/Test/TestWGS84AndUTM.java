package GPSCoordinateUtils.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgeo.proj4j.*;

import GPSUtils.CartesianCoordinate;
import GPSUtils.UTMCoordinate;
import GPSUtils.UTMToWGS84;
import GPSUtils.WGS84Coordinate;
import GPSUtils.WGS84ToUTM;
import agent.vehicle.uav.UAVTelemetryMessage;


class TestWGS84AndUTM {
	
	UTMCoordinate topLeftUTM;
	UTMCoordinate topRightUTM; 
	UTMCoordinate bottomRightUTM;
	UTMCoordinate bottomLeftUTM;    
	
	WGS84Coordinate topLeftWGS; 
	WGS84Coordinate topRightWGS; 
	WGS84Coordinate bottomLeftWGS; 
	WGS84Coordinate bottomRightWGS;  
    
	@BeforeEach
	void setUp() throws Exception {
		topLeftUTM = new UTMCoordinate(new BigDecimal(495900),new BigDecimal(5903545), new BigDecimal(100), 29, 'U');
		topRightUTM = new UTMCoordinate(new BigDecimal(496133),new BigDecimal(5903545), new BigDecimal(100), 29, 'U');
		bottomLeftUTM = new UTMCoordinate(new BigDecimal(495900),new BigDecimal(5903089), new BigDecimal(100), 29, 'U');
		bottomRightUTM = new UTMCoordinate(new BigDecimal(496133),new BigDecimal(5903089), new BigDecimal(100), 29, 'U');
		
		topLeftWGS = new WGS84Coordinate(new BigDecimal(53.28111715), new BigDecimal(9.06149355));
		topRightWGS = new WGS84Coordinate(new BigDecimal(53.28111891), new BigDecimal(9.05799892));
		bottomLeftWGS = new WGS84Coordinate(new BigDecimal(53.27701817), new BigDecimal(9.06148767));
		bottomRightWGS = new WGS84Coordinate(new BigDecimal(53.27701992), new BigDecimal(9.05799337));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	* @example
//	 *   var latlong = new LatLon(48.8582, 2.2945);
//	 *   var utmCoord = latlong.toUtm(); // utmCoord.toString(): '31 N 448252 5411933'

	@Test
	void testGetUTMFromWGS84() throws Exception {
		WGS84Coordinate test = new WGS84Coordinate(48.8582, 2.2945, 100);
		
		System.out.println("Converted test : expected '31 N 448252 5411933'");
		//pretty close!
		System.out.println(WGS84ToUTM.convert(test));
		
		
		System.out.println("Converted test MVType: expected '31 N 448252 5411933'");
		System.out.println(WGS84ToUTM.convertMVTypeScript(test));

		
		
		
		assertEquals(topLeftWGS.toUTM(), topLeftUTM);
		
	}
	
	@Test
	void testGetWGS84FromUTM() throws Exception {
		UTMCoordinate test = new UTMCoordinate(448252, 5411933, 100, 31, 'U');
		System.out.println("\n\nConverted test MVType: expected '48.8582, 2.2945, 100'");
		System.out.println(UTMToWGS84.convert(test, "WGS84"));

		System.out.println("Converted test MVType: expected '48.8582, 2.2945, 100'");
		System.out.println(UTMToWGS84.convertMVTypeScript(test));
		
		//System.out.println(UTMT.convert(test));
		assertEquals(topLeftUTM.toWGS84(), topLeftWGS);
	}
	
	 @Test
	    public void testConvert() throws Exception {
	        System.out.println("convert");
	        
	        BigDecimal easting = new BigDecimal(465005.3449);
	        BigDecimal northing = new BigDecimal(9329005.2);
	        char hemisphere = 'N';
	        int zone = 48;
	        char zoneLetter = 'X';
	        
	        UTMCoordinate utm = new UTMCoordinate(easting, northing, new BigDecimal(100), zone, zoneLetter);
	        String datum = "WGS84";
	        WGS84Coordinate expResult = new WGS84Coordinate(new BigDecimal(84.0), 
	            new BigDecimal(102.0));
	        WGS84Coordinate result = UTMToWGS84.convert(utm, datum);
//	        result.setLatitude(result.getLatitude().setScale(0, RoundingMode.HALF_UP));
//	        result.setLongitude(result.getLongitude().setScale(0, RoundingMode.HALF_UP));
	        System.out.println("\n\nExpected 84, 102");
	        System.out.println("Got: " + result);
	        
	        result = new WGS84Coordinate(61.0, -164.0, 100);
	        System.out.println("Initial WGS84: " + result);
	        utm = WGS84ToUTM.convert(result);
	        System.out.println("First conversion: " + utm);
	        result = UTMToWGS84.convert(utm, "WGS84");
	        System.out.println("Second conversion (back): " + result);
	    }
	
}
