package GPSUtils;

//import org.osgeo.proj4j.;
/**
 * A class which implements the GPSCoordinate interface.
 * Written according to the EPSG:4326 projected coordinate system.
 * Google maps uses the (EPSG:4326) WGS84 projection system.
 * 
 * References: 
 * 1). http://lyzidiamond.com/posts/4326-vs-3857
 * The World Geodetic System of 1984 is the geographic coordinate system (the three-dimensional one)
 * used by GPS to express locations on the earth. WGS84 is the defined coordinate system for GeoJSON,
 * as longitude and latitude in decimal degrees. For the most part, when you describe a lon/lat 
 * coordinate location, it’s based on the EPSG:4326 coordinate system.
 * There is no way to visualize the WGS84 coordinate system on a two-dimensional plane (map), 
 * so most software programs project these coordinates using an equirectangular projection (Plate-Carrée)
 * 
 * The projected Pseudo-Mercator coordinate system takes the WGS84 coordinate system and projects it onto a square. 
 * (This projection is also called Spherical Mercator or Web Mercator.) 
 * But not all of it – the bounds of Pseudo-Mercator are limited to approximately 85.06º North and South latitude. 
 * This projection was first introduced by Google and is used in almost 100% of web maps, but it’s a strange one:
 * the projection uses the WGS84 coordinate system, which uses the WGS84 ellipsoid, but projects the coordinates onto a sphere.
 * 
 * For the most part, web maps rely on data stored with WGS84 coordinates (in some programs this is called “unprojected” data)
 * and then visualize the data using Pseudo-Mercator. 
 * 
 * 
 * 2).http://earth-info.nga.mil/GandG/wgs84/web_mercator/index.html
 * 
 * 3). When generating points along a line of the grid, incrementing the difference between p1 and pn
 * is what occurs in pyproj: 
 * https://github.com/jswhit/pyproj/blob/c5a4df3d2f716a2960af30c0ce865ac52c2372cb/_proj.pyx
*/
public class GPSCoordinate {
	
	
	double lat;
	double lng;
	Double alt;
	
	private static double lowerLatBound = -85.06;
	private static double upperLatBound = 85.06;
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
	
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;	
		
		if(other.getClass() != getClass()) {
			return false;
		}
		else {
			GPSCoordinate otherCoord = (GPSCoordinate) other;
			//return true if the difference is less than ~ 1m
			if((getAlt() != null && otherCoord.getAlt() == null) || getAlt() == null && otherCoord.getAlt() != null) {
				return false;
			}
			if(getAlt() == null && otherCoord.getAlt() == null) {
				return (Math.abs(otherCoord.getLat() - this.getLat()) < Math.pow(10, -5)) && (Math.abs(otherCoord.getLng() - this.getLng()) < Math.pow(10, -4));
			}
			else{
				return (Math.abs(otherCoord.getAlt() - this.getAlt()) < Math.pow(10, -4)) && (Math.abs(otherCoord.getLat() - this.getLat()) < Math.pow(10, -5)) && (Math.abs(otherCoord.getLng() - this.getLng()) < Math.pow(10, -4));
			}
		}
	}
	
	@Override
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
	
	@Override
	public String toString() {
		if(alt != null) return "" + lat + ", " + lng + ", " + alt + "";
		else return "" + lat + ", " + lng + "";		
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
	
	public double getMetresToOther(GPSCoordinate otherCoord) {
		return Math.sqrt(Math.pow(getLatMetresToOther(otherCoord), 2) + Math.pow(getLngMetresToOther(otherCoord), 2));
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
	
	public GPSCoordinate add(GPSCoordinate otherCoord) throws Exception {
		GPSCoordinate returnCoord;
		if(getAlt()!=null && otherCoord.getAlt()!=null) {
			returnCoord = new GPSCoordinate(0, 0, 0.0);
			returnCoord.setAlt(getAlt() + otherCoord.getAlt());
		}
		else {
			returnCoord = new GPSCoordinate(0, 0);
		}
		returnCoord.setLat(getLat() + otherCoord.getLat());
		returnCoord.setLng(getLng() + otherCoord.getLng());
		return returnCoord;
	}
	
	public GPSCoordinate multiply(double number) throws Exception {
		if(number == 0) {
			return new GPSCoordinate(0,0);
		}
		GPSCoordinate returnCoord = clone();
		returnCoord.setLat(getLat() * number);
		returnCoord.setLng(getLng() * number);
//		for(int counter = 1; counter < number; counter++) {
//			returnCoord = returnCoord.add(this);
//		}
		return returnCoord;
	}
	
	public GPSCoordinate divide(int number) throws Exception {
		if(number == 0) {
			return new GPSCoordinate(0,0);
		}
		GPSCoordinate returnCoord = clone();
		returnCoord.setLat(getLat() / number);
		returnCoord.setLng(getLng() / number);
		return returnCoord;
	}

	public GPSCoordinate subtract(GPSCoordinate otherCoord) throws Exception {
		GPSCoordinate returnCoord;
		if(getAlt()!=null && otherCoord.getAlt()!=null) {
			returnCoord = new GPSCoordinate(0, 0, 0.0);
			returnCoord.setAlt(getAlt() - otherCoord.getAlt());
		}	
		else {
			returnCoord = new GPSCoordinate(0, 0);
		}
		returnCoord.setLat(getLat() - otherCoord.getLat());
		returnCoord.setLng(getLng() - otherCoord.getLng());
			
		return returnCoord;
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
		//the positive x-axis, moving clockwise from the coordinate
		if(getQuadrant() == 0 || getQuadrant() == 2) {
			return 90 * getQuadrant() + Math.toDegrees(Math.atan(Math.abs(getLat() / getLng())));
		}
		else {
//			System.out.println(Math.abs(getLat()) / Math.abs(getLng()));
//			System.out.println(Math.toDegrees(Math.atan(Math.abs(getLat()) / Math.abs(getLng()))));
			return 90 * (getQuadrant() + 1) - Math.toDegrees(Math.atan(Math.abs(getLat()) / Math.abs(getLng())));   
		}
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
