package work.assignment.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Communications_Hub.target.classes.agent.Agent;
import agent.AgentImpl;
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
		agent1 = AgentImpl.getInstance("1");
		agent2 = AgentImpl.getInstance("2");
		agent3 = AgentImpl.getInstance("3");
		agent4 = AgentImpl.getInstance("4");		
		
		NUIGcoord0 = new GPSCoordinate(53.2779115341, -9.0597334278);
		NUIGcoord1 = new GPSCoordinate(53.2812554869, -9.0627998557);
		NUIGcoord2 = new GPSCoordinate(53.2823423226, -9.0594844171);
		NUIGcoord3 = new GPSCoordinate(53.2789984548, -9.0564179892);
		
		workResolver = new WorkResolver(WorkType.MAP,
										new ArrayList<Agent>(Arrays.asList(agent1, agent2)), 
										new ArrayList<GPSCoordinate>(Arrays.asList(NUIGcoord0, NUIGcoord1, NUIGcoord2, NUIGcoord3)));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testWorkResolver() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMissions() {
		fail("Not yet implemented");
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
