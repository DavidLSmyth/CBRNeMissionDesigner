package mission.resolver;
import java.util.ArrayList;
import java.util.HashMap;
import work.assignment.grid.GPSCoordinate;
import agent.Agent;

public interface MapMissionStrategy {
	//An interface that defines a family of algorithms, 
	//encapsulates each one and makes them interchangeable
	//Lets the algorithm vary independently from the 
	//clients that use it
	
	//get the sequence of GPS points necessary to map the environment given a list of 
	//agents
	public HashMap<Agent, ArrayList<GPSCoordinate>> getAgentRoutesForMapping() throws Exception;
	
	//Need to write other method signatures that will need to be implemented...
	
}
