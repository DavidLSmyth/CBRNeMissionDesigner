package mission.resolver;

import java.util.ArrayList;

import GPSUtils.GPSCoordinate;
import agent.Agent;
import mission.resolver.RGV.RGVMission;

public class ReamdaRGVMission extends RGVMission {

	static int IDCounter=0;
	public ReamdaRGVMission(ArrayList<MissionPoint> missionPoints) {
		super(IDCounter, missionPoints);
		// TODO Auto-generated constructor stub
		IDCounter++;
	}



}
