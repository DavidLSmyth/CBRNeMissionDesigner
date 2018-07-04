package GPSUtils.grid;

import java.math.BigDecimal;
import java.util.List;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateCosts;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;

/**
 * A class which records a polygon made up of GPSCoordinates
 * Assumption is made that GPSCoordinates are close to each 
 * other which allows Cartesian geometry to be applied
 * @author 13383861
 */

public class GPSPolygon {

	List<GPSCoordinate> boundary;
	
	

	public GPSPolygon(List<GPSCoordinate> boundary) throws Exception {
		//other checks for valid polygon?
		if(boundary.size() <= 2) {
			throw new Exception("Cannot create a polygon with less than 3 vertices");
		}
		setBoundary(boundary);
	}
	
	public GPSGridQuadrilateral getBoundingQuadrilateral() throws Exception {
		//returns a bounding quadrilateral whose edges run parallel to the x,y axes of 
		//WGS coordinate system
		return new GPSGridQuadrilateral(new GPSCoordinate(getLowestLat(), getLowestLng()),
				new GPSCoordinate(getLowestLat(), getHighestLng()),
				new GPSCoordinate(getHighestLat(), getHighestLng()), 
				new GPSCoordinate(getHighestLat(), getLowestLng()));
	}
	
	/**
	 * Returns the bounding coordinates of the polygon. 
	 * @return List<GPSCoordinate>
	 */
	public List<GPSCoordinate> getBoundary() {
		return boundary;
	}
	
	protected BigDecimal getHighestLat() {
		BigDecimal highestLat = GPSCoordinate.LOWER_LAT_BOUND;
		for(GPSCoordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(highestLat)>0) {
				highestLat = coord.getLat();
			}
		}
		return highestLat;
	}
	
	protected BigDecimal getHighestLng() {
		BigDecimal highestLng = GPSCoordinate.LOWER_LNG_BOUND;
		for(GPSCoordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(highestLng)>0) {
				highestLng = coord.getLng();
			}
		}
		return highestLng;
	}
	
	protected BigDecimal getLowestLng() {
		BigDecimal lowestLng = GPSCoordinate.UPPER_LNG_BOUND;
		for(GPSCoordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLng().compareTo(lowestLng)<0) {
				lowestLng = coord.getLng();
			}
		}
		return lowestLng;
	}
	
	protected BigDecimal getLowestLat() {
		BigDecimal lowestLat = GPSCoordinate.UPPER_LNG_BOUND;
		for(GPSCoordinate coord: boundary) {
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(coord.getLat().compareTo(lowestLat)<0) {
				lowestLat = coord.getLat();
			}
		}
		return lowestLat;
	}

	
	protected void setBoundary(List<GPSCoordinate> boundary) {
		this.boundary = boundary;
	}
	
	public String toString() {
		String returnString = "";
		for(GPSCoordinate coord: getBoundary()) {
			returnString += coord.toString();
		}
		return returnString;
	}
}
