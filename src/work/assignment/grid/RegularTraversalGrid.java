package work.assignment.grid;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	StandardGPSGridRectangle boundingRectangle;

	public RegularTraversalGrid(StandardGPSGridRectangle r, double lngSpacingMetres, double latSpacingMetres, double altitude) throws Exception {
		super();
		setLngSpacingMetres(lngSpacingMetres);
		setLatSpacingMetres(latSpacingMetres);
		setNoLatPoints((int) Math.ceil(r.getBoxHeightMetres() / latSpacingMetres));
		setNoLngPoints((int) Math.ceil(r.getBoxWidthMetres() / lngSpacingMetres));
		setBoundingRectangle(r);
		setAltitude(altitude);
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

	public StandardGPSGridRectangle getBoundingRectangle() {
		return boundingRectangle;
	}

	public void setBoundingRectangle(StandardGPSGridRectangle boundingRectangle) {
		this.boundingRectangle = boundingRectangle;
	}

	public ArrayList<GPSCoordinate> getGridPoints() {
		return points;
	}
	
	public double getLatSpacingDegrees() {
		return GPSCoordinate.convertMetresLatToDegrees(getLatSpacingMetres());
	}
	public double getLngSpacingDegrees() {
		return GPSCoordinate.convertMetresLongToDegrees(getLngSpacingMetres());
	}
}
