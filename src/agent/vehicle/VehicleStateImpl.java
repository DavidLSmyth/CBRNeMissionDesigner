package agent.vehicle;

import java.util.Map;

public class VehicleStateImpl implements VehicleState{

	Telemetry telemetry;
	Map<String, String[]> configurationMap;
	
	
	public VehicleStateImpl(Telemetry telemetry, Map<String, String[]> configurationMap) {
		this.telemetry=telemetry;
		this.configurationMap=configurationMap;
	}
	
	@Override
	public Telemetry getTelemetry() {
		return this.telemetry;
	}

	@Override
	public String[] getConfig(String key) {
		return configurationMap.get(key);
	}
	
	@Override
	public Map<String, String[]> getConfig() {
		return configurationMap;
	}

	@Override
	public Telemetry setTelemetry(Telemetry telemetry) {
		this.telemetry=telemetry;
		return null;
	}

	@Override
	public void setConfig(String key, String[] value) {
		configurationMap.put(key, value);
		
	}


}
