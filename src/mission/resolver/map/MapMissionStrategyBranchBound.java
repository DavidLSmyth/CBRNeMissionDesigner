package mission.resolver.map;
import agent.Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionStrategyBranchBound extends MapMissionBase implements MapMissionStrategy{
	
	public MapMissionStrategyBranchBound(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates) throws Exception {
		// TODO Auto-generated constructor stub
		super(agents, missionBoundingCoordinates);
		agentPaths = calculateMapEnvironmentPaths(agents);
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
//			grid = new RegularTraversalGridQuad(quad, 20, 20, 20);
//			//calculateMapEnvironmentPaths(agents, grid);
//		}
//		setGrid(grid);
	}
	
//	private List<Map<Agent, List<GPSCoordinate>>> calculateMapEnvironmentPaths(List<Agent> agents, RegularTraversalGridQuad grid){
//		//each agent adds to their list of points to explore greedily
//		List<GPSCoordinate> exploredPoints;
//		List<GPSCoordinate> pointsToExplore;
//
//	}

	@Override
	protected HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents,
			WindFactor windFactor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
