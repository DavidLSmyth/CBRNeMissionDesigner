package GPSUtils;

public class GPSCoordinateReflector {
	
	boolean reflectLat;
	boolean reflectLng;
	
	public GPSCoordinateReflector() {
		this(false, false);
	}
	
	public GPSCoordinateReflector(boolean reflectLat, boolean reflectLng) {
		setReflectLat(reflectLat);
		setReflectLng(reflectLng);
	}
	
	public GPSCoordinate reflectLat(GPSCoordinate coord) {
		if(reflectLat) { coord.reflectLat(); return coord;}
		else return coord;
	}
	
	public GPSCoordinate reflectLong(GPSCoordinate coord) {
		if(reflectLng) { coord.reflectLng(); return coord;}
		else return coord;
	}

	public boolean isReflectLat() {
		return reflectLat;
	}

	public void setReflectLat(boolean reflectLat) {
		this.reflectLat = reflectLat;
	}

	public boolean isReflectLng() {
		return reflectLng;
	}

	public void setReflectLng(boolean reflectLng) {
		this.reflectLng = reflectLng;
	}
}
