package work.assignment.grid;

public class GPSCoordinateRotator{
	double angleTheta;
	GPSCoordinate rotateCoord;
	public GPSCoordinateRotator(GPSCoordinate coord, double angleTheta) throws Exception {
		//creates a rotator object that can rotate a coordinate around coord by an angle theta
		this.angleTheta = verifyTheta(angleTheta);
		this.rotateCoord = coord;
	}
	
	private double verifyTheta(double angleTheta) throws Exception {
		if(-360 <= angleTheta && angleTheta <= 360) {
			return angleTheta;
		}
		else {
			throw new Exception("Must provide an angle of theta within (-360, 360), not " + angleTheta);
		}
	}
	public GPSCoordinate rotateAnticlockwise(GPSCoordinate coord) throws Exception {
		if(rotateCoord.equals(coord)) return coord;
		else {
			//translate by rotateCoord, rotate about origin, translate back
			GPSCoordinateTranslator t = new GPSCoordinateTranslator(rotateCoord, new GPSCoordinate(0,0));
			coord = t.translate(coord);
			System.out.println("Translated coord to " + coord);
			GPSCoordinate coordCopy = coord.clone();
			//coord.setLng((coordCopy.getLng() * Math.cos(Math.toRadians(angleTheta))) - (coordCopy.getLat() * Math.sin(Math.toRadians(angleTheta))));
			//coord.setLat((coordCopy.getLng() * Math.sin(Math.toRadians(angleTheta))) + (coordCopy.getLat() * Math.cos(Math.toRadians(angleTheta))));
//			coord.setLat(Math.asin(
//					(Math.cos(getTheta() * Math.sin(coord.getLat())) - 
//					(Math.cos(coord.getLng()) * Math.sin(getTheta()) * Math.cos(coord.getLat()))
//					
//							)));
			return t.translateBack(coord);
		}
	}
	
	public GPSCoordinate rotateClockwise(GPSCoordinate coord) throws Exception {
		setTheta(-getTheta());
		GPSCoordinate returnCoord = rotateAnticlockwise(coord);
		setTheta(-getTheta());
		return returnCoord;
	}

	public double getTheta() {
		return angleTheta;
	}

	public void setTheta(double theta) {
		this.angleTheta = theta;
	}

	public GPSCoordinate getRotateCoord() {
		return rotateCoord;
	}

	public void setRotateCoord(GPSCoordinate rotateCoord) {
		this.rotateCoord = rotateCoord;
	}
	
	public String toString() {
		return "Rotates by " + getTheta() + " around coordinate " + getRotateCoord(); 
	}
	
	
}