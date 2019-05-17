public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T>
{
	
	private boolean printRot;
	private boolean dblRot;
	public AVLTree(boolean printRot)
	{
		this.printRot = printRot;
		dblRot = false;
	}
	
	public boolean checkBalance(BinaryNode<T> node) throws Exception
	{
		if(node == null)
			return true;
		 else
	     {
	            int leftHeight = getHeight(node.getLeft());
	            int rightHeight = getHeight(node.getRight());
	            return Math.abs(leftHeight - rightHeight) <= 1;
	     }
	}
	public void insert(T data)
	{
		root = insert(data, root);
	}
	public BinaryNode<T> insert(T data,BinaryNode<T> node)
	{
		return balance(super.insert(data,node));
	}
	
	public void delete(T data)
	{
		root =delete(data, root);
	}
	public BinaryNode<T> delete(T data, BinaryNode<T> node)
	{
		return balance(super.delete(data, node));
	}
	
	public BinaryNode<T> balance(BinaryNode<T> node)
	{
		if(node == null)
			return null;
		if(getHeight(node.getLeft()) - getHeight(node.getRight()) > 1)
		{
			if(getHeight(node.getLeft().getLeft()) >= getHeight(node.getLeft().getRight()))
				node = singleRightRotation(node);
			else
			{	
				dblRot = true;
				node = doubleLeftRightRotation(node);
				dblRot = false;
			}
		}
		else if(getHeight(node.getRight()) -getHeight(node.getLeft())   > 1)
		{
			if(getHeight(node.getRight().getRight()) >= getHeight(node.getRight().getLeft()))
				node = singleLeftRotation(node);
			else
			{
				dblRot = true;
				node = doubleRightLeftRotation(node);
				dblRot = false;
			}
		}
		return node;
	}
	
	private BinaryNode<T> singleRightRotation(BinaryNode<T> k2)
	{
		BinaryNode<T> k1 = k2.getLeft();
		k2.setLeft(k1.getRight());
		k1.setRight(k2);
		k2.setHeight(Math.max(getHeight(k2.getLeft()), getHeight(k2.getRight())) + 1);
		k1.setHeight(Math.max(getHeight(k2.getLeft()), k2.getHeight()) + 1);
		if(printRot && !dblRot)
			System.out.println("Single right rotation occured on " + k2.getData());
		return k1;
	}
	
	private BinaryNode<T> singleLeftRotation(BinaryNode<T> k1)
	{
		BinaryNode<T> k2 = k1.getRight();
		k1.setRight(k2.getLeft());
		k2.setLeft(k1);
		k1.setHeight(Math.max(getHeight(k1.getLeft()), getHeight(k1.getRight())) + 1);
		k2.setHeight(Math.max(getHeight(k2.getRight()), k1.getHeight()) + 1);
		if(printRot && !dblRot)
			System.out.println("Single left rotation occured on " + k1.getData());;
		return k2;
	}
	private BinaryNode<T> doubleLeftRightRotation(BinaryNode<T> k3)
	{
		if(printRot)
			System.out.println("Double left right rotation occured on " + k3.getData());
		k3.setLeft(singleLeftRotation(k3.getLeft()));
		return singleRightRotation(k3);
	}
	
	private BinaryNode<T> doubleRightLeftRotation(BinaryNode<T> k1)
	{
		if(printRot)
			System.out.println("Double right left rotation occured on " + k1.getData());
		k1.setRight(singleRightRotation(k1.getRight()));
		return singleLeftRotation(k1);
	}
}
