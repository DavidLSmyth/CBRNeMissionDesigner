package mission.resolver;

import java.util.ArrayList;
import java.util.Map;

import work.assignment.grid.GPSCoordinate;

public class AeorumMissionJSONHelper {

	public AeorumMissionJSONHelper() {
		// TODO Auto-generated constructor stub
	}
	protected static String wrapGeneric(String param, String data) {
		return ("\"" + param + "\": " + data +  ",\n");
	}
	protected static String wrapGenericLastTerm(String param, String data) {
		return ("\"" + param + "\": " + data +  "\n");
	}
	
	protected static String wrapBlock(String blockName, String blockBody) {
		return ("\"" + blockName + "\": {\n" + blockBody + "}\n");
	}
	
	protected static String wrapString(String param, String data) {
		return wrapGeneric(param, "\"" + data + "\"");
	}
	protected static String wrapStringLastTerm(String param, String data) {
		return wrapGenericLastTerm(param, "\"" + data + "\"");
	}
	
	protected static String wrapDouble(String param, double data) {
		return wrapGeneric(param, String.valueOf(data));
	}
	protected static String wrapDoubleLastTerm(String param, double data) {
		return wrapGenericLastTerm(param, String.valueOf(data));
	}
	protected static String wrapBool(String param, boolean data){
		return wrapGeneric(param, String.valueOf(data));
	}
	protected static String wrapBoolLastTerm(String param, boolean data){
		return wrapGeneric(param, String.valueOf(data));
	}
	public static String getAeorumMissionJSON(AeorumRAVMission mission) {
		StringBuilder b = new StringBuilder();
		b.append("{\n");
		b.append(wrapGeneric("name", mission.getMissionName()));
		b.append(wrapGeneric("desc", mission.getMissionDescription()));
		b.append(wrapGeneric("type", "AUTOMATIC"));
		b.append(wrapGeneric("subtype", "NORMAL"));
		b.append(wrapGeneric("finalAction", mission.getFinalAction().toString()));
		b.append(wrapGeneric("numLaps", "1"));
		b.append(wrapGeneric("mission_points", wrapMissionPoints(mission.getMissionPoints())));
		b.append("}");
		return b.toString();
	}
	
	public static String wrapGPS(GPSCoordinate coord) {
		StringBuilder b = new StringBuilder();
		b.append("\"gps\": {\n");
		b.append(wrapDouble("lat", coord.getLat()));
		b.append(wrapDouble("lng", coord.getLng()));
		b.append(wrapDoubleLastTerm("alt", coord.getAlt()));
		b.append("},\n");
		return b.toString();
	}
	public static String wrapParams(ArrayList<RAVMissionPointParam> RAVMissionPointParams) {
		StringBuilder b = new StringBuilder();		
		int counter = 0;
		for (RAVMissionPointParam RAVMissionPointParam: RAVMissionPointParams) {
			if(counter < RAVMissionPointParams.size()) b.append(wrapGeneric((RAVMissionPointParam.getParamType()).name(), String.valueOf(RAVMissionPointParam.arg)));
			else b.append(wrapGenericLastTerm((RAVMissionPointParam.getParamType()).name(), String.valueOf(RAVMissionPointParam.arg)));
		}
		b.append(",\n");
		return b.toString();
	}
	
	public static String wrapCommands(ArrayList<RAVMissionPointCommand> commands) {
		StringBuilder b = new StringBuilder();
		b.append("\"commands\": [");
		int counter = 1;
		for(RAVMissionPointCommand command: commands) {
			b.append(wrapCommand(command));
			if(counter < commands.size()) b.append(",");
		}
		b.append("]");
		return b.toString();
	}
	
	public static String wrapCommand(RAVMissionPointCommand missionPointCommand) {
		StringBuilder b = new StringBuilder();
		b.append(wrapGeneric("{\ncommand", missionPointCommand.getCommandType().name()));
		Map<String, String> missionPointArgs = missionPointCommand.getArgs();
		b.append("\"args\": {\n");
		int counter = 1;
		for (String entry: missionPointArgs.keySet()) {
			if(counter < missionPointArgs.size()) b.append(wrapGeneric(entry, missionPointArgs.get(entry)));
			else b.append(wrapGenericLastTerm(entry, missionPointArgs.get(entry)));
		}
		b.append("}\n");
		return b.toString();
	}
	
	public static String wrapMissionPoints(ArrayList<MissionPoint> missionPoints) {
		StringBuilder b = new StringBuilder();	
		b.append("\"mission_points\": [{\n");
		for(MissionPoint p: missionPoints) {
			b.append(wrapGeneric("name", p.getName()));
			b.append(wrapGPS(p.getGpsCoord()));
			b.append(wrapParams(p.getParams()));
			b.append(wrapCommands(p.getCommands()));
		}
		return b.toString();
	}

}
