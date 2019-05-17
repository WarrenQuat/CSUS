import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;

public class TreeDriver {

	public static void main(String[] args)
	{
		//print binary search tree
		BST();
		System.out.println("--------------------Press enter to generate AVL tree-------------------");
		Scanner input = new Scanner(System.in);
		input.nextLine();
		try {
			//print avl tree, catch exception if tree is not balanced
			AVLTree();
		}catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		System.out.println("Press enter to time the tree insertions");
		input.nextLine();
		//time avl and bst
		TreeTimed();
	}
	
	public static void BST()
	{
		Scanner input = new Scanner(System.in);
		Random r = new Random();
		BinarySearchTree<Integer> tree= new BinarySearchTree<Integer>();
		
		while(tree.getHeight() < 5)
		{
			int  n = 10 + r.nextInt(89);
			tree.insert(n);
		}	
		TreePrinter treePrinter = new TreePrinter(tree);
		treePrinter.print("Printing Binary search Tree");
		while(!tree.isEmpty())
		{	
			System.out.println("Press enter to delete next root");
			input.nextLine();
			System.out.println("deleting " + tree.getRoot().getData() );	
			tree.delete(tree.getRoot().getData());
			treePrinter = new TreePrinter(tree);
			treePrinter.print("Printing Binary search Tree");	
		}	
	}
	
	public static void AVLTree() throws Exception
	{	
		Scanner input = new Scanner(System.in);
		//create empty AVL tree
		AVLTree<Integer> tree= new AVLTree<Integer>(true);
		ArrayList<Integer> randoms = new ArrayList<Integer>();
		TreePrinter treePrinter = new TreePrinter(tree);
		//fill up an arraylist with values from 1- 100
		for(int i = 0; i<= 100; i++)
		{
			randoms.add (i+1);	
		}
		//shuffle these values to get UNIQUE random values
		Collections.shuffle(randoms);
		//loop for 35 unique values to put in the AVL tree
		for(int i = 0; i < 35; i++)
		{
			System.out.println("Press enter to add next value");
			input.nextLine();
			System.out.println("Inserting " + randoms.get(i));
			tree.insert(randoms.get(i));
			treePrinter = new TreePrinter(tree);
			treePrinter.print("Printing updated AVL tree");
			if(!tree.checkBalance(tree.getRoot()))
				throw new Exception("Tree is not balanced");	
		}
		//print AVL tree	
		treePrinter.print("Printing Binary search Tree");
		while(!tree.isEmpty())
		{	
			if(!tree.checkBalance(tree.getRoot()))
				throw new Exception("tree is not balanced");
			System.out.println("Press enter to delete next root");
			input.nextLine();
			System.out.println("deleting " + tree.getRoot().getData() );	
			tree.delete(tree.getRoot().getData());
			treePrinter = new TreePrinter(tree);
			treePrinter.print("Printing updated AVL tree");					
		}	
	}
	
	public static void TreeTimed()
	{
      long startTime,endTime;
		Random r = new Random();
		ArrayList<Integer> values = new ArrayList<Integer>();
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		AVLTree<Integer> avl = new AVLTree<Integer>(false);
		
		 
		 for(int i = 0; i< 10000; i++)	
		 {
			values.add(r.nextInt(1000));
		 }	
		 
		 startTime = System.currentTimeMillis();
		 for(int i = 0; i<values.size(); i++)
		 {
			 bst.insert(values.get(i));
		 }
		 endTime = System.currentTimeMillis();
		System.out.println("BST insertion took " + (endTime - startTime) + " milliseconds");
		
		startTime = System.currentTimeMillis();
		 for(int i = 0; i<values.size(); i++)
		 {
			 avl.insert(values.get(i));
		 }	
		 endTime = System.currentTimeMillis();
		 System.out.println("AVL insertion took " + (endTime - startTime) + " milliseconds");
    }
}
