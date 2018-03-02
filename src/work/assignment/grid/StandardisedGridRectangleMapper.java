package work.assignment.grid;

public class StandardisedGridRectangleMapper {
	//A class representing a grid rectangle with one point at the origin (0,0)
	//and all other points positive lat, long
	
	GPSCoordinateTranslator tran;
	GPSCoordinateRotator rot;
	GPSCoordinateReflector ref;
	
	public StandardisedGridRectangleMapper(GPSGridRectangle r) throws Exception {
		setTranslate(r.getStandardizedGPSGridTranslator());
		setRotate(r.getStandardizedGPSGridRotator());
		setReflect(r.getStandardizedGPSGridReflector());		
	}
	
	public GPSCoordinate translateToStandard(GPSCoordinate gridCoord) throws Exception {
		gridCoord = getTranslate().translate(gridCoord);
		gridCoord = getRotate().rotateAnticlockwise(gridCoord);
		gridCoord = getReflect().reflectLat(gridCoord);
		gridCoord = getReflect().reflectLong(gridCoord);
		return gridCoord;
	}
	
	public GPSCoordinate translateToOriginal(GPSCoordinate gridCoord) throws Exception {
		//re-reflect
		gridCoord = getReflect().reflectLong(gridCoord);
		gridCoord = getReflect().reflectLat(gridCoord);
		//rotate back to original
		gridCoord = getRotate().rotateClockwise(gridCoord);
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

	public GPSCoordinateReflector getReflect() {
		return ref;
	}

	public void setReflect(GPSCoordinateReflector reflect) {
		this.ref = reflect;
	}
}
