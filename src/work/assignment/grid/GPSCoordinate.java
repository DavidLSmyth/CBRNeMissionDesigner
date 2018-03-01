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
		if(lat >= 0 && lng >= 0) {
			return 0;
		}
		if(lat >= 0 && lng <= 0) {
			return 1;
		}
		if(lat <= 0 && lng <= 0) {
			return 2;
		}
		//lat <= 0 && lng >= 0
		else {
			return 3;
		}
	}
	
	//https://stackoverflow.com/questions/11849636/maximum-lat-and-long-bounds-for-the-world-google-maps-api-latlngbounds
	
	private boolean verifyLat(double lat) {
		return(lowerLatBound <= lat && lat <= upperLatBound);
	}
	
	private boolean verifyLng(double lng) {
		return(lowerLngBound <= lng && lng <= upperLngBound);
	}
	
	private boolean verifyAlt(Double alt) {
		if(alt == null) {
			return true;
		}
		else {
			return(lowerAltBound <= alt && alt <= upperAltBound);
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


	public void setAlt(Double alt) {
		if(verifyAlt(alt)) {
			this.alt = alt;
		}
		else {
			throwRangeException("Altitude", lowerAltBound, upperAltBound);
		}
	}
	
	public GPSCoordinateTranslator getTranslatorFromThisTo(GPSCoordinate otherCoord) {
		//Calculates the translation matrix and 
		//returns a translator object which can translate other gps points
		//by translation from this to other
		return new GPSCoordinateTranslator(this, otherCoord);
	}
	
	public GPSCoordinateRotator getRotatorAboutThis(float angleTheta) throws Exception {
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
			setLat(-getLat());
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
		//the origin
		if(getQuadrant() == 0 || getQuadrant() == 2) {
			return 90 * getQuadrant() + Math.atan(getLat() / getLng());
		}
		else {
			return 90 * (getQuadrant() + 1) - Math.atan(Math.abs(getLat()) / getLng());   
		}
	}
	
	public static double getAngle(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3) throws Exception {
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
		
		if(p3.getLat() < 0) {
			p1.reflectLat();
			p3.reflectLat();
		}
		if(p3.getLng() < 0) {
			p1.reflectLng();
			p3.reflectLng();
		}
		//get angle between p3 and x-axis
		GPSCoordinateRotator r = new GPSCoordinateRotator(p3, Math.atan(p3.getLat()/p3.getLng()));
		//rotate p1 by same angle
		p1 = r.rotate(p1);
		//find angle between p1 and positive x-axis
		//ensure that p1 is in positive quadrant
		return p1.getAngleRelativeToOriginXAxis();
	}
}
