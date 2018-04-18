package mission.resolver.map.runner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import agent.Agent;
import agent.AgentImpl;
import agent.vehicle.uav.AeorumUAV;
import mission.resolver.Mission;
import mission.resolver.MissionPoint;
import mission.resolver.map.MapMissionAnalyserAgent;
import mission.resolver.map.MapMissionBase;
import mission.resolver.map.MapMissionStrategyGreedy;
import work.assignment.CostType;
import work.assignment.WorkResolver;
import work.assignment.WorkType;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;

public class MapMissionRunner {
	
	Agent agent1;
	Agent agent2;
	Agent agent3; 
	Agent agent4; 
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	
	WindFactor windFactor;
	
	MapMissionBase mapMissionBase;
	MapMissionBase mapMissionBase1;
	MapMissionAnalyserAgent agentAnalyser;
	WorkResolver workResolver;
	WorkResolver workResolver1;
	
	CostType costType;
	
	public MapMissionRunner() throws Exception {
		agent1 = new AgentImpl("1");
		agent2 = new AgentImpl("2");
		agent3 = new AgentImpl("3");
		agent4 = new AgentImpl("4");	
		
		
		agent1.setLocation(new ArrayList<Double>(Arrays.asList(53.28, -9.07, 20.0)));
		agent2.setLocation(new ArrayList<Double>(Arrays.asList(53.286, -9.0588, 20.0)));
		agent3.setLocation(new ArrayList<Double>(Arrays.asList(53.2798, -9.0565, 20.0)));
		agent4.setLocation(new ArrayList<Double>(Arrays.asList(53.277, -9.0576, 20.0)));

		agent1.setVehicle(new AeorumUAV("1", "127.0.0.1", "41451"));
		agent2.setVehicle(new AeorumUAV("2", "127.0.0.1", "41452"));
		agent3.setVehicle(new AeorumUAV("3", "127.0.0.1", "41453"));
		agent4.setVehicle(new AeorumUAV("4", "127.0.0.1", "41454"));
		
		NUIGcoord0 = new GPSCoordinate(53.2791748417, -9.0644775368);
        NUIGcoord1 = new GPSCoordinate(53.2801009832, -9.0648776011);
        NUIGcoord2 = new GPSCoordinate(53.2805257224, -9.0621271428);
        NUIGcoord3 = new GPSCoordinate(53.27959959, -9.0617270785);
        
        //northerly wind
        windFactor = new WindFactor(0, 0);
        
        costType = CostType.TOTALTIME;
        
        
        HashMap<Object, Object> optionalParams = new HashMap<Object, Object>();
        optionalParams.put("wind", windFactor);
        
		workResolver = new WorkResolver(WorkType.MAP,
				new ArrayList<Agent>(Arrays.asList(agent1)), 
				new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)),
				optionalParams);

		workResolver1 = new WorkResolver(WorkType.MAP,
				new ArrayList<Agent>(Arrays.asList(agent1)), 
				new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)),
				optionalParams);
        
        mapMissionBase = new MapMissionStrategyGreedy(new ArrayList<Agent> (Arrays.asList(agent1, agent2, agent3)),
        		new ArrayList<GPSCoordinate> (Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)),
        		 costType);

        mapMissionBase1 = new MapMissionStrategyGreedy(new ArrayList<Agent> (Arrays.asList(agent1)),
        		new ArrayList<GPSCoordinate> (Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)),
        		costType);
        
        
        
        //ArrayList<Agent> agents, HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap
        HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap = new HashMap<Agent, ArrayList<GPSCoordinate>>();
        agentPathsMap.put(agent1, workResolver.getMissionFor(agent1).getMissionGPSCoordinates());
//        agentPathsMap.put(agent2, workResolver.getMissionFor(agent2).getMissionGPSCoordinates());
//        agentPathsMap.put(agent3, workResolver.getMissionFor(agent3).getMissionGPSCoordinates());
        
        
        agentAnalyser = new MapMissionAnalyserAgent(new ArrayList<Agent>(Arrays.asList(agent1, agent2, agent3)), agentPathsMap);
	}

	public static void main(String[] args) throws Exception {
		MapMissionRunner runner = new MapMissionRunner();
		System.out.println("Results of planned mission with bounding box: " + runner.NUIGcoord0 + 
				"\t" + runner.NUIGcoord1 + "\t" + runner.NUIGcoord2 + "\t" + runner.NUIGcoord3);
		
		System.out.println(runner.agentAnalyser.getDistanceReport());
		System.out.println(runner.agentAnalyser.getTimeReport(runner.windFactor));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCode\\CommsHubCode\\work_resolver_runner.txt"));
		runner.writeAgentMission(runner.workResolver, writer);
		writer.flush();
		writer.close();
		
	}
	
	void writeAgentMission(WorkResolver workResovler, BufferedWriter outputFile) throws Exception{
		//workResolver.updateAgentMissions();
		HashMap<Agent, Mission> agentMissions = workResolver.getAgentMissions(); 
		System.out.println("Agents Keyset: " + agentMissions.keySet());
		
		for(Agent a: workResolver.getAgentMissions().keySet()) {
			outputFile.write(a.toString() + "\n");
			System.out.println("Number of mission points to write: " + workResolver.getAgentMissions().get(a).getMissionPoints().size());
			for(MissionPoint p :  workResolver.getAgentMissions().get(a).getMissionPoints()) {
				//System.out.println("Next mission point: " + p + "\n");
				outputFile.write(p.toString()+"\n");
			}
			outputFile.write("\n");
			//System.out.println(agentMissions.get(a).getMissionPoints());
		}
	}
}
