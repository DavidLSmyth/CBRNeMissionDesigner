package mission.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RAVMissionPointCommand {
	
	RAVCommandType commandType;
	//Need some way of enumerating all the different types of args
	//e.g. relative pitch (boolean) shouldn't be a string and pitch shouldn't be a string
	HashMap<String, String> args;
	
	public RAVMissionPointCommand(RAVCommandType commandType, HashMap<String, String> args) {
		// verify commandName is valid
		//given corresponding args, verify that args are compatible with commandName
		setCommandType(commandType);
		setArgs(args);
	}
	
	public RAVCommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(RAVCommandType commandType) {
		this.commandType = commandType;
	}

	public HashMap<String, String> getArgs() {
		return args;
	}

	public void setArgs(HashMap<String, String> args) {
		this.args = args;
	}

	private void parseArgs(HashMap<String, String> args) {
		
	}
}
