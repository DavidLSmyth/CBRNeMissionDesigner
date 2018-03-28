package agent;

import java.util.Map;

import agent.component.ComponentState;
import agent.vehicle.VehicleState;

public class AgentState {

	private VehicleState vehicleState;
	private Map<String, ComponentState> componentStates;

	AgentState(VehicleState vehicleState, Map<String, ComponentState> componentStates) {
		this.vehicleState = vehicleState;
		this.componentStates = componentStates;
	}

	AgentState(VehicleState vehicleState) {
		this.vehicleState = vehicleState;
	}

	AgentState(Map<String, ComponentState> componentStates) {
		this.componentStates = componentStates;
	}

	public AgentState() {

	}

	public VehicleState getVehicleState() {
		return vehicleState;
	}

	public void setVehicleState(VehicleState vehicleState) {
		this.vehicleState = vehicleState;
	}

	public Map<String, ComponentState> getComponentStates() {
		return componentStates;
	}

	public void setComponentStates(Map<String, ComponentState> componentStates) {
		this.componentStates = componentStates;
	}

}
