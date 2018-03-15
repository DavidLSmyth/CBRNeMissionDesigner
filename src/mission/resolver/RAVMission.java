package mission.resolver;
import java.util.ArrayList;

import agent.Agent;
import work.assignment.grid.GPSCoordinate;


public class RAVMission extends Mission {

	Agent agents;
	ArrayList<MissionPoint> missionPoints;
	static int IDCounter=0;
	int ID;
	public RAVMission(Agent agent, ArrayList<GPSCoordinate> missionCoords) {
		// TODO Auto-generated constructor stub
		setID(IDCounter);
		IDCounter++;
		setAgent(agent);
		missionPoints = new ArrayList<MissionPoint>();
		for(GPSCoordinate c: missionCoords) {
			missionPoints.add(new MissionPoint(c));
		}
		setMissionPoints(missionPoints);
	}
	
	private void setID(int newID) {
		this.ID = newID;
	}
	
	public int getID() {
		return ID;
	}
	
	public Agent getAgents() {
		return agents;
	}
	public void setAgent(Agent agents) {
		this.agents = agents;
	}
}
