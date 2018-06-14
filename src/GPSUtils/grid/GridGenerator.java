package GPSUtils.grid;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import GPSUtils.CartesianCoordinate;
import GPSUtils.UTMCoordinate;

public class GridGenerator {
	
	//When generating the grid, need to figure out what the max margin of error is for the lat spacing and the 
	//long spacing (since the earth is curved). Generated points will not be equidistant but 
	//should be close if the grid is relatively small
	
	
	
//	public UTMGridGenerator() {
//		// TODO Auto-generated constructor stub
//	}
	private static final BigDecimal TOLERANCE = new BigDecimal(0.000001);
	
	//might need to subclass this for GPS coordinates specifically
	public static List<CartesianCoordinate> generateGridInRectangle(CartesianCoordinate topLeft, 
			CartesianCoordinate topRight, 
			CartesianCoordinate bottomRight,
			CartesianCoordinate bottomLeft,
			BigDecimal latSpacing, 
			BigDecimal lngSpacing) throws Exception {
		if(!verifyRect(topLeft, topRight, bottomRight, bottomLeft)) {
			throw new Exception("Please provide a valid rectangle to work with");
		}
		
		//We can do this because UTM is measured in metres!
		BigDecimal latSpan = topLeft.getY().subtract(bottomLeft.getY());
		BigDecimal lngSpan = topRight.getX().subtract(topLeft.getX());
		
		System.out.println("latSpan: " + latSpan);
		System.out.println("lngSpan: " + lngSpan);
		System.out.println("latSpacing: " + latSpacing);
		System.out.println("lngSpacing: " + lngSpacing);
		latSpan.divide(latSpacing);
		int noLatPoints = latSpan.divide(latSpacing).toBigInteger().intValueExact(); 
		int noLngPoints = latSpan.divide(lngSpacing).toBigInteger().intValueExact();
		
		List<CartesianCoordinate> gridPoints = new ArrayList<CartesianCoordinate>();
		
		
//		for (int latPointCounter = 0; latPointCounter <= getNoLatPoints(); latPointCounter++) {
//			for (int longPointCounter = 0; longPointCounter <= getNoLngPoints(); longPointCounter++) {
//				points.add(r.getLowestLat().add(deltaJ.multiply(longPointCounter)).add(deltaI.multiply(latPointCounter)));
//			}
//		}
		
		for(int latCounter = 0; latCounter < noLatPoints; latCounter++) {
			for(int lngCounter = 0; lngCounter < noLngPoints; lngCounter++) {
				gridPoints.add(bottomLeft.addX(lngSpacing.multiply(BigDecimal.valueOf(lngCounter))).addY(
						latSpacing.multiply(BigDecimal.valueOf(latCounter))));
			}
		}
		
		return gridPoints;
		
	}
	private static boolean verifyRect(CartesianCoordinate topLeft, 
			CartesianCoordinate topRight, 
			CartesianCoordinate bottomRight,
			CartesianCoordinate bottomLeft) {
		//Verify that UTM coordinates form a rectangle
		if(compareBigDecimals(topLeft.getY(), topRight.getY()) && 
				compareBigDecimals(bottomLeft.getY(), bottomRight.getY()) && 
				compareBigDecimals(topLeft.getX(), bottomLeft.getX()) &&
				compareBigDecimals(topRight.getX(), bottomRight.getX()))
//				bottomLeft.getY().compareTo(bottomRight.getY()) == 0 &&
//				topLeft.getX().compareTo(bottomLeft.getX()) == 0 && 
//				topRight.getX().compareTo(bottomRight.getX()) == 0)
			return true;
		else
			return false;
	}
	/**
	 * Compares two big decimals with an error tolerance.
	 * The grid allows corners to be slightly misaligned
	 * @param arg0
	 * @param arg1
	 * @return true if arg0-tol <= arg1 <= arg0+tol
	 */
	
	public static boolean compareBigDecimals(BigDecimal arg0, BigDecimal arg1) { 
		if(arg1.compareTo(arg0.add(TOLERANCE))<=0 &&
				arg0.subtract(TOLERANCE).compareTo(arg1)<=0) {
			return true;
		}
		else {
			return false;
		}
	}

}
