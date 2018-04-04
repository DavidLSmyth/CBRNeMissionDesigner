package mission.resolver.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import agent.Agent;
import mission.resolver.Mission;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateCosts;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionAnalyserAgentNo {
	//A class which analyses the result of the mission mapping code
	//returns statistics on path intersections, redundancies, 
	//projected time taken, etc.
	
	//agent velocities are passed in explicitly because it may be the case
	//that we want to analyse a mission that does not yet have an associated agent
	
	RegularTraversalGridQuad traversalGrid; 
	//ArrayList<Agent> agents;
	//HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths;
	ArrayList<ArrayList<GPSCoordinate>> agentPaths;
	//HashMap<Agent, Double> velocities;
	
	//need the grid to traverse, agents involved, routes planned for agents
	public MapMissionAnalyserAgentNo(RegularTraversalGridQuad traversalGrid, 
			//ArrayList<Agent> agents, 
			//HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths,
			ArrayList<ArrayList<GPSCoordinate>> agentPaths
			//HashMap<Agent, Double> velocities) {
			) {
		setTraversalGrid(traversalGrid);
		//setAgents(agents);
		setAgentPaths(agentPaths);
		//setVelocities(velocities);
	}
	
	public MapMissionAnalyserAgentNo(ArrayList<Mission> agentMissions) {
		ArrayList<ArrayList<GPSCoordinate>> agentPaths = new ArrayList<ArrayList<GPSCoordinate>>();
		setTraversalGrid(null);
		for(Mission m: agentMissions) {
			agentPaths.add(m.getMissionGPSCoordinates());
		}
		setAgentPaths(agentPaths);
	}
	
	public MapMissionAnalyserAgentNo(ArrayList<ArrayList<GPSCoordinate>> agentPaths, Object junk) {
		setAgentPaths(agentPaths);
	}
	
//	private boolean validateAgent(Agent agent) throws Exception {
//		if(agentPaths.containsKey(agent)) return true;
//		else throw new Exception("Agent " + agent + " does not have a path calculated yet");
//	}
	
	private boolean validateAgent(int agentNumber) throws Exception {
		if(agentNumber < getAgentPaths().size()) return true;
		else throw new Exception("Agent number " + agentNumber + " does not have a path calculated yet");
	}

//	public HashMap<Agent, Double> getVelocities() {
//		return velocities;
//	}

//	public void setVelocities(HashMap<Agent, Double> velocities) {
//		this.velocities = velocities;
//	}
	
	public ArrayList<ArrayList<GPSCoordinate>> getAgentPaths() {
		return agentPaths;
	}

	public void setAgentPaths(ArrayList<ArrayList<GPSCoordinate>> agentPaths) {
		this.agentPaths = agentPaths;
	}
	
//	public double getBatteryEstimateForAgent(Agent agent) throws Exception {
//		validateAgent(agent);
//		throw new Exception("Not implemented");
//	}

	/***************************************************************************************
	**********************************ANALYSIS OF TIME TAKEN********************************
	****************************************************************************************/
	public double getTimeEstimateForAgent(int agentNumber, double velocity) throws Exception{
		//gets the time cost for agent assuming that it travels at a constant velocity
		validateAgent(agentNumber);
		double timeCost = 0;
		GPSCoordinate prevCoord = agentPaths.get(agentNumber).get(0);
		for(GPSCoordinate coord: agentPaths.get(agentNumber)) {
			timeCost += GPSCoordinateCosts.getTimeCost(velocity, prevCoord, coord);
			prevCoord = coord;
		}
		return timeCost;
	}
	//HashMap agentnumber, corresponding velocity
	public double getTotalTimeEstimate(HashMap<Integer, Double> velocities) throws Exception {
		double totalTimeEstimate = 0;
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			totalTimeEstimate += getTimeEstimateForAgent(agentNumber, velocities.get(agentNumber));
		}
		return totalTimeEstimate;
	}
	
	public double getAverageTimeEstimate(HashMap<Integer, Double> velocities) throws Exception {
		return getTotalTimeEstimate(velocities) / getAgentPaths().size();
	}
	
	public double getMaximumTimeEstimate(HashMap<Integer, Double> velocities) throws Exception {
		double maxTime = getTimeEstimateForAgent(0, velocities.get(0));
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			double currentTime = getTimeEstimateForAgent(agentNumber, velocities.get(agentNumber));
			if(currentTime > maxTime) {
				maxTime = currentTime;
			}
		}
		return maxTime;
	}
	
	public double getMinimumTimeEstimate(HashMap<Integer, Double> velocities) throws Exception {
		double minTime = getTimeEstimateForAgent(0, velocities.get(0));
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			double currentTime = getTimeEstimateForAgent(agentNumber, velocities.get(agentNumber));
			if(currentTime < minTime) {
				minTime = currentTime;
			}
		}
		return minTime;
	}
	
	public String getTimeReport(HashMap<Integer, Double> velocities) {
		String returnString = "";
		try {
			returnString += String.format("Total time taken by RAVs: %f\n", getTotalTimeEstimate(velocities));
			returnString += String.format("Average time taken by any RAV: %f\n", getAverageTimeEstimate(velocities));
			returnString += String.format("Min time taken by any RAV: %f\n", getMinimumTimeEstimate(velocities));
			returnString += String.format("Max time taken by any RAV: %f\n", getMaximumTimeEstimate(velocities));
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
	
	public double getDistanceEstimateForAgent(Integer agentNumber) throws Exception {
		validateAgent(agentNumber);
		double distanceCost = 0;
		GPSCoordinate prevCoord = agentPaths.get(agentNumber).get(0);
		for(GPSCoordinate coord: agentPaths.get(agentNumber)) {
			distanceCost += coord.getMetresToOther(prevCoord);
			prevCoord = coord;
		}
		return distanceCost;
	}
	
	public double getMinimumDistanceEstimateSingleAgent() {
		//gives an idea of how far a single agent would have to travel
		//to map the environment
		return 0;
	}
	
	public double getMinimumDistanceEstimate() throws Exception {
		double minDistance = getDistanceEstimateForAgent(0);
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			double currentDistance = getDistanceEstimateForAgent(agentNumber);
			if(currentDistance < minDistance) {
				minDistance = currentDistance;
			}
		}
		return minDistance;
	}
	
	public double getMaximumDistanceEstimate() throws Exception {
		double maxDistance = getDistanceEstimateForAgent(0);
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			double currentDistance = getDistanceEstimateForAgent(agentNumber);
			if(currentDistance > maxDistance) {
				maxDistance = currentDistance;
			}
		}
		return maxDistance;
	}
	
	public double getAverageDistanceEstimate() throws Exception {
		double distanceSum = 0;
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			double currentDistance = getDistanceEstimateForAgent(agentNumber);
		}
		return distanceSum / getAgentPaths().size();
	}
	
	
	public double getTotalDistanceEstimate() throws Exception {
		double totalDistanceCost = 0;
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			totalDistanceCost += getDistanceEstimateForAgent(agentNumber);
		}
		return totalDistanceCost;
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
	
	public String toString(HashMap<Integer, Double> agentVelocities) {
		String returnString = "***************** Report for Mission ****************";
		try {
			//Distance report
			returnString += getTimeReport(agentVelocities);
			
			//Time report
			returnString += getDistanceReport(agentVelocities);
			return returnString;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return("Error encountered when analysing mission");
		}
	}
	
	public double getTotalBatteryEstimate() {
		return 0;
	}
	

	public RegularTraversalGridQuad getTraversalGrid() {
		return traversalGrid;
	}

	public void setTraversalGrid(RegularTraversalGridQuad traversalGrid) {
		this.traversalGrid = traversalGrid;
	}

//	public ArrayList<Agent> getAgents() {
//		return agents;
//	}

//	public void setAgents(ArrayList<Agent> agents) {
//		this.agents = agents;
//	}

//	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentPaths() {
//		return agentPaths;
//	}
//
//	public void setAgentPaths(HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths) {
//		this.agentPaths = agentPaths;
//	}

}
