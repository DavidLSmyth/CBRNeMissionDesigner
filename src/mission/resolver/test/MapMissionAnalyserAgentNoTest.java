package mission.resolver.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import agent.Agent;
import agent.AgentImpl;
import agent.vehicle.uav.AeorumUAV;
import mission.resolver.Mission;
import mission.resolver.map.MapMissionAnalyserAgentNo;
import work.assignment.grid.GPSCoordinate;

class MapMissionAnalyserAgentNoTest {
	//ToDo: Add mission tests.
	Agent agent1;
	Agent agent2;
	Agent agent3; 
	
	GPSCoordinate NUIGcoord0;
	GPSCoordinate NUIGcoord1;
	GPSCoordinate NUIGcoord2;
	GPSCoordinate NUIGcoord3;
	@BeforeEach
	void setUp() throws Exception {
		agent1 = new AgentImpl("1");
		agent2 = new AgentImpl("2");
		agent3 = new AgentImpl("3");	
		
		agent1.setLocation(new ArrayList<Double>(Arrays.asList(53.28, -9.07, 20.0)));
		agent2.setLocation(new ArrayList<Double>(Arrays.asList(53.286, -9.0588, 20.0)));
		agent3.setLocation(new ArrayList<Double>(Arrays.asList(53.2798, -9.0565, 20.0)));

		agent1.setVehicle(new AeorumUAV("1", "127.0.0.1", "41451"));
		agent2.setVehicle(new AeorumUAV("2", "127.0.0.1", "41452"));
		agent3.setVehicle(new AeorumUAV("3", "127.0.0.1", "41453"));
		
		GPSCoordinate mission1 = new GPSCoordinate(53.2779115341, -9.0597334278);
		GPSCoordinate mission2 = new GPSCoordinate(53.2812554869, -9.0627998557);
		ArrayList<GPSCoordinate> agent1missions = new ArrayList<GPSCoordinate>(Arrays.asList(mission1, mission2));
		
		GPSCoordinate mission3 = new GPSCoordinate(53.2789984548, -9.0564179892);
		GPSCoordinate mission4 = new GPSCoordinate(53.2779115341, -9.0597334278);
		GPSCoordinate mission5 = new GPSCoordinate(53.2823423226, -9.0594844171);
		ArrayList<GPSCoordinate> agent2missions = new ArrayList<GPSCoordinate>(Arrays.asList(mission3, mission4, mission5));
		
		ArrayList<ArrayList<GPSCoordinate>> agentmissions = new ArrayList<ArrayList<GPSCoordinate>>(Arrays.asList(agent1missions, agent2missions));
		//need to pass in a junk parameter to overload constructor
		MapMissionAnalyserAgentNo analyser = new MapMissionAnalyserAgentNo(agentmissions, null);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMapMissionAnalyserAgentNoRegularTraversalGridQuadArrayListOfArrayListOfGPSCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	void testMapMissionAnalyserAgentNoArrayListOfMissionRegularTraversalGridQuad() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAgentPaths() {
		fail("Not yet implemented");
	}

	@Test
	void testSetAgentPaths() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTimeEstimateForAgent() {
		System.out.println()
	}

	@Test
	void testGetTotalTimeEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAverageTimeEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMaximumTimeEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMinimumTimeEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTimeReport() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDistanceEstimateForAgent() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMinimumDistanceEstimateSingleAgent() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMinimumDistanceEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMaximumDistanceEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAverageDistanceEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTotalDistanceEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDistanceReport() {
		fail("Not yet implemented");
	}

	@Test
	void testToStringHashMapOfIntegerDouble() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTotalBatteryEstimate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTraversalGrid() {
		fail("Not yet implemented");
	}

	@Test
	void testSetTraversalGrid() {
		fail("Not yet implemented");
	}

}
