package work.assignment.grid;

import java.util.ArrayList;

import work.assignment.AgentType;

public class TraversalGrid {
	//A class which contains a set of Points to be explored
	
	int noPoints;
	protected ArrayList<GPSCoordinate> points;
	
	public TraversalGrid() {
		this(new ArrayList<GPSCoordinate>());
	}
	public TraversalGrid(ArrayList<GPSCoordinate> points) {
		setPoints(points);
		setNoPoints(points.size());
	}
	
	//add a method to allow the user to use different cost optimizations
//redundant
//	public static double getCost(GPSCoordinate c1, GPSCoordinate c2, AgentType agentType) {
//		return c1.getMetresToOther(c2);
//	}
//	
//	public static double getPathCost(ArrayList<GPSCoordinate> path, AgentType agentType) {
//		//returns the cost of traversing the path for a given agent
//		double cost = 0;
//		for(int nodeNo = 0; nodeNo < path.size() - 1; nodeNo++) {
//			cost += getCost(path.get(nodeNo), path.get(nodeNo + 1), agentType);
//		}
//		return cost;
//	}
//	
	public int getNoPoints() {
		return noPoints;
	}

	protected void setNoPoints(int noPoints) {
		this.noPoints = noPoints;
	}

	public ArrayList<GPSCoordinate> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<GPSCoordinate> points) {
		this.points = points;
	}
}
