package work.assignment.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import agent.Agent;
import agent.AgentImpl;
//import agent.vehicle.RAV;
import agent.vehicle.VehicleImpl;
import agent.vehicle.VehicleType;
import agent.vehicle.uav.AeorumUAV;
import agent.vehicle.uav.UAV;
import mission.resolver.AeorumMissionJSONHelper;
import mission.resolver.AeorumRAVMission;
import mission.resolver.Mission;
import mission.resolver.MissionPoint;
import work.assignment.WorkResolver;
import work.assignment.WorkType;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;
import java.util.Date;

class WorkResolverTest {

	WorkResolver workResolver;
	
	Agent agent1;
	Agent agent2;
	Agent agent3; 
	Agent agent4; 
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	
	@BeforeEach
	void setUp() throws Exception {
		//WorkResolver(WorkType workType, ArrayList<Agent> agents, List<GPSCoordinate> missionBoundingBox,
		//ArrayList<Object> optionalParams)
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
		
//		NUIGcoord0 = new GPSCoordinate(53.2779115341, -9.0597334278);
//		NUIGcoord1 = new GPSCoordinate(53.2812554869, -9.0627998557);
//		NUIGcoord2 = new GPSCoordinate(53.2823423226, -9.0594844171);
//		NUIGcoord3 = new GPSCoordinate(53.2789984548, -9.0564179892);
//[-9.0604869552,53.2790865775],[-9.0609452143,53.2801474395],[-9.0578275525,53.2806288832],[-9.0573692935,53.2795680332],[-9.0604869552,53.2790865775]
		
//		NUIGcoord0 = new GPSCoordinate(53.2790865775, -9.0604869552);
//        NUIGcoord1 = new GPSCoordinate(53.2801474395, -9.0609452143);
//        NUIGcoord2 = new GPSCoordinate(53.2806288832, -9.0578275525);
//        NUIGcoord3 = new GPSCoordinate(53.2795680332, -9.0573692935);
//
		NUIGcoord0 = new GPSCoordinate(53.2791748417, -9.0644775368);
        NUIGcoord1 = new GPSCoordinate(53.2801009832, -9.0648776011);
        NUIGcoord2 = new GPSCoordinate(53.2805257224, -9.0621271428);
        NUIGcoord3 = new GPSCoordinate(53.27959959, -9.0617270785);
		
		//WorkType workType, ArrayList<Agent> agents, ArrayList<GPSCoordinate> missionBoundingBox,
		//HashMap<Object, Object> optionalParams
		
		System.out.println();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testWorkResolver() {
		fail("Not yet implemented");
	}
	
	void writeAgentMission(WorkResolver workResovler, BufferedWriter outputFile) throws Exception{
		workResolver.updateAgentMissions();
		HashMap<Agent, Mission> agentMissions = workResolver.getAgentMissions(); 
		System.out.println("Agents Keyset: " + agentMissions.keySet());
		for(Agent a: workResolver.getAgentMissions().keySet()) {
			outputFile.write(a.toString() + "\n");
			for(MissionPoint p :  workResolver.getAgentMissions().get(a).getMissionPoints()) {
				System.out.println("Next mission point: " + p + "\n");
				outputFile.write(p.toString()+"\n");
			}
			outputFile.write("\n");
			//System.out.println(agentMissions.get(a).getMissionPoints());
		}
	}

	@Test
	void testGetAgentMissions() throws Exception {
		double t1 = System.currentTimeMillis();
		//create and output file to show results of mission
//		BufferedWriter mission1_writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCode\\CommsHubCode\\test_work_resolver11.txt"));
//		
		WindFactor windFactor = new WindFactor(0, -5);
		HashMap<Object, Object> optionalParams = new HashMap<Object, Object>();
		optionalParams.put("wind", windFactor);
		workResolver = new WorkResolver(WorkType.MAP,
				new ArrayList<Agent>(Arrays.asList(agent1, agent2, agent3)), 
				new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)),
				optionalParams);
//		
//		writeAgentMission(workResolver, mission1_writer);
//		mission1_writer.close();
//		
//		BufferedWriter mission2_writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCode\\CommsHubCode\\test_work_resolver21.txt"));
//		
//		agent1.setLocation(new ArrayList<Double>(Arrays.asList(53.282, -9.052, 20.0)));
//		agent2.setLocation(new ArrayList<Double>(Arrays.asList(53.283, -9.0628, 20.0)));
//		
//		workResolver.setAgents(new ArrayList<Agent> (Arrays.asList(agent1, agent2)));
//		workResolver.updateAgentMissions();
//		
//		writeAgentMission(workResolver, mission2_writer);
//		mission2_writer.close();
		
		BufferedWriter mission3_writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCode\\CommsHubCode\\test_work_resolver32.txt"));
		
		
		workResolver.setAgents(new ArrayList<Agent> (Arrays.asList(agent1, agent2, agent3)));
		workResolver.updateAgentMissions();
		Mission agent1Mission = workResolver.getMissionFor(agent1);
		System.out.println(AeorumMissionJSONHelper.getAeorumMissionJSON((AeorumRAVMission) agent1Mission));
		
		writeAgentMission(workResolver, mission3_writer);
		mission3_writer.close();
		System.out.println(System.currentTimeMillis() - t1);
		
//		WindFactor windFactor = new WindFactor(20, 0);
//		HashMap<Object, Object> optionalParams = new HashMap<Object, Object>();
//		optionalParams.put("wind", windFactor);
//		WorkResolver w1 = new WorkResolver(WorkType.MAP,
//				new ArrayList<Agent>(Arrays.asList(agent1)), 
//				new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)),
//				optionalParams
//				);
//		System.out.println("Number of agents for mission: " + w1.getNoAgents());
//		w1.updateAgentMissions();
//		w1.setAgents(new ArrayList<Agent>(Arrays.asList(agent1)));
////		workResolver.setAgents(new ArrayList<Agent> (Arrays.asList(agent1, agent2, agent3)));
////		workResolver.updateAgentMissions();
//		System.out.println("Getting agent missions");
//		BufferedWriter mission4_writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCode\\CommsHubCode\\test_work_resolver41.txt"));
//		System.out.println("Agent Missions: " + w1.getAgentMissions().keySet()); 
//		writeAgentMission(w1, mission4_writer);
//		mission4_writer.close();
		//System.out.println(AeorumMissionJSONHelper.getAeorumMissionJSON((AeorumRAVMission) agent1Mission));
		BufferedWriter mission4_writer = new BufferedWriter(new FileWriter("D:\\IJCAIDemoCode\\CommsHubCode\\test_mission_json.txt"));
		mission4_writer.write(AeorumMissionJSONHelper.getAeorumMissionJSON((AeorumRAVMission) agent1Mission));
		mission4_writer.close();
		System.out.println(System.currentTimeMillis() - t1);
	}

	@Test
	void testGetMissionFor() {
		fail("Not yet implemented");
	}

	@Test
	void testGetWorkType() {
		fail("Not yet implemented");
	}

	@Test
	void testSetWorkType() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNoAgents() {
		fail("Not yet implemented");
	}

	@Test
	void testSetNoAgents() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAgents() {
		fail("Not yet implemented");
	}

	@Test
	void testSetAgents() {
		fail("Not yet implemented");
	}

	@Test
	void testGetReturnMissions() {
		fail("Not yet implemented");
	}

	@Test
	void testSetReturnMissions() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMissionBoundingBox() {
		fail("Not yet implemented");
	}

	@Test
	void testSetMissionBoundingBox() {
		fail("Not yet implemented");
	}

}
