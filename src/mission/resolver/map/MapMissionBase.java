package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateCosts;
import GPSUtils.grid.GPSPolygon;
import GPSUtils.grid.GPSPolygonGrid;
import agent.Agent;
import work.assignment.CostType;
import work.assignment.environmentalfactors.WindFactor;

//agent velocity 5m/s by default
public abstract class MapMissionBase implements MapMissionStrategy{
	//in order to modify costs, need to extend this class
	List<Agent> agents;
	GPSPolygonGrid grid;
	Map<Agent, List<GPSCoordinate>> agentPaths;
	
	//assuming windFactor is constant for all agents
	WindFactor windFactor;
	
	//the effective velocity which each each should move with
	Map<Agent, Double> agentVelocities;
	
	//The cost type to be taken into account when the agents 
	//routes are being planned
	CostType costType;
	
	protected MapMissionBase(List<Agent> agents, 
			List<GPSCoordinate> missionBoundingCoordinates, 
			Map<Agent, Double> agentVelocities,
			CostType costType) throws Exception {
		
		this(agents, missionBoundingCoordinates, agentVelocities, new WindFactor(0,0), costType);
	}
	
	protected MapMissionBase(List<Agent> agents, 
			List<GPSCoordinate> missionBoundingCoordinates,
			CostType costType) throws Exception {
		
		this(agents, missionBoundingCoordinates, new HashMap<Agent, Double>(), new WindFactor(0,0), costType);
		for(int counter = 0; counter < agents.size(); counter++) {
			//velocity 5m/s by default
			agentVelocities.put(agents.get(counter), Double.valueOf(5.0));
		}
	}
	
	protected MapMissionBase(List<Agent> agents,
			List<GPSCoordinate> missionBoundingCoordinates, 
			Map<Agent, Double> agentVelocities, 
			WindFactor windFactor, 
			CostType costType) throws Exception {
		
			setAgents(agents);
//		if(missionBoundingCoordinates.size() != 4) throw new UnsupportedOperationException("Expected 4 coordinates to map environment"
//				+ " but got " + missionBoundingCoordinates.size());
			GPSPolygon poly = new GPSPolygon(missionBoundingCoordinates); 
			
			System.out.println("performing exploration mission without lat/long spacing ");
			//need to determine the longspacing metres, latspacing metres and 
			//altitude autonomously
			//double lngSpacingMetres, double latSpacingMetres, double altitude
			//for 20% image overlap, use formula w = 4/3 * H * tan(theta), 
			//where H is the altitude that the RAV operates at, theta is the
			//FOV of the camera divided by two
			//don't have this hard-coded
			double operationalAltitude = 32;
			double theta = 45;
			//in general for x% image overlap:
			//2 * operationalAltitude * Math.tan(theta) * ((1-x)/(x+1))
			//for 20% image overlap, 
			double RAVSpacing = (4/3) * operationalAltitude * Math.tan(Math.toRadians(theta));
			grid = new GPSPolygonGrid(poly, RAVSpacing, RAVSpacing, operationalAltitude);
			setGrid(grid);
			setAgentVelocities(agentVelocities);
			//pre-emptively calculate agent paths
			//this.agentPaths = calculateMapEnvironmentPaths(agents, windFactor);
			setWindFactor(windFactor);
			setCostType(costType);
	}
	
	protected MapMissionBase(List<Agent> agents,
			List<GPSCoordinate> missionBoundingCoordinates, 
			Map<Agent, Double> agentVelocities, 
			WindFactor windFactor, 
			CostType costType, 
			double latSpacing,
			double lngSpacing) throws Exception {
		
			System.out.println("performing exploration mission with lat spacing " + latSpacing + " and long spacing " + lngSpacing);
			setAgents(agents);
//		if(missionBoundingCoordinates.size() != 4) throw new UnsupportedOperationException("Expected 4 coordinates to map environment"
//				+ " but got " + missionBoundingCoordinates.size());
			GPSPolygon poly = new GPSPolygon(missionBoundingCoordinates); 
			
			//need to determine the longspacing metres, latspacing metres and 
			//altitude autonomously
			//double lngSpacingMetres, double latSpacingMetres, double altitude
			//for 20% image overlap, use formula w = 4/3 * H * tan(theta), 
			//where H is the altitude that the RAV operates at, theta is the
			//FOV of the camera divided by two
			//don't have this hard-coded
			double operationalAltitude = 32;
			//double theta = 45;
			//in general for x% image overlap:
			//2 * operationalAltitude * Math.tan(theta) * ((1-x)/(x+1))
			//for 20% image overlap, 
			//double RAVSpacing = (4/3) * operationalAltitude * Math.tan(Math.toRadians(theta));
			grid = new GPSPolygonGrid(poly, latSpacing, lngSpacing, operationalAltitude);
			setGrid(grid);
			setAgentVelocities(agentVelocities);
			//pre-emptively calculate agent paths
			//this.agentPaths = calculateMapEnvironmentPaths(agents, windFactor);
			setWindFactor(windFactor);
			setCostType(costType);
	}
	
	public CostType getCostType() {
		return costType;
	}

	public void setCostType(CostType costType) {
		this.costType = costType;
	}

	
	public void updateAgentPaths() throws Exception {
		calculateMapEnvironmentPaths(agents);
	}
	
	public Map<Agent, List<GPSCoordinate>> getAgentPaths(){
		return this.agentPaths;
	}

	public void setAgentPaths(Map<Agent, List<GPSCoordinate>> newAgentPaths){
		this.agentPaths = newAgentPaths;
	}
	
	public Map<Agent, Double> getAgentVelocities() {
		return agentVelocities;
	}

	public void setAgentVelocities(Map<Agent, Double> agentVelocities) {
		this.agentVelocities = agentVelocities;
	}
	
	public WindFactor getWindFactor() {
		return windFactor;
	}

	public void setWindFactor(WindFactor windFactor) {
		this.windFactor = windFactor;
	}
	
	/***************************************** Cost Generating Methods 
	 * @throws Exception ******************************/
	
	protected List<Double> generateDistanceCosts(List<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation) throws Exception{
		List<Double> distanceCosts = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			distanceCosts.add(GPSCoordinateCosts.getDistanceCost(agentLocation, coord));
		}
		return distanceCosts;
	}
	
	protected List<Double> generateTimeCosts(List<GPSCoordinate> availablesgpsCoordinates,
			GPSCoordinate agentLocation,
			WindFactor windFactor, 
			double agentVelocity) throws Exception{		
		List<Double> timeCosts = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			timeCosts.add(GPSCoordinateCosts.getTimeCost(agentVelocity, windFactor, agentLocation, coord));
		}
		return timeCosts;
	}
	protected List<Double> generateTimeCosts(List<GPSCoordinate> availablesgpsCoordinates,
			GPSCoordinate agentLocation,
			double agentVelocity) throws Exception{		
		List<Double> timeCosts = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			timeCosts.add(GPSCoordinateCosts.getTimeCost(agentVelocity, agentLocation, coord));
		}
		return timeCosts;
	}
	
	protected List<Double> generateBatteryCosts(List<GPSCoordinate> availablesgpsCoordinates,
			GPSCoordinate agentLocation) throws Exception{
		List<Double> batteryCosts = new ArrayList<Double>();
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
	
	protected List<Double> getCosts(List<GPSCoordinate> availableGPSCoordinates,
			GPSCoordinate agentLocation, 
			Agent agent, 
			CostType costType) throws Exception{
		return getCosts(availableGPSCoordinates,
				agentLocation,
				getWindFactor(),
				getAgentVelocities().get(agent),
				costType);
	}
	protected List<Double> getCosts(List<GPSCoordinate> availableGPSCoordinates, 
			GPSCoordinate agentLocation,
			Double agentVelocity,
			CostType costType) throws Exception{
		return getCosts(availableGPSCoordinates, agentLocation, new WindFactor(0, 0), agentVelocity, costType);
		
	}
	
	
	//gets the costs of travelling to each of the GPSCoordinates in availablesgpsCoordinates
	//from agentLocation
	protected List<Double> getCosts(List<GPSCoordinate> availableGPSCoordinates, 
			GPSCoordinate agentLocation,
			WindFactor windFactor,
			Double agentVelocity,
			CostType costType) throws Exception{
		List<Double> costs;
		switch(costType) {
			case MAXDISTANCE: costs = generateDistanceCosts(availableGPSCoordinates,
					agentLocation); break;
					
			case TOTALDISTANCE: costs = generateDistanceCosts(availableGPSCoordinates,
					agentLocation); break;
					
			case TOTALTIME: costs = generateTimeCosts(availableGPSCoordinates,
					agentLocation, windFactor, agentVelocity); break;
				
			case MAXTIME: costs = generateTimeCosts(availableGPSCoordinates,
					agentLocation, windFactor, agentVelocity); break;
					
			case BATTERY: costs = generateBatteryCosts(availableGPSCoordinates, agentLocation); break;
			
			default: throw new Exception("Cannot calculate costs");
		}
		return costs;
	}
	
	
	//should be protected?
	public GPSCoordinate getAvailableCoordOfLeastCost(List<GPSCoordinate> availableGPSCoordinates, GPSCoordinate agentLocation,
			CostType costType,
			WindFactor windFactor,
			Double agentVelocity) throws Exception {
		
		List<Double> costs = getCosts(availableGPSCoordinates, agentLocation,
				windFactor,
				agentVelocity, 
				costType);
		System.out.println("Cost of reaching coords: " + availableGPSCoordinates.toString());
		System.out.println(costs.toString());
		return availableGPSCoordinates.get(getMinIndexOfCostArray(costs));
	}
	
	public GPSCoordinate getAvailableCoordOfLeastCost(List<GPSCoordinate> availableGPSCoordinates, GPSCoordinate agentLocation,
			CostType costType,
			Double agentVelocity) throws Exception {
		
		List<Double> costs = getCosts(availableGPSCoordinates, agentLocation,
				agentVelocity, 
				costType);
		
		return availableGPSCoordinates.get(getMinIndexOfCostArray(costs));
	}
	
	public GPSCoordinate getAvailableCoordOfLeastCost(List<GPSCoordinate> availableGPSCoordinates, GPSCoordinate agentLocation,
			Agent agent,
			CostType costType) throws Exception {
		
		List<Double> costs = getCosts(availableGPSCoordinates, agentLocation,
				agent, 
				costType);
		
		return availableGPSCoordinates.get(getMinIndexOfCostArray(costs));
	}
	
	protected int getMinIndexOfCostArray(List<Double> costs) {
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
	
	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public GPSPolygonGrid getGrid() {
		return grid;
	}

	public void setGrid(GPSPolygonGrid grid) {
		this.grid = grid;
	}

	public Map<Agent, List<GPSCoordinate>> getAgentRoutesForMapping() throws Exception {
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
	
	protected abstract Map<Agent, List<GPSCoordinate>> calculateMapEnvironmentPaths(List<Agent> agents) throws Exception;
		//assume a windfactor of 0
		//return calculateMapEnvironmentPaths(agents, new WindFactor(0,0));
	//}
	//protected abstract HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents, WindFactor windFactor) throws Exception;
	//public abstract HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping() throws Exception;
	

}
