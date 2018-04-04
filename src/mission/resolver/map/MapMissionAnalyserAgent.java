package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateCosts;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionAnalyserAgent{
	
	ArrayList<Agent> agents;
	HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap;
	//ArrayList<ArrayList<GPSCoordinate>> agentPaths;
	HashMap<Agent, Double> velocities;
	RegularTraversalGridQuad traversalGrid; 
	//travels at 8 m/s by default
	private static double defaultVelocity = 8;
	protected MapMissionAnalyserAgentNo analyser;
	
	
	public MapMissionAnalyserAgent(RegularTraversalGridQuad traversalGrid, 
			ArrayList<Agent> agents, 
			HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap,
			//ArrayList<ArrayList<GPSCoordinate>> agentPaths
			HashMap<Agent, Double> velocities) {
		analyser = new MapMissionAnalyserAgentNo(traversalGrid, (ArrayList) agentPathsMap.values());
	}
	
	public MapMissionAnalyserAgent(RegularTraversalGridQuad traversalGrid, 
			ArrayList<Agent> agents, 
			HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap) {
		analyser = new MapMissionAnalyserAgentNo(traversalGrid, (ArrayList) agentPathsMap.values());
		velocities = new HashMap<Agent, Double>();
		for(Agent agent: agents) {
			velocities.put(agent, MapMissionAnalyserAgent.defaultVelocity);
		}
	}
	public MapMissionAnalyserAgent(	ArrayList<Agent> agents, 
			HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap) {
		analyser = new MapMissionAnalyserAgentNo((ArrayList) agentPathsMap.values());
		velocities = new HashMap<Agent, Double>();
		for(Agent agent: agents) {
			velocities.put(agent, MapMissionAnalyserAgent.defaultVelocity);
		}
	}
	
	private boolean validateAgent(Agent agent) throws Exception {
		if(agentPathsMap.containsKey(agent)) return true;
		else throw new Exception("Agent " + agent + " does not have a path calculated yet");
	}
	
	protected HashMap<Integer, Double> getVelocitiesIndexMap(){
		HashMap<Integer, Double> baseVelocities = new HashMap<Integer, Double>();
		for(Agent agent: velocities.keySet()) {
			baseVelocities.put(getAgents().indexOf(agent), baseVelocities.get(agent));
		}
		return baseVelocities;
	}
	protected HashMap<Integer, Double> getVelocitiesIndexMap(HashMap<Agent, Double> userVelocities){
		HashMap<Integer, Double> baseVelocities = new HashMap<Integer, Double>();
		for(Agent agent: userVelocities.keySet()) {
			baseVelocities.put(getAgents().indexOf(agent), baseVelocities.get(agent));
		}
		return baseVelocities;
	}
	
	protected int getBaseVelocity(Agent agent) {
		return getAgents().indexOf(agent);
	}
	
	protected int getAgentIndex(Agent agent) {
		return getAgents().indexOf(agent);
	}
	
	/***************************************************************************************
	**********************************ANALYSIS OF TIME TAKEN********************************
	****************************************************************************************/
	public double getTimeEstimateForAgent(Agent agent) throws Exception{
		//gets the time cost for agent assuming that it travels at a constant velocity
		return getTimeEstimateForAgent(agent, getVelocities().get(agent));
	}
	public double getTimeEstimateForAgent(Agent agent, double velocity) throws Exception{
		//gets the time cost for agent assuming that it travels at a constant velocity
		return analyser.getTimeEstimateForAgent(getAgentIndex(agent), velocity);
//		validateAgent(agent);
//		double timeCost = 0;
//		GPSCoordinate prevCoord = agentPathsMap.get(agent).get(0);
//		for(GPSCoordinate coord: agentPathsMap.get(agent)) {
//			timeCost += GPSCoordinateCosts.getTimeCost(velocity, prevCoord, coord);
//			prevCoord = coord;
//		}
//		return timeCost;
	}
	
	
	
	//HashMap agentnumber, corresponding velocity
	public double getTotalTimeEstimate(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities));
//		double totalTimeEstimate = 0;
//		for(Agent agent: getAgentPathsMap().keySet()) {
//			totalTimeEstimate += getTimeEstimateForAgent(agent, velocities.get(agent));
//		}
//		return totalTimeEstimate;
	}
	
	public double getTotalTimeEstimate() throws Exception {
		return getTotalTimeEstimate(getVelocities());
//		double totalTimeEstimate = 0;
//		for(Agent agent: getAgentPathsMap().keySet()) {
//			totalTimeEstimate += getTimeEstimateForAgent(agent, velocities.get(agent));
//		}
//		return totalTimeEstimate;
	}
	
	public double getAverageTimeEstimate(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities)) / getAgentPathsMap().size();
	}
	public double getAverageTimeEstimate() throws Exception {
		return getTotalTimeEstimate(getVelocities());
	}
	
	public double getMaximumTimeEstimate(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getMaximumTimeEstimate(getVelocitiesIndexMap(userVelocities));
//		double maxTime = getTimeEstimateForAgent(0, velocities.get(0));
//		for(Agent agent : getAgents()) {
//			double currentTime = getTimeEstimateForAgent(agent, velocities.get(agent));
//			if(currentTime > maxTime) {
//				maxTime = currentTime;
//			}
//		}
//		return maxTime;
	}
	public double getMaximumTimeEstimate() throws Exception {
		return getMaximumTimeEstimate(getVelocities());
	}
	
	public double getMinimumTimeEstimate(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getMinimumTimeEstimate(getVelocitiesIndexMap(userVelocities));
//		double minTime = getTimeEstimateForAgent(0, velocities.get(0));
//		for(Agent agent = 0; agentNumber < getAgentPathsMap().size(); agentNumber++) {
//			double currentTime = getTimeEstimateForAgent(agentNumber, velocities.get(agentNumber));
//			if(currentTime < minTime) {
//				minTime = currentTime;
//			}
//		}
//		return minTime;
	}
	public double getMinimumTimeEstimate() throws Exception {
		return analyser.getMinimumTimeEstimate(getVelocitiesIndexMap());
	}
	
	public String getTimeReport(HashMap<Agent, Double> userVelocities) {
		String returnString = "";
		try {
			returnString += String.format("Total time taken by RAVs: %f\n", getTotalTimeEstimate(userVelocities));
			returnString += String.format("Average time taken by any RAV: %f\n", getAverageTimeEstimate(userVelocities));
			returnString += String.format("Min time taken by any RAV: %f\n", getMinimumTimeEstimate(userVelocities));
			returnString += String.format("Max time taken by any RAV: %f\n", getMaximumTimeEstimate(userVelocities));
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	public String getTimeReport() {
		String returnString = "";
		try {
			returnString += String.format("Total time taken by RAVs: %f\n", getTotalTimeEstimate(getVelocities()));
			returnString += String.format("Average time taken by any RAV: %f\n", getAverageTimeEstimate(getVelocities()));
			returnString += String.format("Min time taken by any RAV: %f\n", getMinimumTimeEstimate(getVelocities()));
			returnString += String.format("Max time taken by any RAV: %f\n", getMaximumTimeEstimate(getVelocities()));
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	/***************************************************************************************
	**********************************ANALYSIS OF TIME TAKEN********************************
	****************************************************************************************/
	
	/***************************************************************************************
	*****************************ANALYSIS OF DISTANCE TRAVELLED*****************************
	****************************************************************************************/
	
	public double getDistanceEstimateForAgent(Agent agent) throws Exception {
		return analyser.getDistanceEstimateForAgent(getAgentIndex(agent));
//		validateAgent(agentNumber);
//		double distanceCost = 0;
//		GPSCoordinate prevCoord = agentPaths.get(agentNumber).get(0);
//		for(GPSCoordinate coord: agentPaths.get(agentNumber)) {
//			distanceCost += coord.getMetresToOther(prevCoord);
//			prevCoord = coord;
//		}
//		return distanceCost;
	}
	
	public double getMinimumDistanceEstimateSingleAgent() {
		//gives an idea of how far a single agent would have to travel
		//to map the environment
		return 0;
	}
	
	public double getMinimumDistanceEstimate() throws Exception {
		return analyser.getMinimumDistanceEstimate();
//		double minDistance = getDistanceEstimateForAgent(0);
//		for(Agent agent = 0; agentNumber < getAgentPathsMap().size(); agentNumber++) {
//			double currentDistance = getDistanceEstimateForAgent(agentNumber);
//			if(currentDistance < minDistance) {
//				minDistance = currentDistance;
//			}
//		}
//		return minDistance;
	}
	
	public double getMaximumDistanceEstimate() throws Exception {
		return analyser.getMaximumDistanceEstimate();
//		double maxDistance = getDistanceEstimateForAgent(0);
//		for(Agent agent = 0; agentNumber < getAgentPathsMap().size(); agentNumber++) {
//			double currentDistance = getDistanceEstimateForAgent(agentNumber);
//			if(currentDistance > maxDistance) {
//				maxDistance = currentDistance;
//			}
//		}
//		return maxDistance;
	}
	
	public double getAverageDistanceEstimate() throws Exception {
		return analyser.getAverageDistanceEstimate();
//		double distanceSum = 0;
//		for(Agent agent = 0; agentNumber < getAgentPathsMap().size(); agentNumber++) {
//			double currentDistance = getDistanceEstimateForAgent(agentNumber);
//		}
//		return distanceSum / getAgentPathsMap().size();
	}
	
	
	public double getTotalDistanceEstimate() throws Exception {
		return analyser.getTotalDistanceEstimate();
//		double totalDistanceCost = 0;
//		for(Agent agent = 0; agentNumber < getAgentPathsMap().size(); agentNumber++) {
//			totalDistanceCost += getDistanceEstimateForAgent(agentNumber);
//		}
//		return totalDistanceCost;
	}
	
	public String getDistanceReport(HashMap<Integer, Double> velocities) {
		String returnString = "";
		try {
			returnString += String.format("Average distance travelled by RAVs: %f\n", getAverageDistanceEstimate());
			returnString += String.format("Maximum distance travelled by any RAV: %f\n", getMaximumDistanceEstimate());
			returnString += String.format("Min distance travelled by any RAV: %f\n", getMinimumDistanceEstimate());
			returnString += String.format("Max distance travelled by any RAV: %f\n", getMaximumDistanceEstimate());
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	/***************************************************************************************
	*****************************ANALYSIS OF DISTANCE TRAVELLED*****************************
	****************************************************************************************/
	
	
	public RegularTraversalGridQuad getTraversalGrid() {
		return traversalGrid;
	}
	
	
	public void setTraversalGrid(RegularTraversalGridQuad traversalGrid) {
		this.traversalGrid = traversalGrid;
	}
	
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	
	
	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	
	
	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentPathsMap() {
		return agentPathsMap;
	}
	
	
	public void setAgentPathsMap(HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap) {
		this.agentPathsMap = agentPathsMap;
	}
	
	
	public HashMap<Agent, Double> getVelocities() {
		return velocities;
	}
	
	
	public void setVelocities(HashMap<Agent, Double> velocities) {
		this.velocities = velocities;
	}
	

			
}
