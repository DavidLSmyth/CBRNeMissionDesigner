package GPSUtils;

import java.lang.reflect.GenericArrayType;

/*
 * LatLongToUTM.java
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
 
The formulas are based on Charles Karney's formulas for UTM conversion:
"Transverse Mercator with an accuracy of a few nanometers"
Charles F. F. Karney
SRI International, 201 Washington Rd, Princeton, NJ 08543-5300
Information that helped me understand the formulas:
"How to Use the Spreadsheet for Converting UTM to Latitude and Longitude (Or Vice Versa)" and
"Converting UTM to Latitude and Longitude (Or Vice Versa)"
Steven Dutch, Natural and Applied Sciences, University of Wisconsin - Green Bay
Assuming the northern hemisphere
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.print.attribute.standard.RequestingUserName;

import org.apache.commons.math3.analysis.function.Atanh;


/**
 *
 * @author Elaina Cole
 */
public class UTMToWGS84 {
    
    private static final BigDecimal SCALE_FACTOR = new BigDecimal(0.9996);
    private static final BigDecimal FALSE_EASTING = new BigDecimal(500000);
    private static final int PRECISION = 20;
    private static final int SCALE = 20;
    
    
    public static WGS84Coordinate convertMVTypeScript(UTMCoordinate coord) {
    	//if (isNaN(z) || isNaN(x) || isNaN(y)) throw new Error("Invalid coordinate");
    	int z = coord.getZoneNumber();
        BigDecimal falseEasting = new BigDecimal(500*1000);
        BigDecimal falseNorthing = new BigDecimal(10000 * 1000);
        
        char h = 'N';

        //double a = this.datum.ellipsoid.a, f = this.datum.ellipsoid.f;
        // WGS 84:  
        double a = 6378137;
        double b = 6356752.314245;
        double f = 1/298.257223563;

        double k0 = 0.9996; // UTM scale on the central meridian

        BigDecimal x = coord.getX().subtract(falseEasting);               // make x ± relative to central meridian
        BigDecimal y = h=='S' ? coord.getY().subtract(falseNorthing) : coord.getY(); 
        // make y ± relative to equator

        // ---- from Karney 2011 Eq 15-22, 36:

        double e = Math.sqrt(f*(2-f)); // eccentricity
        double n = f / (2 - f);        // 3rd flattening
        double n2 = n*n, n3 = n*n2, n4 = n*n3, n5 = n*n4, n6 = n*n5;

        double A = a/(1+n) * (1 + 1/4*n2 + 1/64*n4 + 1/256*n6); // 2πA is the circumference of a meridian

        double nu = x.doubleValue() / (k0*A);
        double eta = y.doubleValue() / (k0*A);

        double[] beta = new double[]{1/2*n - 2/3*n2 + 37/96*n3 -    1/360*n4 -   81/512*n5 +    96199/604800*n6,
                   1/48*n2 +  1/15*n3 - 437/1440*n4 +   46/105*n5 - 1118711/3870720*n6,
                            17/480*n3 -   37/840*n4 - 209/4480*n5 +      5569/90720*n6,
                                     4397/161280*n4 -   11/504*n5 -  830251/7257600*n6,
                                                   4583/161280*n5 -  108847/3991680*n6,
                                                                 20648693/638668800*n6 };

        double etaPrime = eta;
        for (int j=0; j<6; j++) { etaPrime -= beta[j] * Math.sin(2*j*eta) * Math.cosh(2*j*nu);};

        double nuPrime = nu;
        for (int j=0; j<6; j++) { nuPrime -= beta[j] * Math.cos(2*j*eta) * Math.sinh(2*j*nu);};

        double sinhNuPrime = Math.sinh(nuPrime);
        double sinEtaPrime = Math.sin(etaPrime);
        double cosEtaPrime = Math.cos(etaPrime);

        double tauPrime = sinEtaPrime / Math.sqrt(sinhNuPrime*sinhNuPrime + cosEtaPrime*cosEtaPrime);

        double tau_i = tauPrime;
        Atanh atanh = new Atanh();
        double deltatau_i = 100000000;
        double sigma_i;
        double tau_iPrime;
        do {
            sigma_i = Math.sinh(e*atanh.value(e*tau_i/Math.sqrt(1+tau_i*tau_i)));
            tau_iPrime = tau_i * Math.sqrt(1+sigma_i*sigma_i) - sigma_i * Math.sqrt(1+tau_i*tau_i);
            deltatau_i = (tauPrime - tau_iPrime)/Math.sqrt(1+tau_iPrime*tau_iPrime)
                * (1 + (1-e*e)*tau_i*tau_i) / ((1-e*e)*Math.sqrt(1+tau_i*tau_i));
            tau_i += deltatau_i;
        } while (Math.abs(deltatau_i) > 1e-12); // using IEEE 754 deltatau_i -> 0 after 2-3 iterations
        // note relatively large convergence test as deltatau_i toggles on ±1.12e-16 for eg 31 N 400000 5000000
        double tau = tau_i;

        double phi = Math.atan(tau);

        double lambda = Math.atan2(sinhNuPrime, cosEtaPrime);

        // ---- convergence: Karney 2011 Eq 26, 27

        double p = 1;
        for (int j=0; j<6; j++) { p -= 2*j*beta[j] * Math.cos(2*j*eta) * Math.cosh(2*j*nu);};
        double q = 0;
        for (int j=0; j<6; j++) { q += 2*j*beta[j] * Math.sin(2*j*eta) * Math.sinh(2*j*nu);};

        double γʹ = Math.atan(Math.tan(etaPrime) * Math.tanh(nuPrime));
        double γʺ = Math.atan2(q, p);

        double γ = γʹ + γʺ;

        // ---- scale: Karney 2011 Eq 28

        double sinphi = Math.sin(phi);
        double kʹ = Math.sqrt(1 - e*e*sinphi*sinphi) * Math.sqrt(1 + tau*tau) * Math.sqrt(sinhNuPrime*sinhNuPrime + cosEtaPrime*cosEtaPrime);
        double kʺ = A / a / Math.sqrt(p*p + q*q);

        double k = k0 * kʹ * kʺ;

        // ------------

        double lambda0 = Math.toRadians((z-1)*6 - 180 + 3); // longitude of central meridian
        lambda += lambda0; // move lambda from zonal to global coordinates

        // round to reasonable precision
        BigDecimal lat = new BigDecimal(Math.toDegrees(phi)); // nm precision (1nm = 10^-11°)
        BigDecimal lng = new BigDecimal(Math.toDegrees(lambda)); // (strictly lat rounding should be phi⋅cosphi!)
        //double convergence = BigDecimalMath.toDegrees(γ.toDegrees().toFixed(9));
        //double scale = Number(k.toFixed(12));

        return new WGS84Coordinate(lat, lng);
        //double latLong = new LatLon(lat, lon, this.datum);
        // ... and add the convergence and scale into the LatLon object ... wonderful JavaScript!
//        latLong.convergence = convergence;
//        latLong.scale = scale;

        //return latLong;

    }

    
    /**
     * Converts a UTM into a Coordinate of lat,long with a specific datum.
     * @param utm
     * @param datum
     * @return Coordinate
     * @throws Exception 
     */
    public static WGS84Coordinate convert(UTMCoordinate utm, String datum) throws Exception {
        
        Datum datumInformation = Datum.valueOf(datum);
                
        double[] betaSeries = datumInformation.getBetaSeries();
        
        //char hemisphere = utm.getHemisphere();
        
        double zoneCentralMeridian = utm.getZoneNumber() * 6 - 183;
        
        BigDecimal meridianRadius = new BigDecimal(datumInformation.getMeridianRadius());
        
        BigDecimal northing = utm.getY();
        
        BigDecimal easting = utm.getX();
        
        //assuming that we are working in the northern hemisphere....
        char hemisphere = 'N';
        BigDecimal xiNorth = calcXiNorth(hemisphere, meridianRadius, northing);
        
        BigDecimal etaEast = calcEtaEast(easting, meridianRadius);
        
        BigDecimal xiPrime = calcXiPrime(xiNorth, etaEast, betaSeries);
        
        BigDecimal etaPrime = calcEtaPrime(xiNorth, etaEast, betaSeries);
        
        BigDecimal tauPrime = calcTauPrime(xiPrime, etaPrime);
        
        BigDecimal eccentricity = new BigDecimal(datumInformation.getEccentricity());
        
        BigDecimal sigma = calcSigma(eccentricity, tauPrime);
        
        BigDecimal longitude = calcLongitude(zoneCentralMeridian, etaPrime, xiPrime);
        
        if(longitude.doubleValue() > WGS84Coordinate.MAX_LONGITUDE)
            longitude = new BigDecimal(WGS84Coordinate.MAX_LONGITUDE);
        
        if(longitude.doubleValue() < WGS84Coordinate.MIN_LONGITUDE)
            longitude = new BigDecimal(WGS84Coordinate.MIN_LONGITUDE);
      
        BigDecimal latitude = calcLatitude(tauPrime, sigma, eccentricity, hemisphere);
        
        if(latitude.doubleValue() > WGS84Coordinate.MAX_LATITUDE)
            latitude = new BigDecimal(WGS84Coordinate.MAX_LATITUDE);
        if(latitude.doubleValue() < WGS84Coordinate.MIN_LATITUDE)
            latitude = new BigDecimal(WGS84Coordinate.MIN_LATITUDE);
        
        return new WGS84Coordinate(latitude.setScale(SCALE, BigDecimal.ROUND_HALF_UP), longitude.setScale(SCALE, BigDecimal.ROUND_HALF_UP), utm.getAlt());
        
        //return latAndLong;
        
    }
    
    /**
     * Calculates the xi-north which refers to the north-south direction of the UTM.
     * @param hemisphere
     * @param meridianRadius
     * @param northing
     * @return xi north
     */
    private static BigDecimal calcXiNorth(char hemisphere, BigDecimal 
            meridianRadius, BigDecimal northing) {
        
        BigDecimal xiNorth;

        if(hemisphere == 'N') {
            
            BigDecimal divideByThis = SCALE_FACTOR.multiply(meridianRadius).setScale(
                    PRECISION, RoundingMode.HALF_UP);
            
            xiNorth = northing.divide(divideByThis, PRECISION, RoundingMode.HALF_UP);
            
        }
        
        else {
            
            BigDecimal numerator = (new BigDecimal(10000000)).subtract(northing);
            BigDecimal denominator = SCALE_FACTOR.multiply(meridianRadius);
            
            xiNorth = numerator.divide(denominator, PRECISION, RoundingMode.HALF_UP);
            
        }
            
        
        
        return xiNorth;
        
    }
    
    
    /**
     * Eta-east refers to the east west direction of the UTM.
     * @param easting
     * @param meridianRadius
     * @return eta east
     */
    private static BigDecimal calcEtaEast(BigDecimal easting, BigDecimal meridianRadius) {
        
        BigDecimal etaEast = (easting.subtract(FALSE_EASTING)).divide(
            SCALE_FACTOR.multiply(meridianRadius), PRECISION, RoundingMode.HALF_UP);
        
        return etaEast;
    }
    
    /**
     * 
     * @param xiNorth
     * @param etaEast
     * @param betaSeries
     * @return xi prime
     */
    private static BigDecimal calcXiPrime(BigDecimal xiNorth, BigDecimal etaEast, 
            double[] betaSeries) {
        
        double xiNorthDouble = xiNorth.doubleValue();
        double etaEastDouble = etaEast.doubleValue();
        
        BigDecimal sinOfXiNorth;
        BigDecimal coshOfEtaEast;
        
        BigDecimal subtrahend = new BigDecimal(0.0);
        int multiplicand = 2;
        
        for(double beta : betaSeries) {
            
            sinOfXiNorth = new BigDecimal(Math.sin(multiplicand * xiNorthDouble));
            coshOfEtaEast = new BigDecimal(Math.cosh(multiplicand * etaEastDouble));
            
            subtrahend.add(new BigDecimal(beta).multiply(sinOfXiNorth).multiply(coshOfEtaEast));
            
            multiplicand += 2;
            
        }
        
        BigDecimal xiPrime = xiNorth.subtract(subtrahend);
        
        return xiPrime;
        
        
    }
    
    /**
     * 
     * @param xiNorth
     * @param etaEast
     * @param betaSeries
     * @return eta prime
     */
    private static BigDecimal calcEtaPrime(BigDecimal xiNorth, BigDecimal etaEast,
            double[] betaSeries) {
        
        double xiNorthDouble = xiNorth.doubleValue();
        double etaEastDouble = etaEast.doubleValue();
        
        BigDecimal cosOfXiNorth;
        BigDecimal sinhOfEtaEast;
        
        BigDecimal subtrahend = new BigDecimal(0.0);
        int multiplicand = 2;
        
        for(double beta : betaSeries) {
            
            cosOfXiNorth = new BigDecimal(Math.cos(multiplicand * xiNorthDouble));
            sinhOfEtaEast = new BigDecimal(Math.sinh(multiplicand * etaEastDouble));
            
            subtrahend.add(new BigDecimal(beta).multiply(cosOfXiNorth).multiply(sinhOfEtaEast));
            
            multiplicand += 2;
            
        }
        
        BigDecimal etaPrime = etaEast.subtract(subtrahend);
        
        return etaPrime;
        
        
    }
    
    /**
     * 
     * @param xiPrime
     * @param etaPrime
     * @return tau prime
     */
    private static BigDecimal calcTauPrime(BigDecimal xiPrime, BigDecimal etaPrime) {
        
        double xiPrimeDouble = xiPrime.doubleValue();
        double etaPrimeDouble = etaPrime.doubleValue();
        
        BigDecimal sinOfXiPrime = new BigDecimal(Math.sin(xiPrimeDouble));
        BigDecimal cosOfXiPrime = new BigDecimal(Math.cos(xiPrimeDouble));
        BigDecimal sinhOfEtaPrime = new BigDecimal(Math.sinh(etaPrimeDouble));
        
        BigDecimal squareRoot = new BigDecimal(Math.sqrt(sinhOfEtaPrime.pow(2).
                add(cosOfXiPrime.pow(2)).doubleValue()));
        
        BigDecimal tauPrime = sinOfXiPrime.divide(squareRoot, PRECISION, RoundingMode.HALF_UP);
        
        return tauPrime;
    }
    
    /**
     * 
     * @param eccentricity
     * @param tau
     * @return sigma
     */
    private static BigDecimal calcSigma(BigDecimal eccentricity, BigDecimal tau) {
        
        double eccentricityDouble = eccentricity.doubleValue();
        double tauDouble = tau.doubleValue();
        
        Atanh atanh = new Atanh();
        double sigmaDouble = Math.sinh(eccentricityDouble *
            (atanh.value( eccentricityDouble * tauDouble / Math.sqrt(
            1 + Math.pow(tauDouble, 2)))));
        
        BigDecimal sigma = new BigDecimal (sigmaDouble);
        
        return sigma;
        
    }
    
    /**
     * 
     * @param currentTau
     * @param currentSigma
     * @param originalTau
     * @return function of tau
     */
    private static BigDecimal functionOfTau(BigDecimal currentTau, BigDecimal
        currentSigma, BigDecimal originalTau) {
        
        BigDecimal funcOfTau = originalTau.multiply(new BigDecimal(Math.sqrt(1 + 
            currentSigma.pow(2).doubleValue()))).subtract(currentSigma.multiply(
            new BigDecimal(Math.sqrt(1 + currentTau.pow(2).doubleValue())))).subtract(originalTau);
        
        
        return funcOfTau;
        
    }
    

    /**
     * 
     * @param eccentricity
     * @param currentTau
     * @param currentSigma
     * @return change in tau
     */
    private static BigDecimal changeInTau(BigDecimal eccentricity, BigDecimal 
        currentTau, BigDecimal currentSigma) {
        

        BigDecimal changeInTau = ((new BigDecimal(Math.sqrt((1 + 
            currentSigma.pow(2).doubleValue()) * (1 + 
            currentTau.pow(2).doubleValue())))).subtract(currentSigma.multiply(
            currentTau))).multiply(new BigDecimal(1 - eccentricity.pow(
            2).doubleValue())).multiply(new BigDecimal(Math.sqrt(1 + 
            currentTau.pow(2).doubleValue()))).divide(BigDecimal.ONE.add(BigDecimal.ONE.subtract(
            eccentricity.pow(2))).multiply(currentTau.pow(2)), PRECISION, 
            RoundingMode.HALF_UP);
        
        
        return changeInTau;
        
    }
    
    /**
     * 
     * @param originalTau
     * @param sigma
     * @param eccentricity
     * @param hemisphere
     * @return latitude
     */
    private static BigDecimal calcLatitude(BigDecimal originalTau, BigDecimal sigma, 
            BigDecimal eccentricity, char hemisphere) {
        
        BigDecimal funcOfTau = functionOfTau(originalTau, sigma, originalTau).setScale(PRECISION, RoundingMode.HALF_UP);
        
        BigDecimal changeInTau = changeInTau(eccentricity, originalTau, sigma);
        
        BigDecimal newTau = originalTau.subtract(funcOfTau.divide(changeInTau, 
            PRECISION, RoundingMode.HALF_UP));
          
        BigDecimal latitude = (new BigDecimal(Math.atan(newTau.doubleValue())))
           .multiply(new BigDecimal(180.0 / Math.PI));
        
        if(hemisphere == 'S')
            latitude = latitude.multiply(new BigDecimal(-1));
        
        return latitude;
        
    }
    
    /**
     * 
     * @param zoneCentralMeridian
     * @param etaPrime
     * @param xiPrime
     * @return longitude
     */
    private static BigDecimal calcLongitude(double zoneCentralMeridian, 
        BigDecimal etaPrime, BigDecimal xiPrime) {
        
        double longitudeRadians = Math.atan(Math.sinh(etaPrime.doubleValue())/
            Math.cos(xiPrime.doubleValue()));
        
        BigDecimal changeInLongitude = new BigDecimal(longitudeRadians*180.0/Math.PI);
        
        BigDecimal longitude = new BigDecimal(zoneCentralMeridian).add(changeInLongitude);
        
        return longitude;
    }

}
