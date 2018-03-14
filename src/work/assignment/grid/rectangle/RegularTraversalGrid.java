package work.assignment.grid.rectangle;

import java.util.ArrayList;
import java.util.Arrays;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateUtils;
import work.assignment.grid.TraversalGrid;

public class RegularTraversalGrid extends TraversalGrid{
	//RegularTraversalGrid is created from a bounding rectangle
	//each point has fixed lat,long spacing between other points
	
	// Contains a set of Grid Points which can be explored
	// Cost function gives cost of RAV / RGV travelling
	// from point i to point j

	int noLngPoints;
	int noLatPoints;
	double lngSpacingMetres; // metres
	double latSpacingMetres; // metres
	double altitude;
	ArrayList<ArrayList<Double>> pointsMetres;
	
	GPSGridRectangle boundingRectangle;

	public RegularTraversalGrid(StandardGPSGridRectangle r, double lngSpacingMetres, double latSpacingMetres, double altitude) throws Exception {
		super();
		setLngSpacingMetres(lngSpacingMetres);
		setLatSpacingMetres(latSpacingMetres);
		setNoLatPoints((int) Math.ceil(r.getBoxHeightMetres() / latSpacingMetres));
		setNoLngPoints((int) Math.ceil(r.getBoxWidthMetres() / lngSpacingMetres));

//		setNoLatPoints((int) (r.getLowestLat().getMetresToOther(r.getHighestLong()) / latSpacingMetres));
//		setNoLngPoints((int) (r.getLowestLat().getMetresToOther(r.getLowestLong()) / lngSpacingMetres));
		
//		GPSCoordinate deltaI;
//		GPSCoordinate deltaJ;
//		double thetaOne = GPSCoordinateUtils.getAcuteAngle(r.getLowestLat().add(new GPSCoordinate(0,0.000001)), r.getLowestLat(), r.getHighestLong());
//				r.getLowestLat().getAngleRelativeToOriginXAxis();
//		System.out.println("Calculated the angle between the lowest lat and highest long to be " + thetaOne);
//		
//		double thetaTwo = 90 + GPSCoordinateUtils.getAcuteAngle(r.getLowestLat().add(new GPSCoordinate(0.000001,0.0)), r.getLowestLat(), r.getLowestLong());
//		System.out.println("Calculated the angle between the lowest lat and lowest long to be " + thetaTwo);
//		
//		deltaI = new GPSCoordinate(GPSCoordinateUtils.convertMetresLatToDegrees((r.getLowestLat().getMetresToOther(r.getHighestLong()) / latSpacingMetres), r.getLowestLat().getLat()) *15* Math.sin(Math.toRadians(thetaOne)),
//				GPSCoordinateUtils.convertMetresLatToDegrees((r.getLowestLat().getMetresToOther(r.getHighestLong()) / latSpacingMetres), r.getLowestLat().getLat()) *15* Math.cos(Math.toRadians(thetaOne)));
//		
//		
//		deltaJ = new GPSCoordinate(GPSCoordinateUtils.convertMetresLongToDegrees((r.getLowestLat().getMetresToOther(r.getLowestLong()) / lngSpacingMetres), r.getLowestLat().getLat()) *9* Math.sin(Math.toRadians(thetaTwo)), 
//				GPSCoordinateUtils.convertMetresLongToDegrees((r.getLowestLat().getMetresToOther(r.getLowestLong()) / lngSpacingMetres), r.getLowestLat().getLat()) *9* Math.cos(Math.toRadians(thetaTwo)));		
//		
//		System.out.println("deltaI: " + deltaI);
//		System.out.println("deltaJ: " + deltaJ);
//		
		setBoundingRectangle(r);
		setAltitude(altitude);
//		
//		//for (int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint++) {
//			for (int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
//				//GPSCoordinate t = delta
//				points.add(r.getLowestLat().add(deltaI.multiply(latPoint)));
//				System.out.println(r.getLowestLat().add(deltaI.multiply(latPoint)));
//			}
//		System.out.println(r.getLowestLat());
//		System.out.println(points);
//		//}
//		setNoPoints(points.size());
//		
//		
//		for (int longPoint = 0; longPoint < getNoLatPoints(); longPoint++) {
//			//GPSCoordinate t = delta
//			points.add(r.getLowestLat().add(deltaJ.multiply(longPoint)));
//			System.out.println(r.getLowestLat().add(deltaJ.multiply(longPoint)));
//		}
		
		//store points as one large array - rows of long points
		for (int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint++) {
			for (int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
				points.add(new GPSCoordinate(latPoint * getLatSpacingDegrees(), lngPoint * getLngSpacingDegrees(), altitude));
			}
		}
		setNoPoints(points.size());
			
		
		pointsMetres = new ArrayList<ArrayList<Double>>();
		for (int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint++) {
			for (int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
				pointsMetres.add(new ArrayList<Double>(Arrays.asList(latPoint * getLatSpacingMetres(), lngPoint * getLngSpacingMetres(), altitude)));
			}
		}
	}
	
	@Override
	public String toString() {
		String returnString = "";
		for(int i = 0; i < noLatPoints; i++) {
			for (int j = 0; j < noLngPoints; j++) {
				returnString += " " + "*";
			}
			returnString += "\n";
		}
		return returnString;
	}

	public int getNoLngPoints() {
		return noLngPoints;
	}

	public void setNoLngPoints(int noLngPoints) {
		this.noLngPoints = noLngPoints;
	}

	public int getNoLatPoints() {
		return noLatPoints;
	}

	public void setNoLatPoints(int noLatPoints) {
		this.noLatPoints = noLatPoints;
	}

	public double getLngSpacingMetres() {
		return lngSpacingMetres;
	}

	public void setLngSpacingMetres(double lngSpacing) {
		this.lngSpacingMetres = lngSpacing;
	}

	public double getLatSpacingMetres() {
		return latSpacingMetres;
	}

	public void setLatSpacingMetres(double latSpacing) {
		this.latSpacingMetres = latSpacing;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public GPSCoordinate getLatPointLngPoint(int latPoint, int longPoint) {
		// test this
		return points.get(longPoint * getNoLatPoints() + latPoint);
	}
	public ArrayList<Double> getLatPointLngPointMetres(int latPoint, int longPoint) {
		// test this
		return pointsMetres.get(longPoint * getNoLatPoints() + latPoint);
	}

	public GPSGridRectangle getBoundingRectangle() {
		return boundingRectangle;
	}

	public void setBoundingRectangle(GPSGridRectangle r) {
		this.boundingRectangle = r;
	}

	public ArrayList<GPSCoordinate> getGridPoints() {
		return points;
	}
	
	public double getLatSpacingDegrees() {
		return GPSCoordinateUtils.convertMetresLatToDegrees(getLatSpacingMetres(), points.get(0).getLat());
	}
	public double getLngSpacingDegrees() {
		return GPSCoordinateUtils.convertMetresLongToDegrees(getLngSpacingMetres(), points.get(0).getLat());
	}
}
