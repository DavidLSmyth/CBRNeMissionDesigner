package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import work.assignment.CostType;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateCosts;
import work.assignment.grid.GPSCoordinateUtils;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

//agent velocity 5m/s by default
public abstract class MapMissionBase implements MapMissionStrategy{
	//in order to modify costs, need to extend this class
	ArrayList<Agent> agents;
	RegularTraversalGridQuad grid;
	HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths;
	WindFactor windFactor;
	//the effective velocity which each each should move with
	HashMap<Agent, Double> agentVelocities;
	
	protected MapMissionBase(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates, HashMap<Agent, Double> agentVelocities) throws Exception {
		this(agents, missionBoundingCoordinates, agentVelocities, new WindFactor(0,0));
	}
	
	protected MapMissionBase(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates) throws Exception {
		this(agents, missionBoundingCoordinates, new HashMap<Agent, Double>(), new WindFactor(0,0));
		for(int counter = 0; counter < agents.size(); counter++) {
			//velocity 5m/s by default
			agentVelocities.put(agents.get(counter), Double.valueOf(5.0));
		}
	}
	
	protected MapMissionBase(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates, HashMap<Agent, Double> agentVelocities, WindFactor windFactor) throws Exception {
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
			setAgentVelocities(agentVelocities);
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
	
	public HashMap<Agent, Double> getAgentVelocities() {
		return agentVelocities;
	}

	public void setAgentVelocities(HashMap<Agent, Double> agentVelocities) {
		this.agentVelocities = agentVelocities;
	}
	
	/***************************************** Cost Generating Methods ******************************/
	
	protected ArrayList<Double> generateDistanceCosts(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation){
		ArrayList<Double> distanceCosts = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			distanceCosts.add(GPSCoordinateCosts.getDistanceCost(agentLocation, coord));
		}
		return distanceCosts;
	}
	
	protected ArrayList<Double> generateTimeCosts(ArrayList<GPSCoordinate> availablesgpsCoordinates,
			GPSCoordinate agentLocation,
			WindFactor windFactor, 
			double agentVelocity) throws Exception{		
		ArrayList<Double> timeCosts = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			timeCosts.add(GPSCoordinateCosts.getTimeCost(agentVelocity, windFactor, agentLocation, coord));
		}
		return timeCosts;
	}
	protected ArrayList<Double> generateTimeCosts(ArrayList<GPSCoordinate> availablesgpsCoordinates,
			GPSCoordinate agentLocation,
			double agentVelocity) throws Exception{		
		ArrayList<Double> timeCosts = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			timeCosts.add(GPSCoordinateCosts.getTimeCost(agentVelocity, agentLocation, coord));
		}
		return timeCosts;
	}
	
	protected ArrayList<Double> generateBatteryCosts(ArrayList<GPSCoordinate> availablesgpsCoordinates,
			GPSCoordinate agentLocation) throws Exception{
		ArrayList<Double> batteryCosts = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			//throws an exception
			batteryCosts.add(GPSCoordinateCosts.getBatteryCost(agentLocation, coord));
		}
		return batteryCosts;
	}
	
	/**************************************** Cost Generating Methods *******************************/
	
//should not need to implement this as passing in 0 values to overloaded method should yield same results
//	protected ArrayList<Double> getCosts(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation,
//			CostType costType) throws Exception{
//		ArrayList<Double> costs;
//		switch(costType) {
//			case MAXDISTANCE: costs = generateDistanceCosts(availablesgpsCoordinates,
//					agentLocation); break;
//					
//			case TOTALDISTANCE: costs = generateDistanceCosts(availablesgpsCoordinates,
//					agentLocation); break;
//					
//			case BATTERY: costs = generateBatteryCosts(availablesgpsCoordinates, agentLocation); break;
//			
//			default: throw new Exception("Cannot calculate costs");
//		}
//		return costs;
//	}
	
	protected ArrayList<Double> getCosts(ArrayList<GPSCoordinate> availablesgpsCoordinates, 
			GPSCoordinate agentLocation,
			WindFactor windFactor,
			Double agentVelocity,
			CostType costType) throws Exception{
		ArrayList<Double> costs;
		switch(costType) {
			case MAXDISTANCE: costs = generateDistanceCosts(availablesgpsCoordinates,
					agentLocation); break;
					
			case TOTALDISTANCE: costs = generateDistanceCosts(availablesgpsCoordinates,
					agentLocation); break;
					
			case TOTALTIME: costs = generateTimeCosts(availablesgpsCoordinates,
					agentLocation, windFactor, agentVelocity); break;
				
			case MAXTIME: costs = generateTimeCosts(availablesgpsCoordinates,
					agentLocation, windFactor, agentVelocity); break;
					
			case BATTERY: costs = generateBatteryCosts(availablesgpsCoordinates, agentLocation); break;
			
			default: throw new Exception("Cannot calculate costs");
		}
		return costs;
	}
	
	
	//should be protected?
	public GPSCoordinate getAvailableCoordOfLeastCost(ArrayList<GPSCoordinate> availableGPSCoordinates, GPSCoordinate agentLocation,
			CostType costType,
			WindFactor windFactor,
			Double agentVelocity) throws Exception {
		
		ArrayList<Double> costs = getCosts(availableGPSCoordinates, agentLocation,
				windFactor,
				agentVelocity, 
				costType);
		
		return availableGPSCoordinates.get(getMinIndexOfCostArray(costs));
	}
	
	protected int getMinIndexOfCostArray(ArrayList<Double> costs) {
		int minCoordCostLocation = 0;
		int counter = 0;
		double currentMinCost = costs.get(0);
		for(double cost: costs) {
			if (cost < currentMinCost) {
				minCoordCostLocation = counter;
				currentMinCost = cost;
			}
			counter++;
		}
		return minCoordCostLocation;
	}
	
	//should be protected
//	public GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation) {
//		ArrayList<Double> distances = new ArrayList<Double>();
//		for(GPSCoordinate coord: availablesgpsCoordinates) {
//			distances.add(agentLocation.getMetresToOther(coord));
//		}
//		int minCoordDistLocation = 0;
//		int counter = 0;
//		double currentMinDistance = distances.get(0);
//		for(double distance: distances) {
//			if (distance < currentMinDistance) {
//				minCoordDistLocation = counter;
//				currentMinDistance = distance;
//			}
//			counter++;
//		}
//		return availablesgpsCoordinates.get(minCoordDistLocation);
//	}
	
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
	
	protected HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents) throws Exception{
		//assume a windfactor of 0
		return calculateMapEnvironmentPaths(agents, new WindFactor(0,0));
	}
	protected abstract HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents, WindFactor windFactor) throws Exception;
	//public abstract HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping() throws Exception;
	

}
