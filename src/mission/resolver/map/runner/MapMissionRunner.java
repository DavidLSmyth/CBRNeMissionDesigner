package mission.resolver.map.runner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GPSUtils.GPSCoordinate;
import GPSUtils.grid.GPSPolygon;
import GPSUtils.grid.GPSPolygonGrid;
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
	MapMissionBase mapMissionBase2;
	MapMissionAnalyserAgent agentAnalyser;
	WorkResolver workResolver;
	WorkResolver workResolver1;
	WorkResolver workResolver2;
	List<WorkResolver> workResolvers;
	
	CostType costType;
	
	public MapMissionRunner(GPSPolygonGrid grid, double latSpacing, double lngSpacing) throws Exception {
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
        
        
        //Map<Object, Object> optionalParams = new HashMap<Object, Object>();
        //optionalParams.put("wind", windFactor);
        
		workResolver = new WorkResolver(WorkType.MAP,
				new ArrayList<Agent>(Arrays.asList(agent1)),
				CostType.TOTALDISTANCE,
				grid.getPolygon().getBoundary(), latSpacing, lngSpacing);

		workResolver1 = new WorkResolver(WorkType.MAP,
				new ArrayList<Agent>(Arrays.asList(agent1, agent2)), 
				CostType.TOTALDISTANCE,
				grid.getPolygon().getBoundary(), latSpacing, lngSpacing);
		
		workResolver2 = new WorkResolver(WorkType.MAP,
				new ArrayList<Agent>(Arrays.asList(agent1, agent2, agent3)),
				CostType.TOTALDISTANCE,
				grid.getPolygon().getBoundary(), latSpacing, lngSpacing);
		
		workResolvers = Arrays.asList(workResolver, workResolver1, workResolver2);
		
        
//        mapMissionBase = new MapMissionStrategyGreedy(new ArrayList<Agent> (Arrays.asList(agent1)),
//        		grid.generateContainedGPSCoordinates(),
//        		 costType);

//        mapMissionBase1 = new MapMissionStrategyGreedy(new ArrayList<Agent> (Arrays.asList(agent1, agent2)),
//        		grid.generateContainedGPSCoordinates(),
//        		costType);
//        
//        mapMissionBase2 = new MapMissionStrategyGreedy(new ArrayList<Agent> (Arrays.asList(agent1, agent2, agent3)),
//        		grid.generateContainedGPSCoordinates(),
//        		costType);
        
        
        
        //ArrayList<Agent> agents, HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap
//        HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap = new HashMap<Agent, ArrayList<GPSCoordinate>>();
//        agentPathsMap.put(agent1, workResolver.getMissionFor(agent1).getMissionGPSCoordinates());
//        agentPathsMap.put(agent2, workResolver.getMissionFor(agent2).getMissionGPSCoordinates());
//        agentPathsMap.put(agent3, workResolver.getMissionFor(agent3).getMissionGPSCoordinates());
//        
//        
//        agentAnalyser = new MapMissionAnalyserAgent(new ArrayList<Agent>(Arrays.asList(agent1, agent2, agent3)), agentPathsMap);
	}
	
	private static List<GPSCoordinate> coordinateParser(String [] args) throws Exception {
		List<GPSCoordinate> returnCoords = new ArrayList<GPSCoordinate>();
		int counter = 0;
		double latVal = 0;
		double lngVal = 0;
		
		for(String arg: args){
			if(counter % 2 == 0) {
				latVal = Double.parseDouble(arg);
			}
			else {
				lngVal = Double.parseDouble(arg);
				returnCoords.add(new GPSCoordinate(latVal, lngVal));
			}
			counter++;
		}
		return returnCoords;
	}
	
	public static void main(String[] args) throws Exception {
		//first argument is number of RAVS
		//all other arguments are bounding box of GPSCoordinates
		System.out.println();
		
		int noRavs = Integer.parseInt(args[0]);
		double latSpacing = Double.parseDouble(args[1]);
		double lngSpacing = Double.parseDouble(args[2]);
		
		System.out.println("noRavs: " + noRavs);
		List<GPSCoordinate> polyCoords = coordinateParser(Arrays.copyOfRange(args, 3, args.length));
		System.out.println("Polygon coordinates: " + polyCoords);
		GPSPolygonGrid grid = new GPSPolygonGrid(new GPSPolygon(polyCoords), latSpacing, lngSpacing);
//		System.out.println("Polygon grid: " + grid);
//		System.out.println(grid.generateContainedGPSCoordinates());
		MapMissionRunner runner = new MapMissionRunner(grid, latSpacing, lngSpacing);
		System.out.println("number of agents involved: " + runner.workResolvers.get(noRavs - 1));
		//System.out.println("agent1 mission: " + runner.workResolvers.get(noRavs - 1).getAgentMissions().get(runner.agent1));
//		runner.workResolver.getNoAgents();
//		runner.workResolver.getNoAgents();
		
//		System.out.println("Results of planned mission with bounding box: " + runner.NUIGcoord0 + 
//				"\t" + runner.NUIGcoord1 + "\t" + runner.NUIGcoord2 + "\t" + runner.NUIGcoord3);
//		
		//System.out.println(runner.agentAnalyser.getDistanceReport());
//		System.out.println(runner.agentAnalyser.getTimeReport(runner.windFactor));
//		
		//BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCodeAll\\\\RCode\\\\ShinyApp\\\\Data\\\\"));
		String writeDirectory = "D:\\IJCAIDemoCodeAll\\RCode\\ShinyApp\\Data\\";
		System.out.println(runner.workResolvers.get(noRavs - 1).getNoAgents());
		runner.writeAgentMission(runner.workResolvers.get(noRavs - 1), writeDirectory);
		runner.writeGeneratedGPSGridCoordinates(grid, writeDirectory);
//		writer.flush();
//		writer.close();
		
	}
	
	void writeGeneratedGPSGridCoordinates(GPSPolygonGrid grid, String fileDirectory) throws Exception {
		BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileDirectory + "gridPoints.csv"));
		outputFile.write("lat, long, alt\n");
		for(GPSCoordinate coord: grid.generateContainedGPSCoordinates()) {
			outputFile.write(coord.toString()+'\n');
		}
		outputFile.flush();
		outputFile.close();
	}
	
	void writeAgentMission(WorkResolver workRes, String fileDirectory) throws Exception{
		//workResolver.updateAgentMissions();
		Map<Agent, Mission> agentMissions = workRes.getAgentMissions(); 
		System.out.println("Agents Keyset: " + agentMissions.keySet());
		
		for(Agent a: workRes.getAgentMissions().keySet()) {
			//outputFile.write(a.toString() + "\n");
			System.out.println("File location: " + fileDirectory + a.toString().replaceAll("\\s+", "").replaceAll(":",  "") + ".csv");
			BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileDirectory + a.toString().replaceAll("\\s+", "").replaceAll(":",  "") + ".csv"));
			System.out.println("Number of mission points to write: " + workRes.getAgentMissions().get(a).getMissionPoints().size());
			outputFile.write("lat, long, alt\n");
			for(MissionPoint p :  workRes.getAgentMissions().get(a).getMissionPoints()) {
				//System.out.println("Next mission point: " + p + "\n");
				outputFile.write(p.toString()+"\n");
			}
			outputFile.write("\n");
			outputFile.flush();
			outputFile.close();
			//System.out.println(agentMissions.get(a).getMissionPoints());
		}
	}
}
