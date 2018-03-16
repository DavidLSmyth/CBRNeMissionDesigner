package mission.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import work.assignment.grid.GPSCoordinate;

public class MissionPoint {
	
	static int IDCounter = 0;
	int ID;
	GPSCoordinate gpsCoord;
	HashMap<String, Object> params;
	ArrayList<RAVMissionPointCommand> commands;

	public MissionPoint(GPSCoordinate gpsCoord) {
		this(gpsCoord, new HashMap<String, Object>());
	}
	public MissionPoint(GPSCoordinate gpsCoord, HashMap<String, Object> params) {
		this(gpsCoord, new HashMap<String, Object>(), new ArrayList<RAVMissionPointCommand>());
	}
	
	public MissionPoint(GPSCoordinate gpsCoord, HashMap<String, Object> params, ArrayList<RAVMissionPointCommand> commands) {
		// TODO Auto-generated constructor stub
		setID(IDCounter);
		IDCounter++;
		setGpsCoord(gpsCoord);
		//speed should be in params
		setParams(params);
		setCommands(commands);
	}
	
	public double getDistanceCostEstimate(GPSCoordinate coord) {
		/**
		* Returns a cost estimate in terms of distance of travelling from the provided coordinate to 
		* the mission points coordinate and executing the missionPoint 
		*/
		return getGpsCoord().getMetresToOther(coord);
	}
	
	public double getTimeCostEstimate(GPSCoordinate coord) throws Exception {
		/**
		* Returns a cost estimate in terms of time taken to travel from the provided coordinate to 
		* the mission points coordinate and executing the missionPoint 
		*/
		
		// speed = distance / time <=> time = distance / speed
		//return getGpsCoord().getMetresToOther(coord) / missionSpeed + time taken to record images, rotate gimbal, etc.
		throw new Exception("Not yet implemented");
	}

	public String toString() {
		return getGpsCoord().toString();
	}
	public int getID() {
		return ID;
	}
	private void setID(int newID) {
		this.ID = newID;
	}
	public GPSCoordinate getGpsCoord() {
		return gpsCoord;
	}
	public void setGpsCoord(GPSCoordinate gpsCoord) {
		this.gpsCoord = gpsCoord;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	public ArrayList<RAVMissionPointCommand> getCommands() {
		return commands;
	}
	public void setCommands(ArrayList<RAVMissionPointCommand> commands) {
		this.commands = commands;
	}

}
