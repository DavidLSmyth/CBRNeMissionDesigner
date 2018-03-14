package mission.resolver;

import work.assignment.AgentType;
import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import work.assignment.grid.GPSCoordinate;

public abstract class Mission {
	
	int missionID;
	//MissionPoint consists of ID, GPS Coordinate, params, commands relative to gps coordinate
	ArrayList<MissionPoint> missionPoints;
	HashMap<Object, Object> params;
	HashMap<Object, Object> commands;
	
	public static Mission makeMission(Agent agent, ArrayList<GPSCoordinate> missionPoints) {
		if (agent.getVehicle().getVehicleType().equals(AgentType.AIR_VEHICLE)){
			return new RAVMission(agent, missionPoints);
		}
		else if(agent.getVehicle().getVehicleType().equals(AgentType.GROUND_VEHICLE)) {
			return new RGVMission();
		}
		else {
			return null;
		}
	}
	
	public abstract boolean submitMissionForExecution();
}
