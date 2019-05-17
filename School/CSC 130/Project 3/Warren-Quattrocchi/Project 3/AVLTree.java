//Warren Quattrocchi
//CSC 130
//AVLTree class
public class AVLTree <E extends Comparable<? super E>> extends BinarySearchTree<E>
{
	private int height;
	
	public AVLTree()
	{

	}

	public BSTNode balance(BSTNode node)
	{
		if(node == null)
			return null;
		if(getHeight(node.left)- getHeight(node.right) > 1)
		{
			if(getHeight(node.left.left) >= getHeight(node.left.right))
				node = singleRightRotation(node);
			else
			{	
				node = doubleLeftRightRotation(node);
			}
		}
		else if(getHeight(node.right) - getHeight(node.left)   > 1)
		{
			if(getHeight(node.right.right) >= getHeight(node.right.left))
				node = singleLeftRotation(node);
			else
			{
				node = doubleRightLeftRotation(node);
			}
		}
		return node;
	}
   
   //call inccount from bst class
   //balance the resulting tree
	public void incCount(E data)
	{
		super.incCount(data);
      overallRoot = balance(overallRoot);
	}
   
	private BSTNode singleRightRotation(BSTNode k2)
	{
		BSTNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}
	
	private BSTNode singleLeftRotation(BSTNode k1)
	{
		BSTNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		return k2;
	}
   
	private BSTNode doubleLeftRightRotation(BSTNode k3)
	{
		k3.left = singleLeftRotation(k3.left);
		return singleRightRotation(k3);
	}
	
	private BSTNode doubleRightLeftRotation(BSTNode k1)
	{
		k1.right = singleRightRotation(k1.right);
		return singleLeftRotation(k1);
	}
	public int getHeight()
	{
		return height;
	}
   
	int getHeight(BSTNode node) 
	{
		int right;
		int left;
		if(node == null) 
			return 0;
		else 
      {
			left = getHeight(node.left);
			right = getHeight(node.right);
			if(right > left) 
				return right+1;
			else 
				return left+1;
		}
	}
}	

