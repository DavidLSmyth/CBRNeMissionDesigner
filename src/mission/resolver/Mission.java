package mission.resolver;

import work.assignment.AgentType;
import work.assignment.CostTypes;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import agent.vehicle.VehicleType;
import work.assignment.grid.GPSCoordinate;

public abstract class Mission {
	
	int missionID;
	//MissionPoint consists of ID, GPS Coordinate, params, commands relative to gps coordinate
	ArrayList<MissionPoint> missionPoints;
	
	public static Mission makeMission(Agent agent, ArrayList<GPSCoordinate> missionPoints) throws Exception {
		//Given an agent an a list of GPS coordinates, creates a mission for that agent
		if (agent.getVehicle().getVehicleType().equals(VehicleType.AIR_VEHICLE)){
			System.out.println("Returning a new RAVMission:  " + missionPoints.toString());
			return new RAVMission(agent, missionPoints);
		}
		else if(agent.getVehicle().getVehicleType().equals(VehicleType.GROUND_VEHICLE)) {
			return new RGVMission(agent, missionPoints);
		}
		else {
			throw new Exception("Not implemented, vehicle type is: " + agent.getVehicle().getVehicleType());
		}
	}
	
	public double getMissionCostEstimate(CostTypes costType) {
		//returns an estimate of the cost of an instance of a mission given a cost type
		
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
