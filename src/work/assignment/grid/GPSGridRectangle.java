package work.assignment.grid;

import java.util.ArrayList;

//Should this extend from GPS GridQuadrilateral?
public class GPSGridRectangle {
	//Assume that the user can pass in rectangular region to map - even if not, create approximate rectangular region
	//in API given multiple coordinates
	//These are marginal better than p1, p2, p3 p4.
	//Impose some structure
	
	//The point that contains the lowest long coordinate in the rectangle
	GPSCoordinate lowestLong;
	GPSCoordinate highestLong;
	GPSCoordinate highestLat;
	GPSCoordinate lowestLat;
	
	GPSCoordinateTranslator standardizedGPSGridTranslator;
	GPSCoordinateRotator standardizedGPSGridRotator;
	//2 different orientations:
	
	//   p2
	//  /   ⬊ 
	// p1     p3
	//    ⬊          /
	//      p4
	
	//or 
	
	// p1⬈p2
	//  \   \
	//   p3⬈p4
	
	// p1
	//  /  ⬊ 
	//   p3   p2
	//     ⬊    /
	//      p4
	
	public GPSGridRectangle(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3, GPSCoordinate p4) throws Exception{
		//allow a tolerance for the user to pass in  a quadrilateral that isn't a rectangle
		//need a measure for how close an object is to being rectangle
		//check how close any 2 angles are to being 90 degrees. Allow 5% tolerance?
		lowestLong = p1;
		highestLong = p1;
		highestLat = p1;
		lowestLat = p1;
		ArrayList<GPSCoordinate> points = new ArrayList<GPSCoordinate>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		for(GPSCoordinate p:points) {
			if(p.getLat() < lowestLat.getLat()){
				lowestLat = p;
			}
			if(p.getLat() > highestLat.getLat()){
				highestLat = p;
			}
			if(p.getLng() < lowestLong.getLng()){
				lowestLong = p;
			}
			if(p.getLat() > highestLat.getLat()){
				highestLat = p;
			}
		}
		//check that each point is unique
		//2 points are on same latitude - arbitrary which is which here
		//this also implies that the same point can be highestLong / lowestLong
		//  
		//  *--*
		//  |  |
		//  *--*
		//all of lowestLat, highestLat and either lowestLong/highestLong will be the same point
		//deal with this case again
		if(highestLat.equals(lowestLat)) {
			
		}
		if(highestLat.equals(highestLong)) {
			
		}
		if(highestLong.equals(lowestLat)) {
			
		}

		//ensure all angles are 90 +- 5
		//for those that aren't, correct to 90
		if(!ensureRectangle()) {
			throw new Exception("GPSGrid rectangular must be provided with rectangular coordinates");
		}
		
		standardizedGPSGridTranslator = getStandardizedGPSGridTranslator();
		standardizedGPSGridRotator = getStandardizedGPSGridRotator();
	}
	
	boolean ensureRectangle() throws Exception {
		double tolPercentage = 0.05;	
		for(ArrayList<GPSCoordinate> corner: getCorners()) {
			if(90 - GPSCoordinate.getAcuteAngle(corner.get(0), corner.get(1), corner.get(2)) <= 90 * tolPercentage ||
					90 - GPSCoordinate.getAcuteAngle(corner.get(0), corner.get(1), corner.get(2)) >= 90 * tolPercentage) {
				return false;
			}
		}
		return true;
	}
	
	ArrayList<ArrayList<GPSCoordinate>> getCorners() {
		ArrayList<ArrayList<GPSCoordinate>> corners= new ArrayList<ArrayList<GPSCoordinate>> ();
		ArrayList<GPSCoordinate> corner1 = new ArrayList<GPSCoordinate> ();
		corner1.add(highestLong);
		corner1.add(highestLat);
		corner1.add(lowestLong);
		
		ArrayList<GPSCoordinate> corner2 = new ArrayList<GPSCoordinate> ();
		corner2.add(highestLat);
		corner2.add(lowestLong);
		corner2.add(lowestLat);
		
		ArrayList<GPSCoordinate> corner3 = new ArrayList<GPSCoordinate> ();
		corner3.add(lowestLong);
		corner3.add(lowestLat);
		corner3.add(highestLong);
		
		ArrayList<GPSCoordinate> corner4 = new ArrayList<GPSCoordinate> ();
		corner4.add(lowestLat);
		corner4.add(highestLong);
		corner4.add(highestLat);
		
		corners.add(corner1);
		corners.add(corner2);
		corners.add(corner3);
		corners.add(corner4);
		return corners;
	}
	
	double getBoxWidthMetres() {
		//assuming width longitude - doesn't really matter
		return highestLong.getLngMetresToOther(lowestLong);
	}
	
	//method to get the operations needed to get box translated to origin and rotated to all positive
	public GPSGridRectangle getStandardizedGPSGridRectangle() throws Exception {
		//get new GPSGridRectangle translated back to origin
		
		GPSCoordinate lowestLong1 = lowestLong.clone();
		GPSCoordinate lowestLat1 = lowestLat.clone();
		GPSCoordinate highestLong1 = highestLong.clone();
		GPSCoordinate highestLat1 = highestLat.clone();
		
		//pick an arbitrary point to translate back to origin
		
		//translate all points of rectangle correspondingly
		lowestLong1 = standardizedGPSGridTranslator.translate(lowestLong1);
		highestLong1 = standardizedGPSGridTranslator.translate(highestLong1);
		lowestLat1 = standardizedGPSGridTranslator.translate(lowestLat1);
		highestLat1 = standardizedGPSGridTranslator.translate(highestLat1);
		
		//rotate another point onto x-axis and rotate all other points correspondingly
		lowestLat1 = standardizedGPSGridRotator.rotateClockwise(lowestLat1);
		lowestLong1 = standardizedGPSGridRotator.rotateClockwise(lowestLong1);
		highestLat1 = standardizedGPSGridRotator.rotateClockwise(highestLat1);
		highestLong1 = standardizedGPSGridRotator.rotateClockwise(highestLong1);
		
		return new GPSGridRectangle(lowestLat1, lowestLong1, highestLat1, highestLong1);
	}
	
	public GPSCoordinateTranslator getStandardizedGPSGridTranslator() throws Exception {
		return lowestLong.getTranslatorFromThisTo(new GPSCoordinate(0,0));
	}
	
	public GPSCoordinateRotator getStandardizedGPSGridRotator() throws Exception {
		//getStandardizedGPSGridTranslator
		GPSCoordinateTranslator t = getStandardizedGPSGridTranslator();
		//translate lowestLong back to origin
		//then get rotator which rotates lowestLat back to x-axis
		return t.translate(lowestLong).getRotatorAboutThis(
				t.translate(lowestLat).getAngleRelativeToOriginXAxis());
	}

}









