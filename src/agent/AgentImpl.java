package agent;

import java.util.ArrayList;
import java.util.Map;

import agent.component.Component;
import agent.component.ComponentManager;
import agent.component.ComponentManagerImpl;
import agent.component.ComponentType;
import agent.vehicle.Vehicle;
import work.assignment.grid.GPSCoordinate;

public class AgentImpl implements Agent {
	private AgentState currentState;
	private String Id;
	private Vehicle vehicle;
	private ComponentManager componentManager = new ComponentManagerImpl();
	private static Agent agent;
	ArrayList<Double> location;

	public AgentImpl(String id) {
		this.Id = id;
	}

	public static Agent getInstance() {
		return agent;
	}
	
	public String toString() {
		return "Agent: " + Id;
	}
	
	@Override
	public ArrayList<Double> getLocation() {
		return this.location;
	}
	@Override
	public void setLocation(ArrayList<Double> location) {
		this.location = location;
	}
	
	@Override
	public AgentState getCurrentState() {
		updateCurrentState();
		return currentState;
	}

	@Override
	public void setCurrentState(AgentState currentState) {
		this.currentState = currentState;
	}

	@Override
	public String getId() {
		return Id;
	}

	@Override
	public void setId(String id) {
		Id = id;
	}

	@Override
	public Component addComponent(Component newComponenet) {
		return componentManager.addComponent(newComponenet);

	}
	
	@Override
	public Component addComponent(String id, ComponentType componentType, String subType, String ip, String port) {
		return componentManager.createAndAddComponent( id,  componentType,  subType,  ip,  port);

	}
	

	@Override
	public Component getComponent(String componentId) {
		return componentManager.getComponent(componentId);
	}
	

	@Override
	public Component removeComponent(String componentId) {
		return componentManager.removeComponent(componentId);
	}

	@Override
	public Map<String, Component> getComponentMap() {
		return this.componentManager.getComponentMap();
	}

	@Override
	public ComponentManager getComponentManager() {
		return this.componentManager;
	}

	@Override
	public Vehicle setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		return vehicle;
	}

	@Override
	public Vehicle getVehicle() {
		return this.vehicle;
	}

	private void updateCurrentState() {
		if (vehicle != null && componentManager.getComponentMap().size() != 0) {
			currentState = new AgentState(vehicle.getState(), componentManager.getCurrentStateList());
		} else if (vehicle != null) {
			currentState = new AgentState(vehicle.getState());
		} else if (componentManager.getComponentMap().size() != 0) {
			currentState = new AgentState(componentManager.getCurrentStateList());
		} else {
			currentState = new AgentState();
		}

	}

	


}
