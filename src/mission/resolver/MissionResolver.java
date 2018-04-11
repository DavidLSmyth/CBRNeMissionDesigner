package mission.resolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Agent;
import agent.vehicle.VehicleType;
import mission.resolver.map.MapMissionBase;
import mission.resolver.map.MapMissionStrategy;
import mission.resolver.map.MapMissionStrategyGreedy;
import work.assignment.CostType;
import work.assignment.WorkType;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MissionResolver {
	//Given a list of agents and a work type, returns a map of
	//<Agent, mission> for each agent
	
	ArrayList<Agent> missionAgents;
	WorkType workType;
	ArrayList<GPSCoordinate> missionBoundingCoordinates;
	HashMap<Object, Object> optionalParams;

	public MissionResolver(ArrayList<Agent> missionAgents, WorkType workType,
			ArrayList<GPSCoordinate> missionBoundingCoordinates){
		setMissionAgents(missionAgents);
		setWorkType(workType);
		setMissionBoundingCoordinates(missionBoundingCoordinates);	
	}
	
	public MissionResolver(ArrayList<Agent> missionAgents, WorkType workType,
			ArrayList<GPSCoordinate> missionBoundingCoordinates, HashMap<Object, Object> optionalParams){
		setMissionAgents(missionAgents);
		setWorkType(workType);
		setMissionBoundingCoordinates(missionBoundingCoordinates);	
		setOptionalParams(optionalParams);
	}
	
	
	
	
	public HashMap<Object, Object> getOptionalParams() {
		return optionalParams;
	}

	public void setOptionalParams(HashMap<Object, Object> optionalParams) {
		this.optionalParams = optionalParams;
	}

	public HashMap<Agent, Mission> resolveMissions() throws Exception{
		//Given a  list of agents and a workType, resolves the work that each needs to do in 
		//order to (attempt to) minimize the cost for the overall system
		if(workType == WorkType.MAP) {
			//need to create a mission with mission points, params, commands relative to GPS coordinate
			//List<Agent> agents, List<GPSCoordinate> missionBoundingCoordinates
			try {
				System.out.println("Resolving missions for " + getMissionAgents().size() + " agents.");
						// " + getMissionAgents().get(0).getId() + getMissionAgents().get(1).getId());
				MapMissionBase strategy;
					
				//base case with no optional params
				if(getOptionalParams() == null) {
					//swap out this strategy for another strategy that is more efficient
					//cost type should depend on what is trying to be achieved
					strategy = new MapMissionStrategyGreedy(getMissionAgents(), getMissionBoundingCoordinates(), CostType.TOTALTIME);
					strategy.updateAgentPaths();
					HashMap<Agent, ArrayList<GPSCoordinate>> agentRoutes= strategy.getAgentRoutesForMapping();
					System.out.println("Agent routes greedy no wind: " + agentRoutes);
					System.out.println("First agent route mission resolver: " + agentRoutes.get(getMissionAgents().get(0)));
					HashMap<Agent, Mission> returnMap = new HashMap<Agent, Mission>();
					//convert arraylist of gps points to mission for each agent
					for(Agent agent: agentRoutes.keySet()) {
						returnMap.put(agent, Mission.makeMission(agent, agentRoutes.get(agent)));
					}
					System.out.println("Resolved agent missions as: " + returnMap.toString());
					return returnMap;
				}
				
				
				else {
					if(getOptionalParams().containsKey("wind")) {
						System.out.println("Dealing with wind...");
						System.out.println(getOptionalParams().get("wind"));
						//wind should be an arraylist <north, east>
						WindFactor windFactor =  (WindFactor) getOptionalParams().get("wind");
						System.out.println("Wind factor: " + windFactor);
						//WindFactor windFactor = new WindFactor(windDetails.get(0), windDetails.get(1));
						
//						ArrayList<Agent> agents, 
//						ArrayList<GPSCoordinate> missionBoundingCoordinates,
//						HashMap<Agent, Double> agentVelocities,
//						WindFactor windFactor, 
//						CostType costType
						
						HashMap<Agent, Double> agentVelocities = new HashMap<Agent, Double>();
						for(Agent agent: getMissionAgents()) {
							agentVelocities.put(agent, agent.getVehicle().getOperationalVelocity());
						}
						
						strategy = new MapMissionStrategyGreedy(getMissionAgents(), 
								getMissionBoundingCoordinates(),
								//assume there is some way to get the velocity each agent can travel with
								agentVelocities,
								windFactor,
								CostType.TOTALTIME);
						HashMap<Agent, ArrayList<GPSCoordinate>> agentRoutes= strategy.getAgentRoutesForMapping();
						HashMap<Agent, Mission> returnMap = new HashMap<Agent, Mission>();
						//convert arraylist of gps points to mission for each agent
						for(Agent agent: agentRoutes.keySet()) {
							returnMap.put(agent, Mission.makeMission(agent, agentRoutes.get(agent)));
						}
						System.out.println("Resolved agent missions as: " + returnMap.toString());
						return returnMap;
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error in resolving missions: " + e.getMessage());
				throw e;
				//return null;
			}
			
		}
		else {
			throw new Exception("Not implemnted");
			//return null;
		}
		return null;
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
