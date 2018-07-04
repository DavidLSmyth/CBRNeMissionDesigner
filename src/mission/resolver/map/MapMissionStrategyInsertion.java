package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GPSUtils.GPSCoordinate;
import agent.Agent;
import work.assignment.CostType;
import work.assignment.environmentalfactors.WindFactor;

public class MapMissionStrategyInsertion extends MapMissionBase {

	public MapMissionStrategyInsertion(List<Agent> agents, List<GPSCoordinate> missionBoundingCoordinates,
			Map<Agent, Double> agentVelocities, CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, agentVelocities, costType);
		// TODO Auto-generated constructor stub
	}

	public MapMissionStrategyInsertion(List<Agent> agents, List<GPSCoordinate> missionBoundingCoordinates,
			CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, costType);
		// TODO Auto-generated constructor stub
	}

	public MapMissionStrategyInsertion(List<Agent> agents, List<GPSCoordinate> missionBoundingCoordinates,
			Map<Agent, Double> agentVelocities, WindFactor windFactor, CostType costType) throws Exception {
		super(agents, missionBoundingCoordinates, agentVelocities, windFactor, costType);
		// TODO Auto-generated constructor stub
	}

	//https://web.tuke.sk/fei-cit/butka/hop/htsp.pdf
	@Override
	protected HashMap<Agent, List<GPSCoordinate>> calculateMapEnvironmentPaths(List<Agent> agents)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
