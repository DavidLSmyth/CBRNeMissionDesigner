package GPSUtils;
import java.math.BigDecimal;
import java.util.function.DoubleToLongFunction;
/** The UTMCoordinate class is used in the mission designer to generate a set of grid points which
 * have a consistent spacing. This is done because the UTM conformal projection gives a 2-D 
 * Cartesian coordinate system representation of the earth, which is suitable for generating
 * equally spaced geometric points, wheras the WGS84 geographic coordinate system represents the 
 * earth as an ellipsoid, which makes it difficult to generate equally spaced geometric points
 * easily. 
 * 
 * Following the code outlined here: @see <a href="http://docs.ros.org/melodic/api/geodesy/html/python/_modules/geodesy/utm.html">ROSGeodesy</a>
 *
 *  */
public class UTMCoordinate extends GPSCoordinateBase implements CartesianCoordinate{

	private int zoneNumber;
    private BigDecimal easting;
    private BigDecimal northing;
    private BigDecimal altitude;
    private char zoneLetter;
    
    public static final Character[] ZONE_LETTERS = new Character[] {'*', 'C', 'D', 
        'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 
        'V', 'W', 'X'};
    
    public static final Integer[] ZONE_NUMBERS = new Integer[] {1, 2, 3, 4, 5, 6, 
        7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 
        44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60};
    
    public static final int MIN_EASTING = 0;
    public static final int MAX_EASTING = 1000000;
    
    public static final int MIN_NORTHING = 0;
    public static final int MAX_NORTHING = 10000000;
	
	public static UTMCoordinate fromWGS84(WGS84Coordinate coord) throws Exception {
		return WGS84ToUTM.convert(coord); 
	}
	
	public UTMCoordinate(double easting, double northing, double altitude, int zoneNumber, char zoneLetter) throws Exception{
		this(new BigDecimal(easting), new BigDecimal(northing), new BigDecimal(altitude), zoneNumber, zoneLetter);
	}
	/**
     * If you do not know the hemisphere or zone letter, denote it with an asterisk,
     * but you must know one or the other. 
     * @param easting
     * @param northing
     * @param hemisphere
     * @param zoneNumber
     * @param zoneLetter
     * @throws Exception 
     */
    public UTMCoordinate(BigDecimal easting, BigDecimal northing, BigDecimal altitude, int zoneNumber, char zoneLetter) throws Exception{
        super(northing, easting, altitude);
    	zoneLetter = Character.toUpperCase(zoneLetter);        
        if (easting.compareTo(BigDecimal.ZERO) < 0 || northing.compareTo(
            BigDecimal.ZERO) < 0) {
            throw new Exception("Easting and Northing must be >= ZERO.");
        }
        
        if(zoneNumber < 1 || zoneNumber > 60)
            throw new Exception("Zone number must fall in the range from 1 to 60.");
        
        if((Character.isLetter(zoneLetter) == false && zoneLetter != '*') || zoneLetter == 'A' ||
            zoneLetter == 'B' || zoneLetter == 'I' || zoneLetter == 'O' || 
            zoneLetter == 'Y' || zoneLetter == 'Z') {
            
            throw new Exception("Zone letter must be a letter in the alphabet, "
                    + "except A, B, I, O, Y, and Z and yours was " + zoneLetter);
        
        }
        
        
        this.easting = easting;
        this.northing = northing;
        this.zoneNumber = zoneNumber;
        this.zoneLetter = zoneLetter;
    }
//	
//	public UTMCoordinate() {
//		this(0, 0);
//	}
//	
//	public UTMCoordinate(BigDecimal lat, BigDecimal lng) {
//		this(lat, lng, Double.valueOf(new BigDecimal("5.0")));
//	}
//	
//	public UTMCoordinate(double lat, double lng, double alt, int zoneNumber, String band) {
//		super(lat, lng, alt);
//		setZoneNumber(zoneNumber);
//		setBand(band);
//	}
//
//	public UTMCoordinate(double lat, double lng, double alt) {
//		this(lat, lng, alt, 0, "");
//		// TODO Auto-generated constructor stub
//	}
//	public UTMCoordinate(double lat, double lng, double alt, int zoneNumber) {
//		this(lat, lng, alt, zoneNumber, "");
//		// TODO Auto-generated constructor stub
//	}
//	public UTMCoordinate(double lat, double lng, double alt, String band) {
//		this(lat, lng, alt, 0, band);
//		// TODO Auto-generated constructor stub
//	}
    
    @Override
	public boolean equals(Object obj) {
    	if(obj == null) {
			return false;
		}
		//only check whether this object is equal to another WGS84 object
		if(!UTMCoordinate.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final UTMCoordinate other = (UTMCoordinate) obj;
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
		
		if(this.getBand() != other.getBand()) {
			return false;
		}
		
		if(this.getZoneNumber() != other.getZoneNumber()) {
			return false;
		}
		
		return true;
	}
	
	public Boolean is2D() {
		return alt == null;
	}

	@Override
	public String toString() {
		return "UTM: [" + lng + ", " + lat + ", " + alt + ", " + zoneNumber + zoneLetter + "]";
	}
	
	public WGS84Coordinate toWGS84() throws Exception {
		return UTMToWGS84.convert(this, "WGS84");
	}
	
	public UTMCoordinate add(UTMCoordinate other) throws Exception {
		//returns this + other
		//constructor is Easting, Northing
		return new UTMCoordinate(getX().add(other.getX()),
			getY().add(other.getY()), altitude, zoneNumber, zoneLetter);
	}
	
	public UTMCoordinate addMetresEasting(double distance) throws Exception {
		//returns this + other
		return new UTMCoordinate(getX().add(new BigDecimal(distance)),
				getY(), altitude, zoneNumber, zoneLetter);
	}
	
	public UTMCoordinate addMetresNorthing(double distance) throws Exception {
		//returns this + other
		return new UTMCoordinate(getX(),
				getY().add(new BigDecimal(distance)), altitude, zoneNumber, zoneLetter);
	}
	public UTMCoordinate subtractMetresNorthing(double distance) throws Exception {
		//returns this + other
		return new UTMCoordinate(getX(),
				getY().subtract(new BigDecimal(distance)), altitude, zoneNumber, zoneLetter);
	}
	
	public UTMCoordinate subtractMetresEasting(double distance) throws Exception {
		//returns this + other
		return new UTMCoordinate(getX().subtract(new BigDecimal(distance)),
				getY(), altitude, zoneNumber, zoneLetter);
	}
	
	public UTMCoordinate subtract(UTMCoordinate other) throws Exception {
		return new UTMCoordinate(getX().subtract(other.getX()),
				getY().subtract(other.getY()), altitude, zoneNumber, zoneLetter);
	}
	
	@Override
	public BigDecimal getMetresToOther(CartesianCoordinate other) {
		if(getZ() != null && other.getZ() != null) {
			return BigDecimal.valueOf(StrictMath.sqrt(
					getLat().subtract(other.getY()).pow(2).add(
					getLng().subtract(other.getX().pow(2))).add(
					getAlt().subtract(other.getZ().pow(2))).doubleValue()));
		}
		else {
			return BigDecimal.valueOf(StrictMath.sqrt(
					getLat().subtract(other.getY()).pow(2).add(
					getLng().subtract(other.getX()).pow(2)).doubleValue()));
		}
//		return Math.sqrt(Math.pow(getLat() - other.getY(), 2) +Math.pow(getLng() - other.getX(), 2));
		
	}
	public int getZoneNumber() {
		return zoneNumber;
	}

	public void setZoneNumber(int zoneNumber) {
		this.zoneNumber = zoneNumber;
	}

	public char getBand() {
		return zoneLetter;
	}

	public void setBand(char band) {
		this.zoneLetter = band;
	}

	@Override
	public BigDecimal getX() {
		// TODO Auto-generated method stub
		return getLng();
	}

	@Override
	public BigDecimal getY() {
		// TODO Auto-generated method stub
		return getLat();
	}



	@Override
	public CartesianCoordinate add(CartesianCoordinate other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartesianCoordinate subtract(CartesianCoordinate other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getZ() {
		// TODO Auto-generated method stub
		return getAlt();
	}
	@Override
	public CartesianCoordinate addX(BigDecimal x) throws Exception {
		// TODO Auto-generated method stub
		return addMetresEasting(x.doubleValue());
	}
	@Override
	public CartesianCoordinate addY(BigDecimal y) throws Exception {
		// TODO Auto-generated method stub
		return addMetresNorthing(y.doubleValue());
	}
	@Override
	public CartesianCoordinate subtractY(BigDecimal y) throws Exception {
		// TODO Auto-generated method stub
		return subtractMetresNorthing(y.doubleValue());
	}
	@Override
	public CartesianCoordinate subtractX(BigDecimal x) throws Exception {
		// TODO Auto-generated method stub
		return subtractMetresEasting(x.doubleValue());
	}
}
