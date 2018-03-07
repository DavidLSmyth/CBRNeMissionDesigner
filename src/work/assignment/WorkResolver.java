package work.assignment;

import java.util.ArrayList;
import java.util.Map;

public class WorkResolver {
	/*
	 *  A class that is responsible for taking in a work type, #agents, work boundary, optional params
	 * and resolves the work that each individual agent will need to carry out in the form of a mission
	 */
	int noAgents;
	ArrayList<AgentType> agentTypes;
	WorkType workType;
	
	//need some way of passing in # agents, and each type of agent
	public WorkResolver(WorkType workType, ArrayList<AgentType> agentTypes, ArrayList<Object> optionalParams) {
		setWorkType(workType);
		setNoAgents(agentTypes.size());
		setAgentTypes(agentTypes);
		//
		if(workType == WorkType.MAP) {
			
		}
		
	}
	
	public ArrayList<Mission> generateMissions() {
		//generates a list of missions corresponding to the list of agent types passed into the constructor
		
		for(AgentType a: agentTypes) {
			MissionResolver m = new MissionResolver(a, getWorkType());
			
		}
		
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
	
	public ArrayList<AgentType> getAgentTypes() {
		return agentTypes;
	}
	
	public void setAgentTypes(ArrayList<AgentType> agentType) {
		this.agentTypes = agentType;
	}
}
