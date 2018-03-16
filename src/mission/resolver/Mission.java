package mission.resolver;

import work.assignment.AgentType;
import work.assignment.CostType;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import agent.vehicle.VehicleType;
import work.assignment.grid.GPSCoordinate;

public abstract class Mission {
	
	protected int missionID;
	//MissionPoint consists of ID, GPS Coordinate, params, commands relative to gps coordinate
	protected ArrayList<MissionPoint> missionPoints;
	//speed at which drones will carry out mission
	protected double missionSpeed;
	//a description of the mission to be logged in the database
	protected String missionDescription;
	//the default mission speed for the drone is 2 m/s
	protected static final double defaultMissionSpeed = 2.0;
	
	protected Mission(int missionID, ArrayList<MissionPoint> missionPoints) {
		this(missionID, missionPoints, "Default description");
	}
	
	//protected since don't want to be instantiated directly but do want subclasses to be able to access
	protected Mission(int missionID, ArrayList<MissionPoint> missionPoints, String missionDescription) {
		setMissionID(missionID);
		setMissionPoints(missionPoints);
	}
	
	public String getMissionDescription() {
		return missionDescription;
	}

	public void setMissionDescription(String missionDescription) {
		this.missionDescription = missionDescription;
	}

	
	public int getMissionID() {
		return missionID;
	}
	//ID needs to be unique - don't let user set
	protected void setMissionID(int missionID) {
		this.missionID = missionID;
	}


	public static Mission makeMission(Agent agent, ArrayList<GPSCoordinate> missionPoints) throws Exception {
		//Given an agent an a list of GPS coordinates, creates a mission for that agent
		//assume carry out mission at 2 m/s on average if mission speed is not specified
		return makeMission(agent, missionPoints, defaultMissionSpeed);
	}
	
	
	
	public static Mission makeMission(Agent agent, ArrayList<GPSCoordinate> missionPoints, double missionSpeed) throws Exception {
		//Given an agent an a list of GPS coordinates, creates a mission for that agent
		if (agent.getVehicle().getVehicleType().equals(VehicleType.AIR_VEHICLE)){
			ArrayList<MissionPoint> missionPointsCopy = new ArrayList<MissionPoint>();
			for(GPSCoordinate c: missionPoints) {
				missionPointsCopy.add(new MissionPoint(c));
			}
			return new AeorumRAVMission(missionPointsCopy);
		}
		else if(agent.getVehicle().getVehicleType().equals(VehicleType.GROUND_VEHICLE)) {
			ArrayList<MissionPoint> missionPointsCopy = new ArrayList<MissionPoint>();
			for(GPSCoordinate c: missionPoints) {
				missionPointsCopy.add(new MissionPoint(c));
			}
			return new ReamdaRGVMission(missionPointsCopy);
		}
		else {
			throw new Exception("Not implemented, vehicle type is: " + agent.getVehicle().getVehicleType());
		}
	}
	
	public double getMissionCostEstimateDistance() {
		//returns an estimate of the distance cost in metres of an instance of a mission given a cost type
		double distanceCost = 0;
		for(int counter = 0; counter < getMissionPoints().size()-1; counter++) {
			distanceCost = getMissionPoints().get(counter).getGpsCoord().getMetresToOther(getMissionPoints().get(counter+1).getGpsCoord());
		}
		return distanceCost;
	}
	public double getMissionCostEstimateTime() {
		//returns an estimate of the distance cost of an instance of a mission given a cost type
		double distanceCost = 0;
		for(int counter = 0; counter < getMissionPoints().size()-1; counter++) {
			distanceCost = getMissionPoints().get(counter).getGpsCoord().getMetresToOther(getMissionPoints().get(counter+1).getGpsCoord());
		}
		//
		return distanceCost / 2;
		
	}
	public double getMissionCostEstimateBattery() throws Exception{
		throw new Exception("Not implemented");
	}
	
	public ArrayList<MissionPoint> getMissionPoints(){
		return missionPoints;
	}
	
	public void setMissionPoints(ArrayList<MissionPoint> missionPoints) {
		this.missionPoints = missionPoints;
	}
	
	public String toString() {
		return "Mission " + missionID + "\n" + missionPoints.toString();
		
	}

}
