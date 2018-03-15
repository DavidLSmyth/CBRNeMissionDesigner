package work.assignment;
import agent.Agent;
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
	HashMap<Agent, Mission> agentMissions;
	ArrayList<GPSCoordinate> missionBoundingBox;
	Map<Object, Object> optionalParams;
	
	public WorkResolver(WorkType workType, ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingBox) throws Exception {
		this(workType, agents, missionBoundingBox, null);
	}


	//need some way of passing in # agents, and each type of agent
	public WorkResolver(WorkType workType, ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingBox,
			HashMap<Object, Object> optionalParams) throws Exception {
		setWorkType(workType);
		setNoAgents(agents.size());
		setAgents(agents);
		setMissionBoundingBox(missionBoundingBox);
		setAgentMissions(new HashMap<Agent, Mission>());
		setOptionalParams(optionalParams);
		calculateMissions();
	}
	
	public void updateAgentMissions() throws Exception {
		calculateMissions();
	}

	protected void calculateMissions() throws Exception {
		//sets the missions of each agent for the provided data
		System.out.println("Calculating the missions each agent needs to carry out");
		MissionResolver resolver = new MissionResolver(getAgents(), getWorkType(), getMissionBoundingBox());
		setAgentMissions(resolver.resolveMissions());
	}
	
	public Mission getMissionFor(Agent agent) {
		return getAgentMissions().get(agent);
	}
	
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
	
	public HashMap<Agent, Mission> getAgentMissions() {
		return agentMissions;
	}

	public void setAgentMissions(HashMap<Agent, Mission> newAgentMissions) {
		this.agentMissions = newAgentMissions;
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
