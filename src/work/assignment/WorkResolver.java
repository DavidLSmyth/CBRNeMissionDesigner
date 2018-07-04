package work.assignment;
import agent.Agent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GPSUtils.GPSCoordinate;
import mission.resolver.Mission;
import mission.resolver.MissionResolver;

public class WorkResolver {
	/*
	 *  A class that is responsible for taking in a work type, #agents, work boundary, optional params
	 * and resolves the work that each individual agent will need to carry out in the form of a mission.
	 * Here cost type should be decided by the program, but allow the user to overload if they choose - reconsider this.
	 */
	int noAgents;
	List<Agent> agents;
	//type of work that needs to be resolved among multiple agents
	WorkType workType;
	//type of cost that needs to be minimized
	CostType costType;
	//give a default costType of total distance traveled by agents - this is probably bad? 
	//this should be autonomously decided given they type of mission
	//protected static CostType defaultCostType = CostType.TOTALDISTANCE;
	
	Map<Agent, Mission> agentMissions;
	List<GPSCoordinate> missionBoundingPolygon;
	Map<Object, Object> optionalParams;
	
	

	
//	public WorkResolver(WorkType workType, ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingBox) throws Exception {
//		this(workType, agents, null ,missionBoundingBox, null);
//	}
	
	public WorkResolver(WorkType workType, List<Agent> agents, CostType costType, List<GPSCoordinate> missionBoundingPolygon) throws Exception {
		this(workType, agents, costType, missionBoundingPolygon, null, null, null);
	}
	public WorkResolver(WorkType workType, List<Agent> agents, CostType costType, 
			List<GPSCoordinate> missionBoundingPolygon, Double latSpacing, Double lngSpacing) throws Exception {
		this(workType, agents, costType, missionBoundingPolygon, null, latSpacing, lngSpacing);

	}

	//need some way of passing in # agents, and each type of agent
	public WorkResolver(WorkType workType, List<Agent> agents, List<GPSCoordinate> missionBoundingPolygon,
			Map<Object, Object> optionalParams) throws Exception {
		this(workType, agents, null, missionBoundingPolygon, optionalParams, null, null);
	}
	
	//need some way of passing in # agents, and each type of agent
	//add option in the constructor to calculate agent missions immediately or 
	//essentially lazily evaluate
	public WorkResolver(WorkType workType, List<Agent> agents, CostType costType, List<GPSCoordinate> missionBoundingPolygon,
			Map<Object, Object> optionalParams, Double latSpacing, Double lngSpacing) throws Exception {
		setWorkType(workType);
		setNoAgents(agents.size());
		setAgents(agents);
		if(costType != null) {
			setCostType(costType);
		}
		else {
			switch(workType) {
				case MAP: setCostType(CostType.TOTALTIME); break;
				case SEARCH: setCostType(CostType.BATTERY); break;
				default: setCostType(CostType.TOTALTIME); break;
			}
		}
		setMissionBoundingPolygon(missionBoundingPolygon);
		setAgentMissions(new HashMap<Agent, Mission>());
		setOptionalParams(optionalParams);
		
		//get rid of this in future, not a good solution
		if(latSpacing != null && lngSpacing != null) {
			//calculate the agent missions on construction?
			updateAgentMissions(latSpacing, lngSpacing);
		}
		else {
			//calculate the agent missions on construction?
			updateAgentMissions();
		}
	}
	
	
	
	public void updateAgentMissions() throws Exception {
		calculateMissions();
	}
	
	public void updateAgentMissions(double latSpacing, double lngSpacing) throws Exception {
		calculateMissions(latSpacing, lngSpacing);
	}
	
	public CostType getCostType() {
		return costType;
	}
	public void setCostType(CostType costType) throws Exception {
		if(costType == null) {
			//attempt to minimize total distance by default
			throw new Exception("Cost type not provided");
			//this.costType = CostType.TOTALTIME;
		}
		this.costType = costType;
	}

	protected void calculateMissions() throws Exception {
		//sets the missions of each agent for the provided data
		System.out.println("Calculating the missions each agent needs to carry out");
		MissionResolver resolver;
		if(getOptionalParams()!= null) {
			System.out.println("Using mission resolver with wind");
			resolver = new MissionResolver(getAgents(), getWorkType(), getMissionBoundingBox(), getOptionalParams());
		}
		else {
			resolver = new MissionResolver(getAgents(), getWorkType(), getMissionBoundingBox());
		}
		setAgentMissions(resolver.resolveMissions());
	}
	
	protected void calculateMissions(double latSpacing, double lngSpacing) throws Exception {
		//sets the missions of each agent for the provided data
		System.out.println("Calculating the missions each agent needs to carry out");
		MissionResolver resolver;
		if(getOptionalParams()!= null) {
			System.out.println("Using mission resolver with wind");
			resolver = new MissionResolver(getAgents(), getWorkType(), getMissionBoundingBox(), getOptionalParams());
		}
		else {
			resolver = new MissionResolver(getAgents(), getWorkType(), getMissionBoundingBox());
		}
		setAgentMissions(resolver.resolveMissions(latSpacing, lngSpacing));
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
	
	public List<Agent> getAgents() {
		return agents;
	}
	
	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	
	public Map<Agent, Mission> getAgentMissions() {
		return agentMissions;
	}

	public void setAgentMissions(Map<Agent, Mission> map) {
		this.agentMissions = map;
	}
	public List<GPSCoordinate> getMissionBoundingBox() {
		return missionBoundingPolygon;
	}

	public void setMissionBoundingPolygon(List<GPSCoordinate> missionBoundingPolygon2) {
		this.missionBoundingPolygon = missionBoundingPolygon2;
	}
	public Map<Object, Object> getOptionalParams() {
		return optionalParams;
	}

	public void setOptionalParams(Map<Object, Object> optionalParams2) {
		this.optionalParams = optionalParams2;
	}
}
