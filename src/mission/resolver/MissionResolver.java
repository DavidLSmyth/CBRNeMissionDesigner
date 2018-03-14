package mission.resolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Communications_Hub.target.classes.agent.Agent;
import work.assignment.WorkType;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MissionResolver {
	//Given a list of agents and a work type, returns a map of
	//<Agent, mission> for each agent
	
	ArrayList<Agent> missionAgents;
	WorkType workType;
	ArrayList<GPSCoordinate> missionBoundingCoordinates;

	public MissionResolver(ArrayList<Agent> missionAgents, WorkType workType, ArrayList<GPSCoordinate> missionBoundingCoordinates){
		setMissionAgents(missionAgents);
		setWorkType(workType);
		setMissionBoundingCoordinates(missionBoundingCoordinates);
	}
	
	public HashMap<Agent, Mission> resolveMissions(){
		//Given a  list of agents and a workType, resolves the work that each needs to do in 
		//order to (attempt to) minimize the cost for the overall system
		if(workType == WorkType.MAP) {
			//need to create a mission with mission points, params, commands relative to GPS coordinate
			//List<Agent> agents, List<GPSCoordinate> missionBoundingCoordinates
			MapMissionStrategyGreedy strategy;
			try {
				strategy = new MapMissionStrategyGreedy(getMissionAgents(), getMissionBoundingCoordinates());
				HashMap<Agent, ArrayList<GPSCoordinate>> agentRoutes= strategy.getAgentRoutesForMapping(getMissionAgents());
				HashMap<Agent, Mission> returnMap = new HashMap<Agent, Mission>();
				for(Agent agent: agentRoutes.keySet()) {
					returnMap.put(agent, Mission.makeMission(agent, agentRoutes.get(agent)));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			//throw new Exception("Not implemnted");
			return null;
		}
	}
	
	public ArrayList<Agent> getMissionAgents() {
		return missionAgents;
	}
	
	public void setMissionAgents(ArrayList<Agent> missionAgents) {
		this.missionAgents = missionAgents;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
	
	public ArrayList<GPSCoordinate> getMissionBoundingCoordinates() {
		return missionBoundingCoordinates;
	}

	public void setMissionBoundingCoordinates(ArrayList<GPSCoordinate> missionBoundingCoordinates) {
		this.missionBoundingCoordinates = missionBoundingCoordinates;
	}
}
