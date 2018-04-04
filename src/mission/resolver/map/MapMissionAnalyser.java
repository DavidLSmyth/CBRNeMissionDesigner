package mission.resolver.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import agent.Agent;
import work.assignment.grid.GPSCoordinate;
import work.assignment.grid.GPSCoordinateCosts;
import work.assignment.grid.quadrilateral.RegularTraversalGridQuad;

public class MapMissionAnalyser {
	//A class which analyses the result of the mission mapping code
	//returns statistics on path intersections, redundancies, 
	//projected time taken, etc.
	RegularTraversalGridQuad traversalGrid; 
	ArrayList<Agent> agents;
	HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths;
	HashMap<Agent, Double> velocities;
	
	//need the grid to traverse, agents involved, routes planned for agents
	public MapMissionAnalyser(RegularTraversalGridQuad traversalGrid, 
			ArrayList<Agent> agents, 
			HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths,
			HashMap<Agent, Double> velocities) {
		setTraversalGrid(traversalGrid);
		setAgents(agents);
		setAgentPaths(agentPaths);
		setVelocities(velocities);
	}
	
	private boolean validateAgent(Agent agent) throws Exception {
		if(agentPaths.containsKey(agent)) return true;
		else throw new Exception("Agent " + agent + " does not have a path calculated yet");
	}

	public HashMap<Agent, Double> getVelocities() {
		return velocities;
	}

	public void setVelocities(HashMap<Agent, Double> velocities) {
		this.velocities = velocities;
	}
	
	public double getBatteryEstimateForAgent(Agent agent) throws Exception {
		validateAgent(agent);
		throw new Exception("Not implemented");
	}
	/***************************************************************************************
	**********************************ANALYSIS OF TIME TAKEN********************************
	****************************************************************************************/
	public double getTimeEstimateForAgent(Agent agent, double velocity) throws Exception{
		//gets the time cost for agent assuming that it travels at a constant velocity
		validateAgent(agent);
		double timeCost = 0;
		GPSCoordinate prevCoord = agentPaths.get(agent).get(0);
		for(GPSCoordinate coord: agentPaths.get(agent)) {
			timeCost += GPSCoordinateCosts.getTimeCost(velocity, prevCoord, coord);
			prevCoord = coord;
		}
		return timeCost;
	}
	
	public double getTotalTimeEstimate(HashMap<Agent, Double> velocities) throws Exception {
		double totalTimeEstimate = 0;
		for(Agent agent: getAgents()) {
			totalTimeEstimate += getTimeEstimateForAgent(agent, velocities.get(agent));
		}
		return totalTimeEstimate;
	}
	
	public double getAverageTimeEstimate(HashMap<Agent, Double> velocities) throws Exception {
		return getTotalTimeEstimate(velocities) / getAgents().size();
	}
	
	public double getMaximumTimeEstimate(HashMap<Agent, Double> velocities) throws Exception {
		double maxTime = getTimeEstimateForAgent(getAgents().get(0), velocities.get(getAgents().get(0)));
		for(Agent agent: getAgents()) {
			double currentTime = getTimeEstimateForAgent(getAgents().get(0), velocities.get(getAgents().get(0)));
			if(currentTime > maxTime) {
				maxTime = currentTime;
			}
		}
		return maxTime;
	}
	
	public double getMinimumTimeEstimate(HashMap<Agent, Double> velocities) throws Exception {
		double minTime = getTimeEstimateForAgent(getAgents().get(0), velocities.get(getAgents().get(0)));
		for(Agent agent: getAgents()) {
			double currentTime = getTimeEstimateForAgent(getAgents().get(0), velocities.get(getAgents().get(0)));
			if(currentTime < minTime) {
				minTime = currentTime;
			}
		}
		return minTime;
	}
	
	public String getTimeReport() {
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
	**********************************ANALYSIS OF TIME TAKEN********************************
	****************************************************************************************/
	
	/***************************************************************************************
	*****************************ANALYSIS OF DISTANCE TRAVELLED*****************************
	****************************************************************************************/
	
	public double getDistanceEstimateForAgent(Agent agent) throws Exception {
		validateAgent(agent);
		double distanceCost = 0;
		GPSCoordinate prevCoord = agentPaths.get(agent).get(0);
		for(GPSCoordinate coord: agentPaths.get(agent)) {
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
		double minDistance = getDistanceEstimateForAgent(getAgents().get(0));
		for(Agent agent: getAgents()) {
			double currentDistance = getDistanceEstimateForAgent(agent);
			if(currentDistance < minDistance) {
				minDistance = currentDistance;
			}
		}
		return minDistance;
	}
	
	public double getMaximumDistanceEstimate() throws Exception {
		double maxDistance = getDistanceEstimateForAgent(getAgents().get(0));
		for(Agent agent: getAgents()) {
			double currentDistance = getDistanceEstimateForAgent(agent);
			if(currentDistance > maxDistance) {
				maxDistance = currentDistance;
			}
		}
		return maxDistance;
	}
	
	public double getAverageDistanceEstimate() throws Exception {
		double distanceSum = 0;
		for(Agent agent: getAgents()) {
			double currentDistance = getDistanceEstimateForAgent(agent);
		}
		return distanceSum / getAgents().size();
	}
	
	
	public double getTotalDistanceEstimate() throws Exception {
		double totalDistanceCost = 0;
		for(Agent agent: getAgents()) {	
			totalDistanceCost += getDistanceEstimateForAgent(agent);
		}
		return totalDistanceCost;
	}
	
	public String getDistanceReport() {
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
	
	public String toString() {
		String returnString = "";
		try {
			//Distance report
			returnString += String.format("Average distance travelled by RAVs: %f\n", getAverageDistanceEstimate());
			returnString += String.format("Maximum distance travelled by any RAV: %f\n", getMaximumDistanceEstimate());
			returnString += String.format("Min distance travelled by any RAV: %f\n", getMinimumDistanceEstimate());
			returnString += String.format("Max distance travelled by any RAV: %f\n", getMaximumDistanceEstimate());
			
			//Time report
			returnString += String.format("Total time taken by RAVs: %f\n", getTotalTimeEstimate(velocities));
			returnString += String.format("Average time taken by any RAV: %f\n", getAverageTimeEstimate(velocities));
			returnString += String.format("Min time taken by any RAV: %f\n", getMinimumTimeEstimate(velocities));
			returnString += String.format("Max time taken by any RAV: %f\n", getMaximumTimeEstimate(velocities));
			
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

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentPaths() {
		return agentPaths;
	}

	public void setAgentPaths(HashMap<Agent, ArrayList<GPSCoordinate>> agentPaths) {
		this.agentPaths = agentPaths;
	}
	

}
