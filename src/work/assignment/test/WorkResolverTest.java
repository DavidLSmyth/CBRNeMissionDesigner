package work.assignment.test;

import static org.junit.jupiter.api.Assertions.*;

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
import agent.vehicle.RAV;
import agent.vehicle.VehicleImpl;
import agent.vehicle.VehicleType;
import agent.vehicle.uav.AeorumUAV;
import mission.resolver.Mission;
import mission.resolver.MissionPoint;
import work.assignment.WorkResolver;
import work.assignment.WorkType;
import work.assignment.grid.GPSCoordinate;

class WorkResolverTest {

	WorkResolver workResolver;
	
	agent.Agent agent1;
	agent.Agent agent2;
	agent.Agent agent3; 
	agent.Agent agent4; 
	
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
		agent1.setVehicle(new RAV("1"));
		agent2.setVehicle(new RAV("2"));
		
		NUIGcoord0 = new GPSCoordinate(53.2779115341, -9.0597334278);
		NUIGcoord1 = new GPSCoordinate(53.2812554869, -9.0627998557);
		NUIGcoord2 = new GPSCoordinate(53.2823423226, -9.0594844171);
		NUIGcoord3 = new GPSCoordinate(53.2789984548, -9.0564179892);
		
		
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

	@Test
	void testGetAgentMissions() throws Exception {
		workResolver = new WorkResolver(WorkType.MAP,
				new ArrayList<Agent>(Arrays.asList(agent1, agent2)), 
				new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)));
		
		System.out.println("Resolving work for agents");
		HashMap<Agent, Mission> agentMissions = workResolver.getAgentMissions(); 
		
		for(agent.Agent a: agentMissions.keySet()) {
			System.out.println("Mission for agent: " + a.getId() + "\n" + agentMissions.get(a).getMissionPoints());
			for(MissionPoint p : agentMissions.get(a).getMissionPoints()) {
				System.out.println(p);
			}
			//System.out.println(agentMissions.get(a).getMissionPoints());
		}
		
		agent1.setLocation(new ArrayList<Double>(Arrays.asList(53.282, -9.052, 20.0)));
		agent2.setLocation(new ArrayList<Double>(Arrays.asList(53.283, -9.0628, 20.0)));
		workResolver.setAgents(new ArrayList<Agent> (Arrays.asList(agent1, agent2)));
		workResolver.updateAgentMissions();
		agentMissions = workResolver.getAgentMissions(); 
		for(agent.Agent a: agentMissions.keySet()) {
			System.out.println("Mission for agent: " + a.getId() + "\n" + agentMissions.get(a).getMissionPoints());
			for(MissionPoint p : agentMissions.get(a).getMissionPoints()) {
				System.out.println(p);
			}
			//System.out.println(agentMissions.get(a).getMissionPoints());
		}
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
