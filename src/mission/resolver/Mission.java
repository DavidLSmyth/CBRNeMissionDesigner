package mission.resolver;



import java.util.ArrayList;
import java.util.List;

import GPSUtils.GPSCoordinate;
import agent.Agent;
import agent.vehicle.VehicleType;
import mission.resolver.RAV.RAVMissionPoint;

public abstract class Mission {
	
	protected int missionID;
	//MissionPoint consists of ID, GPS Coordinate, params, commands relative to gps coordinate
	protected ArrayList<? extends MissionPoint> missionPoints;
	//name is an easy way to identify a mission
	protected String missionName;
	//speed at which drones will carry out mission
	protected double missionSpeed;
	//a description of the mission to be logged in the database
	protected String missionDescription;
	//a final action for the drone to carry out when finished executing its mission points
	protected FinalAction finalAction;
		//a default final action for the drone to carry out when finished executing its mission points
	protected static FinalAction defaultFinalAction = FinalAction.RTL;
	//the default mission speed for the drone is 2 m/s
	protected static double defaultMissionSpeed = 2.0;
	
	protected Mission(int missionID, ArrayList<? extends MissionPoint> missionPoints) {
		this(missionID, missionPoints, "Default name");
	}
	protected Mission(int missionID, ArrayList<? extends MissionPoint> missionPoints, FinalAction finalAction) {
		this(missionID, missionPoints, "Default name", "Default description", finalAction);
	}

	protected Mission(int missionID, ArrayList<? extends MissionPoint> missionPoints, String missionName) {
		this(missionID, missionPoints, "Default name", "Default description", null);
	}
	
	//protected since don't want to be instantiated directly but do want subclasses to be able to access
	protected Mission(int missionID, ArrayList<? extends MissionPoint> missionPoints, String missionName, String missionDescription, FinalAction finalAction) {
		setMissionName(missionName);
		setMissionDescription(missionDescription);
		setMissionID(missionID);
		setMissionPoints(missionPoints);
		if (finalAction != null) setFinalAction(finalAction);
		else setFinalAction(defaultFinalAction);
		
	}
	
	//protected since don't want to be instantiated directly but do want subclasses to be able to access
	protected Mission(int missionID, ArrayList<MissionPoint> missionPoints, String missionName, String missionDescription) {
		this(missionID, missionPoints, missionName, missionDescription, null);
	}
	
	public FinalAction getFinalAction() {
		return finalAction;
	}
	
	public void setFinalAction(FinalAction finalAction) {
		this.finalAction = finalAction;
	}
	
	public ArrayList<GPSCoordinate> getMissionGPSCoordinates(){
		//returns the GPS coordinates for the mission
		ArrayList<GPSCoordinate> missionGPSCoordinates = new ArrayList<GPSCoordinate>();
		for(MissionPoint m: getMissionPoints()) {
			missionGPSCoordinates.add(m.getGpsCoord());
		}
		return missionGPSCoordinates;
	}
	
	public String getMissionName() {
		return missionName;
	}
	//allow anyone to set the mission name
	public void setMissionName(String missionName) {
		this.missionName = missionName;
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


	public static Mission makeMission(Agent agent, List<GPSCoordinate> list) throws Exception {
		//Given an agent an a list of GPS coordinates, creates a mission for that agent
		//assume carry out mission at 2 m/s on average if mission speed is not specified
		return makeMission(agent, list, defaultMissionSpeed);
	}
	
	
	
	public static Mission makeMission(Agent agent, List<GPSCoordinate> missionPoints, double missionSpeed) throws Exception {
		//Given an agent an a list of GPS coordinates, creates a mission for that agent
		if (agent.getVehicle().getVehicleType().equals(VehicleType.AIR_VEHICLE)){
			ArrayList<RAVMissionPoint> missionPointsCopy = new ArrayList<RAVMissionPoint>();
			for(GPSCoordinate c: missionPoints) {
				missionPointsCopy.add(new RAVMissionPoint(c));
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
	
	public double getMissionCostEstimateDistance() throws Exception {
		//returns an estimate of the distance cost in metres of an instance of a mission given a cost type
		double distanceCost = 0;
		for(int counter = 0; counter < getMissionPoints().size()-1; counter++) {
			distanceCost = getMissionPoints().get(counter).getGpsCoord().getMetresToOther(getMissionPoints().get(counter+1).getGpsCoord());
		}
		return distanceCost;
	}
	public double getMissionCostEstimateTime() throws Exception {
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
	
	public ArrayList<? extends MissionPoint> getMissionPoints(){
		return missionPoints;
	}
	
	public void setMissionPoints(ArrayList<? extends MissionPoint> missionPoints) {
		this.missionPoints = missionPoints;
	}
	
	public String toString() {
		return "Mission :" + missionID + "\tMission Points: " + missionPoints.toString();
		
	}

}
