package work.assignment.grid.rectangle;

import java.util.ArrayList;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateUtils;

public class StandardGPSGridRectangle extends GPSGridRectangle {
	
	public StandardGPSGridRectangle(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3, GPSCoordinate p4)
			throws Exception {
		// TODO Auto-generated constructor stub
		//check that rectangle is standardised
		super(p1,p2,p3,p4);
		
		if(!verifyOriginInRect()) throw new Exception("Origin must be in rectangle");
		if(!verifyPointsPositive()) throw new Exception("All points must be positive");
		if(!verifyRightAngles()) throw new Exception("All angles must be 90 degrees");
		
	}
	
	
	//private 
	public static ArrayList<GPSCoordinate> createCorner(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3){
		ArrayList<GPSCoordinate> returnList = new ArrayList<GPSCoordinate>();
		returnList.add(p1);
		returnList.add(p2);
		returnList.add(p3);
		return returnList;
	}
	
	@Override
	public ArrayList<ArrayList<GPSCoordinate>> getCorners() throws Exception {
		ArrayList<ArrayList<GPSCoordinate>> standardCorners = new ArrayList<ArrayList<GPSCoordinate>>();
		GPSCoordinate origin = new GPSCoordinate(0,0);
		GPSCoordinate bottomRight = new GPSCoordinate(0,0);
		GPSCoordinate topRight = new GPSCoordinate(0,0);
		GPSCoordinate topLeft = new GPSCoordinate(0,0);
		System.out.println("\n\n\n setting Standard corners");
		for(GPSCoordinate p: points) {
			if(p.equals(new GPSCoordinate(0,0))) {
				origin = p;
				System.out.println("Set origin as " + origin);
			}
			else if(Math.abs(p.getLat()) < Math.pow(10, -5) && bottomRight.equals(origin)) {
				bottomRight = p;
				System.out.println("Set bottomRight as " + bottomRight);
			}
			else if(Math.abs(p.getLng()) < Math.pow(10, -4) && topLeft.equals(origin)) {
				topLeft = p;
				System.out.println("Set topLeft" + topLeft);
			}
			else if(topRight.equals(origin)){
				topRight = p;
				System.out.println("Set topRight as " + topRight);
			}
		}
		System.out.println("\n\n");
		System.out.println("origin: " + origin);
		System.out.println("topLeft: " + topLeft);
		System.out.println("topRight: " + topRight);
		System.out.println("bottomRight: " + bottomRight);
		
		standardCorners.add(createCorner(bottomRight, origin, topLeft));
		standardCorners.add(createCorner(origin, bottomRight, topRight));
		standardCorners.add(createCorner(origin, topLeft, topRight));
		standardCorners.add(createCorner(topRight, topLeft, origin));
		return standardCorners;
	}
	
	//private
	@Override
	public boolean verifyRightAngles() throws Exception {
		
		for(ArrayList<GPSCoordinate> c:getCorners()) {
			System.out.println("Calculated angle as: " + GPSCoordinateUtils.getAcuteAngle(c.get(0), c.get(1), c.get(2)));
			if(Math.abs(GPSCoordinateUtils.getAcuteAngle(c.get(0), c.get(1), c.get(2)) - 90) > 4) {
				System.out.println("points: " + c.get(0) +  c.get(1) + c.get(2));
				return false;
			}
		}
		return true;
	}
	
	public GPSCoordinate getOrigin() throws Exception {
		for(GPSCoordinate p: points) {
			if(p.equals(new GPSCoordinate(0,0))) {
				return p;
				//System.out.println("Set origin as " + origin);
			}
		}
		throw new Exception("Couldnt find origin");
	}
	
	public GPSCoordinate getBottomRight() throws Exception {
		for(GPSCoordinate p: points) {
			if(Math.abs(p.getLat()) < Math.pow(10, -5)) {
				return p;
				//System.out.println("Set bottomRight as " + bottomRight);
			}
		}
		throw new Exception("Couldnt find bottom right");
	}
	
	public GPSCoordinate getTopLeft() throws Exception {
		for(GPSCoordinate p: points) {
			if(Math.abs(p.getLng()) < Math.pow(10, -4)) {
				return p;
				//System.out.println("Set bottomRight as " + bottomRight);
			}
		}
		throw new Exception("Couldnt find top left");	
	}
	
	public GPSCoordinate getTopRight() throws Exception {
		for(GPSCoordinate p: points) {
			if(!(Math.abs(p.getLng()) < Math.pow(10, -4)) && !(Math.abs(p.getLat()) < Math.pow(10, -5))) {
				return p;
				//System.out.println("Set bottomRight as " + bottomRight);
			}
		}
		throw new Exception("Couldnt find top right");	
	}
}
