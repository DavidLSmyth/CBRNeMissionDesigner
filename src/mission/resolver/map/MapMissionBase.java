package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateUtils;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public abstract class MapMissionBase {
	
	ArrayList<Agent> agents;
	RegularTraversalGridQuad grid;
	HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths;
	WindFactor windFactor;
	
	protected MapMissionBase(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates) throws Exception {
//		setAgents(agents);
//		if(missionBoundingCoordinates.size() != 4) throw new UnsupportedOperationException("Expected 4 coordinates to map environment"
//				+ " but got " + missionBoundingCoordinates.size());
//		else {
//			GPSGridQuadrilateral quad = new GPSGridQuadrilateral(missionBoundingCoordinates.get(0),
//					missionBoundingCoordinates.get(1),
//					missionBoundingCoordinates.get(2),
//					missionBoundingCoordinates.get(3)); 
//			
//			//need to determine the longspacing metres, latspacing metres and 
//			//altitude autonomously
//			//double lngSpacingMetres, double latSpacingMetres, double altitude
//			grid = new RegularTraversalGridQuad(quad, 10, 15, 20);
//			setGrid(grid);
//			this.agentPaths = calculateMapEnvironmentPaths(agents);
//		}
		this(agents, missionBoundingCoordinates, new WindFactor(0,0));
	}
	protected MapMissionBase(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates, WindFactor windFactor) throws Exception {
		setAgents(agents);
		if(missionBoundingCoordinates.size() != 4) throw new UnsupportedOperationException("Expected 4 coordinates to map environment"
				+ " but got " + missionBoundingCoordinates.size());
		else {
			GPSGridQuadrilateral quad = new GPSGridQuadrilateral(missionBoundingCoordinates.get(0),
					missionBoundingCoordinates.get(1),
					missionBoundingCoordinates.get(2),
					missionBoundingCoordinates.get(3)); 
			
			//need to determine the longspacing metres, latspacing metres and 
			//altitude autonomously
			//double lngSpacingMetres, double latSpacingMetres, double altitude
			grid = new RegularTraversalGridQuad(quad, 10, 15, 20);
			setGrid(grid);
			//pre-emptively calculate agent paths
			//this.agentPaths = calculateMapEnvironmentPaths(agents, windFactor);
			this.windFactor = windFactor;
		}
	}
	
	public void updateAgentPaths() throws Exception {
		calculateMapEnvironmentPaths(agents, windFactor);
	}
	
	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentPaths(){
		return this.agentPaths;
	}
	
	//should be protected
	public GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation,
			WindFactor windFactor) throws Exception {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			//take wind factor into account here
			//System.out.println(windFactor.getEastComponent());
			double wind_factor_long = -(GPSCoordinateUtils.convertLongDegreeDifferenceToMetres(coord.getLng(), agentLocation.getLng(), agentLocation.getLat())/agentLocation.getMetresToOther(coord));
			if(coord.getLng() - agentLocation.getLng() < 0) {
				wind_factor_long = - wind_factor_long;
			}
			wind_factor_long = wind_factor_long - windFactor.getEastComponent();
					//+ windFactor.getEastComponent();
			double wind_factor_lat = (GPSCoordinateUtils.convertLatDegreeDifferenceToMetres(coord.getLat(), agentLocation.getLat())/agentLocation.getMetresToOther(coord));
			if(coord.getLat() - agentLocation.getLat() < 0) {
				wind_factor_lat = - wind_factor_lat;
			}
			wind_factor_lat = wind_factor_lat - windFactor.getNorthComponent();
			//+ windFactor.getNorthComponent();
			
			System.out.println("wind factor long: " + wind_factor_long);
			System.out.println("wind factor lat: " + wind_factor_lat);
//			
//			System.out.println("Effective speed moving to coordinate " + coord + " is " +Math.sqrt(Math.pow(wind_factor_long, 2) + Math.pow(wind_factor_lat,2)));
			
			double total_effect = agentLocation.getMetresToOther(coord) + Math.sqrt(Math.pow(wind_factor_long, 2) + Math.pow(wind_factor_lat,2));
			System.out.println("total effect of wind for " + coord + " : " + total_effect);
			distances.add(total_effect);
		}
		int minCoordDistLocation = 0;
		int counter = 0;
		double currentMinDistance = distances.get(0);
		for(double distance: distances) {
			if (distance < currentMinDistance) {
				minCoordDistLocation = counter;
				currentMinDistance = distance;
			}
			counter++;
		}
		return availablesgpsCoordinates.get(minCoordDistLocation);
	}
	
	//should be protected
	public GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation) {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			distances.add(agentLocation.getMetresToOther(coord));
		}
		int minCoordDistLocation = 0;
		int counter = 0;
		double currentMinDistance = distances.get(0);
		for(double distance: distances) {
			if (distance < currentMinDistance) {
				minCoordDistLocation = counter;
				currentMinDistance = distance;
			}
			counter++;
		}
		return availablesgpsCoordinates.get(minCoordDistLocation);
	}
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public RegularTraversalGridQuad getGrid() {
		return grid;
	}

	public void setGrid(RegularTraversalGridQuad grid) {
		this.grid = grid;
	}

	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping() throws Exception {
		// TODO Auto-generated method stub
		try {
			//return calculateMapEnvironmentPaths(getAgents());
			return getAgentPaths();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	protected abstract HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents, WindFactor windFactor) throws Exception;
	//public abstract HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping() throws Exception;
	

}
