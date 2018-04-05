package work.assignment.grid;

import agent.Agent;

import work.assignment.environmentalfactors.WindFactor;

public class GPSCoordinateCosts {

	public GPSCoordinateCosts() {
		// TODO Auto-generated constructor stub
	}
	
	public static double getDistanceCost(GPSCoordinate c1, GPSCoordinate c2) {
		//assuming that the agent can travel with a constant velocity once it has accelerated, calculates the distance
		//cost of travelling from one gps coordinate to another
		return c1.getMetresToOther(c2);
	}
	//protected
	protected static double getLatDistanceCost(GPSCoordinate c1, GPSCoordinate c2) {
		return c1.getLatMetresToOther(c2);
	}
	//protected
	protected static double getLongDistanceCost(GPSCoordinate c1, GPSCoordinate c2) {
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
		
		double theta = Math.abs(Math.atan(GPSCoordinateUtils.convertLatDegreeDifferenceToMetresSigned(from.getLat(), to.getLat()) / GPSCoordinateUtils.convertLongDegreeDifferenceToMetresSigned(from.getLng(), to.getLng(), from.getLat())));
//		System.out.println("theta: " + theta);
//		System.out.println("Theta degrees: " + Math.toDegrees(theta));
		
		//System.out.println(windFactor.getNorthComponent());
		//System.out.println(windFactor.getEastComponent());
		double effectiveVelocityLat =  ((GPSCoordinateUtils.convertLatDegreeDifferenceToMetresSigned(to.getLat(), from.getLat())/
				Math.abs(GPSCoordinateUtils.convertLatDegreeDifferenceToMetresSigned(to.getLat(), from.getLat()))) * 
				agentVelocity * Math.sin(theta)) + windFactor.getNorthComponent();
	
		//System.out.println("effectiveVelocityLat: " + effectiveVelocityLat);
		
		double effectiveVelocityLong = (GPSCoordinateUtils.convertLongDegreeDifferenceToMetresSigned(to.getLng(), from.getLng(), from.getLat())/
				Math.abs(GPSCoordinateUtils.convertLongDegreeDifferenceToMetresSigned(to.getLng(), from.getLng(), from.getLat()))) * 
				(agentVelocity * Math.cos(theta)) + windFactor.getEastComponent();
		
		//System.out.println("effectiveVelocityLong: " + effectiveVelocityLong);
		
		double effectiveVelocity = Math.sqrt(Math.pow(effectiveVelocityLat, 2) + Math.pow(effectiveVelocityLong, 2));
		//System.out.println("effective velocity: " + effectiveVelocity);
		
		if(effectiveVelocity == 0) {
			throw new Exception("The RAV effective velocity is zero");
		}
		return getDistanceCost(from, to) / effectiveVelocity;
	}
	
	public static double getBatteryCost(Agent agent, GPSCoordinate c1, GPSCoordinate c2) {
		return 0;
	}

}
