package mission.resolver.map;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import GPSUtils.GPSCoordinate;
import GPSUtils.grid.GPSPolygon;
import GPSUtils.grid.GPSPolygonGrid;
import agent.Agent;
import agent.AgentImpl;
import agent.vehicle.uav.AeorumUAV;
import work.assignment.CostType;

public class MapMissionStrategyGreedyRunner {
	
	Agent agent1;
	Agent agent2;
	Agent agent3; 
	Agent agent4; 
	MapMissionStrategyGreedy greedyMission;
	private List<Agent> missionAgents;
	
	public MapMissionStrategyGreedyRunner(GPSPolygonGrid grid, double latSpacing, double lngSpacing, int noRavs) throws Exception {
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
		
		List<Agent> agents = Arrays.asList(agent1, agent2, agent3);
		missionAgents = new ArrayList<Agent>();
		for(int counter = 0; counter < noRavs; counter++) {
			missionAgents.add(agents.get(counter));
		}
		
		greedyMission = new MapMissionStrategyGreedy(missionAgents, grid.getPolygon().getBoundary(), CostType.TOTALDISTANCE, latSpacing, lngSpacing);
		

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
				System.out.println("Coordinate parser added coordinate " + latVal + ", " + lngVal);
				returnCoords.add(new GPSCoordinate(latVal, lngVal));
			}
			counter++;
		}
		return returnCoords;
	}
	
	void writeGeneratedGPSGridCoordinates(GPSPolygonGrid grid, String fileDirectory) throws Exception {
		System.out.println("writing generated grid of GPS coordinates to " + fileDirectory + "gridPoints.csv");
		BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileDirectory + "gridPoints.csv"));
		outputFile.write("lat, long, alt\n");
		for(GPSCoordinate coord: grid.generateContainedGPSCoordinates()) {
			outputFile.write(coord.toString()+'\n');
		}
		outputFile.flush();
		outputFile.close();
	}
	
	void writeAgentMissions(Map<Agent, List<GPSCoordinate>> agentMissions, String fileDirectory) throws Exception{
		//workResolver.updateAgentMissions();

		System.out.println("Agents Keyset: " + agentMissions.keySet());
		
		for(Agent a: agentMissions.keySet()) {
			//outputFile.write(a.toString() + "\n");
			System.out.println("File location: " + fileDirectory + a.toString().replaceAll("\\s+", "").replaceAll(":",  "") + ".csv");
			BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileDirectory + a.toString().replaceAll("\\s+", "").replaceAll(":",  "") + ".csv"));
			System.out.println("Writing mission for agent " + a);
			System.out.println("Number of mission points to write: " + agentMissions.get(a).size());
			outputFile.write("lat, long, alt\n");
			for(GPSCoordinate p :  agentMissions.get(a)) {
				System.out.println("Next mission point: " + p + "\n");
				outputFile.write(p.toString()+"\n");
			}
			outputFile.write("\n");
			outputFile.flush();
			outputFile.close();
			//System.out.println(agentMissions.get(a).getMissionPoints());
		}
	}

	public static void main(String[] args) throws Exception {
		int noRavs = Integer.parseInt(args[0]);
		double latSpacing = Double.parseDouble(args[1]);
		double lngSpacing = Double.parseDouble(args[2]);
		List<GPSCoordinate> polyCoords = coordinateParser(Arrays.copyOfRange(args, 3, args.length));
		GPSPolygonGrid grid = new GPSPolygonGrid(new GPSPolygon(polyCoords), latSpacing, lngSpacing);
		MapMissionStrategyGreedyRunner runner = new MapMissionStrategyGreedyRunner(grid, latSpacing, lngSpacing, noRavs);
		String writeDirectory = "D:\\IJCAIDemoCodeAll\\RCode\\ShinyApp\\Data\\";
		runner.writeGeneratedGPSGridCoordinates(grid, writeDirectory);
		Map<Agent, List<GPSCoordinate>> agentMissions = runner.greedyMission.getAgentPaths();
		runner.writeAgentMissions(agentMissions, writeDirectory);
	}

}
