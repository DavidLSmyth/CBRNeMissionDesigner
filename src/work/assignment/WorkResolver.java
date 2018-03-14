package work.assignment;
import Communications_Hub.target.classes.agent.Agent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mission.resolver.Mission;
import mission.resolver.MissionResolver;
import work.assignment.grid.GPSCoordinate;

public class WorkResolver {
	/*
	 *  A class that is responsible for taking in a work type, #agents, work boundary, optional params
	 * and resolves the work that each individual agent will need to carry out in the form of a mission
	 */
	int noAgents;
	ArrayList<Agent> agents;
	WorkType workType;
	HashMap<Agent, Mission> returnMissions;
	ArrayList<GPSCoordinate> missionBoundingBox;
	Map<Object, Object> optionalParams;
	
	public WorkResolver(WorkType workType, ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingBox) {
		this(workType, agents, missionBoundingBox, null);
	}


	//need some way of passing in # agents, and each type of agent
	public WorkResolver(WorkType workType, ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingBox,
			Map<Object, Object> optionalParams) {
		setWorkType(workType);
		setNoAgents(agents.size());
		setAgents(agents);
		setMissionBoundingBox(missionBoundingBox);
		Map<AgentType, Mission> returnMissions = new HashMap<AgentType, Mission>();	
		//resolve work
		setOptionalParams(optionalParams);
	}




	public void getMissions() {
		//
		Mission returnMission = new Mission();
		//get optimal paths for each agent
		//get all agents that have map c
		//ArrayList<Agent> missionAgents, WorkType workType, ArrayList<GPSCoordinate> missionBoundingCoordinates
		MissionResolver resolver = new MissionResolver(getAgents(), getWorkType(), getMissionBoundingBox());
		setReturnMissions(resolver.resolveMissions());
	}
	
	public Mission getMissionFor(Agent agent) {
		Mission returnMission = getReturnMissions().get(agent);
		//have some way of validating?
		return Mission.makeMission(agent, returnMission);
	}
	
//	public ArrayList<Mission> generateMissions() {
//		//generates a list of missions corresponding to the list of agent types passed into the constructor
//		
//		for(AgentType a: agentTypes) {
//			//MissionResolver m = new MissionResolver(a, getWorkType());
//			
//		}
//		
//	}
	
	public WorkType getWorkType() {
		return workType;
	}
	
	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
	
	public int getNoAgents() {
		return noAgents;
	}
	
	public void setNoAgents(int noAgents) {
		this.noAgents = noAgents;
	}
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	
	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	
	public HashMap<Agent, Mission> getReturnMissions() {
		return returnMissions;
	}

	public void setReturnMissions(HashMap<Agent, Mission> returnMissions) {
		this.returnMissions = returnMissions;
	}
	public ArrayList<GPSCoordinate> getMissionBoundingBox() {
		return missionBoundingBox;
	}

	public void setMissionBoundingBox(ArrayList<GPSCoordinate> missionBoundingBox) {
		this.missionBoundingBox = missionBoundingBox;
	}
	public Map<Object, Object> getOptionalParams() {
		return optionalParams;
	}

	public void setOptionalParams(Map<Object, Object> optionalParams) {
		this.optionalParams = optionalParams;
	}
}
