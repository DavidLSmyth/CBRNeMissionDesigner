package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;

import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.GPSGridQuadrilateral;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public abstract class MapMissionBase {
	
	ArrayList<Agent> agents;
	RegularTraversalGridQuad grid;
	
	protected MapMissionBase(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates) throws Exception {
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
	
	protected GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation,
			WindFactor windFactor) {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(GPSCoordinate coord: availablesgpsCoordinates) {
			//take wind factor into account here
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
	
	protected GPSCoordinate getNearestAvailableCoord(ArrayList<GPSCoordinate> availablesgpsCoordinates, GPSCoordinate agentLocation) {
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
			return calculateMapEnvironmentPaths(getAgents());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public abstract HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping() throws Exception;
	

}
