package mission.resolver;

import java.util.ArrayList;

import agent.Agent;
import work.assignment.grid.GPSCoordinate;

public class AeorumRAVMission extends RAVMission {

	static int IDCounter = 0;
	public AeorumRAVMission(ArrayList<MissionPoint> missionCoords) {
		super(IDCounter, missionCoords);
		IDCounter++;
		// TODO Auto-generated constructor stub
	}


}
