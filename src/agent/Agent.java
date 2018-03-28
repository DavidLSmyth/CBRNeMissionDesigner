package agent;

import java.util.ArrayList;
import java.util.Map;

import agent.component.Component;
import agent.component.ComponentManager;
import agent.component.ComponentType;
import agent.vehicle.Vehicle;

public interface Agent {
	
	
	public AgentState getCurrentState();

	public void setCurrentState(AgentState currentState);

	public String getId();

	public void setId(String id);
	
	public Component addComponent(Component newComponenet);
	
	public Component addComponent(String id, ComponentType componentType, String subType, String ip, String port);
	
	public Component getComponent(String componenetId);

	public Component removeComponent(String componentId);
	
	public Map<String, Component> getComponentMap();
	
	public Vehicle setVehicle(Vehicle vehicle);
	
	public Vehicle getVehicle();

	public ComponentManager getComponentManager();

	public void setLocation(ArrayList<Double> location);
	public ArrayList<Double> getLocation();


}
