package mission.resolver.map.runner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PrimitiveIterator.OfDouble;

import org.omg.CosNaming._BindingIteratorImplBase;

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
	
	static final String usageString = "This jar can be run in the following format:\n"+
		 "java -jar jarName working_directory number_of_ravs latitude_spacing longitude_spacing RAV1LocationLat, RAV1LocationLong, RAV2LocationLat, ..., RAVNLocationLong, PolygonBoundingGPSCoodinate1Lat PolygonBoundingGPSCoodinate1Long PolygonBoundingGPSCoodinate2Lat ... PolygonBoundingGPSCoodinateNLong";
	/*
	Agent agent1;
	Agent agent2;
	Agent agent3; 
	Agent agent4;
	List<Agent> agents;
	*/
	
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
	
	public MapMissionRunner(GPSPolygonGrid grid, double latSpacing, double lngSpacing, int noRavs, List<Agent> agents) throws Exception {
		
		
				
		//agents = Arrays.asList(agent1, agent2, agent3, agent4);
//		NUIGcoord0 = new GPSCoordinate(53.2791748417, -9.0644775368);
//        NUIGcoord1 = new GPSCoordinate(53.2801009832, -9.0648776011);
//        NUIGcoord2 = new GPSCoordinate(53.2805257224, -9.0621271428);
//        NUIGcoord3 = new GPSCoordinate(53.27959959, -9.0617270785);
        
        //northerly wind
        windFactor = new WindFactor(0, 0);
        
        costType = CostType.TOTALTIME;
        
        
        //Map<Object, Object> optionalParams = new HashMap<Object, Object>();
        //optionalParams.put("wind", windFactor);
        
//        List<WorkResolver> workResolvers = new ArrayList<WorkResolver>();
//        for(int counter = 0; counter < noRavs; counter++) {
//        	workResolvers.add(new WorkResolver(WorkType.MAP,
//				new ArrayList<Agent>(Arrays.copyOfRange(agents, 0, to)),
//				CostType.TOTALDISTANCE,
//				grid.getPolygon().getBoundary(), latSpacing, lngSpacing);)
//        }
        
		workResolver = new WorkResolver(WorkType.MAP,
				agents.subList(0, noRavs),
				CostType.TOTALDISTANCE,
				grid.getPolygon().getBoundary(), latSpacing, lngSpacing);
		
		Map<Agent, List<GPSCoordinate>> agentMissions = new HashMap<Agent, List<GPSCoordinate>>();
		for(Agent a: workResolver.getAgentMissions().keySet()) {
			agentMissions.put(a, workResolver.getMissionFor(a).getMissionGPSCoordinates());
		}
//		System.out.println("AgentMissions size: " + agentMissions.size());
		
		agentAnalyser = new MapMissionAnalyserAgent(agents.subList(0, noRavs), agentMissions);

//		workResolver1 = new WorkResolver(WorkType.MAP,
//				new ArrayList<Agent>(Arrays.asList(agent1, agent2)), 
//				CostType.TOTALDISTANCE,
//				grid.getPolygon().getBoundary(), latSpacing, lngSpacing);
//		
//		workResolver2 = new WorkResolver(WorkType.MAP,
//				new ArrayList<Agent>(Arrays.asList(agent1, agent2, agent3)),
//				CostType.TOTALDISTANCE,
//				grid.getPolygon().getBoundary(), latSpacing, lngSpacing);
//		
//		workResolvers = Arrays.asList(workResolver, workResolver1, workResolver2);
		
        
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
        
        
        
//        ArrayList<Agent> agents, HashMap<Agent, ArrayList<GPSCoordinate>> agentPathsMap;
        
//        
//        
        
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
	
	public static void printUsageMessage(String usageMessage) {
		System.out.println(usageMessage);
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println("Running...");
		//first argument is number of RAVS
		//all other arguments are bounding box of GPSCoordinates
//		System.out.println();
		//ToDo: refactor so that the number of command line arguments is determined by one thing
		//i.e. shouldn't need to say 4 below
		if(args.length < 7) {
			printUsageMessage(usageString);
			System.out.println("Please provide at least 9 command line arguments to run this jar");
			System.exit(0);
		}
		
		int ArgsCounter = 0;
		
		//String workingDir = args[ArgsCounter++];
		//String writeDirectory = workingDir+args[ArgsCounter++];
		
		//String workingDir = args[ArgsCounter++];
		String writeDirectory = args[ArgsCounter++];
		
		
//		System.out.println("parsed working dir as: " + workingDir);
		int noRavs = Integer.parseInt(args[ArgsCounter++]);
		double latSpacing = Double.parseDouble(args[ArgsCounter++]);
		double lngSpacing = Double.parseDouble(args[ArgsCounter++]);
		
		//This is really messy, clean up in future
		Agent agent1 = new AgentImpl("1");
		Agent agent2 = new AgentImpl("2");
		Agent agent3 = new AgentImpl("3");
		Agent agent4 = new AgentImpl("4");	
		
		//agent1.setLocation(new ArrayList<Double>(Arrays.asList(53.28, -9.07, 20.0)));
		
		/*
		agent1.setLocation(new ArrayList<Double>(Arrays.asList(53.2793, -9.0638, 20.0)));
		agent2.setLocation(new ArrayList<Double>(Arrays.asList(53.2793, -9.0638, 20.0)));
		agent3.setLocation(new ArrayList<Double>(Arrays.asList(53.2798, -9.0565, 20.0)));
		agent4.setLocation(new ArrayList<Double>(Arrays.asList(53.277, -9.0576, 20.0)));
		*/
		
		agent1.setVehicle(new AeorumUAV("1", "127.0.0.1", "41451"));
		agent2.setVehicle(new AeorumUAV("2", "127.0.0.1", "41452"));
		agent3.setVehicle(new AeorumUAV("3", "127.0.0.1", "41453"));
		agent4.setVehicle(new AeorumUAV("4", "127.0.0.1", "41454"));
		List<Agent> agents = Arrays.asList(agent1, agent2, agent3, agent4);
		for(int ravNumber = 0; ravNumber < noRavs; ravNumber++) {
			agents.get(ravNumber).setLocation(new ArrayList<Double>(Arrays.asList(Double.parseDouble(args[ArgsCounter++]), Double.parseDouble(args[ArgsCounter++]), 20.0)));
		}
		
//		System.out.println("noRavs: " + noRavs);
		List<GPSCoordinate> polyCoords = coordinateParser(Arrays.copyOfRange(args, ArgsCounter++, args.length));
		//System.out.println("Polygon coordinates: " + polyCoords);
		GPSPolygonGrid grid = new GPSPolygonGrid(new GPSPolygon(polyCoords), latSpacing, lngSpacing);
//		System.out.println("Polygon grid: " + grid);
//		System.out.println(grid.generateContainedGPSCoordinates());
		MapMissionRunner runner = new MapMissionRunner(grid, latSpacing, lngSpacing, noRavs, agents);
		//System.out.println("number of agents involved: " + runner.workResolvers.get(noRavs - 1));
		//System.out.println("agent1 mission: " + runner.workResolvers.get(noRavs - 1).getAgentMissions().get(runner.agent1));
//		runner.workResolver.getNoAgents();
//		runner.workResolver.getNoAgents();
		
//		System.out.println("Results of planned mission with bounding box: " + runner.NUIGcoord0 + 
//				"\t" + runner.NUIGcoord1 + "\t" + runner.NUIGcoord2 + "\t" + runner.NUIGcoord3);
 
		
        System.out.println(runner.agentAnalyser.getDistanceReport());
//		System.out.println(runner.agentAnalyser.getTimeReport(runner.windFactor));
//		
		//BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCodeAll\\\\RCode\\\\ShinyApp\\\\Data\\\\"));
		//String writeDirectory = workingDir + "\\RCode\\ShinyApp\\Data\\"; 
				//"D:\\IJCAIDemoCodeAll\\RCode\\ShinyApp\\Data\\";
//		System.out.println("writing to directory " + writeDirectory);
		//System.out.println(runner.workResolvers.get(noRavs - 1).getNoAgents());
		runner.writeAgentMission(runner.workResolver, writeDirectory);
		runner.writeGeneratedGPSGridCoordinates(grid, writeDirectory);
//		writer.flush();
//		writer.close();
		
	}
	
	void writeGeneratedGPSGridCoordinates(GPSPolygonGrid grid, String fileDirectory) throws Exception {
		BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileDirectory + "/gridPoints.csv"));
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
		//System.out.println("Agents Keyset: " + agentMissions.keySet());
		
		for(Agent a: workRes.getAgentMissions().keySet()) {
			//outputFile.write(a.toString() + "\n");
//			System.out.println("File location: " + fileDirectory + a.toString().replaceAll("\\s+", "").replaceAll(":",  "") + ".csv");
			//System.out.println("Writing agent route to directory: " + fileDirectory + "/" + a.toString().replaceAll("\\s+", "").replaceAll(":",  "") + ".csv");
			BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileDirectory + "/" + a.toString().replaceAll("\\s+", "").replaceAll(":",  "") + ".csv"));
//			System.out.println("Number of mission points to write: " + workRes.getAgentMissions().get(a).getMissionPoints().size());
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
