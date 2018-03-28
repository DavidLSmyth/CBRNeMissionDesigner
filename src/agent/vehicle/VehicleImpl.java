package agent.vehicle;

//import org.springframework.boot.web.client.RestTemplateBuilder;

//import com.fasterxml.jackson.databind.ObjectMapper;

import agent.communications.messages.vehicle.VehicleCommandMessage;

public abstract class VehicleImpl implements Vehicle {

	protected VehicleType vehicleType;

	protected String id;
	
	protected String url;
	//protected RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
	//protected ObjectMapper mapper = new ObjectMapper();

	
	protected VehicleImpl(String id,String ip, String port,VehicleType vehicleType){
		this.id=id;
		this.url = "http://" + ip + ":" + port;
		this.vehicleType=vehicleType;
	}

	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public void setID(String id) {
		this.id=id;

	}

	@Override
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	@Override
	public String getURL(){
		return this.url;
	}
	
	@Override
	public abstract String postMission(VehicleCommandMessage vehicleCommandMessage);

	@Override
	public abstract String enableVehicle();

	@Override
	public abstract String updateMission(VehicleCommandMessage vehicleCommandMessage);

	@Override
	public abstract String startMission(String missionId, int priority);

	@Override
	public abstract Telemetry getTelemetry();
}
