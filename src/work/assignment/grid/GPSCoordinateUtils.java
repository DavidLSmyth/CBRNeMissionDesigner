package work.assignment.grid;

//https://msi.nga.mil/msisitecontent/staticfiles/calculators/degree.html
//inspect degree.html
public class GPSCoordinateUtils {
	static double m1 = 111132.92;
	static double m2 = -559.82;
	static double m3 = 1.175;
	static double m4 = -0.0023;
	
	static double p1 = 111412.84;
	static double p2 = -93.5;
	static double p3 = 0.118;
	
	public static double degToRadian(double degrees) {
		return Math.toRadians(degrees);
	}
	public static double radianToDeg(double radians) {
		return Math.toDegrees(radians);
	}
	//private
	private static double getLenMetresOneDegreeLat(double lat) {
		lat = degToRadian(lat);
		return (GPSCoordinateUtils.m1 + (GPSCoordinateUtils.m2 * Math.cos(2 * lat)) + (GPSCoordinateUtils.m3 * Math.cos(4 * lat)) +
		(GPSCoordinateUtils.m4 * Math.cos(6 * lat)));
	}
	
	//private
	private static double getLenMetresOneDegreeLong(double lat) {
		lat = degToRadian(lat);
		return (GPSCoordinateUtils.p1 * Math.cos(lat)) + (GPSCoordinateUtils.p2 * Math.cos(3 * lat)) +
		(GPSCoordinateUtils.p3 * Math.cos(5 * lat));
	}
	
	public static double convertLatDegreeDifferenceToMetres(double lat1, double lat2) {
				// longitude calculation term 3
		//given two degrees of latitude, calculates the distance in metres between them.
		//assuming lat1 and lat2 are 'close'
		return Math.abs(lat1 - lat2) * getLenMetresOneDegreeLat(lat1);
	}
	
	public static double convertLatDegreeDifferenceToMetresSigned(double lat1, double lat2) {
		// longitude calculation term 3
	//given two degrees of latitude, calculates the distance in metres between them.
	//assuming lat1 and lat2 are 'close'
	return (lat1 - lat2) * getLenMetresOneDegreeLat(lat1);
	}
	
	public static double convertLongDegreeDifferenceToMetres(double long1, double long2, double lat) {
		// longitude calculation term 3
		//given two degrees of latitude, calculates the distance in metres between them.
		//assuming lat1 and lat2 are 'close'
		return Math.abs(long1 - long2) * getLenMetresOneDegreeLong(lat);
	}
	
	public static double convertLongDegreeDifferenceToMetresSigned(double long1, double long2, double lat) {
		// longitude calculation term 3
		//given two degrees of latitude, calculates the distance in metres between them.
		//assuming lat1 and lat2 are 'close'
		return (long1 - long2) * getLenMetresOneDegreeLong(lat);
	}
	
	public static double convertMetresLatToDegrees(double metres, double lat) {
		//given a distance in metres at a certain latitude, convert to a degree delta
		return metres / getLenMetresOneDegreeLat(lat);
		
	}
	
	public static double convertMetresLongToDegrees(double metres, double lat) {
		return metres/getLenMetresOneDegreeLong(lat);
	}

	public static double getAcuteAngle(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3) throws Exception {
		//first check p1!=p2, p2!=p3, p1!=p3
		
		//Assuming all lats positive/negative and all longs positive/negative
		//angle calculated is /_p1 p2 p3
		//translate p2 back to 0
		//rotate p3 to x-axis
		//find angle between vector p1 and x-axis
		//first get a translator from p2 back to 0
		GPSCoordinateTranslator t = p2.getTranslatorFromThisTo(new GPSCoordinate(0,0));		
		p1 = t.translate(p1);
		p3 = t.translate(p3);
		
		//set p3 as the angle closer 0 deg from positive x - axis
		if(p1.getAngleRelativeToOriginXAxis() < p3.getAngleRelativeToOriginXAxis()) {
			GPSCoordinate temp = new GPSCoordinate(p1.getLat(), p1.getLng(), p1.getAlt());
			p1 = new GPSCoordinate(p3.getLat(), p3.getLng(), p3.getAlt());
					//p3.clone();
			p3 = new GPSCoordinate(temp.getLat(), temp.getLng(), temp.getAlt());
					//temp.clone();
		}
//		System.out.println("p1 Angle relative to originx axis: " + p1.getAngleRelativeToOriginXAxis());
//		System.out.println("p3 Angle relative to originx axis: " + p3.getAngleRelativeToOriginXAxis());
		double returnAngle = p1.getAngleRelativeToOriginXAxis() - p3.getAngleRelativeToOriginXAxis();
		if(returnAngle > 180) return (360 - returnAngle);
		else return returnAngle;
	}

}
