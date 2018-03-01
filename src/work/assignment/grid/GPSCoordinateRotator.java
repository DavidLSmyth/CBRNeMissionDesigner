package work.assignment.grid;


public class GPSCoordinateRotator{
	double angleTheta;
	GPSCoordinate rotateCoord;
	GPSCoordinateRotator(GPSCoordinate coord, double angleTheta) throws Exception {
		//creates a rotator object that can rotate a coordinate around coord by an angle theta
		this.angleTheta = verifyTheta(angleTheta);
		this.rotateCoord = coord;
	}
	
	private double verifyTheta(double angleTheta) throws Exception {
		if(-360 <= angleTheta && angleTheta <= 360) {
			return angleTheta;
		}
		else {
			throw new Exception("Must provide an angle of theta within (-360, 360)");
		}
	}
	public GPSCoordinate rotate(GPSCoordinate coord) throws Exception {
		//translate by rotateCoord, rotate about origin, translate back
		GPSCoordinateTranslator t = new GPSCoordinateTranslator(rotateCoord, new GPSCoordinate(0,0));
		coord = t.translate(coord);
		coord.setLng((coord.getLng() * Math.cos(angleTheta)) - (coord.getLat() * Math.sin(angleTheta)));
		coord.setLat((coord.getLng() * Math.sin(angleTheta)) + (coord.getLat() * Math.cos(angleTheta)));
		return coord;		
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
	
	
}