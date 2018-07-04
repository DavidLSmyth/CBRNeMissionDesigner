package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;

import GPSUtils.GPSCoordinate;
import GPSUtils.grid.GPSPolygonGrid;
import agent.Agent;
import mission.resolver.Mission;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionAnalyserMapMission extends MapMissionAnalyserAgent{
	
	HashMap<Agent, Mission> agentMissions;
	GPSPolygonGrid polyGrid; 
	protected MapMissionAnalyserAgent analyser;
	
	//A class that analyses an overall mission
	public MapMissionAnalyserMapMission(HashMap<Agent, Mission> agentMissions,
		GPSPolygonGrid polyGrid,
		HashMap<Agent, Double> velocities) {
		// TODO Auto-generated constructor stub
//		RegularTraversalGridQuad traversalGrid, 
//		ArrayList<Agent> agents, 
//		HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap,
//		//ArrayList<ArrayList<GPSCoordinate>> agentPaths
//		HashMap<Agent, Double> velocities
		super(polyGrid, (ArrayList)agentMissions.keySet(), new HashMap<Agent, ArrayList<GPSCoordinate>>(), velocities);
		HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap = new HashMap<Agent, ArrayList<GPSCoordinate>>();
		for(Agent agent: agentMissions.keySet()) {
			agentPathsMap.put(agent, agentMissions.get(agent).getMissionGPSCoordinates());
		}
		setAgentPathsMap(agentPathsMap);
		
	}
	
	public MapMissionAnalyserMapMission(HashMap<Agent, Mission> agentMissions,
			GPSPolygonGrid polyGrid) {
		// TODO Auto-generated constructor stub
		//				RegularTraversalGridQuad traversalGrid, 
		//				ArrayList<Agent> agents, 
		//				HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap,
		//				//ArrayList<ArrayList<GPSCoordinate>> agentPaths
		//				HashMap<Agent, Double> velocities
		super(polyGrid, (ArrayList)agentMissions.keySet(), new HashMap<Agent, ArrayList<GPSCoordinate>>());
		HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap = new HashMap<Agent, ArrayList<GPSCoordinate>>();
		for(Agent agent: agentMissions.keySet()) {
			agentPathsMap.put(agent, agentMissions.get(agent).getMissionGPSCoordinates());
		}
		setAgentPathsMap(agentPathsMap);
	}
	
	public MapMissionAnalyserMapMission(HashMap<Agent, Mission> agentMissions) {
		// TODO Auto-generated constructor stub
		//				RegularTraversalGridQuad traversalGrid, 
		//				ArrayList<Agent> agents, 
		//				HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap,
		//				//ArrayList<ArrayList<GPSCoordinate>> agentPaths
		//				HashMap<Agent, Double> velocities
		super((ArrayList)agentMissions.keySet(), new HashMap<Agent, ArrayList<GPSCoordinate>>());
		HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap = new HashMap<Agent, ArrayList<GPSCoordinate>>();
		for(Agent agent: agentMissions.keySet()) {
			agentPathsMap.put(agent, agentMissions.get(agent).getMissionGPSCoordinates());
		}
		setAgentPathsMap(agentPathsMap);
	}
	
	
	

}
