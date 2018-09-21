package GPSUtils;

import agent.Agent;

import work.assignment.environmentalfactors.WindFactor;

public class GPSCoordinateCosts {

//	public GPSCoordinateCosts() {
//		// TODO Auto-generated constructor stub
//	}
	
	public static double getDistanceCost(GPSCoordinate c1, GPSCoordinate c2) throws Exception {
		//assuming that the agent can travel with a constant velocity once it has accelerated, calculates the distance
		//cost of travelling from one gps coordinate to another
		return c1.getMetresToOther(c2);
	}
	//protected
	public static double getLatDistanceCost(GPSCoordinate c1, GPSCoordinate c2) throws Exception {
		return c1.getLatMetresToOther(c2);
	}
	//protected
	public static double getLongDistanceCost(GPSCoordinate c1, GPSCoordinate c2) throws Exception {
		return c1.getLngMetresToOther(c2);
	}
	
	public static double getTimeCost(double agentVelocity, GPSCoordinate c1, GPSCoordinate c2) throws Exception {
		return getTimeCost(agentVelocity, new WindFactor(0,0), c1, c2);
	}
	
	public static double getTimeCost(double agentVelocity, WindFactor windFactor, GPSCoordinate from, GPSCoordinate to) throws Exception {
		//System.out.println("lat metres: " + GPSCoordinateUtils.convertLatDegreeDifferenceToMetresSigned(to.getLat(), from.getLat()));
		//System.out.println("long metres: " + GPSCoordinateUtils.convertLongDegreeDifferenceToMetresSigned(to.getLng(), from.getLng(), from.getLat()));
//		System.out.println("From: " + from);
//		System.out.println("To: " + to);
		if(from.equals(to)) {
			return 0;
		}
		
		//double theta = Math.abs(Math.atan(GPSCoordinateUtils.convertLatDegreeDifferenceToMetresSigned(from.getLat(), to.getLat()) / GPSCoordinateUtils.convertLongDegreeDifferenceToMetresSigned(from.getLng(), to.getLng(), from.getLat())));
		
		double hyp = from.getMetresToOther(to);
		double sinTheta = from.getLatMetresToOther(to) / hyp;
		double cosTheta = from.getLngMetresToOther(to) / hyp;
				
//		System.out.println("theta: " + theta);
//		System.out.println("Theta degrees: " + Math.toDegrees(theta));
		
		//System.out.println(windFactor.getNorthComponent());
		//System.out.println(windFactor.getEastComponent());
		
		//effectiveVelocitylat is the distance lat in metres needed to travel from, to 
		
		//-1 for 
		double effectiveVelocityLat = (sinTheta * agentVelocity * ((to.getLat().compareTo(from.getLat()) > 0) ? 1.0: -1.0)) + windFactor.getNorthComponent(); 
//		double effectiveVelocityLat =  ((GPSCoordinateUtils.convertLatDegreeDifferenceToMetresSigned(to.getLat(), from.getLat())/
//				Math.abs(GPSCoordinateUtils.convertLatDegreeDifferenceToMetresSigned(to.getLat(), from.getLat()))) * 
//				agentVelocity * Math.sin(theta)) + windFactor.getNorthComponent();
	
		//System.out.println("effectiveVelocityLat: " + effectiveVelocityLat);
		
//		double effectiveVelocityLong = (GPSCoordinateUtils.convertLongDegreeDifferenceToMetresSigned(to.getLng(), from.getLng(), from.getLat())/
//				Math.abs(GPSCoordinateUtils.convertLongDegreeDifferenceToMetresSigned(to.getLng(), from.getLng(), from.getLat()))) * 
//				(agentVelocity * Math.cos(theta)) + windFactor.getEastComponent();
		
		double effectiveVelocityLong = (cosTheta * agentVelocity * ((to.getLng().compareTo(from.getLng()) > 0) ? 1.0: -1.0)) + windFactor.getEastComponent();
		
		//System.out.println("effectiveVelocityLong: " + effectiveVelocityLong);
		
		//Magnitude of effective velocity
		double effectiveVelocity = Math.sqrt(Math.pow(effectiveVelocityLat, 2) + Math.pow(effectiveVelocityLong, 2));
		//System.out.println("effective velocity: " + effectiveVelocity);
		
		if(effectiveVelocity == 0) {
			throw new Exception("The RAV effective velocity is zero; it will take inifinitely long to reach destination");
		}
		return getDistanceCost(from, to) / effectiveVelocity;
	}
	
	public static double getBatteryCost(GPSCoordinate c1, GPSCoordinate c2) throws Exception {
		throw new Exception("Not implemented");
	}

}
