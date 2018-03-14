package mission.resolver;
import Communications_Hub.target.classes.agent.Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionStrategyBranchBound implements MapMissionStrategy {

	List<Agent> agents;
	RegularTraversalGridQuad grid;
	public MapMissionStrategyBranchBound(List<Agent> agents, List<GPSCoordinate> missionBoundingCoordinates) throws Exception {
		// TODO Auto-generated constructor stub
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
			//calculateMapEnvironmentPaths(agents, grid);
		}
		setGrid(grid);
	}
	
//	private List<Map<Agent, List<GPSCoordinate>>> calculateMapEnvironmentPaths(List<Agent> agents, RegularTraversalGridQuad grid){
//		//each agent adds to their list of points to explore greedily
//		List<GPSCoordinate> exploredPoints;
//		List<GPSCoordinate> pointsToExplore;
//
//	}

	
	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public RegularTraversalGridQuad getGrid() {
		return grid;
	}

	public void setGrid(RegularTraversalGridQuad grid) {
		this.grid = grid;
	}

	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping(ArrayList<Agent> agents) {
		// TODO Auto-generated method stub
		return null;
	}

}
