package agent.vehicle;

import java.util.Map;

import agent.communications.messages.vehicle.VehicleCommandMessage;

public interface Vehicle {

	public String getID();

	public void setID(String id);

	public VehicleType getVehicleType();
	
	public String getURL();

	public String postMission(VehicleCommandMessage vehicleCommandMessage);

	public String enableVehicle();

	public String updateMission(VehicleCommandMessage vehicleCommandMessage);

	public String startMission(String missionId, int priority);

	public Telemetry getTelemetry();
	
	public Map<String, String[]> getConfiguration();
	
	public VehicleState getState();

	public String ping();
	
}
