package GPSUtils;
/*
 * LatLongToUTMCoordinate.java
 *
 * Created Oct 25, 2015
 *
 * Copyright 2015 CIRDLES.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 

The formulas are based on Charles Karney's formulas for UTMCoordinate conversion:
"Transverse Mercator with an accuracy of a few nanometers"
Charles F. F. Karney
SRI International, 201 Washington Rd, Princeton, NJ 08543-5300

Information that helped me understand the formulas:
"How to Use the Spreadsheet for Converting UTMCoordinate to Latitude and Longitude (Or Vice Versa)" and
"Converting UTMCoordinate to Latitude and Longitude (Or Vice Versa)"
Steven Dutch, Natural and Applied Sciences, University of Wisconsin - Green Bay

 */

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.math3.analysis.function.Asin;
import org.apache.commons.math3.analysis.function.Asinh;
import org.apache.commons.math3.analysis.function.Atanh;



/**
 * Converts latitude and longitude coordinates to UTMCoordinate.
 * @author Elaina Cole
 */
public class WGS84ToUTM {
    
    private static final BigDecimal SCALE_FACTOR = new BigDecimal(0.9996);
    private static final BigDecimal FALSE_EASTING = new BigDecimal(500000);
    private static final BigDecimal SOUTH_HEMISPHERE_SUBTRACTION = new BigDecimal(10000000);
    private static final int PRECISION = 9;
    private static final int SCALE = 5;
    
    
    /**
     * Converts double latitude longitude to BigDecimal and converts it to UTMCoordinate
     * 
     * @param latitude
     * @param longitude
     * @param datumName
     * @return UTMCoordinate
     * @throws java.lang.Exception
     * 
     * 
     */
    
//    public static UTMCoordinate convert(double latitude, double longitude, String datumName) throws Exception {
//        
//        return convert(new BigDecimal(latitude), new BigDecimal(longitude), datumName);
//        
//    }     
//    
    
    public static UTMCoordinate convertMVTypeScript(WGS84Coordinate coord) throws Exception {
    	if (coord.getLat() == null || coord.getLng() == null) throw new Exception("Invalid WGS coordinate to covert; coords cannot be null");
	    if (!(-80<=coord.getLat().doubleValue() && coord.getLat().doubleValue()<=84)) throw new Exception("Outside UTM limits");

	    double falseEasting = 500e3, falseNorthing = 10000e3;

	    double zone = Math.floor((coord.getLng().doubleValue()+180)/6) + 1; // longitudinal zone
	    double lambda0 = Math.toRadians((zone-1)*6 - 180 + 3); // longitude of central meridian

	    // ---- handle Norway/Svalbard exceptions
	    // grid zones are 8deg tall; 0dcegN is offset 10 into latitude bands array
	    String mgrsLatBands = "CDEFGHJKLMNPQRSTUVWXX"; // X is repeated for 80-84°N
	    char latBand = mgrsLatBands.charAt((int)Math.floor(coord.getLat().doubleValue()/8+10));
	    // adjust zone & central meridian for Norway
	    if (zone==31 && latBand=='V' && coord.getLng().doubleValue()>= 3) { zone++; lambda0 += Math.toRadians(6); }
	    // adjust zone & central meridian for Svalbard
	    if (zone==32 && latBand=='X' && coord.getLng().doubleValue()<  9) { zone--; lambda0 -= Math.toRadians(6); }
	    if (zone==32 && latBand=='X' && coord.getLng().doubleValue()>= 9) { zone++; lambda0 += Math.toRadians(6); }
	    if (zone==34 && latBand=='X' && coord.getLng().doubleValue()< 21) { zone--; lambda0 -= Math.toRadians(6); }
	    if (zone==34 && latBand=='X' && coord.getLng().doubleValue()>=21) { zone++; lambda0 += Math.toRadians(6); }
	    if (zone==36 && latBand=='X' && coord.getLng().doubleValue()< 33) { zone--; lambda0 -= Math.toRadians(6); }
	    if (zone==36 && latBand=='X' && coord.getLng().doubleValue()>=33) { zone++; lambda0 += Math.toRadians(6); }

	    double phi = Math.toRadians(coord.getLat().doubleValue());      // latitude ± from equator
	    double lambda = Math.toRadians(coord.getLng().doubleValue()) - lambda0; // longitude ± from central meridian

	    //double a = this.datum.ellipsoid.a, f = this.datum.ellipsoid.f;
	    // WGS 84: 
	    double a = 6378137;
	    double b = 6356752.314245;
	    double f = 1/298.257223563;

	    double k0 = 0.9996; // UTM scale on the central meridian

	    // ---- easting, northing: Karney 2011 Eq 7-14, 29, 35:

	    double e = Math.sqrt(f*(2-f)); // eccentricity
	    double n = f / (2 - f);        // 3rd flattening
	    double n2 = n*n;
	    double n3 = n*n2;
	    double n4 = n*n3;
	    double n5 = n*n4;
	    double n6 = n*n5; // TODO: compare Horner-form accuracy?

	    double coslambda = Math.cos(lambda), sinlambda = Math.sin(lambda), tanlambda = Math.tan(lambda);

	    double tau = Math.tan(phi); // tau equivalent tanphi, tauPrime equivalent tanphi prime; prime (') indicates angles on the conformal sphere
	    Atanh atanh = new Atanh();
	    double sigma = Math.sinh(e*atanh.value(e*tau/Math.sqrt(1+tau*tau)));

	    double tauPrime = tau*Math.sqrt(1+sigma*sigma) - sigma*Math.sqrt(1+tau*tau);

	    double epsilonPrime = Math.atan2(tauPrime, coslambda);
	    Asinh asinh = new Asinh();
	    double nuPrime = asinh.value(sinlambda / Math.sqrt(tauPrime*tauPrime + coslambda*coslambda));

	    double A = a/(1+n) * (1 + 1/4*n2 + 1/64*n4 + 1/256*n6); // 2piA is the circumference of a meridian

//	    double alpha = [ null, // note alpha is one-based array (6th order Krüger expressions)
//	        1/2*n - 2/3*n2 + 5/16*n3 +   41/180*n4 -     127/288*n5 +      7891/37800*n6,
//	              13/48*n2 -  3/5*n3 + 557/1440*n4 +     281/630*n5 - 1983433/1935360*n6,
//	                       61/240*n3 -  103/140*n4 + 15061/26880*n5 +   167603/181440*n6,
//	                               49561/161280*n4 -     179/168*n5 + 6601661/7257600*n6,
//	                                                 34729/80640*n5 - 3418889/1995840*n6,
//	                                                              212378941/319334400*n6 ];
	    double[] alpha = new double[] {// note alpha is one-based array (6th order Krüger expressions)
	                     (1/2)*n - (2/3)*n2 + (5/16)*n3 +   (41/180)*n4 -     (127/288)*n5 +      (7891/37800)*n6,
	                     (13/48)*n2 -  (3/5)*n3 + (557/1440)*n4 +     (281/630)*n5 - (1983433/1935360)*n6,
	                     (61/240)*n3 -  (103/140)*n4 + (15061/26880)*n5 +   (167603/181440)*n6,
	                     (49561/161280)*n4 -     (179/168)*n5 + (6601661/7257600)*n6,
	                     (34729/80640)*n5 - (3418889/1995840)*n6,
	                     (212378941/319334400)*n6 };

	    double epsilon = epsilonPrime;
	    int j;
	    for (j=0; j<6; j++) { 
	    	epsilon += alpha[j] * Math.sin(2*j*epsilonPrime) * Math.cosh(2*j*nuPrime);
	    }

	    double nu = nuPrime;
	    for (j=0; j<6; j++) { 
	    	nu += alpha[j] * Math.cos(2*j*epsilonPrime) * Math.sinh(2*j*nuPrime);
	    }

	    double x = k0 * A * nu;
	    double y = k0 * A * epsilon;

	    // ---- convergence: Karney 2011 Eq 23, 24

	    double pPrime = 1;
	    for (j=0; j<6; j++) {
	    	pPrime += 2*j*alpha[j] * Math.cos(2*j*epsilonPrime) * Math.cosh(2*j*nuPrime);
	    }
	    
	    double qPrime = 0;
	    for (j=0; j<6; j++) {
	    	qPrime += 2*j*alpha[j] * Math.sin(2*j*epsilonPrime) * Math.sinh(2*j*nuPrime);
	    }

	    double gammaPrime = Math.atan(tauPrime / Math.sqrt(1+tauPrime*tauPrime)*tanlambda);
	    double gammaDoublePrime = Math.atan2(qPrime, pPrime);

	    double gamma = gammaPrime + gammaDoublePrime;

	    // ---- scale: Karney 2011 Eq 25

	    double sinphi = Math.sin(phi);
	    double kPrime = Math.sqrt(1 - e*e*sinphi*sinphi) * Math.sqrt(1 + tau*tau) / Math.sqrt(tauPrime*tauPrime + coslambda*coslambda);
	    double kDoublePrime = A / a * Math.sqrt(pPrime*pPrime + qPrime*qPrime);

	    double k = k0 * kPrime * kDoublePrime;

	    // ------------

	    // shift x/y to false origins
	    x = x + falseEasting;             // make x relative to false easting
	    if (y < 0) y = y + falseNorthing; // make y in southern hemisphere relative to false northing

	    // round to reasonable precision
	    //x = Number(x.toFixed(6)); // nm precision
	    BigDecimal returnX = new BigDecimal(x);
	    //y = Number(y.toFixed(6)); // nm precision
	    BigDecimal returnY = new BigDecimal(y);
	    
	    //ignore convergence and scale for now
//	    double convergence = Number(gamma.toDegrees().toFixed(9));
//	    double scale = Number(k.toFixed(12));

	    char h = coord.getLat().doubleValue()>=0 ? 'N' : 'S'; // hemisphere

//	    return new Utm(zone, h, x, y, convergence, scale);
	    return new UTMCoordinate(returnX, returnY, coord.getAlt(), (int) zone, latBand);
	}
    
    
    /**
     * Converts BigDecimal latitude longitude to UTMCoordinate 
     * 
     * @param latitude
     * @param longitude
     * @param datumName
     * @return UTMCoordinate
     * @throws java.lang.Exception
     * 
     * 
     */
    public static UTMCoordinate convert(WGS84Coordinate coord) throws Exception{
    	BigDecimal latitude = coord.getLat();
    	BigDecimal longitude = coord.getLng();
    	BigDecimal altitude = coord.getAlt();
    	String datumName = "WGS84";
        Datum datum = Datum.valueOf(datumName);
        
        BigDecimal meridianRadius = new BigDecimal(datum.getMeridianRadius());
        BigDecimal eccentricity = new BigDecimal(datum.getEccentricity());
        
        BigDecimal latitudeRadians = latitude.abs().multiply(
                new BigDecimal(Math.PI)).divide(new BigDecimal(180.0), PRECISION,
                RoundingMode.HALF_UP);
        
        int zoneNumber = calcZoneNumber(longitude);
        
        BigDecimal zoneCentralMeridian = calcZoneCentralMeridian(zoneNumber);
        
        BigDecimal changeInLongitudeDegree = (longitude.subtract(zoneCentralMeridian)
                ).abs().setScale(PRECISION, RoundingMode.HALF_UP);
        
        BigDecimal changeInLongitudeRadians = (changeInLongitudeDegree.multiply(
                new BigDecimal(Math.PI))).divide(new BigDecimal(180), PRECISION, 
                RoundingMode.HALF_UP);
        

        BigDecimal conformalLatitude = calcConformalLatitude(eccentricity, 
                latitudeRadians).setScale(PRECISION, RoundingMode.HALF_UP);
        
        BigDecimal tauPrime = (new BigDecimal(Math.tan(conformalLatitude.
                doubleValue()))).setScale(PRECISION, RoundingMode.HALF_UP);
        
        BigDecimal xiPrimeNorth = calcXiPrimeNorth(changeInLongitudeRadians, 
                tauPrime).setScale(PRECISION, RoundingMode.HALF_UP);
        
        BigDecimal etaPrimeEast = calcEtaPrimeEast(changeInLongitudeRadians, 
                tauPrime).setScale(PRECISION, RoundingMode.HALF_UP);
        
        double[] alphaSeries = datum.getAlphaSeries();
        

        BigDecimal xiNorth = calcXiNorth(xiPrimeNorth, etaPrimeEast, 
                alphaSeries).setScale(PRECISION, RoundingMode.HALF_UP);
        
        BigDecimal etaEast = calcEtaEast(xiPrimeNorth, etaPrimeEast, 
                alphaSeries).setScale(PRECISION, RoundingMode.HALF_UP);
        
        BigDecimal easting = calcEasting(meridianRadius, etaEast, longitude, 
                zoneCentralMeridian).setScale(PRECISION, RoundingMode.HALF_UP);
        BigDecimal northing = calcNorthing(meridianRadius, xiNorth, 
                latitude).setScale(PRECISION, RoundingMode.HALF_UP);
        
        char zoneLetter = calcZoneLetter(latitude);
        char hemisphere = calcHemisphere(latitude);
        
        if(easting.doubleValue() > UTMCoordinate.MAX_EASTING)
            easting = new BigDecimal(UTMCoordinate.MAX_EASTING);
        if(easting.doubleValue() < UTMCoordinate.MIN_EASTING)
            easting = new BigDecimal(UTMCoordinate.MIN_EASTING);
        
        if(northing.doubleValue() > UTMCoordinate.MAX_NORTHING)
            northing = new BigDecimal(UTMCoordinate.MAX_NORTHING);
        if(northing.doubleValue() < UTMCoordinate.MIN_NORTHING)
            northing = new BigDecimal(UTMCoordinate.MIN_NORTHING);
        
        UTMCoordinate UTMCoordinate = new UTMCoordinate(easting.setScale(SCALE, RoundingMode.HALF_UP), 
                northing.setScale(SCALE, RoundingMode.HALF_UP), altitude, zoneNumber, zoneLetter);
        
        return UTMCoordinate;
    }
    

    
    /**
     * Returns the zone number that the longitude corresponds to on the UTMCoordinate map
     * 
     * @param longitude
     * @return int zone number
     * 
     * 
     */
    private static int calcZoneNumber(BigDecimal longitude) {
        int zoneNumber;
        BigDecimal six = new BigDecimal(6);
        
        if (longitude.signum() < 0) {
            
            BigDecimal oneEighty = new BigDecimal(180);
            zoneNumber = ((oneEighty.add(longitude)).divide(six, PRECISION, 
                    RoundingMode.HALF_UP)).intValue()+ 1;
        }
            
        else {
            
            BigDecimal thirtyOne = new BigDecimal(31);
            zoneNumber = ((longitude.divide(six, PRECISION, RoundingMode.HALF_UP)).abs().add(
                    thirtyOne)).intValue();
        }
        
        if(zoneNumber > 60)
            zoneNumber = 60;
        
        if(zoneNumber < 1)
            zoneNumber = 1;
        
        return zoneNumber;
        
    }
    
    /**
     * Central meridian is the center of the zone on the UTMCoordinate map
     * 
     * @param zoneNumber
     * @return BigDecimal central meridian
     * 
     * 
     */
    private static BigDecimal calcZoneCentralMeridian(int zoneNumber) {
        
        BigDecimal zoneCentralMeridian = new BigDecimal(zoneNumber * 6 - 183);
        return zoneCentralMeridian;
    
    }
    
    /**
     * Eccentricity helps define the shape of the ellipsoidal representation of
     * the earth
     * 
     * Conformal latitude gives an angle-preserving (conformal) transformation 
     * to the sphere
     * 
     * It defines a transformation from the ellipsoid to a sphere 
     * of arbitrary radius such that the angle of intersection between any two 
     * lines on the ellipsoid is the same as the corresponding angle on the sphere.
     * 
     * @param eccentricity
     * @param latitudeRadians
     * @return BigDecimal conformal latitude
     * 
     
     */
    private static BigDecimal calcConformalLatitude(BigDecimal eccentricity, BigDecimal latitudeRadians) {
        
        BigDecimal conformalLatitude;
        
        double latRadDouble = latitudeRadians.doubleValue();
        double eccDouble = eccentricity.doubleValue();
        double confLatDouble;
        
        Atanh atanh = new Atanh();
        Asinh asinh = new Asinh();
        
        confLatDouble = Math.atan(Math.sinh(asinh.value( Math.tan(latRadDouble)) -
            eccDouble * atanh.value(eccDouble * Math.sin(latRadDouble))));
        
        conformalLatitude = new BigDecimal(confLatDouble);
        
        return conformalLatitude;
        
    }
    

    /**
     * tau refers to the torsion of a curve
     * xi refers to the north-south direction
     * 
     * @param changeInLongitudeRadians
     * @param tauPrime
     * @return BigDecimal xi prime
     * 
     * 
     */
    private static BigDecimal calcXiPrimeNorth(BigDecimal changeInLongitudeRadians,
            BigDecimal tauPrime) {
        
        double cosOfLatRad = Math.cos(changeInLongitudeRadians.doubleValue());
        
        BigDecimal xiPrime = new BigDecimal(Math.atan(tauPrime.doubleValue() / cosOfLatRad));
        
        return xiPrime;
    }
    
    /**
     * eta refers to the east-west direction
     * 
     * @param changeInLongitudeRadians
     * @param tauPrime
     * @return BigDecimal eta prime
     * 
     * 
     */
    private static BigDecimal calcEtaPrimeEast(BigDecimal changeInLongitudeRadians, 
            BigDecimal tauPrime) {
        
        BigDecimal etaPrime;
        
        double sinOfLatRad = Math.sin(changeInLongitudeRadians.doubleValue());
        double cosOfLatRad = Math.cos(changeInLongitudeRadians.doubleValue());
        double cosOfLatRadSquared = Math.pow(cosOfLatRad, 2);
        
        BigDecimal tauPrimeSquared = tauPrime.pow(2);
        
        double sqrt = Math.sqrt(tauPrimeSquared.doubleValue() + cosOfLatRadSquared);
        double sinOverSqrt = sinOfLatRad / sqrt;
        
        Asinh asinhOfSin = new Asinh();
        etaPrime = new BigDecimal(asinhOfSin.value(sinOverSqrt));
        
        return etaPrime;
        
    }
    
    
    /**
     * alpha series based on Krüger series, which entails mapping the ellipsoid 
     * to the conformal sphere.
     * @param xiPrimeNorth
     * @param etaPrimeEast
     * @param alphaSeries
     * @return BigDecimal xi
     * 
     */
    private static BigDecimal calcXiNorth(BigDecimal xiPrimeNorth,
            BigDecimal etaPrimeEast, double[] alphaSeries) {

        double multiplicand = 2;
        double xiNorthDouble = xiPrimeNorth.doubleValue();
        double xiPrimeNortDouble = xiPrimeNorth.doubleValue();
        double etaPrimeEastDouble = etaPrimeEast.doubleValue();
        
        for (double alpha : alphaSeries) {
            
            double sinOfXiPrimeNorth = Math.sin(
                    xiPrimeNortDouble * multiplicand);
            
            double coshOfEtaPrimeEast = Math.cosh(
                    etaPrimeEastDouble * multiplicand);
            
            double augend = (alpha * sinOfXiPrimeNorth) * 
                    coshOfEtaPrimeEast;
            
            xiNorthDouble = xiNorthDouble + augend;
            
            multiplicand = multiplicand + 2;
        }
        
        BigDecimal xiNorth = new BigDecimal(xiNorthDouble);
        
        return xiNorth;
       
        
    }
    
    /**
     * 
     * @param xiPrimeNorth
     * @param etaPrimeEast
     * @param alphaSeries
     * @return BigDecimal eta
     */
    private static BigDecimal calcEtaEast(BigDecimal xiPrimeNorth,BigDecimal 
            etaPrimeEast, double[] alphaSeries) {
        
        
        double multiplicand = 2;
        double etaEastDouble = etaPrimeEast.doubleValue();
        double etaPrimeEastDouble = etaPrimeEast.doubleValue();
        double xiPrimeNorthDouble = xiPrimeNorth.doubleValue();
        
        for(int i=0; i < alphaSeries.length - 1; i++) {
            
            double cosOfXiPrimeNorth = Math.cos(
                    xiPrimeNorthDouble * multiplicand);
            
            double sinhOfEtaPrimeEast = Math.sinh(
                    etaPrimeEastDouble * multiplicand);
            
            double augend = (alphaSeries[i] * cosOfXiPrimeNorth)*
                    sinhOfEtaPrimeEast;
            
            etaEastDouble = etaEastDouble + augend;
            multiplicand = multiplicand + 2;
            
        }
        
        BigDecimal etaEast = new BigDecimal(etaEastDouble);
        
        return etaEast;

        
    }
    
    /**
     * Latitude corresponds to a zone letter on the UTMCoordinate map. Match up zone letter
     * and zone number on UTMCoordinate map to find the specific zone.
     * @param latitude
     * @return char zone letter
     */
    private static char calcZoneLetter(BigDecimal latitude) {
        String letters = "CDEFGHJKLMNPQRSTUVWXX";
        double lat = latitude.doubleValue();
        
        if(lat >= -80 && lat <= 84)
            return letters.charAt(new Double(Math.floor((lat+80.0)/8.0)).intValue());
        
        else if(lat > 84)
            return 'X';
        else
            return 'C';
        
    } 

    /**
     * The meridian radius is based on the lines that run north-south on a map
     * UTMCoordinate easting coordinates are referenced to the center line of the zone 
     * known as the central meridian
     * The central meridian is assigned an 
     * easting value of 500,000 meters East.
     * @param meridianRadius
     * @param etaEast
     * @param longitude
     * @param centralMeridian
     * @return BigDecimal easting
     * 
     */
    private static BigDecimal calcEasting(BigDecimal meridianRadius, BigDecimal
            etaEast, BigDecimal longitude, BigDecimal centralMeridian) { 
        
        BigDecimal easting = (SCALE_FACTOR.multiply(meridianRadius)).multiply(etaEast);
        BigDecimal eastOfCM = BigDecimal.ONE;
        
        if (longitude.compareTo(centralMeridian) < 0)
            eastOfCM = eastOfCM.multiply(new BigDecimal(-1));
        
        easting = FALSE_EASTING.add(eastOfCM.multiply(easting));
        
        
        return easting;
    }
    
    /**
     * UTMCoordinate northing coordinates are measured relative to the equator
     * For locations north of the equator the equator is assigned the northing 
     * value of 0 meters North
     * To avoid negative numbers, locations south of the equator are made with 
     * the equator assigned a value of 10,000,000 meters North.
     * @param meridianRadius
     * @param xiNorth
     * @param latitude
     * @return BigDecimal northing
     */
    private static BigDecimal calcNorthing(BigDecimal meridianRadius, BigDecimal 
            xiNorth, BigDecimal latitude) {
        
        BigDecimal northing = (SCALE_FACTOR.multiply(meridianRadius)).multiply(xiNorth);
        
        if(latitude.signum() < 0)
            northing = SOUTH_HEMISPHERE_SUBTRACTION.subtract(northing);
        

        return northing;
        
    }
    
    /**
     * Returns whether the latitude indicates being in the southern or northern
     * hemisphere. 
     * @param latitude
     * @return char Hemisphere
     */
    private static char calcHemisphere(BigDecimal latitude) {
        
        char hemisphere = 'N';
        
        if (latitude.signum() == -1)
            hemisphere = 'S';
        
        return hemisphere;
    }
    
}

