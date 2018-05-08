package mission.resolver.map.utils;

import java.util.ArrayList;

//Node holds data type T
public abstract class Node<T, EdgeSub extends Edge<?>> {
	T data;
	ArrayList<EdgeSub> edges;
	
	public Node(T data) {
		// TODO Auto-generated constructor stub
		setData(data);
	}
	
	public int getDegree() {
		return edges.size();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<?, ?> other = (Node<?, ?>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		return true;
	}

	public boolean addEdge(EdgeSub e) {
		if(e.getStart() == this || e.getEnd() ==this) {
			edges.add(e);
			return true;
		}
		else {
			return false;
		}
	}
	public boolean removeEdge(EdgeSub e) {
		return edges.remove(e);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
