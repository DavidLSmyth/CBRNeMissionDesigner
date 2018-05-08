package mission.resolver.map.utils;

//A class which contains a start node and an end node
public abstract class Edge<NodeSub extends Node<?, ?>> {
	NodeSub start;
	NodeSub end;
	public Edge(NodeSub start, NodeSub end) {
		// TODO Auto-generated constructor stub
		setStart(start);
		setEnd(end);
	}
	
	public abstract double getCost();
	
	public Node<?, ?> getOtherNode(NodeSub n) {
		if(n.equals(start)) {
			return end;
		}
		else if(n.equals(end)) {
			return start;
		}
		else {
			System.out.println(n + " is not involved in Edge " + toString());
			return null;
		}
	}
	
	public String toString() {
		return "StartNode: " + start.toString() + ", EndNode: " + end.toString();
	}
	
	public NodeSub getStart() {
		return start;
	}
	public void setStart(NodeSub start) {
		this.start = start;
	}
	public NodeSub getEnd() {
		return end;
	}
	public void setEnd(NodeSub end) {
		this.end = end;
	}

}
