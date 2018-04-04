package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import mission.resolver.Mission;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionAnalyserMapMission {
	
	HashMap<Agent, Mission> agentMissions;
	RegularTraversalGridQuad traversalGrid; 
	protected MapMissionAnalyserAgentNo analyser;
	
	public MapMissionAnalyserMapMission(HashMap<Agent, Mission> agentMissions,
	RegularTraversalGridQuad traversalGrid,
	MapMissionAnalyserAgentNo analyser) {
		// TODO Auto-generated constructor stub
		analyser = new MapMissionAnalyserAgentNo(agentMissions, traversalGrid);
	}

}
