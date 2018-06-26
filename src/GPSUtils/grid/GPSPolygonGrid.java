package GPSUtils.grid;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateUtils;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;

public class GPSPolygonGrid {

	GPSPolygon polygon;
	double latSpacing;
	double lngSpacing;
	
	private int noGridPointsInBoundingRect;
	private int noGridPointsInPolygon;
	private boolean canReturnCachedGridPoints;
	//a cache to store calculated grid points in Rectangle
	private List<GPSCoordinate> cachedRectangleGridPoints;
	private List<GPSCoordinate> cachedPolygonGridPoints;
	
	/**
	 * @param polygon
	 * @param latSpacing - latitude spacing in metres
	 * @param lngSpacing - longitude spacing in metres
	 */
	public GPSPolygonGrid(GPSPolygon polygon, double latSpacing, double lngSpacing) {
		setPolygon(polygon);
		setLatSpacing(latSpacing);
		setLngSpacing(lngSpacing);
		canReturnCachedGridPoints = false;
	}
	
	public double getHeight() throws Exception {
		GPSGridQuadrilateral quad = polygon.getBoundingQuadrilateral();
		return GPSCoordinateUtils.getDistanceMetresBetweenWGS84(new GPSCoordinate(quad.getLowestLat(), quad.getLowestLong())
		, new GPSCoordinate(quad.getHighestLat(), quad.getLowestLong()));
	}
	
	public double getWidth() throws Exception {
		GPSGridQuadrilateral quad = polygon.getBoundingQuadrilateral();
		return GPSCoordinateUtils.getDistanceMetresBetweenWGS84(new GPSCoordinate(quad.getLowestLat(), quad.getLowestLong())
		, new GPSCoordinate(quad.getHighestLat(), quad.getHighestLong()));
	}
	
	/**
	 * A method which generates GPSCoordinates in the bounding rectangle of this polygon.
	 * Returned cached grid points if previously generated.
	 * @return List<GPSCoordinate>
	 * @throws Exception
	 */
	private List<GPSCoordinate> generateGPSCoordinatesBoundingRect() throws Exception{
		if(canReturnCachedGridPoints) {
			return cachedRectangleGridPoints;
		}
		GPSGridQuadrilateral quad = polygon.getBoundingQuadrilateral();
		//System.out.println("Testing rectangle: " + quad);
		
		double quadHeight = getHeight();
		double quadWidth = getWidth();
		//System.out.println("quadHeight: " + quadHeight);
		
		int noLatPoints = (int) (quadHeight / latSpacing);
		int noLngPoints = (int) (quadWidth / lngSpacing);
		
		if(noLatPoints == 0 && noLngPoints ==0) {
			throw new Exception("No points generated in bounding rectangle, you need to decrease your grid spacing");
		}
//		System.out.println("noLatPoints: " + noLatPoints);
//		System.out.println("noLngPoints: " + noLngPoints);
		
		GPSCoordinate bottomLeft = new GPSCoordinate(quad.getLowestLat(), quad.getLowestLong()); 
		GPSCoordinate bottomRight = new GPSCoordinate(quad.getLowestLat(), quad.getHighestLong());
		GPSCoordinate topRight = new GPSCoordinate(quad.getHighestLat(), quad.getHighestLong());
		
		GPSCoordinate latDegreeDifference = topRight.subtract(bottomRight).divide(noLatPoints); 
		GPSCoordinate lngDegreeDifference = bottomRight.subtract(bottomLeft).divide(noLngPoints); 
		
		List<GPSCoordinate> gridPoints = new ArrayList<GPSCoordinate>();
		
		for(int latPointCounter=0; latPointCounter < noLatPoints; latPointCounter++) {
			for(int lngPointCounter = 0; lngPointCounter < noLngPoints; lngPointCounter++) {
				gridPoints.add(bottomLeft.add(latDegreeDifference.multiply(latPointCounter).add(lngDegreeDifference.multiply(lngPointCounter))));
			}
		}
		canReturnCachedGridPoints = true;
		cachedRectangleGridPoints = gridPoints;
		return gridPoints;
	}
	
	/**
	 * 
	 * @param test - a GPSCoordinate which will be tested for inclusion in this polynomial
	 * @return true if test is in this polynomial, else false
	 */
	private boolean pointInPolygon(GPSCoordinate test) {
		int i;
	    int j;
	    boolean result = false;
	    List<GPSCoordinate> boundaryPoints = polygon.getBoundary();
	    BigDecimal exp1;
	    BigDecimal exp2;
	    BigDecimal exp3;
	    BigDecimal exp4;
	    BigDecimal exp5;
	    BigDecimal exp6;
	    BigDecimal exp7;
	    for (i = 0, j = polygon.getBoundary().size() - 1; i < polygon.getBoundary().size(); j = i++) {
	    	
	    	exp1 = boundaryPoints.get(j).getLng().subtract(boundaryPoints.get(i).getLng());
	    	exp2 = test.getLat().subtract(boundaryPoints.get(i).getLat());
	    	exp3 = boundaryPoints.get(j).getLat().subtract(boundaryPoints.get(i).getLat());
	    	exp4 = boundaryPoints.get(i).getLng();
	    	
	    	exp5 = exp2.divide(exp3, MathContext.DECIMAL32);
	    	exp6 = exp1.multiply(exp5);
	    	exp7 = exp4.add(exp6);
	    	
	    	if((boundaryPoints.get(i).getLat().compareTo(test.getLat())>0) != (boundaryPoints.get(j).getLat().compareTo(test.getLat())>0)
	    	&& (test.getLng().compareTo(exp7) <0)){
	    		result = !result;
	    	}
	    }
	    return result;
	}
		
	/**
	 * A method which generates GPSCoordinates contained in this polynomial
	 * @return
	 * @throws Exception
	 */
	public List<GPSCoordinate> generateContainedGPSCoordinates() throws Exception{
		if(canReturnCachedGridPoints) {
			return cachedPolygonGridPoints;
		}
		//for each generated GPS coordinate in the bounding rectangle, check whether it lies inside the polygon
		List<GPSCoordinate> gridPoints = new ArrayList<GPSCoordinate>();
		
		//Exception will be thrown from here if there are no coordinates generated in bounding rectangle.
		List<GPSCoordinate> boundingRectCoords = generateGPSCoordinatesBoundingRect();

		int pointsInPolygonCounter = 0;
		
		for(GPSCoordinate coord: boundingRectCoords) {
			//System.out.println("Testing if point " + coord + " is in grid.");
			if(pointInPolygon(coord)) {
				gridPoints.add(coord);
				pointsInPolygonCounter++;
			}
		}
		
		setNoGridPointsInBoundingRect(boundingRectCoords.size());
		setNoGridPointsInPolygon(pointsInPolygonCounter);
		

		System.out.println("Number of generated points in rect: " + getNoGridPointsInBoundingRect());
		System.out.println("Number of generated points in poly: " + getNoGridPointsInPolygon());
		System.out.println("Ratio of generated points in bounding rect vs. generated points in poly: " + Double.toString(getRatioOfPolyToRectGridPoints()));
		cachedPolygonGridPoints = gridPoints;
		canReturnCachedGridPoints = true;
		return gridPoints;
	}
	
	public double getRatioOfPolyToRectGridPoints() {
		if (getNoGridPointsInBoundingRect() != 0) { 
			return ((double) getNoGridPointsInPolygon()) / ((double)getNoGridPointsInBoundingRect());
		}
		else {
			System.out.println("Warning: no points found in bounding rect - perhaps change the grid spacing.");
			return -1;
		}
	}
	
	public int getNoGridPointsInBoundingRect() {
		return noGridPointsInBoundingRect;
	}

	protected void setNoGridPointsInBoundingRect(int noGridPointsInBoundingRect) {
		this.noGridPointsInBoundingRect = noGridPointsInBoundingRect;
	}

	public int getNoGridPointsInPolygon() {
		return noGridPointsInPolygon;
	}

	protected void setNoGridPointsInPolygon(int noGridPointsInPolygon) {
		canReturnCachedGridPoints = false;
		this.noGridPointsInPolygon = noGridPointsInPolygon;
	}
	
	public GPSPolygon getPolygon() {
		return polygon;
	}

//	public int getNoGridPoints() {
//		return noGridPoints;
//	}
	
	public void setPolygon(GPSPolygon polygon) {
		canReturnCachedGridPoints = false;
		this.polygon = polygon;
	}


	public double getLatSpacing() {
		return latSpacing;
	}


	public void setLatSpacing(double latSpacing) {
		canReturnCachedGridPoints = false;
		this.latSpacing = latSpacing;
	}


	public double getLngSpacing() {
		return lngSpacing;
	}


	public void setLngSpacing(double lngSpacing) {
		canReturnCachedGridPoints = false;
		this.lngSpacing = lngSpacing;
	}
}
