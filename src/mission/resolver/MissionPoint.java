package mission.resolver;

import java.util.ArrayList;
import java.util.Map;

import work.assignment.grid.GPSCoordinate;

public class MissionPoint {
	
	int ID;
	GPSCoordinate gpsCoord;
	ArrayList<Map<String, Object>> params;
	ArrayList<MissionCommand> commands;

	public MissionPoint(int ID, GPSCoordinate gpsCoord) {
		this(ID, gpsCoord, new ArrayList<Map<String, Object>>());
	}
	public MissionPoint(int ID, GPSCoordinate gpsCoord, ArrayList<Map<String, Object>> params) {
		this(ID, gpsCoord, new ArrayList<Map<String, Object>>(), new ArrayList<MissionCommand>());
	}
	
	public MissionPoint(int ID, GPSCoordinate gpsCoord, ArrayList<Map<String, Object>> params, ArrayList<MissionCommand> commands) {
		// TODO Auto-generated constructor stub
	}

}
