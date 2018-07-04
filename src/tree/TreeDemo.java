package tree;

import java.util.Arrays;

/** CT2109 NUI Galway: 
 * Class to demonstrate the use of Tree code. 
 * Based on code by Carrano & Savitch.
 * @author Michael Madden.
 */

public class TreeDemo 
{
	public static void main(String[] args) 
	{
		// Create a tree
		System.out.println("Constructing a test tree ...");
		Tree<String> testTree = new Tree<String>();
		createTree1(testTree);
		
		// Display some statistics about it
		System.out.println("\nSome statistics about the test tree ...");
		displayStats(testTree);
		
		// Perform in-order traversal
		System.out.println("\nIn-order traversal of the test tree, printing each node when visiting it ...");
		testTree.preOrderTraverse();
		testTree.find("j");
		
	} // end of main
	
	
	public static void createTree1(Tree<String> tree)
	{
		// To create a tree, build it up from the bottom:
		// create subtree for each leaf, then create subtrees linking them,
		// until we reach the root.
		
	  	System.out.println("\nCreating a treee that looks like this:\n");
	  	System.out.println("     A      ");
	    System.out.println("   /   \\   "); // '\\' is the escape character for backslash
	    System.out.println("  B     C   ");
	    System.out.println(" / \\   / \\");
	    System.out.println("D   E  F  G ");
	    System.out.println();
	    
	    Tree<String> cTree = new Tree<String>("c");
	    Tree<String> eTree = new Tree<String>("e");
	    Tree<String> jTree = new Tree<String>("j");
	    Tree<String> hTree = new Tree<String>("h");
	    Tree<String> aTree = new Tree<String>("a");
	    
	    Tree<String> dTree = new Tree<String>("d", Arrays.asList(cTree, eTree, jTree));
	    Tree<String> iTree = new Tree<String>("i", Arrays.asList(hTree));
	    
	    Tree<String> gTree = new Tree<String>("g", Arrays.asList(iTree));
	    
	    Tree<String> bTree = new Tree<String>("b", Arrays.asList(aTree, dTree));
	    
	    tree.setTree("f", Arrays.asList(bTree, gTree));
	    
	    
	    

	    // First the leaves
//		Tree<String> dTree = new Tree<String>("D");
//		// neater to use the constructor the initialisation ...
//		Tree<String> eTree = new Tree<String>("E");
//		Tree<String> fTree = new Tree<String>("F");
//		Tree<String> gTree = new Tree<String>("G");
//		
//		// Now the subtrees joining leaves:
//		Tree<String> bTree = new Tree<String>("B", Arrays.asList(dTree, eTree));
//		Tree<String> cTree = new Tree<String>("C", Arrays.asList(fTree, gTree));
//
//		// Now the root
//		tree.setTree("A", Arrays.asList(bTree, cTree));
		
		System.out.println("Does dtree have children: " + aTree.getRootNode().hasChildren());
	} // end createTree1
	
	public static void displayStats(Tree<String> tree)
	{
		if (tree.isEmpty())
			System.out.println("The tree is empty");
		else
			System.out.println("The tree is not empty");
		
		System.out.println("Root of tree is " + tree.getRootData());
		System.out.println("Height of tree is " + tree.getHeight());
		System.out.println("No. of nodes in tree is " + tree.getNumberOfNodes());
	} // end displayStats 
}
