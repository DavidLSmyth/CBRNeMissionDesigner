package mission.resolver;

import java.util.ArrayList;
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


	private String getMissionStructure() {
		/**
		 * Returns a string builder which creates an Aeorum mission given a certain structure. 
		 * Could have multiple versions of this method for different types of aeorum mission structure.
		 */
		StringBuilder builder = new StringBuilder();		
		return "";
	}

	public String getJSONMission() {
		return "";
		
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
