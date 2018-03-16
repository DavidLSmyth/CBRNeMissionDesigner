package mission.resolver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mission.resolver.AeorumMissionJSONHelper;
import mission.resolver.AeorumRAVMission;
import mission.resolver.FinalAction;
import mission.resolver.RAVCommandType;
import mission.resolver.RAVMissionPoint;
import mission.resolver.RAVMissionPointCommand;
import mission.resolver.RAVMissionPointParam;
import mission.resolver.RAVParamType;
import work.assignment.grid.GPSCoordinate;

class JSONHelperTest {

	@BeforeEach
	void setUp() throws Exception {
		//(GPSCoordinate gpsCoord, ArrayList<RAVMissionPointParam> params, ArrayList<RAVMissionPointCommand> commands, String name
		GPSCoordinate coorda = new GPSCoordinate(36.7473546059581,-4.55352991819382, Double.valueOf(70.0));
		
		ArrayList<RAVMissionPointParam> paramsa = new ArrayList<RAVMissionPointParam>(Arrays.asList(
				new RAVMissionPointParam(RAVParamType.KEEP_YAW, "false"),
				new RAVMissionPointParam(RAVParamType.ALT_TYPE, "absolute"),
				new RAVMissionPointParam(RAVParamType.VERIFY_ALT, "false")));
		
		HashMap<String, String> commandsa1args = new HashMap<String, String>();
		commandsa1args.put("angle", "90");
		commandsa1args.put("relative", "true");
		RAVMissionPointCommand commanda1 = new RAVMissionPointCommand(RAVCommandType.TURN, commandsa1args);

		HashMap<String, String> commandsa2args = new HashMap<String, String>();
		commandsa2args.put("pitch", "-45");
		commandsa2args.put("relative_pitch", "false");
		commandsa2args.put("yaw", "0");
		commandsa2args.put("relative_yaw", "false");
		RAVMissionPointCommand commanda2 = new RAVMissionPointCommand(RAVCommandType.GIMBAL, commandsa2args);
		
		GPSCoordinate coordb = new GPSCoordinate(36.7474029630395,-4.55367207527161, Double.valueOf(70.0));
		
		ArrayList<RAVMissionPointParam> paramsb = new ArrayList<RAVMissionPointParam>(Arrays.asList(
				new RAVMissionPointParam(RAVParamType.KEEP_YAW, "false"),
				new RAVMissionPointParam(RAVParamType.ALT_TYPE, "absolute"),
				new RAVMissionPointParam(RAVParamType.VERIFY_ALT, "false")));
		
		HashMap<String, String> commandsb1 = new HashMap<String, String>();
		//(GPSCoordinate gpsCoord, ArrayList<RAVMissionPointParam> params, ArrayList<RAVMissionPointCommand> commands, String name
		RAVMissionPoint p1 = new RAVMissionPoint(coorda, paramsa,
				new ArrayList<RAVMissionPointCommand>(Arrays.asList(commanda1, commanda2)), 
				"MP1");
		RAVMissionPoint p2 = new RAVMissionPoint(coordb, paramsb,
				new ArrayList<RAVMissionPointCommand>(), 
				"MP1");
		AeorumRAVMission test = new AeorumRAVMission("SimpleMission", "Test","Auto","Normal",FinalAction.RTL,1,new ArrayList<MissionPoint>(Arrays.asList(p1,p2)));  
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testJSONHelper() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapGeneric() {
		assertEquals("\"name\": \"SimpleMission\",\n"), AeorumMissionJSONHelper.)
	}

	@Test
	void testWrapGenericLastTerm() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapBlock() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapDouble() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapDoubleLastTerm() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapBool() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapBoolLastTerm() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapGPS() throws Exception {
		GPSCoordinate coord = new GPSCoordinate(36.7473546059581, -4.55352991819382, Double.valueOf(70));
		assertEquals("\"gps\": {\n\"lat\": 36.7473546059581,\n\"lng\": -4.55352991819382,\n\"alt\": 70.0\n},\n", AeorumMissionJSONHelper.wrapGPS(coord));
	}

	@Test
	void testWrapParams() {
		
	}

	@Test
	void testWrapCommands() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapCommand() {
		fail("Not yet implemented");
	}

	@Test
	void testWrapMissionPoints() {
		fail("Not yet implemented");
	}

}
