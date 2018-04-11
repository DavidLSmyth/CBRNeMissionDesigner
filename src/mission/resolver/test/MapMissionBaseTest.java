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
import mission.resolver.map.MapMissionBase;
import mission.resolver.map.MapMissionStrategyGreedy;
import work.assignment.CostType;
import work.assignment.environmentalfactors.WindFactor;
import work.assignment.grid.GPSCoordinate;

class MapMissionBaseTest {

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
	
	CostType costType;
	
	@BeforeEach
	void setUp() throws Exception {
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
        
        windFactor = new WindFactor(0, -5);
        
        costType = CostType.TOTALDISTANCE;
        
        mapMissionBase = new MapMissionStrategyGreedy(new ArrayList<Agent> (Arrays.asList(agent1, agent2)),
        		new ArrayList<GPSCoordinate> (Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)),
        		 costType);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMapMissionBaseArrayListOfAgentArrayListOfGPSCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	void testMapMissionBaseArrayListOfAgentArrayListOfGPSCoordinateWindFactor() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAgentPaths() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNearestAvailableCoordArrayListOfGPSCoordinateGPSCoordinateWindFactor() throws Exception {
		System.out.println(mapMissionBase.getAvailableCoordOfLeastCost(new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord1,NUIGcoord2,NUIGcoord3)), NUIGcoord0, agent1, costType));
		assertEquals(NUIGcoord3, mapMissionBase.getAvailableCoordOfLeastCost(new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord1,NUIGcoord2,NUIGcoord3)), NUIGcoord0, agent1, CostType.MAXTIME));
	}

	@Test
	void testGetNearestAvailableCoordArrayListOfGPSCoordinateGPSCoordinate() throws Exception {
		assertEquals(NUIGcoord1, mapMissionBase.getAvailableCoordOfLeastCost(new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord1,NUIGcoord2,NUIGcoord3)), NUIGcoord0, agent1, costType));
		
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
	void testGetGrid() {
		fail("Not yet implemented");
	}

	@Test
	void testSetGrid() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAgentRoutesForMapping() {
		fail("Not yet implemented");
	}

	@Test
	void testCalculateMapEnvironmentPaths() {
		fail("Not yet implemented");
	}

}
