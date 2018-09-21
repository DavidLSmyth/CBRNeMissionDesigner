package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import GPSUtils.GPSCoordinate;
import GPSUtils.GPSCoordinateCosts;
import GPSUtils.grid.GPSPolygonGrid;
import agent.Agent;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionAnalyserAgent{
	
	List<Agent> agents;
	Map<Agent, List<GPSCoordinate>> agentPathsMap;
	//ArrayList<ArrayList<GPSCoordinate>> agentPaths;
	Map<Agent, Double> velocities;
	GPSPolygonGrid polyGrid;
	protected MapMissionAnalyserAgentNo analyser;
	
	
	public MapMissionAnalyserAgent(GPSPolygonGrid polyGrid, 
			List<Agent> agents, 
			Map<Agent, List<GPSCoordinate>> agentPathsMap,
			//ArrayList<ArrayList<GPSCoordinate>> agentPaths
			HashMap<Agent, Double> velocities) {
		List<List<GPSCoordinate>> agentPathsList = new ArrayList<List<GPSCoordinate>>();
		for(Agent agent: agentPathsMap.keySet()) {
			agentPathsList.add(agentPathsMap.get(agent));
		}
		analyser = new MapMissionAnalyserAgentNo(polyGrid, agentPathsList);
		setVelocities(velocities);
		setAgentPathsMap(agentPathsMap);
		setAgents(agents);
		setTraversalGrid(polyGrid);
	}
	
	public MapMissionAnalyserAgent(GPSPolygonGrid polyGrid, 
			List<Agent> agents, 
			Map<Agent, List<GPSCoordinate>> agentPathsMap) {
		this(polyGrid, agents, agentPathsMap, new HashMap<Agent, Double>());
		//update velocities
		velocities = new HashMap<Agent, Double>();
		for(Agent agent: agents) {
			velocities.put(agent, agent.getVehicle().getOperationalVelocity());
		}
		setVelocities(velocities);
	}
	
	public MapMissionAnalyserAgent(List<Agent> agents, 
			Map<Agent, List<GPSCoordinate>> agentPathsMap) {
		this(null, agents, agentPathsMap, new HashMap<Agent, Double>());
		//update velocities
		velocities = new HashMap<Agent, Double>();
		for(Agent agent: agents) {
			velocities.put(agent, agent.getVehicle().getOperationalVelocity());
		}
		setVelocities(velocities);
	}
	
	private boolean validateAgent(Agent agent) throws Exception {
		if(agentPathsMap.containsKey(agent)) return true;
		else throw new Exception("Agent " + agent + " does not have a path calculated yet");
	}
	
	protected Map<Integer, Double> getVelocitiesIndexMap(){
		Map<Integer, Double> baseVelocities = new HashMap<Integer, Double>();
		for(Agent agent: velocities.keySet()) {
			baseVelocities.put(getAgents().indexOf(agent), velocities.get(agent));
		}
		return baseVelocities;
	}
	protected Map<Integer, Double> getVelocitiesIndexMap(Map<Agent, Double> userVelocities){
		Map<Integer, Double> baseVelocities = new HashMap<Integer, Double>();
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
	}
	public double getTimeEstimateForAgent(Agent agent, double velocity, WindFactor windFactor) throws Exception{
		//gets the time cost for agent assuming that it travels at a constant velocity
		return analyser.getTimeEstimateForAgent(getAgentIndex(agent), windFactor, velocity);
	}
	
	//HashMap agentnumber, corresponding velocity
	public double getTotalTimeEstimate(Map<Agent, Double> userVelocities) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities));
	}

	public double getTotalTimeEstimate(Map<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities), windFactor);
	}
	
	public double getTotalTimeEstimate() throws Exception {
		return getTotalTimeEstimate(getVelocities());
	}
	
	public double getTotalTimeEstimate(WindFactor windFactor) throws Exception {
		return getTotalTimeEstimate(getVelocities(), windFactor);
	}
	
	public double getAverageTimeEstimate(Map<Agent, Double> userVelocities) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities)) / getAgentPathsMap().size();
	}

	public double getAverageTimeEstimate(Map<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities), windFactor) / getAgentPathsMap().size();
	}
	
	public double getAverageTimeEstimate() throws Exception {
		return getTotalTimeEstimate(getVelocities());
	}

	public double getAverageTimeEstimate(WindFactor windFactor) throws Exception {
		return getTotalTimeEstimate(getVelocities(), windFactor);
	}
	
	public Entry<Double, Integer> getMaximumTimeEstimate(Map<Agent, Double> userVelocities) throws Exception {
		return analyser.getMaximumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities));
	}
	public Entry<Double, Integer> getMaximumTimeEstimate(Map<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getMaximumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities), windFactor);
	}	
	
	public Entry<Double, Integer> getMinimumTimeEstimateAndCorrespondingAgent(Map<Agent, Double> userVelocities) throws Exception {
		return analyser.getMinimumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities));
	}
	public Entry<Double, Integer> getMinimumTimeEstimateAndCorrespondingAgent(Map<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getMinimumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities), windFactor);
	}
	
	public String getTimeReport(Map<Agent, Double> userVelocities) {
		//String returnString = "";
		try {
			return analyser.getTimeReport(getVelocitiesIndexMap(userVelocities));
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	public String getTimeReport(WindFactor windFactor) {
		//String returnString = "";
		try {
			return analyser.getTimeReport(getVelocitiesIndexMap(), windFactor);
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	
	public String getTimeReport(Map<Agent, Double> userVelocities, WindFactor windFactor) {
		//String returnString = "";
		try {
			return analyser.getTimeReport(getVelocitiesIndexMap(userVelocities), windFactor);
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	
	public String getTimeReport() {
		//String returnString = "";
		try {
//			HashMap<Integer, Double> timeRepVelocities = new HashMap<Integer, Double>();
//			int counter = 0;
//			for(Agent agent: getVelocities().keySet()) {
//				timeRepVelocities.put(counter, getVelocities().get(agent));
//				counter++;
//			}
			return analyser.getTimeReport(getVelocitiesIndexMap());
//			returnString += wrapReportLine(String.format("Total time taken by RAVs: %f\n", getTotalTimeEstimate(getVelocities())));
//			returnString += wrapReportLine(String.format("Average time taken by any RAV: %f\n", getAverageTimeEstimate(getVelocities())));
//			returnString += wrapReportLine(String.format("Min time taken by any RAV: %f\n", getMinimumTimeEstimate(getVelocities())));
//			returnString += wrapReportLine(String.format("Max time taken by any RAV: %f\n", getMaximumTimeEstimate(getVelocities())));
//			return returnString;
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
	
	public Entry<Double, Integer> getMinimumDistanceEstimate() throws Exception {
		return analyser.getMinimumDistanceEstimateAndCorrespondingAgent();
//		double minDistance = getDistanceEstimateForAgent(0);
//		for(Agent agent = 0; agentNumber < getAgentPathsMap().size(); agentNumber++) {
//			double currentDistance = getDistanceEstimateForAgent(agentNumber);
//			if(currentDistance < minDistance) {
//				minDistance = currentDistance;
//			}
//		}
//		return minDistance;
	}
	
	public Entry<Double, Integer> getMaximumDistanceEstimate() throws Exception {
		return analyser.getMaximumDistanceEstimateAndCorrespondingAgent();
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
	
	
	
	public String getDistanceReport() {
		//String returnString = "";
		try {
			return analyser.getDistanceReport();
//			returnString += String.format("Average distance travelled by RAVs: %f\n", getAverageDistanceEstimate());
//			returnString += String.format("Maximum distance travelled by any RAV: %f\n", getMaximumDistanceEstimate());
//			returnString += String.format("Min distance travelled by any RAV: %f\n", getMinimumDistanceEstimate());
//			returnString += String.format("Max distance travelled by any RAV: %f\n", getMaximumDistanceEstimate());
//			return returnString;
		}
		catch(Exception e) {
			System.out.println("Error encountered when analysing distance travelled during mission");
			e.printStackTrace();
			return("");
		}
	}
	/***************************************************************************************
	*****************************ANALYSIS OF DISTANCE TRAVELLED*****************************
	****************************************************************************************/
	
	
	public GPSPolygonGrid getTraversalGrid() {
		return polyGrid;
	}
	
	
	public void setTraversalGrid(GPSPolygonGrid polyGrid) {
		this.polyGrid = polyGrid;
	}
	
	
	public List<Agent> getAgents() {
		return agents;
	}
	
	
	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	
	
	public Map<Agent, List<GPSCoordinate>> getAgentPathsMap() {
		return agentPathsMap;
	}
	
	
	public void setAgentPathsMap(Map<Agent, List<GPSCoordinate>> agentPathsMap) {
		this.agentPathsMap = agentPathsMap;
	}
	
	
	public Map<Agent, Double> getVelocities() {
		return velocities;
	}
	
	
	public void setVelocities(Map<Agent, Double> velocities) {
		this.velocities = velocities;
	}
	

			
}
