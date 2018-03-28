package agent.vehicle.uav;

import agent.vehicle.VehicleImpl;
import agent.vehicle.VehicleType;

public abstract class UAV extends VehicleImpl {

	UAV(String id, String ip, String port) {
		super(id,  ip, port, VehicleType.AIR_VEHICLE);
	}


}
