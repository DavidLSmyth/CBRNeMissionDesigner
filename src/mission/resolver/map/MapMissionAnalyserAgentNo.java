package mission.resolver.map;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import agent.Agent;
import mission.resolver.Mission;
import work.assignment.environmentalfactors.WindFactor;
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
	
	public int numberOfGridPointsVisitedByAgent(int agentNumber) {
		return getAgentPaths().get(agentNumber).size();
	}
	
	public int totalNumberOfGridPointsVisited() {
		int sum = 0;
		for(int counter = 0; counter < getAgentPaths().size(); counter++) {
			sum += numberOfGridPointsVisitedByAgent(counter);
		}
		return sum;
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
//		validateAgent(agentNumber);
//		double timeCost = 0;
//		GPSCoordinate prevCoord = agentPaths.get(agentNumber).get(0);
//		for(GPSCoordinate coord: agentPaths.get(agentNumber)) {
//			timeCost += GPSCoordinateCosts.getTimeCost(velocity, prevCoord, coord);
//			//System.out.println("Adding time cost: " + timeCost);
//			prevCoord = coord;
//		}
//		return timeCost;
		return getTimeEstimateForAgent(agentNumber, new WindFactor(0, 0), velocity);
	}
	public double getTimeEstimateForAgent(int agentNumber, WindFactor windFactor, double velocity) throws Exception{
		//gets the time cost for agent assuming that it travels at a constant velocity
		validateAgent(agentNumber);
		double timeCost = 0;
		GPSCoordinate prevCoord = agentPaths.get(agentNumber).get(0);
		for(GPSCoordinate coord: agentPaths.get(agentNumber)) {
			timeCost += GPSCoordinateCosts.getTimeCost(velocity, windFactor, prevCoord, coord);
			//System.out.println("Adding time cost: " + timeCost);
			prevCoord = coord;
		}
		return timeCost;
	}
	
	
	//HashMap agentnumber, corresponding velocity
	public double getTotalTimeEstimate(HashMap<Integer, Double> velocities) throws Exception {
		return getTotalTimeEstimate(velocities, new WindFactor(0, 0));
	}

	public double getTotalTimeEstimate(HashMap<Integer, Double> velocities, WindFactor windFactor) throws Exception {
		double totalTimeEstimate = 0;
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			totalTimeEstimate += getTimeEstimateForAgent(agentNumber, windFactor, velocities.get(agentNumber));
		}
		return totalTimeEstimate;
	}
	
	public double getAverageTimeEstimate(HashMap<Integer, Double> velocities) throws Exception {
		return getAverageTimeEstimate(velocities, new WindFactor(0, 0));
		//return getTotalTimeEstimate(velocities, ) / getAgentPaths().size();
	}
	public double getAverageTimeEstimate(HashMap<Integer, Double> velocities, WindFactor windFactor) throws Exception {
		return getTotalTimeEstimate(velocities, windFactor) / getAgentPaths().size();
	}
	
	public SimpleEntry<Double, Integer> getMaximumTimeEstimateAndCorrespondingAgent(HashMap<Integer, Double> velocities) throws Exception {
		
//		double maxTime = getTimeEstimateForAgent(0, velocities.get(0));
//		int agentWhichTakesMaxTime = 0;
//		double currentTime;
//		
//		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
//			currentTime = getTimeEstimateForAgent(agentNumber, velocities.get(agentNumber));
//			if(currentTime > maxTime) {
//				maxTime = currentTime;
//				agentWhichTakesMaxTime = agentNumber;
//			}
//		}
//		
//		return new SimpleEntry<Double, Integer>(maxTime, agentWhichTakesMaxTime);
		return getMaximumTimeEstimateAndCorrespondingAgent(velocities, new WindFactor(0, 0));
	}
	
	public SimpleEntry<Double, Integer> getMaximumTimeEstimateAndCorrespondingAgent(
			HashMap<Integer, Double> velocities, WindFactor windFactor) throws Exception {
		// TODO Auto-generated method stub
		double maxTime = getTimeEstimateForAgent(0, windFactor, velocities.get(0));
		int agentWhichTakesMaxTime = 0;
		double currentTime;
		
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			currentTime = getTimeEstimateForAgent(agentNumber, windFactor, velocities.get(agentNumber));
			if(currentTime > maxTime) {
				maxTime = currentTime;
				agentWhichTakesMaxTime = agentNumber;
			}
		}
		
		return new SimpleEntry<Double, Integer>(maxTime, agentWhichTakesMaxTime);
	}
	
	public SimpleEntry<Double, Integer> getMinimumTimeEstimateAndCorrespondingAgent(HashMap<Integer, Double> velocities) throws Exception {
		
		double minTime = getTimeEstimateForAgent(0, velocities.get(0));
		int agentWhichTakesMinTime = 0;
		double currentTime;
		
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			currentTime = getTimeEstimateForAgent(agentNumber, velocities.get(agentNumber));
			if(currentTime < minTime) {
				minTime = currentTime;
				agentWhichTakesMinTime = agentNumber;
			}
		}
		
		return new SimpleEntry(minTime, agentWhichTakesMinTime);
	}
	public SimpleEntry<Double, Integer> getMinimumTimeEstimateAndCorrespondingAgent(HashMap<Integer, Double> velocities, WindFactor windFactor) throws Exception {
		
		double minTime = getTimeEstimateForAgent(0, windFactor, velocities.get(0));
		int agentWhichTakesMinTime = 0;
		double currentTime;
		
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			currentTime = getTimeEstimateForAgent(agentNumber, windFactor, velocities.get(agentNumber));
			if(currentTime < minTime) {
				minTime = currentTime;
				agentWhichTakesMinTime = agentNumber;
			}
		}
		
		return new SimpleEntry(minTime, agentWhichTakesMinTime);
	}
	
	
	private String wrapReportLine(String string) {
		String returnString = "*\t" + string;
		for(int counter = 0; counter < 51 - string.length() - 1; counter++) {
			returnString += " ";
		}
		returnString += "*\n";
		returnString += "*	                                                  *\n";
		return returnString;
	}
	
	protected String getBaseTimeReport(HashMap<Integer, Double> velocities) {
		String returnString = "";
		try {
			//returnString +="*********************** Time Report ***********************\n";
			returnString += wrapReportLine(String.format("Total time taken by agents:            %.3fs", getTotalTimeEstimate(velocities)));
			returnString += wrapReportLine(String.format("Average time taken by agents:          %.3fs", getAverageTimeEstimate(velocities)));
			
			//report back which RAV takes the max/min time
			SimpleEntry<Double, Integer> minTime = getMinimumTimeEstimateAndCorrespondingAgent(velocities);
			returnString += wrapReportLine(String.format("Min time taken by any agent(%02d):       %.3fs", minTime.getValue(), minTime.getKey()));
			
			SimpleEntry<Double, Integer> maxTime = getMaximumTimeEstimateAndCorrespondingAgent(velocities);
			returnString += wrapReportLine(String.format("Max time taken by any agent(%02d):       %.3fs", maxTime.getValue(), maxTime.getKey()));
			for(int agentCounter = 0; agentCounter < getAgentPaths().size(); agentCounter++) {
				returnString += wrapReportLine(String.format("Time taken by agent(%02d):               %.3fs", agentCounter, getTimeEstimateForAgent(agentCounter, velocities.get(agentCounter))));
			}
			//returnString +="*********************** Time Report ***********************\n";
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}

	protected String getBaseTimeReport(HashMap<Integer, Double> velocities, WindFactor windFactor) {
		String returnString = "";
		try {
			//returnString +="*********************** Time Report ***********************\n";
			returnString += wrapReportLine(String.format("Total time taken by agents:            %.3fs", getTotalTimeEstimate(velocities, windFactor)));
			returnString += wrapReportLine(String.format("Average time taken by agents:          %.3fs", getAverageTimeEstimate(velocities, windFactor)));
			
			//report back which RAV takes the max/min time
			SimpleEntry<Double, Integer> minTime = getMinimumTimeEstimateAndCorrespondingAgent(velocities, windFactor);
			returnString += wrapReportLine(String.format("Min time taken by any agent(%02d):       %.3fs", minTime.getValue(), minTime.getKey()));
			
			SimpleEntry<Double, Integer> maxTime = getMaximumTimeEstimateAndCorrespondingAgent(velocities, windFactor);
			returnString += wrapReportLine(String.format("Max time taken by any agent(%02d):       %.3fs", maxTime.getValue(), maxTime.getKey()));
			for(int agentCounter = 0; agentCounter < getAgentPaths().size(); agentCounter++) {
				returnString += wrapReportLine(String.format("Time taken by agent(%02d):               %.3fs", agentCounter, getTimeEstimateForAgent(agentCounter, windFactor, velocities.get(agentCounter))));
			}
			//returnString +="*********************** Time Report ***********************\n";
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}

	public String getTimeReport(HashMap<Integer, Double> velocities) {
		try {
			String returnString ="*********************** Time Report ***********************\n";
			returnString += getGridPointReport();
			returnString += getBaseTimeReport(velocities);
			returnString +="*********************** Time Report ***********************\n";
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	public String getTimeReport(HashMap<Integer, Double> velocities, WindFactor windFactor) {
		try {
			String returnString ="*********************** Time Report ***********************\n";
			returnString += getGridPointReport(windFactor);
			returnString += getBaseTimeReport(velocities, windFactor);
			returnString +="*********************** Time Report ***********************\n";
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
		//need to have traversalGrid here, throw exception if not present
		return 0;
	}
	
	public SimpleEntry<Double, Integer> getMinimumDistanceEstimateAndCorrespondingAgent() throws Exception {
	
		double minDistance = getDistanceEstimateForAgent(0);
		double currentDistance;
		int agentWhichTakesMinDistance = 0;
	
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			currentDistance = getDistanceEstimateForAgent(agentNumber);
			if(currentDistance < minDistance) {
				minDistance = currentDistance;
				agentWhichTakesMinDistance = agentNumber;
			}
		}
		
		return new SimpleEntry<Double, Integer>(minDistance, agentWhichTakesMinDistance);
	}
	
	public SimpleEntry<Double, Integer> getMaximumDistanceEstimateAndCorrespondingAgent() throws Exception {
		
		double maxDistance = getDistanceEstimateForAgent(0);
		double currentDistance;
		int agentWhichTakesMaxDistance = 0;

		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			currentDistance = getDistanceEstimateForAgent(agentNumber);
			if(currentDistance > maxDistance) {
				maxDistance = currentDistance;
				agentWhichTakesMaxDistance = agentNumber;
			}
		}
		
		return new SimpleEntry<Double, Integer>(maxDistance, agentWhichTakesMaxDistance);
	}
	
	public double getAverageDistanceEstimate() throws Exception {
		return getTotalDistanceEstimate() / getAgentPaths().size();
	}
	
	
	public double getTotalDistanceEstimate() throws Exception {
		double totalDistanceCost = 0;
		for(int agentNumber = 0; agentNumber < getAgentPaths().size(); agentNumber++) {
			totalDistanceCost += getDistanceEstimateForAgent(agentNumber);
		}
		return totalDistanceCost;
	}
	
	protected String getBaseDistanceReport() {
		try {
			String returnString = "";
			returnString += wrapReportLine(String.format("Average distance travelled by agents:  %.3fm", getAverageDistanceEstimate()));
			returnString += wrapReportLine(String.format("Total distance travelled by agents:    %.3fm", getTotalDistanceEstimate()));
			
			SimpleEntry<Double, Integer> minDist = getMinimumDistanceEstimateAndCorrespondingAgent();
			returnString += wrapReportLine(String.format("Min dist. travelled by any agent(%02d):  %.3fm", minDist.getValue(), minDist.getKey()));
			
			SimpleEntry<Double, Integer> maxDist = getMaximumDistanceEstimateAndCorrespondingAgent();
			returnString += wrapReportLine(String.format("Max dist. travelled by any agent(%02d):  %.3fm", maxDist.getValue(), maxDist.getKey()));
			
			for(int agentCounter = 0; agentCounter < getAgentPaths().size(); agentCounter++) {
				returnString += wrapReportLine(String.format("Distance travelled by agent(%02d):       %.3fm",agentCounter, getDistanceEstimateForAgent(agentCounter)));
			}
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	
	public String getGridPointReport() {
		try {
			String returnString = "";
			returnString +=     wrapReportLine("Number of agents:                      " + getAgentPaths().size());
			returnString +=     wrapReportLine("Total # of grid points visited:        " + totalNumberOfGridPointsVisited());
			for(int agentCounter = 0; agentCounter < getAgentPaths().size(); agentCounter++) {
				returnString += wrapReportLine("# of grid points visited by agent " + agentCounter + ":   " + numberOfGridPointsVisitedByAgent(agentCounter));
			}
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when reporting agent data");
		}
	}
	
	public String getGridPointReport(WindFactor windFactor) {
		try {
			String returnString = "";
			returnString +=     wrapReportLine("Number of agents:                      " + getAgentPaths().size());
			returnString +=     wrapReportLine("Total # of grid points visited:        " + totalNumberOfGridPointsVisited());
			for(int agentCounter = 0; agentCounter < getAgentPaths().size(); agentCounter++) {
				returnString += wrapReportLine("# of grid points visited by agent " + agentCounter + ":   " + numberOfGridPointsVisitedByAgent(agentCounter));
			}
			returnString += 	wrapReportLine("Wind direction:   " + windFactor);
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when reporting agent data");
		}
	}
	
	public String getDistanceReport() {
		try {
			String returnString = "********************* Distance Report *********************\n";
			returnString += getGridPointReport();
			returnString += getBaseDistanceReport();
			returnString +="********************* Distance Report *********************\n";
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when analysing distance travelled during mission");
		}
	}
	public String getDistanceReport(WindFactor windFactor) {
		try {
			String returnString = "********************* Distance Report *********************\n";
			returnString += getGridPointReport(windFactor);
			returnString += getBaseDistanceReport();
			returnString +="********************* Distance Report *********************\n";
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
	
	public String getReport(HashMap<Integer, Double> velocities) {
		try {
			String returnString = "********************* Mission  Report *********************\n";
			returnString += "*	                                                  *\n";
			returnString += getGridPointReport();
			returnString += "*-------------------- Distance Report --------------------*\n";
			returnString += "*	                                                  *\n";
			returnString += getBaseDistanceReport(); 
			returnString += "*---------------------- Time  Report ---------------------*\n";
			returnString += "*	                                                  *\n";
			returnString += getBaseTimeReport(velocities);
			returnString += "********************* Mission  Report *********************\n";
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return("Error encountered when generating mission report");
		}
	}
	
	public String toString(HashMap<Integer, Double> agentVelocities) {
		try {
			return getReport(agentVelocities);
			
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
