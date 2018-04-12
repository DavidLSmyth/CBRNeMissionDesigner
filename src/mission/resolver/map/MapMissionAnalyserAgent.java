package mission.resolver.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import agent.Agent;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateCosts;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionAnalyserAgent{
	
	ArrayList<Agent> agents;
	HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap;
	//ArrayList<ArrayList<GPSCoordinate>> agentPaths;
	HashMap<Agent, Double> velocities;
	RegularTraversalGridQuad traversalGrid; 
	protected MapMissionAnalyserAgentNo analyser;
	
	
	public MapMissionAnalyserAgent(RegularTraversalGridQuad traversalGrid, 
			ArrayList<Agent> agents, 
			HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap,
			//ArrayList<ArrayList<GPSCoordinate>> agentPaths
			HashMap<Agent, Double> velocities) {
		ArrayList<ArrayList<GPSCoordinate>> agentPathsList = new ArrayList<ArrayList<GPSCoordinate>>();
		for(Agent agent: agentPathsMap.keySet()) {
			agentPathsList.add(agentPathsMap.get(agent));
		}
		analyser = new MapMissionAnalyserAgentNo(traversalGrid, agentPathsList);
		setVelocities(velocities);
		setAgentPathsMap(agentPathsMap);
		setAgents(agents);
		setTraversalGrid(traversalGrid);
	}
	
	public MapMissionAnalyserAgent(RegularTraversalGridQuad traversalGrid, 
			ArrayList<Agent> agents, 
			HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap) {
		this(traversalGrid, agents, agentPathsMap, new HashMap<Agent, Double>());
		//update velocities
		velocities = new HashMap<Agent, Double>();
		for(Agent agent: agents) {
			velocities.put(agent, agent.getVehicle().getOperationalVelocity());
		}
		setVelocities(velocities);
	}
	
	public MapMissionAnalyserAgent(	ArrayList<Agent> agents, 
			HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap) {
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
	
	protected HashMap<Integer, Double> getVelocitiesIndexMap(){
		HashMap<Integer, Double> baseVelocities = new HashMap<Integer, Double>();
		for(Agent agent: velocities.keySet()) {
			baseVelocities.put(getAgents().indexOf(agent), velocities.get(agent));
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
	}
	public double getTimeEstimateForAgent(Agent agent, double velocity, WindFactor windFactor) throws Exception{
		//gets the time cost for agent assuming that it travels at a constant velocity
		return analyser.getTimeEstimateForAgent(getAgentIndex(agent), windFactor, velocity);
	}
	
	//HashMap agentnumber, corresponding velocity
	public double getTotalTimeEstimate(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities));
	}

	public double getTotalTimeEstimate(HashMap<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities), windFactor);
	}
	
	public double getTotalTimeEstimate() throws Exception {
		return getTotalTimeEstimate(getVelocities());
	}
	
	public double getTotalTimeEstimate(WindFactor windFactor) throws Exception {
		return getTotalTimeEstimate(getVelocities(), windFactor);
	}
	
	public double getAverageTimeEstimate(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities)) / getAgentPathsMap().size();
	}

	public double getAverageTimeEstimate(HashMap<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getTotalTimeEstimate(getVelocitiesIndexMap(userVelocities), windFactor) / getAgentPathsMap().size();
	}
	
	public double getAverageTimeEstimate() throws Exception {
		return getTotalTimeEstimate(getVelocities());
	}

	public double getAverageTimeEstimate(WindFactor windFactor) throws Exception {
		return getTotalTimeEstimate(getVelocities(), windFactor);
	}
	
	public SimpleEntry<Double, Integer> getMaximumTimeEstimate(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getMaximumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities));
	}
	public SimpleEntry<Double, Integer> getMaximumTimeEstimate(HashMap<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getMaximumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities), windFactor);
	}	
	
	public SimpleEntry<Double, Integer> getMinimumTimeEstimateAndCorrespondingAgent(HashMap<Agent, Double> userVelocities) throws Exception {
		return analyser.getMinimumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities));
	}
	public SimpleEntry<Double, Integer> getMinimumTimeEstimateAndCorrespondingAgent(HashMap<Agent, Double> userVelocities, WindFactor windFactor) throws Exception {
		return analyser.getMinimumTimeEstimateAndCorrespondingAgent(getVelocitiesIndexMap(userVelocities), windFactor);
	}
	
	public String getTimeReport(HashMap<Agent, Double> userVelocities) {
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
	
	public String getTimeReport(HashMap<Agent, Double> userVelocities, WindFactor windFactor) {
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
	
	public SimpleEntry<Double, Integer> getMinimumDistanceEstimate() throws Exception {
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
	
	public SimpleEntry<Double, Integer> getMaximumDistanceEstimate() throws Exception {
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
