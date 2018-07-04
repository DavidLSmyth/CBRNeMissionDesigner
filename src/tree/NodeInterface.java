package tree;

import java.util.List;

public interface NodeInterface<T> {
	 public T getData();
	  
	  /** Task: Sets the data portion of the node.
	   *  @param newData  the data object */
	  public void setData(T newData);
	  
	  /** Task: Retrieves the left child of the node.
	   *  @return the node that is this node’s left child */
	  public List<TreeNode<T>> getChildren();
	  
	  /** Task: Sets the node’s right child to a given node.
	   *  @param rightChild  a node that will be the right child */
	  public void setChildren(List<TreeNode<T>> children);
	  
	  /** Task: Detects whether the node has a left child.
	   *  @return true if the node has a left child */
	  public boolean hasChildren();

	  /** Task: Detects whether the node is a leaf.
	   *  @return true if the node is a leaf */
	  public boolean isLeaf();
	  
	  /** Task: Counts the nodes in the subtree rooted at this node.
	   *  @return the number of nodes in the subtree rooted at this node */
	  public int getNumberOfNodes();
	  
		/** Task: Computes the height of the subtree rooted at this node.
		   *  @return the height of the subtree rooted at this node */
		public int getHeight();

	  /** Task: Copies the subtree rooted at this node.
	   *  @return the root of a copy of the subtree rooted at this node */
	  public NodeInterface<T> copy();
}
