package mission.resolver.map.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import work.assignment.grid.GPSCoordinate;

public class MapTree extends MapGraph {
	TreeNode root;
	protected HashMap<GPSCoordinate, TreeNode> pointNodeMap;
	protected HashSet<TreeNode> edges;
	public MapTree(TreeNode root) {
		// TODO Auto-generated constructor stub
		this.root = root;
	}
//	
//	public int getHeight() {
//		return root.getHeight();
//	}
	
	public boolean addVertex(GPSCoordinate location, TreeNode parent)
	{
		if (location == null) {
			return false;
		}
		TreeNode n = pointNodeMap.get(location);
		if (n == null) {
			n = new TreeNode(location);
			parent.addChild(n);
			pointNodeMap.put(location, n);
			return true;
		}
		else {
			return false;
		}
	}
	
//	protected List<TreeNode> getChildren(TreeNode node){
//		return node.getChildren();
//	}

}
