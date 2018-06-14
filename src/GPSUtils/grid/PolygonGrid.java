package GPSUtils.grid;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import GPSUtils.CartesianCoordinate;
import GPSUtils.GPSCoordinate;

/**
 * 
 * @author 13383861
 *
 */
public class PolygonGrid {
	protected PolygonDouble boundary;
	protected List<CartesianCoordinate> gridPoints;
	protected int numberOfPoints;
	protected double latSpacing;
	protected double lngSpacing;
	
	/**
	 * A grid which contains grid points in a bounding region
	 * @param bounds
	 * @param latSpacing
	 * @param lngSpacing
	 */
	public PolygonGrid(Polygon boundary, double latSpacing, double lngSpacing) {
		setBoundary(boundary);
		setLatSpacing(latSpacing);
		setLatSpacing(latSpacing);
	}
	
	protected List<CartesianCoordinate> generateGridPoints() {
//		RectangleDouble boundingRect = boundary.getBounds();
//		double noLatPoints = boundingRect.height / latSpacing;
//		double noLngPoints = boundingRect.width / lngSpacing;
//		List<CartesianCoordinate> gridPoints = new ArrayList<CartesianCoordinate>();
//		BigDecimal lowestLat = boundingR
//		PointDouble topLeft = boundary.getBounds().getLocation();
//		PointDouble topRight =boundary.getBounds().;
//		PointDouble topLeft = boundary.getBounds().getLocation();
//		
//		GPSCoordinate topLeftCoord = new GPSCoordinate(topLeft.y, topLeft.x);
//		
//		
//		for(int latPointCounter=0; latPointCounter < boundingRect.height; latPointCounter++) {
//			for(int lngPointCounter = 0; lngPointCounter < boundingRect.width; lngPointCounter++) {
//				gridPoints.add(topLeftCoord.subtract(latSpacing));
//			}
//		}
		return null;
	}
	
	public Polygon getBoundary() {
		return boundary;
	}

	public void setBoundary(Polygon boundary) {
		//this.boundary = boundary;
	}

	public List<CartesianCoordinate> getGridPoints() {
		return gridPoints;
	}
	
	protected void setGridPoints(List<CartesianCoordinate> gridPoints) {
		this.gridPoints = gridPoints;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
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
