package agent.vehicle.uav;

import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;

import agent.communications.messages.vehicle.VehicleCommandMessage;
import agent.vehicle.Telemetry;
import agent.vehicle.VehicleState;
import agent.vehicle.VehicleStateImpl;

public class AeorumUAV extends UAV {
	
	
	//final static Logger LOG = LoggerFactory.getLogger(AeorumUAV.class);
	private static final double DEFAULT_VELOCITY = 5;
	public double velocity;
	
	
	public AeorumUAV(String droneId, String ip, String port, double velocity) {
		super(droneId, ip, port);
		setVelocity(velocity);
	}
	
	public AeorumUAV(String droneId, String ip, String port) {
		super(droneId, ip, port);
		setVelocity(AeorumUAV.DEFAULT_VELOCITY);
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public double getVelocity() {
		return this.velocity;
	}

	@Override
	public Map<String, String[]> getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postMission(VehicleCommandMessage vehicleCommandMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String enableVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateMission(VehicleCommandMessage vehicleCommandMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String startMission(String missionId, int priority) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Telemetry getTelemetry() {
		// TODO Auto-generated method stub
		return null;
	}
}

//	@Override
//	public String ping() {
//		//RestTemplate restTemplate = restTemplateBuilder.build();
//
//		String response = "";
//		String pingRequestURL = url + "/ping";
//		//LOG.info("ping : " + pingRequestURL);
////		try {
////			response = restTemplate.getForObject(pingRequestURL, String.class);
////		} catch (RestClientException e) {
////
////			System.out.println(e.toString());
////		}
////		String methodReturn = this.id + " ping result: " + response;
////		LOG.info("return : " + methodReturn);
////		return methodReturn;
//		return "ping";
//	}
//
//	@Override
//	public String enableVehicle() {
////		RestTemplate restTemplate = restTemplateBuilder.build();
////
////		String response = "";
////		String missionLoadRequestURL = url + "/extAPI/enableDrone?droneId=" + this.id;
////		LOG.info("Enable Drone : " + missionLoadRequestURL);
////
////		try {
////			response = restTemplate.getForObject(missionLoadRequestURL, String.class);
////		} catch (RestClientException e) {
////			System.out.println(e.toString());
////		}
////		LOG.info("return : " + response);
////		return response;
//	}
//
//	@Override
//	public String postMission(VehicleCommandMessage vehicleCommandMessage) {
//		RestTemplate restTemplate = restTemplateBuilder.build();
//
//		String response = "";
//		String missionLoadRequestURL = url + "/extAPI/missionLoad";
//		LOG.info("Mission Load : " + missionLoadRequestURL);
//
//		try {
//			response = restTemplate.postForObject(missionLoadRequestURL, vehicleCommandMessage, String.class);
//		} catch (RestClientException e) {
//			System.out.println(e.toString());
//		}
//		LOG.info("return : " + response);
//		return response;
//	}
//
//	@Override
//	public String updateMission(VehicleCommandMessage vehicleCommandMessage) {
//		RestTemplate restTemplate = restTemplateBuilder.build();
//
//		String response = "";
//		String updateMissionRequestURL = url + "/extAPI/missionLoad";
//		LOG.info("Update Mission : " + updateMissionRequestURL + " - body: " + vehicleCommandMessage.toString());
//
//		try {
//			response = restTemplate.postForObject(updateMissionRequestURL, vehicleCommandMessage, String.class);
//		} catch (RestClientException e) {
//			System.out.println(e.toString());
//		}
//
//		LOG.info("return : " + response);
//		return response;
//	}
//
//	@Override
//	public String startMission(String missionId, int priority) {
//		RestTemplate restTemplate = restTemplateBuilder.build();
//
//		String response = "";
//		String startMissionRequestURL = url + "/extAPI/missionAutoStart?droneId=" + this.id + "&missionId="
//				+ missionId + "&finalAction=rtl&numLaps=1&currentMap=XXX&currentLap=1&priority=" + priority;
//		LOG.info("Start Mission : " + startMissionRequestURL + ", missionId " + missionId + ", Priority:" + priority);
//
//		try {
//			response = restTemplate
//					.getForObject(
//							url + "/extAPI/missionAutoStart?droneId=" + this.id + "&missionId=" + missionId
//									+ "&finalAction=rtl&numLaps=1&currentMp=0&currentLap=1&priority=" + priority,
//							String.class);
//		} catch (RestClientException e) {
//			System.out.println(e.toString());
//		}
//
//		LOG.info("return : " + response);
//		return response;
//	}
//
//	@Override
//	public Telemetry getTelemetry() {
//		RestTemplate restTemplate = restTemplateBuilder.build();
//
//		String telemetryRequestURL = url + "/extAPI/telemetry?droneId=" + this.id;
//		LOG.info("Telemetry Request : " + telemetryRequestURL);
//		UAVTelemetry uavTelemetry = restTemplate.getForObject(telemetryRequestURL, UAVTelemetry.class);
//	
//
//		LOG.info("return : " + uavTelemetry);
//
//		return uavTelemetry;
//	}
//
//	@Override
//	public VehicleState getState() {
//		return new VehicleStateImpl(getTelemetry(),getConfiguration());
//	}
//	
//	public Map<String, String[]> getConfiguration(){
////		TODO
//		return null;
//	}
//
////	public String exploreArea(Command exploreCommand) {
////
////		LOG.info("explore area : " + exploreCommand.toString());
////
////		String response = null;
////		MissionResolver missionResolver = new MissionResolver();
////		
////		// TODO
////		VehicleCommandMessage autonomousVehicleCommandMessage = null;
////
////			try {
////				autonomousVehicleCommandMessage = mapper.readValue(
////						missionResolver.exploreMissionForGrid(new Grid(), this.droneId), VehicleCommandMessage.class);
////			} catch (JsonParseException e) {
////				return e.toString();
////			} catch (JsonMappingException e) {
////				return e.toString();
////			} catch (IOException e) {
////				return e.toString();
////			}
////
////
////		LOG.info("autonomousVehicleCommandMessage : " + autonomousVehicleCommandMessage.toString());
////		
////		response = postMission(autonomousVehicleCommandMessage);
////
////		LOG.info("return : " + response);
////
////		return response;
////	}
//
//}
