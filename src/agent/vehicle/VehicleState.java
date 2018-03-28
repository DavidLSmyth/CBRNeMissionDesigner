package agent.vehicle;

import java.util.Map;

public interface VehicleState {

	public Telemetry getTelemetry();
	
	public Telemetry setTelemetry(Telemetry telemetry);

	public String[] getConfig(String key);

	public void setConfig(String key, String[] value);

	Map<String, String[]> getConfig();

}
