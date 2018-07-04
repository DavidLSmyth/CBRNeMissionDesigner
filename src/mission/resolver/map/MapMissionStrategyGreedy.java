package mission.resolver.map;

import agent.Agent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GPSUtils.GPSCoordinate;
import work.assignment.CostType;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionStrategyGreedy extends MapMissionBase {
	
	public MapMissionStrategyGreedy(List<Agent> agents,
			List<GPSCoordinate> missionBoundingCoordinates,
			CostType costType) throws Exception {
		
		super(agents, missionBoundingCoordinates, costType);
		//pre-emptively calculate agent paths
		setAgentPaths(calculateMapEnvironmentPaths(agents));
	}
	
	public MapMissionStrategyGreedy(List<Agent> agents, 
			List<GPSCoordinate> missionBoundingCoordinates,
			Map<Agent, Double> agentVelocities, 
			CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, agentVelocities, new WindFactor(0,0), costType);
		setAgentPaths(calculateMapEnvironmentPaths(agents));
	}
	
	public MapMissionStrategyGreedy(List<Agent> agents, 
			List<GPSCoordinate> missionBoundingCoordinates,
			CostType costType,
			double latSpacing, 
			double lngSpacing) throws Exception {
		super(agents, missionBoundingCoordinates, new HashMap<Agent, Double>(), new WindFactor(0,0), costType, latSpacing, lngSpacing);
		setAgentPaths(calculateMapEnvironmentPaths(agents));
	}
	
	public MapMissionStrategyGreedy(List<Agent> agents, 
			List<GPSCoordinate> missionBoundingCoordinates,
			Map<Agent, Double> agentVelocities,
			WindFactor windFactor, 
			CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, agentVelocities, windFactor, costType);
		setAgentPaths(calculateMapEnvironmentPaths(agents));
	}
	
	
	protected Map<Agent, List<GPSCoordinate>> initialiseReturnMap(List<Agent> agents) throws Exception{
		Map<Agent, List<GPSCoordinate>> returnMap = new HashMap<Agent, List<GPSCoordinate>>(); 
		for(Agent agent: agents) {
			returnMap.put(agent, new ArrayList<GPSCoordinate>(Arrays.asList(new GPSCoordinate(agent.getLocation().get(0),
					agent.getLocation().get(1),
					agent.getLocation().get(2)))));
		}
		return returnMap;
	}
	
	protected Map<Agent, List<GPSCoordinate>> calculateMapEnvironmentPaths(List<Agent> agents) throws Exception{
		//each agent adds to their list of points to explore greedily
		List<GPSCoordinate> exploredPoints = new ArrayList<GPSCoordinate>();
		List<GPSCoordinate> pointsToExplore = grid.generateContainedGPSCoordinates();
		System.out.println("pointsToExplore: " + pointsToExplore);
		//System.out.println("Working with wind factor: " + windFactor);
//		exploredPoints = 
//		pointsToExplore 
		
		//contains the route for each agent
		//HashMap<Agent, ArrayList<GPSCoordinate>> returnMap = new HashMap<Agent, ArrayList<GPSCoordinate>>(); 
		
		Map<Agent, List<GPSCoordinate>> returnMap = initialiseReturnMap(agents);
		//System.out.println("Caulculating paths for " + agents.size() + " agents");
		//System.out.println("Size of returnMap set as: " + returnMap.size());
		for(Agent agent: agents) {
			System.out.println("Agent: " + agent.getId() + " initial path " + (returnMap.get(agent)));
		}
		
		//denotes the agent who currently can choose next GPS coordinate to explore
		int agentNo = 0;
		int noAgents = agents.size();
		Agent currentAgent;
		List<GPSCoordinate> agentPath;
		System.out.println(pointsToExplore.size() + " points to explore");
		//While there are still points left to explore
		System.out.println("Greedy algorithm using cost type: " + getCostType());
		while(!(pointsToExplore.size() == 0)) {
			currentAgent = agents.get(agentNo % noAgents);
			agentNo++;
			agentPath = returnMap.get(currentAgent);
			//get coord which has min cost for the agent to travel to from its current location
//			ArrayList<GPSCoordinate> availableGPSCoordinates, GPSCoordinate agentLocation,
//			CostType costType,
//			WindFactor windFactor,
//			Double agentVelocity
			
			System.out.println("Points left to explore: " + pointsToExplore);
			GPSCoordinate nearestCoord = getAvailableCoordOfLeastCost(pointsToExplore, 
					agentPath.get(agentPath.size()-1), 
					//getCostType(),
					costType.MAXDISTANCE,
					getWindFactor(),
					currentAgent.getVehicle().getOperationalVelocity());
			System.out.println("Found least cost coord for agent " + currentAgent + " to be " + nearestCoord);
			agentPath.add(nearestCoord);
			System.out.println("Updated the path of agent " + currentAgent.getId() + " to " + agentPath);
			//update the returnMap
			returnMap.put(currentAgent, agentPath);
			//append the current coordinate to the explored points map
			exploredPoints.add(nearestCoord);
			System.out.println("Points to explore: " + pointsToExplore);
			//remove the coordinate from the points to explore map
			pointsToExplore.remove(nearestCoord);
		}
		//System.out.println("Returning returnMap: " + returnMap.toString());
		return returnMap;
		//iterate over agents until all grid points are assigned
	}

//	protected HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents) throws Exception{
//		return calculateMapEnvironmentPaths(agents, new WindFactor(0,0));
//	}


//	protected HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents) throws Exception{
//		//return calculateMapEnvironmentPaths(agents, new WindFactor(0,0));
//		//each agent adds to their list of points to explore greedily
//		ArrayList<GPSCoordinate> exploredPoints;
//		ArrayList<GPSCoordinate> pointsToExplore;
//		
//		exploredPoints = new ArrayList<GPSCoordinate>();
//		pointsToExplore = grid.getPoints();
//		HashMap<Agent, ArrayList<GPSCoordinate>> returnMap = new HashMap<Agent, ArrayList<GPSCoordinate>>(); 
//		//new Map<Agent, ArrayList<GPSCoordinate>>();
//		//initialise returnList;
//		for(Agent agent: agents) {
//			returnMap.put(agent, new ArrayList<GPSCoordinate>(Arrays.asList(new GPSCoordinate(agent.getLocation().get(0),
//					agent.getLocation().get(1),
//					agent.getLocation().get(2)
//					))));
//		}
//		//System.out.println("Caulculating paths for " + agents.size() + " agents");
//		//System.out.println("Size of returnMap set as: " + returnMap.size());
//		for(Agent agent: agents) {
//			System.out.println("Agent: " + agent.getId() + " initial path " + (returnMap.get(agent)));
//		}
//		int agentNo = 0;
//		int noAgents = agents.size();
//		Agent currentAgent;
//		ArrayList<GPSCoordinate> agentPath;
//		//While there are still points left to explore
//		while(!(pointsToExplore.size() == 0)) {
//			currentAgent = agents.get(agentNo % noAgents);
//			agentNo++;
//			
//			agentPath = returnMap.get(currentAgent);
//			//get coord which has min cost
//			GPSCoordinate nearestCoord = getNearestAvailableCoord(pointsToExplore, agentPath.get(agentPath.size()-1));
////			GPSCoordinate nearestCoord = getNearestAvailableCoord(pointsToExplore, new GPSCoordinate(currentAgent.getLocation().get(0),
////					currentAgent.getLocation().get(1),
////					currentAgent.getLocation().get(2)
////					));
//			agentPath.add(nearestCoord);
//			//System.out.println("Updated the path of agent " + currentAgent.getId() + " to " + agentPath);
//			returnMap.put(currentAgent, agentPath);
//			exploredPoints.add(nearestCoord);
//			//System.out.println("Points to explore: " + pointsToExplore);
//			pointsToExplore.remove(nearestCoord);
//		}
//		System.out.println("Returning returnMap: " + returnMap.toString());
//		return returnMap;
//		//iterate over agents until all grid points are assigned
//	}


	
//	private GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation) {
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

//	@Override
//	public Map<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping(ArrayList<Agent> agents, RegularTraversalGridQuad grid){
//		// TODO Auto-generated method stub
//		//return a Map of agent, list of GPSCoordinates, where the values are the GPS Coordinates that
//		//each agent must visit in order to fulfill the map task
//		
//		
//		//If list of agents is of size one, return 
//		return agentPaths;
//	}
	
	


}
