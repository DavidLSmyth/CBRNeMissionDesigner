package mission.resolver.map.utils;

import java.util.ArrayList;

public class TreeNode<T, EdgeSub extends Edge> extends Node<T, EdgeSub> {
	
	protected TreeNode<T, EdgeSub> parent;
	protected ArrayList<TreeNode<T, EdgeSub>> children;
	
	public TreeNode(T data) {
		super(data);
		// TODO Auto-generated constructor stub
	}
	
	//add to children by adding edges
	public boolean addEdge(EdgeSub e) {
		if(e.getStart() == this || e.getEnd() ==this) {
			edges.add(e);
			children.add((TreeNode<T, EdgeSub>) e.getOtherNode(this));
			return true;
		}
		else {
			return false;
		}
	}
	public boolean removeEdge(EdgeSub e) {
		return edges.remove(e);
	}
}
