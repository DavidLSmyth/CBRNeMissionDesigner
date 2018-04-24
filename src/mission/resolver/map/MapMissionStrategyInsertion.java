package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import work.assignment.CostType;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;

public class MapMissionStrategyInsertion extends MapMissionBase {

	public MapMissionStrategyInsertion(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates,
			HashMap<Agent, Double> agentVelocities, CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, agentVelocities, costType);
		// TODO Auto-generated constructor stub
	}

	public MapMissionStrategyInsertion(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates,
			CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, costType);
		// TODO Auto-generated constructor stub
	}

	public MapMissionStrategyInsertion(ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingCoordinates,
			HashMap<Agent, Double> agentVelocities, WindFactor windFactor, CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, agentVelocities, windFactor, costType);
		// TODO Auto-generated constructor stub
	}

	//https://web.tuke.sk/fei-cit/butka/hop/htsp.pdf
	@Override
	protected HashMap<Agent, ArrayList<GPSCoordinate>> calculateMapEnvironmentPaths(ArrayList<Agent> agents)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
