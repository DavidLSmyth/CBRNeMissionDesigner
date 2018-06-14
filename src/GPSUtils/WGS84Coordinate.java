package GPSUtils;

import java.math.BigDecimal;
import org.apache.commons.math3.*;

/** WGS84 is the Geographic coordinate system used with GPS to express locations on earth.
 * WGS84 describes the earth as an oblate spheroid along its norht-south axis.
 * There is no way to visualise WGS84 coordinates on a 2D plane so some kind of transformation is 
 * needed - this is provided with the {@link toUTM}toUTM method.
 * Pseudo-Mercator 
 *  */
public class WGS84Coordinate extends GPSCoordinateBase {
	public static final int MAX_LONGITUDE = 180;
    public static final int MIN_LONGITUDE = -180;
    
    public static final int MAX_LATITUDE = 90;
    public static final int MIN_LATITUDE = -90;
    private final Datum datum = Datum.WGS84; 
    
	public WGS84Coordinate(BigDecimal lat, BigDecimal lng) throws Exception {
		this(lat, lng, null);
		// TODO Auto-generated constructor stub
	}
	public WGS84Coordinate(double lat, double lng) throws Exception {
		this(new BigDecimal(lat), new BigDecimal(lng), null);
		// TODO Auto-generated constructor stub
	}

	public WGS84Coordinate(BigDecimal lat, BigDecimal lng, BigDecimal alt) throws Exception {
		super(lat, lng, alt);
		// TODO Auto-generated constructor stub
	}
	public WGS84Coordinate(double lat, double lng, double alt) {
		this(new BigDecimal(lat), new BigDecimal(lng), new BigDecimal(alt));
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "WGS84: [" + lat + ", " + lng + "]";
	}
	
	public String latLngRep() {
		return getLat().toString() + ", " + getLng().toString();
	}
	

	
	 @Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		//only check whether this object is equal to another WGS84 object
		if(!WGS84Coordinate.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final WGS84Coordinate other = (WGS84Coordinate) obj;
		if(this.getAlt() == null && other.getAlt() != null) {
			return false;
		}
		if(this.getAlt() != null && other.getAlt() == null) {
			return false;
		}
		if(!this.getLat().equals(other.getLat())) { 
			return false;
		}
		if(!this.getLng().equals(other.getLng())) { 
			return false;
		}
		
		return true;
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/**
	   * This method is used to convert WGS84 coordinates to projected UTM coordinates.
	 * @throws Exception 
	   */
	 public UTMCoordinate toUTM() throws Exception {
		 return WGS84ToUTM.convertMVTypeScript(this);
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 /**
     * Get the datum of the specific Coordinate.
     * @return the datum
     */
    public Datum getDatum() {
        return datum;
    }

	
	/**
	* Vincenty method for finding distance between two points on a spheroid.
	* Karney's method is faster and more accurate but more difficult to implement
	*/
	public double getGeodesicMetresToOther(WGS84Coordinate other) {
		BigDecimal lat1 = this.getLat();
		BigDecimal lat2 = other.getLat();
		BigDecimal lng1 = this.getLng();
		BigDecimal lng2 = other.getLng();
		
		double a = 6378137, b = 6356752.314245, f = 1 / 298.257223563;
		double L = Math.toRadians(lng2.subtract(lng1).doubleValue());
		double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1.doubleValue())));
		double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2.doubleValue())));
		double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
		double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
		double cosSqAlpha;
		double sinSigma;
		double cos2SigmaM;
		double cosSigma;
		double sigma;

		double lambda = L, lambdaP, iterLimit = 100;
		do 
		{
			double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
			sinSigma = Math.sqrt(	(cosU2 * sinLambda)
									* (cosU2 * sinLambda)
									+ (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
									* (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
								);
			if (sinSigma == 0) 
			{
				return 0;
			}

			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1 - sinAlpha * sinAlpha;
			cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;

			double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
			lambdaP = lambda;
			lambda = 	L + (1 - C) * f * sinAlpha	
						* 	(sigma + C * sinSigma	
								* 	(cos2SigmaM + C * cosSigma
										* 	(-1 + 2 * cos2SigmaM * cos2SigmaM)
									)
							);
		
		} while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

		if (iterLimit == 0) 
		{
			return 0;
		}

		double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
		double A = 1 + uSq / 16384
				* (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		double deltaSigma = 
					B * sinSigma
						* (cos2SigmaM + B / 4
							* (cosSigma 
								* (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM
									* (-3 + 4 * sinSigma * sinSigma)
										* (-3 + 4 * cos2SigmaM * cos2SigmaM)));
		
		double s = b * A * (sigma - deltaSigma);
		
		return s;
	}

}
