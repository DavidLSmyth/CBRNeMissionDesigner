package work.assignment.grid.quadrilateral;

import java.util.ArrayList;

import work.assignment.grid.GPSCoordinate;

public class GPSGridQuadrilateral {
	GPSCoordinate p1;
	GPSCoordinate p2;
	GPSCoordinate p3;
	GPSCoordinate p4;
	ArrayList<GPSCoordinate> points;
	
	ArrayList<ArrayList<GPSCoordinate>> corners;
	
	public GPSGridQuadrilateral(GPSCoordinate p1, GPSCoordinate p2, GPSCoordinate p3, GPSCoordinate p4) {
		//Assuming p1 has edge to p2 has edge to p3 has edge to p4 has edge to p1
		points = new ArrayList<GPSCoordinate>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		generateCorners();
	}
	
	private void generateCorners() {
		corners= new ArrayList<ArrayList<GPSCoordinate>> ();
		
		ArrayList<GPSCoordinate> corner = new ArrayList<GPSCoordinate> ();
		for(int counter = 0; counter < 4; counter++) {
			for(int pointNo = 0; pointNo < 4; pointNo++) {
				corner.add(points.get((counter + pointNo) % 4));
			}
			corners.add(corner);
		}
//		ArrayList<GPSCoordinate> corner1 = new ArrayList<GPSCoordinate> ();
//		corner1.add(points.get(0));
//		corner1.add(points.get(1));
//		corner1.add(points.get(2));
//		
//		ArrayList<GPSCoordinate> corner2 = new ArrayList<GPSCoordinate> ();
//		corner2.add(points.get(1));
//		corner2.add(points.get(2));
//		corner2.add(points.get(3));
//		
//		ArrayList<GPSCoordinate> corner3 = new ArrayList<GPSCoordinate> ();
//		corner3.add(points.get(2));
//		corner3.add(points.get(3));
//		corner3.add(points.get(0));
//		
//		ArrayList<GPSCoordinate> corner4 = new ArrayList<GPSCoordinate> ();
//		corner4.add(points.get(3));
//		corner4.add(points.get(0));
//		corner4.add(points.get(1));
//		
//		corners.add(corner1);
//		corners.add(corner2);
//		corners.add(corner3);
//		corners.add(corner4);		
	}
	
	public GPSCoordinate getLowestLong() {
		GPSCoordinate lowestLong = p1;
		for(GPSCoordinate p:points) {
			if(p.getLng() < lowestLong.getLng()){
				lowestLong = p;
			}
		}
		return lowestLong;
	}

	public GPSCoordinate getHighestLong() {
		GPSCoordinate highestLong = p1;
		for(GPSCoordinate p:points) {
			if(p.getLng() > highestLong.getLng()){
				highestLong = p;
			}
		}
		return highestLong;
	}

	public GPSCoordinate getHighestLat() {
		//return highestLat;
		GPSCoordinate highestLat = p1;
		for(GPSCoordinate p:points) {
			if(p.getLat() > highestLat.getLat()){
				highestLat = p;
			}
		}
		return highestLat;
	}

	public GPSCoordinate getLowestLat() {
		//return lowestLat;
		GPSCoordinate lowestLat = p1;
		for(GPSCoordinate p:points) {
			if(p.getLat() < lowestLat.getLat()){
				lowestLat = p;
			}
		}
		return lowestLat;
	}
	
	@Override
	public String toString() {
		return "(" + p1.toString() + ", " + p2.toString() + ", " + p3.toString() + ", " + p4.toString() + ")";
	}

}

