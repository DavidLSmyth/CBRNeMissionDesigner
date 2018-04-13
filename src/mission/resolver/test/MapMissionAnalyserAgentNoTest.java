package mission.resolver.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	MapMissionAnalyserAgentNo analyser;
	ArrayList<ArrayList<GPSCoordinate>> agentmissions;
	HashMap<Integer, Double> velocities;
	
	@BeforeEach
	void setUp() throws Exception {
		agent1 = new AgentImpl("1");
		agent2 = new AgentImpl("2");
		agent3 = new AgentImpl("3");	
		
		agent1.setLocation(new ArrayList<Double>(Arrays.asList(53.28, -9.07, 20.0)));
		agent2.setLocation(new ArrayList<Double>(Arrays.asList(53.286, -9.0588, 20.0)));
		agent3.setLocation(new ArrayList<Double>(Arrays.asList(53.2798, -9.0565, 20.0)));

		agent1.setVehicle(new AeorumUAV("1", "127.0.0.1", "41451", 5));
		agent2.setVehicle(new AeorumUAV("2", "127.0.0.1", "41452", 5));
		agent3.setVehicle(new AeorumUAV("3", "127.0.0.1", "41453", 5));

		agent3.setVehicle(new AeorumUAV("3", "127.0.0.1", "41453"));
		
		GPSCoordinate mission1 = new GPSCoordinate(53.2779115341, -9.0597334278);
		GPSCoordinate mission2 = new GPSCoordinate(53.2812554869, -9.0627998557);
		ArrayList<GPSCoordinate> agent1missions = new ArrayList<GPSCoordinate>(Arrays.asList(mission1, mission2));
		
		GPSCoordinate mission3 = new GPSCoordinate(53.2789984548, -9.0564179892);
		GPSCoordinate mission4 = new GPSCoordinate(53.2779115341, -9.0597334278);
		GPSCoordinate mission5 = new GPSCoordinate(53.2823423226, -9.0594844171);
		ArrayList<GPSCoordinate> agent2missions = new ArrayList<GPSCoordinate>(Arrays.asList(mission3, mission4, mission5));
		
		velocities = new HashMap<Integer, Double>();
		velocities.put(0, 8.0);
		velocities.put(1, 8.0);
		
		agentmissions = new ArrayList<ArrayList<GPSCoordinate>>(Arrays.asList(agent1missions, agent2missions));
		//need to pass in a junk parameter to overload constructor
		analyser = new MapMissionAnalyserAgentNo(agentmissions, null);
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
	void testGetTimeEstimateForAgent() throws Exception {
		//distance 423
		assertEquals(141, analyser.getTimeEstimateForAgent(0, 3.0), 2);
		//distance 251 + 490/3
		assertEquals(247, analyser.getTimeEstimateForAgent(1, 3.0), 2);
	}

	@Test
	void testGetTotalTimeEstimate() throws Exception {
		//53+92.5
		assertEquals(146, analyser.getTotalTimeEstimate(velocities), 2);
	}

	@Test
	void testGetAverageTimeEstimate() throws Exception {
		assertEquals(146/2, analyser.getAverageTimeEstimate(velocities), 2);
	}

	@Test
	void testGetMaximumTimeEstimate() throws Exception {
		assertEquals(92.5, (double) analyser.getMaximumTimeEstimateAndCorrespondingAgent(velocities).getKey(), 2);
		assertEquals(1, (int) analyser.getMaximumTimeEstimateAndCorrespondingAgent(velocities).getValue());
	}

	@Test
	void testGetMinimumTimeEstimate() throws Exception {
		assertEquals(53, (double) analyser.getMinimumTimeEstimateAndCorrespondingAgent(velocities).getKey(), 2);
		assertEquals(0, (int) analyser.getMinimumTimeEstimateAndCorrespondingAgent(velocities).getValue());
	}

	@Test
	void testGetTimeReport() {
		System.out.println(analyser.getTimeReport(velocities));
	}

	@Test
	void testGetDistanceEstimateForAgent() throws Exception {
		assertEquals(423, analyser.getDistanceEstimateForAgent(0), 5);
		assertEquals(251 + 490, analyser.getDistanceEstimateForAgent(1), 5);
	}

	@Test
	void testGetMinimumDistanceEstimateSingleAgent() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMinimumDistanceEstimate() throws Exception {
		assertEquals(423, (double) analyser.getMinimumDistanceEstimateAndCorrespondingAgent().getKey(),5);
		assertEquals(0, (int) analyser.getMinimumDistanceEstimateAndCorrespondingAgent().getValue());
	}

	@Test
	void testGetMaximumDistanceEstimate() throws Exception {
		assertEquals(251+490, (double) analyser.getMaximumDistanceEstimateAndCorrespondingAgent().getKey(),5);
		assertEquals(1, (int) analyser.getMaximumDistanceEstimateAndCorrespondingAgent().getValue());
	}

	@Test
	void testGetAverageDistanceEstimate() throws Exception {
		assertEquals((251 + 490 + 423)/2, analyser.getAverageDistanceEstimate(),5);
	}

	@Test
	void testGetTotalDistanceEstimate() throws Exception {
		assertEquals((251 + 490 + 423), analyser.getTotalDistanceEstimate(),10);
	}

	@Test
	void testGetDistanceReport() {
		System.out.println(analyser.getDistanceReport());
		System.out.println(analyser.getReport(velocities));
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
