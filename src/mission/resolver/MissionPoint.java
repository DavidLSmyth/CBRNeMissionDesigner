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
	ArrayList<MissionCommand> commands;

	public MissionPoint(GPSCoordinate gpsCoord) {
		this(gpsCoord, new HashMap<String, Object>());
	}
	public MissionPoint(GPSCoordinate gpsCoord, HashMap<String, Object> params) {
		this(gpsCoord, new HashMap<String, Object>(), new ArrayList<MissionCommand>());
	}
	
	public MissionPoint(GPSCoordinate gpsCoord, HashMap<String, Object> params, ArrayList<MissionCommand> commands) {
		// TODO Auto-generated constructor stub
		setID(IDCounter);
		IDCounter++;
		setGpsCoord(gpsCoord);
		setParams(params);
		setCommands(commands);
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
	public ArrayList<MissionCommand> getCommands() {
		return commands;
	}
	public void setCommands(ArrayList<MissionCommand> commands) {
		this.commands = commands;
	}

}
