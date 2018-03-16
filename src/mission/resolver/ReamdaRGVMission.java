package mission.resolver;

import java.util.ArrayList;

import agent.Agent;
import work.assignment.grid.GPSCoordinate;

public class ReamdaRGVMission extends RGVMission {

	static int IDCounter=0;
	public ReamdaRGVMission(ArrayList<MissionPoint> missionPoints) {
		super(IDCounter, missionPoints);
		// TODO Auto-generated constructor stub
		IDCounter++;
	}



}
