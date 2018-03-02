package work.assignment.grid;

import java.util.ArrayList;

public class TraversalGrid {
	int noLngPoints;
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

	int noLatPoints;
	double lngSpacing; //metres
	public double getLngSpacing() {
		return lngSpacing;
	}

	public void setLngSpacing(double lngSpacing) {
		this.lngSpacing = lngSpacing;
	}

	public double getLatSpacing() {
		return latSpacing;
	}

	public void setLatSpacing(double latSpacing) {
		this.latSpacing = latSpacing;
	}

	double latSpacing; //metres
	double altitude;
	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	GPSGridRectangle boundingRectangle;

	ArrayList<GPSCoordinate> gridPoints;
	
	public TraversalGrid(GPSGridRectangle r, double lngSpacing, double latSpacing, double altitude) throws Exception {
		setLngSpacing(lngSpacing);
		setLatSpacing(latSpacing);
		setNoLatPoints((int) (r.getBoxWidthMetres() / latSpacing));
		setNoLngPoints((int) (r.getBoxHeightMetres() / lngSpacing));
		setBoundingRectangle(r);
		for(int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint ++) {
			for(int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
				gridPoints.set(lngPoint * getNoLatPoints() + lngPoint, new GPSCoordinate(latPoint * getLatSpacing(), lngPoint * getLngSpacing(), altitude));
			}
		}
	}
	
	public static double getCost(GPSCoordinate c1, GPSCoordinate c2) {
		//cost of traversal is given as the straight-line distance from 
		//coordinate1 to coordinate2
		return Math.sqrt(Math.pow(c1.getLatMetresToOther(c2), 2) + Math.pow(c1.getLngMetresToOther(c2), 2));
	}
	
	public GPSCoordinate getij(int i, int j) {
		//test this
		return gridPoints.get(i * getNoLatPoints() + j);
	}
	
	public GPSGridRectangle getBoundingRectangle() {
		return boundingRectangle;
	}

	public void setBoundingRectangle(GPSGridRectangle boundingRectangle) {
		this.boundingRectangle = boundingRectangle;
	}
	public ArrayList<GPSCoordinate> getGridPoints() {
		return gridPoints;
	}

	public void setGridPoints(ArrayList<GPSCoordinate> gridPoints) {
		this.gridPoints = gridPoints;
	}
}
