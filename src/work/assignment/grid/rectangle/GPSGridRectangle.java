package work.assignment.grid.rectangle;

import java.util.ArrayList;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateRotator;
import work.assignment.grid.GPSCoordinateTranslator;
import work.assignment.grid.GPSCoordinateUtils;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;

//Should this extend from GPS GridQuadrilateral?
public class GPSGridRectangle extends GPSGridQuadrilateral{
	//Assume that the user can pass in rectangular region to map - even if not, create approximate rectangular region
	//in API given multiple coordinates
	//These are marginal better than p1, p2, p3 p4.
	//Impose some structure
	
	//Don't think need a reflector, since rotating lowestLat around lowestLong should always
	//give correct orientation
	
	//The point that contains the lowest long coordinate in the rectangle
//	GPSCoordinate lowestLong;
//	GPSCoordinate highestLong;
//	GPSCoordinate highestLat;
//	GPSCoordinate lowestLat;
	
	GPSCoordinate p1;
	GPSCoordinate p2;
	GPSCoordinate p3;
	GPSCoordinate p4;
	ArrayList<GPSCoordinate> points;
	
	ArrayList<ArrayList<GPSCoordinate>> corners;
	
//	GPSCoordinateTranslator standardizedGPSGridTranslator;
//	GPSCoordinateRotator standardizedGPSGridRotator;
	//GPSCoordinateReflector standardizedGPSGridReflector;
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
	
//	protected GPSGridRectangle(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3, GPSCoordinate p4, String subclass) {
//		
//		this.p1 = p1;
//		this.p2 = p2;
//		this.p3 = p3;
//		this.p4 = p4;
//				
//		points = new ArrayList<GPSCoordinate>();
//		points.add(p1);
//		points.add(p2);
//		points.add(p3);
//		points.add(p4);
//	}
	
	public GPSGridRectangle(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3, GPSCoordinate p4) throws Exception{
		//allow a tolerance for the user to pass in  a quadrilateral that isn't a rectangle
		//need a measure for how close an object is to being rectangle
		//check how close any 2 angles are to being 90 degrees. Allow 5% tolerance?
	
		
		//this(p1,p2,p3,p4, "Sub");
		super(p1,p2,p3,p4);
			
		generateCorners();
		//if standardGPSRectangle, return StandardGPSRectangle
		
		//ensure all angles are 90 +- 5
		//for those that aren't, correct to 90
		
		if(!verifyRightAngles()) {
			throw new Exception("GPSGridRectangle must be provided with coordinates that form 90 degree corners");
		}
		
		//check that each point is unique
		//2 points are on same latitude - arbitrary which is which here
		//this also implies that the same point can be highestLong / lowestLong
		//  
		//  *--*
		//  |  |
		//  *--*
		
		//all of lowestLat, highestLat and either lowestLong/highestLong will be the same point
		//this *should* be case for standardized grid rectangle
	}


	private void generateCorners() {
		corners= new ArrayList<ArrayList<GPSCoordinate>> ();
		ArrayList<GPSCoordinate> corner1 = new ArrayList<GPSCoordinate> ();
		corner1.add(getHighestLong());
		corner1.add(getHighestLat());
		corner1.add(getLowestLong());
		
		ArrayList<GPSCoordinate> corner2 = new ArrayList<GPSCoordinate> ();
		corner2.add(getHighestLat());
		corner2.add(getLowestLong());
		corner2.add(getLowestLat());
		
		ArrayList<GPSCoordinate> corner3 = new ArrayList<GPSCoordinate> ();
		corner3.add(getLowestLong());
		corner3.add(getLowestLat());
		corner3.add(getHighestLong());
		
		ArrayList<GPSCoordinate> corner4 = new ArrayList<GPSCoordinate> ();
		corner4.add(getLowestLat());
		corner4.add(getHighestLong());
		corner4.add(getHighestLat());
		
		corners.add(corner1);
		corners.add(corner2);
		corners.add(corner3);
		corners.add(corner4);
	}
	
	public boolean verifyRightAngles() throws Exception {
		//allow 3% error
		double tolPercentage = 0.03;	
		for(ArrayList<GPSCoordinate> corner: getCorners()) {
			System.out.println("Angle between GPS Coords: " + corner + GPSCoordinateUtils.getAcuteAngle(corner.get(0), corner.get(1), corner.get(2)));
			//System.out.println("Angle between GPS Coords: " + corner + GPSCoordinate.getAcuteAngle(corner.get(0), corner.get(2), corner.get(1)));

			if(Math.abs(90 - GPSCoordinateUtils.getAcuteAngle(corner.get(0), corner.get(1), corner.get(2))) >= 90 * tolPercentage) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<ArrayList<GPSCoordinate>> getCorners() throws Exception {
		return corners;
	}
	
	public double getBoxWidthMetres() {
		//assuming width longitude - doesn't really matter
		return getHighestLong().getLngMetresToOther(getLowestLong());
	}
	
	public double getBoxHeightMetres() {
		//assuming width longitude - doesn't really matter
		return getHighestLat().getLatMetresToOther(getLowestLat());
	}
	
	
	//method to get the operations needed to get box translated to origin and rotated to all positive
	public StandardGPSGridRectangle getStandardizedGPSGridRectangle() throws Exception {
		//get new GPSGridRectangle translated back to origin
		
		GPSCoordinate lowestLong1 = getLowestLong().clone();
		GPSCoordinate lowestLat1 = getLowestLat().clone();
		GPSCoordinate highestLong1 = getHighestLong().clone();
		GPSCoordinate highestLat1 = getHighestLat().clone();
		ArrayList<GPSCoordinate> rectanglePoints = new ArrayList<GPSCoordinate>();
		
		rectanglePoints.add(lowestLat1);
		rectanglePoints.add(lowestLong1);		
		rectanglePoints.add(highestLat1);
		rectanglePoints.add(highestLong1);
		
		//pick an arbitrary point to translate back to origin
		int counter = 0;
		for(GPSCoordinate c: rectanglePoints) {
			//translate all points relative to arbitrary points
			c = getStandardizedGPSGridTranslator().translate(c);
			//rotate all points onto positive x-axis
			c = getStandardizedGPSGridRotator().rotateClockwise(c);
			//if any lat is negative, need a reflection
//			if(c.getLat() < 0) {
//				standardizedGPSGridReflector.setReflectLat(true);
//				c = standardizedGPSGridReflector.reflectLat(c);
//			}
			//same for long
//			if(c.getLng() < 0) {
//				standardizedGPSGridReflector.setReflectLng(true);
//				c = standardizedGPSGridReflector.reflectLong(c);
//			}
			System.out.println("Setting c as: " + c);
			rectanglePoints.set(counter, c);
			counter++;
			
		}
		
		//translate all points of rectangle correspondingly
//		lowestLong1 = standardizedGPSGridTranslator.translate(lowestLong1);
//		highestLong1 = standardizedGPSGridTranslator.translate(highestLong1);
//		lowestLat1 = standardizedGPSGridTranslator.translate(lowestLat1);
//		highestLat1 = standardizedGPSGridTranslator.translate(highestLat1);
//		
//		//rotate another point onto x-axis and rotate all other points correspondingly
//		lowestLat1 = standardizedGPSGridRotator.rotateClockwise(lowestLat1);
//		lowestLong1 = standardizedGPSGridRotator.rotateClockwise(lowestLong1);
//		highestLat1 = standardizedGPSGridRotator.rotateClockwise(highestLat1);
//		highestLong1 = standardizedGPSGridRotator.rotateClockwise(highestLong1);
		//check whether points need to be reflected
		return new StandardGPSGridRectangle(rectanglePoints.get(0), rectanglePoints.get(1), rectanglePoints.get(2), rectanglePoints.get(3));
	}
	
	public GPSCoordinateTranslator getStandardizedGPSGridTranslator() throws Exception {
		return getLowestLong().getTranslatorFromThisTo(new GPSCoordinate(0,0));
	}
	
//	public GPSCoordinateReflector getStandardizedGPSGridReflector() throws Exception {
//		//if any standardized points are negative - return the reflector, 
//		//else return the coord
//		GPSCoordinateReflector = 
//		GPSCoordinateTranslator t = getStandardizedGPSGridTranslator();
//		GPSCoordinateRotator r = getStandardizedGPSGridRotator();
//		GPSCoordinate lowestLngTranslated = t.translate(getLowestLong());
//		GPSCoordinate lowestLatTranslated = t.translate(getLowestLat());
//		double lowestLatAngle = lowestLatTranslated.getAngleRelativeToOriginXAxis();
//		System.out.println("lowestLatAngle: " + lowestLatAngle);
//		GPSCoordinateRotator r = lowestLngTranslated.getRotatorAboutThis(lowestLatAngle);
//		
//		return return ;
//	}
	
	public GPSCoordinateRotator getStandardizedGPSGridRotator() throws Exception {
		//getStandardizedGPSGridTranslator
		GPSCoordinateTranslator t = getStandardizedGPSGridTranslator();
		//translate lowestLong back to origin
		//then get rotator which rotates lowestLat back to x-axis
		System.out.println("lowest lat" + getLowestLat());
		System.out.println("lowest long " + getLowestLong());
		System.out.println(t.translate(getLowestLat()));
		System.out.println(t.translate(getLowestLat()).getAngleRelativeToOriginXAxis());
		return t.translate(getLowestLong()).getRotatorAboutThis(
				t.translate(getLowestLat()).getAngleRelativeToOriginXAxis());
	}
	
	
//	public void setLowestLat(GPSCoordinate lowestLat) {
//		this.lowestLat = lowestLat;
//	}
	
//	public Object getMapperToStandardForm() {
//		//returns an operator which maps GPS points in the rectangle to 
//		//their position relative to some coordinate
//		return new StandardisedGridRectangleMapper(this).convertToStandard();
//		
//	}
//
//	public void getMapperToOriginalForm() {
//		//returns an operator which maps GPS points in the rectangle to 
//		//their position relative to some coordinate
//		
//	}
	
	@Override
	public String toString() {
		return "LowestLat: " + getLowestLat() + "\nLowestLong: " + getLowestLong() + "\nHighestLat: " + getHighestLat() + "\nHighestLong: " + getHighestLong(); 
	}
	
	//private
	public boolean verifyOriginInRect() throws Exception {
		GPSCoordinate origin = new GPSCoordinate(0,0);
		System.out.println("p1: " + p1);
		System.out.println("p2: " + p2);
		System.out.println("p3: " + p3);
		System.out.println("p4: " + p4);
		if(!p1.equals(origin) && (!p2.equals(origin)) && (!p3.equals(origin)) && (!p4.equals(origin))) return false;
		else return true;
	}
	//private
	public boolean verifyPointsPositive() {
		for(GPSCoordinate p:points) {
			if((p.getLat() < -Math.pow(10, -4)) || p.getLng() < -Math.pow(10, -4)) return false;
		}
		return true;
	}
}









