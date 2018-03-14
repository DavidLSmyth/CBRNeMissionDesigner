package mission.resolver;

import Communications_Hub.target.classes.agent.Agent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionStrategyGreedy implements MapMissionStrategy{
	
	ArrayList<Agent> agents;
	RegularTraversalGridQuad grid;
	HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths;
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
			grid = new RegularTraversalGridQuad(quad, 20, 20, 20);
			agentPaths = calculateMapEnvironmentPaths(agents);
			
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
			returnMap.put(agent, new ArrayList<GPSCoordinate>());
		}
		int agentNo = 0;
		int noAgents = agents.size();
		Agent currentAgent;
		ArrayList<GPSCoordinate> agentPath;
		while(!exploredPoints.equals(grid.getPoints())) {
			currentAgent = agents.get(agentNo % noAgents);
			agentPath = returnMap.get(currentAgent);
			//get coord which has min cost
			GPSCoordinate nearestCoord = getNearestAvailableCoord(pointsToExplore, new GPSCoordinate(currentAgent.getLocation().get(0),
					currentAgent.getLocation().get(1),
					currentAgent.getLocation().get(2)
					));
			agentPath.add(nearestCoord);
			returnMap.put(currentAgent, agentPath);
			exploredPoints.add(nearestCoord);
			pointsToExplore.remove(nearestCoord);
		}
		return returnMap;
		//iterate over agents until all grid points are assigned
	}
	
	private GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> gpsCoordinates, GPSCoordinate agentLocation) {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(GPSCoordinate coord: gpsCoordinates) {
			distances.add(agentLocation.getMetresToOther(coord));
		}
		int minCoordDistLocation = 0;
		int counter = 0;
		double currentMinDistance = distances.get(0);
		for(double distance: distances) {
			if (distance < currentMinDistance) {
				minCoordDistLocation = counter;
			}
			counter++;
		}
		return gpsCoordinates.get(minCoordDistLocation);
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



	@Override
	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping(ArrayList<Agent> agents) {
		// TODO Auto-generated method stub
		try {
			return calculateMapEnvironmentPaths(agents);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
