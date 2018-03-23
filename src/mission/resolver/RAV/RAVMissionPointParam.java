package mission.resolver.RAV;

import java.util.Map;

public class RAVMissionPointParam {
	RAVParamType ParamType;
	//Need some way of enumerating all the different types of args
	//e.g. keep_yaw shoudln't be string (boolean) and verify_alt shoudln't be string (boolean)
	String arg;
	public RAVMissionPointParam(RAVParamType paramType, String arg) {
		setParamType(paramType);
		setArg(arg);
	}
	public RAVParamType getParamType() {
		return ParamType;
	}
	public void setParamType(RAVParamType paramType) {
		ParamType = paramType;
	}
	public String getArg() {
		return arg;
	}
	public void setArg(String arg) {
		this.arg = arg;
	}

}
