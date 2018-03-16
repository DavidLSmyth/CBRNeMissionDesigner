package mission.resolver;

import java.util.ArrayList;
import java.util.HashMap;

import work.assignment.grid.GPSCoordinate;

public class RAVMissionPoint extends MissionPoint {
	//add functionality here for things like a gimbal, 
	//keep_yaw, alt_type, verify_alt, etc.
	private double velocity;
	private double keepYaw;
	ArrayList<RAVMissionPointCommand> commands;
	public RAVMissionPoint(GPSCoordinate gpsCoord) {
		super(gpsCoord);
		// TODO Auto-generated constructor stub
	}

	public RAVMissionPoint(GPSCoordinate gpsCoord, ArrayList<RAVMissionPointParam> params) {
		super(gpsCoord, params);
		// TODO Auto-generated constructor stub
	}

	public RAVMissionPoint(GPSCoordinate gpsCoord, ArrayList<RAVMissionPointParam> params, ArrayList<RAVMissionPointCommand> commands, String name) {
		super(gpsCoord, params, commands, name);
		// TODO Auto-generated constructor stub
	}
	
//	public String toString() throws Exception{
//		throw new Exception("Not yet implemented");
//	}

}
