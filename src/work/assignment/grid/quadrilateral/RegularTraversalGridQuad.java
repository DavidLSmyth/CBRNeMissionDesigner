package work.assignment.grid.quadrilateral;

import java.util.ArrayList;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateUtils;
import work.assignment.grid.TraversalGrid;
import work.assignment.grid.rectangle.GPSGridRectangle;

public class RegularTraversalGridQuad extends TraversalGrid{

		//RegularTraversalGridQuad is created from a bounding quadrilateral
		//each point has fixed lat,long spacing between other points
		
		// Contains a set of Grid Points which can be explored
		// Cost function gives cost of RAV / RGV travelling
		// from point i to point j

		int noLngPoints;
		int noLatPoints;
		double lngSpacingMetres; // metres
		double latSpacingMetres; // metres
		double altitude;
		ArrayList<Double> pointsMetres;
		
		double thetaOne;
		double thetaTwo;
		
		GPSCoordinate deltaI;
		GPSCoordinate deltaJ;
		
		GPSGridRectangle boundingRectangle;

		public RegularTraversalGridQuad(GPSGridQuadrilateral r, double lngSpacingMetres, double latSpacingMetres, double altitude) throws Exception {
			super();
			setLngSpacingMetres(lngSpacingMetres);
			setLatSpacingMetres(latSpacingMetres);
//			setNoLatPoints((int) Math.ceil(r.getBoxHeightMetres() / latSpacingMetres));
//			setNoLngPoints((int) Math.ceil(r.getBoxWidthMetres() / lngSpacingMetres));

			double distanceLowestLatHighestLong = r.getLowestLat().getMetresToOther(r.getHighestLong());
//			System.out.println("distance from lowest lat to highest long: " + distanceLowestLatHighestLong +
//					" divided by " + getLatSpacingMetres());
			
			double distanceLowestLatLowestLong = r.getLowestLat().getMetresToOther(r.getLowestLong()) ;
//			System.out.println("distance from lowest lat to lowest long: " + distanceLowestLatLowestLong +
//					" divided by " + getLngSpacingMetres());
			
			setNoLatPoints((int)Math.ceil(r.getLowestLat().getMetresToOther(r.getHighestLong()) / getLatSpacingMetres()));
			setNoLngPoints((int)Math.ceil(r.getLowestLat().getMetresToOther(r.getLowestLong()) / getLngSpacingMetres()));
		
//			System.out.println("No lat points: " + getNoLatPoints());
//			System.out.println("No lng points: " + getNoLngPoints());
			
			
			thetaOne = GPSCoordinateUtils.getAcuteAngle(r.getLowestLat().add(new GPSCoordinate(0,0.000001)), r.getLowestLat(), r.getHighestLong());
//					r.getLowestLat().getAngleRelativeToOriginXAxis();
			//System.out.println("Calculated the angle between the lowest lat and highest long to be " + thetaOne);
			
			thetaTwo = 90+GPSCoordinateUtils.getAcuteAngle(r.getLowestLat().add(new GPSCoordinate(0.000001,0.0)), r.getLowestLat(), r.getLowestLong());
			//System.out.println("Calculated the angle between the lowest lat and lowest long to be " + thetaTwo);
			
			
			deltaI = r.getHighestLong().subtract(r.getLowestLat()).divide(getNoLatPoints());
			deltaJ = r.getLowestLong().subtract(r.getLowestLat()).divide(getNoLngPoints());			
			
//			deltaI = new GPSCoordinate(GPSCoordinateUtils.convertMetresLatToDegrees((r.getLowestLat().getMetresToOther(r.getHighestLong()) / latSpacingMetres) * Math.sin(Math.toRadians(thetaOne)), r.getLowestLat().getLat()),
//					GPSCoordinateUtils.convertMetresLatToDegrees((r.getLowestLat().getMetresToOther(r.getHighestLong()) / latSpacingMetres) * Math.cos(Math.toRadians(thetaOne)), r.getLowestLat().getLat()) );
//			
//			
//			deltaJ = new GPSCoordinate(GPSCoordinateUtils.convertMetresLongToDegrees((r.getLowestLat().getMetresToOther(r.getLowestLong()) / lngSpacingMetres)* Math.sin(Math.toRadians(thetaTwo)), r.getLowestLat().getLat()), 
//					GPSCoordinateUtils.convertMetresLongToDegrees((r.getLowestLat().getMetresToOther(r.getLowestLong()) / lngSpacingMetres) * Math.cos(Math.toRadians(thetaTwo)), r.getLowestLat().getLat()));		
//			
//			System.out.println("deltaI: " + deltaI);
//			System.out.println("deltaJ: " + deltaJ);
			
			//setBoundingRectangle(r);
			setAltitude(altitude);
			//for (int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint++) {
//			for (int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
//				//GPSCoordinate t = delta
//				points.add(r.getLowestLat().add(deltaI.multiply(latPoint)));
//				System.out.println(r.getLowestLat().add(deltaI.multiply(latPoint)));
//			}
//			System.out.println(r.getLowestLat());
//			System.out.println(points);
			//}
			setNoPoints(points.size());
			
//			
//			for (int longPoint = 0; longPoint < getNoLatPoints(); longPoint++) {
//				//GPSCoordinate t = delta
//				points.add(r.getLowestLat().add(deltaJ.multiply(longPoint)));
//				System.out.println(r.getLowestLat().add(deltaJ.multiply(longPoint)));
//			}
			
			for (int latPointCounter = 0; latPointCounter <= getNoLatPoints(); latPointCounter++) {
				for (int longPointCounter = 0; longPointCounter <= getNoLngPoints(); longPointCounter++) {
					points.add(r.getLowestLat().add(deltaJ.multiply(longPointCounter)).add(deltaI.multiply(latPointCounter)));
				}
			}
			
			
			//store points as one large array - rows of long points
//			for (int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint++) {
//				for (int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
//					points.add(new GPSCoordinate(latPoint * getLatSpacingDegrees(), lngPoint * getLngSpacingDegrees(), altitude));
//				}
//			}
//			setNoPoints(points.size());
			
//			for (int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint++) {
//				for (int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
//					points.add(new GPSCoordinate(latPoint * getLatSpacingDegrees(), lngPoint * getLngSpacingDegrees(), altitude));
//				}
//			}
			setNoPoints(points.size());
			
//			pointsMetres = new ArrayList<ArrayList<Double>>();
//			for (int lngPoint = 0; lngPoint < getNoLngPoints(); lngPoint++) {
//				for (int latPoint = 0; latPoint < getNoLatPoints(); latPoint++) {
//					pointsMetres.add(new ArrayList<Double>(Arrays.asList(latPoint * getLatSpacingMetres(), lngPoint * getLngSpacingMetres(), altitude)));
//				}
//			}
		}
		
		public double getThetaOne() {
			return thetaOne;
		}
		
		public double getThetaTwo() {
			return thetaTwo;
		}
		
		public GPSCoordinate getDeltaI() {
			return deltaI;
		}
		
		public GPSCoordinate getDeltaJ() {
			return deltaJ;
		}
		
		@Override
		public String toString() {
			String returnString = "";
//			for(int i = 0; i < noLatPoints; i++) {
//				for (int j = 0; j < noLngPoints; j++) {
//					returnString += " " + "*";
//				}
//				returnString += "\n";
//			}
			for(GPSCoordinate c: getPoints()) {
				returnString += c.toString() + "\n";
			}
			return returnString;
		}

		public int getNoLngPoints() {
			return noLngPoints;
		}

		public void setNoLngPoints(int noLngPoints) {
			this.noLngPoints = noLngPoints;
		}
		
		@Override
		public int getNoPoints() {
			return getNoLatPoints() * getNoLngPoints();
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
		
//		public ArrayList<Double> getLatPointLngPointMetres(int latPoint, int longPoint) {
//			// test this
//			return pointsMetres.get(longPoint * getNoLatPoints() + latPoint);
//		}

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
