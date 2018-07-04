package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * A class that implements the ADT binary tree.
 * 
 * @author Frank M. Carrano
 * @version 2.0
 */
public class Tree<T> implements TreeInterface<T>
{
	private TreeNode<T> root;
	private List<T> preOrderTraversal;

	public Tree()
	{
		root = null;
	} // end default constructor

	public Tree(T rootData)
	{
		root = new TreeNode<T>(rootData);
	} // end constructor

	public Tree(T rootData, List<Tree<T>> subTrees)
	{
		privateSetTree(rootData, subTrees);
	} // end constructor

	public void setTree(T rootData)
	{
		root = new TreeNode<T>(rootData);
	} // end setTree

	public void setTree(T rootData, List<Tree<T>> subTrees)
	{
		privateSetTree(rootData, subTrees);
	} // end setTree

	// 26.08
	private void privateSetTree(T rootData, List<Tree<T>> subTrees)
	{
		root = new TreeNode<T>(rootData);
		List<TreeNode<T>> treesToAdd = new ArrayList<TreeNode<T>>();
		for(Tree<T> tree: subTrees) {
			if(tree.root != null && !tree.isEmpty()) {
				treesToAdd.add(tree.root.copy());
			}
		}
		root.setChildren(treesToAdd);
	} // end privateSetTree


	private TreeNode<T> copyNodes() // not essential
	{
		return (TreeNode<T>) root.copy();
	} // end copyNodes

	// 26.09
	public T getRootData()
	{
		T rootData = null;

		if (root != null)
			rootData = root.getData();

		return rootData;
	} // end getRootData

	// 26.09
	public boolean isEmpty()
	{
		return root == null;
	} // end isEmpty

	// 26.09
	public void clear()
	{
		root = null;
	} // end clear

	// 26.09
	protected void setRootData(T rootData)
	{
		root.setData(rootData);
	} // end setRootData

	// 26.09
	protected void setRootNode(TreeNode<T> rootNode)
	{
		root = rootNode;
	} // end setRootNode

	// 26.09
	protected TreeNode<T> getRootNode()
	{
		return root;
	} // end getRootNode

	// 26.10
	public int getHeight()
	{
		return root.getHeight();
	} // end getHeight


	// 26.10
	public int getNumberOfNodes()
	{
		return root.getNumberOfNodes();
	} // end getNumberOfNodes

	// 26.12
	/**
	 * 
	 * @return A list which contains the order in which nodes should be visited for a pre-order traversal
	 */
	public List<T> preOrderTraverse() 
	{
		preOrderTraversal = new ArrayList<T>();
		preOrderTraverse(root);
		System.out.println("Results of pre-order traversal: " + preOrderTraversal);
		return preOrderTraversal;
	} // end inorderTraverse

	private void preOrderTraverse(TreeNode<T> node) 
	{
		if (node != null)
		{	
			preOrderTraversal.add(node.getData());
			if(node.hasChildren()) {
				for(TreeNode<T> n: node.getChildren()) {
					preOrderTraverse(n);
				}
			}
		} // end if
	} // end inorderTraverse
	
	public TreeNode<T> find(T searchData) {
		System.out.println("Searching for " + searchData);
		TreeNode<T> findNode = find(root, searchData);
		System.out.println("Found: " + findNode);
		return findNode;
	}
	private TreeNode<T> find(TreeNode<T> searchNode, T searchData){
		Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		stack.push(searchNode);
		TreeNode<T> current; 
		while(!stack.isEmpty()) {
			current = stack.pop();
			System.out.println("Visited: " + current);
			if(current.getData().equals(searchData)) {
				return current;
			}
			else {
				if(current.hasChildren()) {
					for(TreeNode<T> node: current.getChildren()) {
						stack.push(node);
					}
				}
			}
		}
		return null;
	}
	
	

} // end BinaryTree