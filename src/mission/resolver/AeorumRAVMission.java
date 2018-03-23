package mission.resolver;

import java.util.ArrayList;

import mission.resolver.RAV.RAVMission;
import mission.resolver.RAV.RAVMissionPoint;
public class AeorumRAVMission extends RAVMission {
	
	static int IDCounter = 0;
	String missionDescription;
//	MissionType missionType;
//	MissionsubType missionSubtype;
	String missionType;
	String missionSubtype;
	FinalAction finalAction;
	int numLaps;
	public AeorumRAVMission(ArrayList<RAVMissionPoint> missionPoints) {
		this("Default name", 
				"Default missionDescriptions",
				"AUTOMATIC",
				"NORMAL", 
				FinalAction.RTL,
				1, 
				missionPoints);
	}
	
	public AeorumRAVMission(String missionName, String missionDescription, String missionType,
			String missionSubtype,
			FinalAction finalAction,
			int numLaps,
			ArrayList<RAVMissionPoint> missionPoints) {
		super(IDCounter, missionPoints);
		IDCounter++;
		setMissionName(missionName);
		setMissionDescription(missionDescription);
		setNumLaps(numLaps);
		// TODO Auto-generated constructor stub
	}

	public String getMissionJSON() {
		return AeorumMissionJSONHelper.getAeorumMissionJSON(this);
		
	}
	public String getMissionDescription() {
		return missionDescription;
	}

	public void setMissionDescription(String missionDescription) {
		this.missionDescription = missionDescription;
	}

	public FinalAction getFinalAction() {
		return finalAction;
	}

	public void setFinalAction(FinalAction finalAction) {
		this.finalAction = finalAction;
	}

	public int getNumLaps() {
		return numLaps;
	}
	
	public void setNumLaps(int numLaps) {
		this.numLaps = numLaps;
	}
}
