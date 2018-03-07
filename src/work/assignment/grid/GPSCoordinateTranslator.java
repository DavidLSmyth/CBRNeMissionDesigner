package work.assignment.grid;

public class GPSCoordinateTranslator{
	double latDelta; //degrees
	double lngDelta; //degrees
	GPSCoordinate gpsCoordFrom;
	GPSCoordinate gpsCoordTo;
	
	public GPSCoordinateTranslator(GPSCoordinate gpsCoordFrom, GPSCoordinate gpsCoordTo) {
		this.latDelta = gpsCoordTo.getLat() - gpsCoordFrom.getLat();
		this.lngDelta = gpsCoordTo.getLng() - gpsCoordFrom.getLng();
		this.gpsCoordFrom = gpsCoordFrom;
		this.gpsCoordTo = gpsCoordTo;
	}
	public GPSCoordinate translate(GPSCoordinate gpsCoord) throws Exception {
		return new GPSCoordinate(gpsCoord.getLat() + latDelta, gpsCoord.getLng() + lngDelta, gpsCoord.getAlt());
	}
	public GPSCoordinate translateBack(GPSCoordinate gpsCoord) throws Exception{
		return new GPSCoordinate(gpsCoord.getLat() - latDelta, gpsCoord.getLng() - lngDelta, gpsCoord.getAlt());
	}
	
	public double getLatDelta() {
		return latDelta;
	}
	public double getLngDelta() {
		return lngDelta;
	}
	public double getLatDeltaMetres() {
		//1 deg lat ~ 111.03 Km in Europe
		return GPSCoordinateUtils.convertLatDegreeDifferenceToMetres(gpsCoordFrom.getLat(), gpsCoordTo.getLat());
//				latDelta * 111.32 * 1000);
	}
	public double getLngDeltaMetres() {
		//1 deg long ~ 85.39Km in Europe
		//calculate max error in this assumption
		return GPSCoordinateUtils.convertLongDegreeDifferenceToMetres(gpsCoordFrom.getLng(), gpsCoordTo.getLng(), gpsCoordFrom.getLat());
		//return Math.abs(lngDelta * 85.39 * 1000);
	}
	
	public String toString() {
		return "Translates by latDelta " + getLatDelta() + ", longDelta " + getLngDelta(); 
	}
}