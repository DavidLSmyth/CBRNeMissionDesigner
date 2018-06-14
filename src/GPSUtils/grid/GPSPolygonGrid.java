package GPSUtils.grid;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import GPSUtils.CartesianCoordinate;
import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateUtils;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;

public class GPSPolygonGrid {

	GPSPolygon polygon;
	double latSpacing;
	double lngSpacing;
	private int noGridPoints;
	
	/**
	 * 
	 * @param polygon
	 * @param latSpacing - latitude spacing in metres
	 * @param lngSpacing - longitude spacing in metres
	 */
	public GPSPolygonGrid(GPSPolygon polygon, double latSpacing, double lngSpacing) {
		setPolygon(polygon);
		setLatSpacing(latSpacing);
		setLngSpacing(lngSpacing);
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
	
	private List<GPSCoordinate> generateGPSCoordinatesBoundingRect() throws Exception{
		GPSGridQuadrilateral quad = polygon.getBoundingQuadrilateral();
		
		double quadHeight = getHeight();
		double quadWidth = getWidth();

		int noLatPoints = (int) (quadHeight / latSpacing);
		int noLngPoints = (int) (quadWidth / lngSpacing);
		
		GPSCoordinate bottomLeft = new GPSCoordinate(quad.getLowestLat(), quad.getLowestLong()); 
		GPSCoordinate bottomRight = new GPSCoordinate(quad.getLowestLat(), quad.getHighestLong());
		GPSCoordinate topRight = new GPSCoordinate(quad.getLowestLat(), quad.getHighestLong());
		
		GPSCoordinate latDegreeDifference = topRight.subtract(bottomRight).divide(noLatPoints); 
		GPSCoordinate lngDegreeDifference = bottomRight.subtract(bottomLeft).divide(noLngPoints); 
		
		List<GPSCoordinate> gridPoints = new ArrayList<GPSCoordinate>();
		
		for(int latPointCounter=0; latPointCounter < noLatPoints; latPointCounter++) {
			for(int lngPointCounter = 0; lngPointCounter < noLngPoints; lngPointCounter++) {
				gridPoints.add(bottomLeft.add(latDegreeDifference.multiply(latPointCounter).add(lngDegreeDifference.multiply(lngPointCounter))));
			}
		}
		return gridPoints;
	}
	
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
	    	
	    	exp5 = exp2.divide(exp3);
	    	exp6 = exp1.multiply(exp5);
	    	exp7 = exp4.add(exp6);
	    	
	    	if((boundaryPoints.get(i).getLat().compareTo(test.getLat())>0) != (boundaryPoints.get(j).getLat().compareTo(test.getLat())>0)
	    	&& (test.getLng().compareTo(exp7) <0)){
	    		result = !result;
	    	}
	    }
	    return result;
	}
		
	
	public List<GPSCoordinate> generateContainedGPSCoordinates() throws Exception{
		//for each generated GPS coordinate in the bounding rectangle, check whether it lies inside the polygon
		List<GPSCoordinate> gridPoints = new ArrayList<GPSCoordinate>();
		for(GPSCoordinate coord: generateGPSCoordinatesBoundingRect()) {
			if(pointInPolygon(coord)) {
				gridPoints.add(coord);
			}
		}
		return gridPoints;
	}
	
	public GPSPolygon getPolygon() {
		return polygon;
	}

	public int getNoGridPoints() {
		return noGridPoints;
	}
	
	public void setPolygon(GPSPolygon polygon) {
		this.polygon = polygon;
	}


	public double getLatSpacing() {
		return latSpacing;
	}


	public void setLatSpacing(double latSpacing) {
		this.latSpacing = latSpacing;
	}


	public double getLngSpacing() {
		return lngSpacing;
	}


	public void setLngSpacing(double lngSpacing) {
		this.lngSpacing = lngSpacing;
	}
}
