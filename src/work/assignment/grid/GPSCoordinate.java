package work.assignment.grid;

public class GPSCoordinate {
	double lat;
	double lng;
	Double alt;
	
	private static double lowerLatBound = -85;
	private static double upperLatBound = 85;
	private static double lowerLngBound = -180;
	private static double upperLngBound = 180;
	//m above sea level
	private static double lowerAltBound = 0;
	private static double upperAltBound = 20000;
		
	public GPSCoordinate(double lat, double lng) throws Exception {
		this(lat,lng,null);
	}
	
	
	public GPSCoordinate(double lat, double lng, Double alt) throws Exception {
		setLat(lat);
		setLng(lng);
		setAlt(alt);
	}
	
	
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;	
		
		if(other.getClass() != getClass()) {
			return false;
		}
		else {
			GPSCoordinate otherCoord = (GPSCoordinate) other;
			if((getAlt() != null && otherCoord.getAlt() == null) || getAlt() == null && otherCoord.getAlt() != null) {
				return false;
			}
			if(getAlt() == null && otherCoord.getAlt() == null) {
				return (otherCoord.getLat() - this.getLat() < Math.pow(10, -10)) && (otherCoord.getLng() - this.getLng() < Math.pow(10, -10));
			}
			else{
				return ((otherCoord.getAlt() - this.getAlt() < Math.pow(10, -10)) && (otherCoord.getLat() - this.getLat() < Math.pow(10, -10)) && (otherCoord.getLng() - this.getLng() < Math.pow(10, -10)));
			}
		}
	}
	
	public GPSCoordinate clone() {
		try {
			return new GPSCoordinate(lat, lng, alt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//do nothing; if this object has already been created safely there
			//should be no issues creating a copy
			return this;
		}
	}
	
	public String toString() {
		if(alt != null) return "(" + lat + ", " + lng + ", " + alt + ")";
		else return "(" + lat + ", " + lng + ")";		
	}
	
	
	public double getLatMetresToOther(GPSCoordinate otherCoord) {
		GPSCoordinateTranslator t = getTranslatorFromThisTo(otherCoord);
		return t.getLatDeltaMetres();
	}
	
	public double getLngMetresToOther(GPSCoordinate otherCoord) {
		GPSCoordinateTranslator t = getTranslatorFromThisTo(otherCoord);
		return t.getLngDeltaMetres();
	}
	
	public Exception throwRangeException(String type, double lowerBound, double upperBound) {
		return new Exception(type + " must lie in the range (" + lowerBound + ", " + upperBound + ")");
	}
	
	public int getQuadrant() {
		if(lat >= 0) {
			if(lng >= 0) return 0;
			else return 1;
		}
		else {
			if(lng <= 0) return 2;
			else return 3;
		}
	}
	
	//https://stackoverflow.com/questions/11849636/maximum-lat-and-long-bounds-for-the-world-google-maps-api-latlngbounds
	
	public static boolean verifyLat(double lat) {
		return(lowerLatBound <= lat && lat <= upperLatBound);
	}
	
	public static boolean verifyLng(double lng) {
		return(lowerLngBound <= lng && lng <= upperLngBound);
	}
	
	public static boolean verifyAlt(Double altitude) {
		if(altitude == null) {
			//null is valid for alt
			return true;
		}
		else {
			return(lowerAltBound <= altitude && altitude <= upperAltBound);
		}
	}
	
	public static boolean verifyAlt(Integer altitude) {
		//need == instead of .equals to avoid null pointer
		if(altitude == null) {
			return true;
		}
		else {
			return verifyAlt(Double.valueOf(altitude.doubleValue()));
		}
	}
	

	public double getLat() {
		return lat;
	}


	public void setLat(double lat) throws Exception {
		if(verifyLat(lat)) {
			this.lat = lat;
		}
		else {
			throw throwRangeException("Latitude", lowerLatBound, upperLatBound);
		}
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) throws Exception {
		if(verifyLng(lng)) {
			this.lng = lng;
		}
		else {
			throw throwRangeException("Longitude", lowerLngBound, upperLngBound);
		}
	}


	public Double getAlt() {
		return alt;
	}


	public void setAlt(Double newAlt) throws Exception {
		if(newAlt == null || verifyAlt(newAlt)) {
			this.alt = newAlt;
		}
		else {
			throw throwRangeException("Altitude", lowerAltBound, upperAltBound);
		}
	}
	
	public void setAlt(Integer newAlt) throws NumberFormatException, Exception {
		if(newAlt != null) {
			setAlt(Double.valueOf(newAlt.toString()));
		}
		else {
			Double nullDouble = null;
			setAlt(nullDouble);
		}
	}
	
	public GPSCoordinateTranslator getTranslatorFromThisTo(GPSCoordinate otherCoord) {
		//Calculates the translation matrix and 
		//returns a translator object which can translate other gps points
		//by translation from this to other
		return new GPSCoordinateTranslator(this, otherCoord);
	}
	
	public GPSCoordinateRotator getRotatorAboutThis(double angleTheta) throws Exception {
		//Calculates the rotation matrix and 
		//returns a rotator object which can rotate other gps points
		//around this through angle theta
		return new GPSCoordinateRotator(this, angleTheta);
	}
	
	public void reflectLat() {
		// reflects latitude
		try {
			setLat(-getLat());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//do nothing; lat is symmetric negative and 
			//can't have been set outside of this range in the 
			//first place
		}
	}
	
	public void reflectLng() {
		// reflects latitude
		try {
			setLng(-getLng());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// TODO Auto-generated catch block
			//do nothing; lat is symmetric negative and 
			//can't have been set outside of this range in the 
			//first place
		}
	}
	
	public double getAngleRelativeToOriginXAxis() {
		//returns the angle between the vector defined by current coordinate and 
		//the positive x-axis, anti-clockwise
		if(getQuadrant() == 0 || getQuadrant() == 2) {
			return 90 * getQuadrant() + Math.toDegrees(Math.atan(Math.abs(getLat() / getLng())));
		}
		else {
//			System.out.println(Math.abs(getLat()) / Math.abs(getLng()));
//			System.out.println(Math.toDegrees(Math.atan(Math.abs(getLat()) / Math.abs(getLng()))));
			return 90 * (getQuadrant() + 1) - Math.toDegrees(Math.atan(Math.abs(getLat()) / Math.abs(getLng())));   
		}
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
			GPSCoordinate temp = p1.clone();
			p1 = p3.clone();
			p3 = temp.clone();
		}
		
		double returnAngle = p1.getAngleRelativeToOriginXAxis() - p3.getAngleRelativeToOriginXAxis();
		if(returnAngle > 180) return (360 - returnAngle);
		else return returnAngle;
	}


	public static double getLowerLatBound() {
		return lowerLatBound;
	}


	public static double getUpperLatBound() {
		return upperLatBound;
	}


	public static double getLowerLngBound() {
		return lowerLngBound;
	}


	public static double getUpperLngBound() {
		return upperLngBound;
	}


	public static double getLowerAltBound() {
		return lowerAltBound;
	}


	public static double getUpperAltBound() {
		return upperAltBound;
	}
}
