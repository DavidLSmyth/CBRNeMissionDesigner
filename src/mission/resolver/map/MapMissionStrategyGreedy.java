package mission.resolver.map;

import agent.Agent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionStrategyGreedy implements MapMissionStrategy{
	
	ArrayList<Agent> agents;
	RegularTraversalGridQuad grid;
	//HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths;
	
	
	public MapMissionStrategyGreedy(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates) throws Exception {
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
			grid = new RegularTraversalGridQuad(quad, 20, 25, 20);
			//agentPaths = calculateMapEnvironmentPaths(agents);
			
		}
		setGrid(grid);
	}


	private HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents) throws Exception{
		//each agent adds to their list of points to explore greedily
		ArrayList<GPSCoordinate> exploredPoints;
		ArrayList<GPSCoordinate> pointsToExplore;
		
		exploredPoints = new ArrayList<GPSCoordinate>();
		pointsToExplore = grid.getPoints();
		HashMap<Agent, ArrayList<GPSCoordinate>> returnMap = new HashMap<Agent, ArrayList<GPSCoordinate>>(); 
		//new Map<Agent, ArrayList<GPSCoordinate>>();
		//initialise returnList;
		for(Agent agent: agents) {
			returnMap.put(agent, new ArrayList<GPSCoordinate>(Arrays.asList(new GPSCoordinate(agent.getLocation().get(0),
					agent.getLocation().get(1),
					agent.getLocation().get(2)
					))));
		}
		//System.out.println("Caulculating paths for " + agents.size() + " agents");
		//System.out.println("Size of returnMap set as: " + returnMap.size());
		for(Agent agent: agents) {
			System.out.println("Agent: " + agent.getId() + " initial path " + (returnMap.get(agent)));
		}
		int agentNo = 0;
		int noAgents = agents.size();
		Agent currentAgent;
		ArrayList<GPSCoordinate> agentPath;
		//While there are still points left to explore
		while(!(pointsToExplore.size() == 0)) {
			currentAgent = agents.get(agentNo % noAgents);
			agentNo++;
			
			agentPath = returnMap.get(currentAgent);
			//get coord which has min cost
			GPSCoordinate nearestCoord = getNearestAvailableCoord(pointsToExplore, agentPath.get(agentPath.size()-1));
//			GPSCoordinate nearestCoord = getNearestAvailableCoord(pointsToExplore, new GPSCoordinate(currentAgent.getLocation().get(0),
//					currentAgent.getLocation().get(1),
//					currentAgent.getLocation().get(2)
//					));
			agentPath.add(nearestCoord);
			//System.out.println("Updated the path of agent " + currentAgent.getId() + " to " + agentPath);
			returnMap.put(currentAgent, agentPath);
			exploredPoints.add(nearestCoord);
			//System.out.println("Points to explore: " + pointsToExplore);
			pointsToExplore.remove(nearestCoord);
		}
		System.out.println("Returning returnMap: " + returnMap.toString());
		return returnMap;
		//iterate over agents until all grid points are assigned
	}
	
	private GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation) {
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
			return calculateMapEnvironmentPaths(getAgents());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}


}
