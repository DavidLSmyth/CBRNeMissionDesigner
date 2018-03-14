package work.assignment.grid.rectangle;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateRotator;
import work.assignment.grid.GPSCoordinateTranslator;

public class StandardisedGridRectangleMapper {
	//A class representing a grid rectangle with one point at the origin (0,0)
	//and all other points positive lat, long
	
	GPSCoordinateTranslator tran;
	GPSCoordinateRotator rot;
	//GPSCoordinateReflector ref;
	GPSGridRectangle originalRect;
	
	public StandardisedGridRectangleMapper(GPSGridRectangle unstandardisedGridRectangle) throws Exception {
		setTranslate(unstandardisedGridRectangle.getStandardizedGPSGridTranslator());
		setRotate(unstandardisedGridRectangle.getStandardizedGPSGridRotator());
		System.out.println("Translator: " + tran);
		System.out.println("Rotator: " + rot);
		//setReflect(r.getStandardizedGPSGridReflector());
		this.originalRect = unstandardisedGridRectangle ;
	}
	
	public GPSCoordinate convertToStandard(GPSCoordinate gridCoord) throws Exception {
		//translates coordinate relative to lowestLong and 
		//then rotates anticlockwise relative to lowestLat
		gridCoord = getTranslate().translate(gridCoord);
		gridCoord = getRotate().rotateClockwise(gridCoord);
//		gridCoord = getReflect().reflectLat(gridCoord);
//		gridCoord = getReflect().reflectLong(gridCoord);
		System.out.println(gridCoord);
		return gridCoord;
	}
	
	public GPSCoordinate convertToOriginal(GPSCoordinate gridCoord) throws Exception {
		//re-reflect
//		gridCoord = getReflect().reflectLong(gridCoord);
//		gridCoord = getReflect().reflectLat(gridCoord);
		//rotate back to original
		gridCoord = getRotate().rotateAnticlockwise(gridCoord);
		//translate back to original
		gridCoord = getTranslate().translateBack(gridCoord);
		return gridCoord;
	}

	public GPSCoordinateTranslator getTranslate() {
		return tran;
	}

	public void setTranslate(GPSCoordinateTranslator translate) {
		this.tran = translate;
	}

	public GPSCoordinateRotator getRotate() {
		return rot;
	}

	public void setRotate(GPSCoordinateRotator rotate) {
		this.rot = rotate;
	}
	
	@Override
	public String toString() {
		return "Translates to standard by: Lat - " + tran.getLatDelta() + " Long - " + tran.getLngDelta() + 
				"\n and rotates by and angle of " + rot.getTheta() + " around the point " + rot.getRotateCoord();
	}

//	public GPSCoordinateReflector getReflect() {
//		return ref;
//	}
//
//	public void setReflect(GPSCoordinateReflector reflect) {
//		this.ref = reflect;
//	}
}
