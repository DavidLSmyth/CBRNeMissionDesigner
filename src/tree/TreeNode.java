package tree;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeNode<T> implements NodeInterface<T> {

	private T data;
	// a list of subtrees
	private List<TreeNode<T>> children;

	public TreeNode() {
		this(null); // call next constructor
	} // end default constructor

	public TreeNode(T dataPortion) {
		this(dataPortion, null); // call next constructor
	} // end constructor

	public TreeNode(T dataPortion, List<TreeNode<T>> subTrees) {
		this.data = dataPortion;
		this.children = subTrees;
	} // end constructor

	public T getData() {
		return data;
	} // end getData

	public List<TreeNode<T>> getChildren() {
		return children;
	} // end getLeftChild

	public boolean isLeaf() {
		return (children == null);
	} // end isLeaf

	// 26.06
	public String toString() {
		return getData().toString();
	}

	// 26.11
	public int getHeight() {
		return getHeight(this); // call private getHeight
	} // end getHeight

	// 26.11
	private int getHeight(TreeNode<T> node) {
		int height = 0;
		List<Integer> heights = new ArrayList<Integer>();
		if(node.hasChildren()) {
			for(TreeNode<T> child: node.getChildren()) {
				System.out.println(child);
				heights.add(getHeight(child));
			}
		}
		System.out.println("subTree heights: " + heights);
		if (node != null && heights.size() != 0)
			height = 1 + Collections.max(heights);
		System.out.println("Height of node " + node + " is " + height);
		//return 0 if a leaf, else height
		return height;
	} // end getHeight

	// 26.11
	public int getNumberOfNodes() {
		int noChildren = 0;
		if(children != null) {
			for(TreeNode<T> child: children) {
				noChildren += child.getNumberOfNodes();
			}
		}
		return 1 + noChildren;
	} // end getNumberOfNodes

	@Override
	public void setData(Object newData) {
		this.data = (T) newData;
	}


	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		if(children != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addChild(TreeNode<T> child) {
		if(child != null) {
			children.add(child);
		}
	}

	@Override
	public TreeNode<T> copy() {
		TreeNode<T> newRoot = new TreeNode<T>(data);
		if (hasChildren()) {
			newRoot.children = new ArrayList<TreeNode<T>>();
			for(TreeNode<T> child: getChildren()) {
				newRoot.children.add(child.copy());
			}
		}
		return newRoot;
	} // end copy

	@Override
	public void setChildren(List<TreeNode<T>> children) {
		this.children = children;
		
	}

}
