public class BinarySearchTree <T extends Comparable<T>>
	{
		protected BinaryNode<T> root;
		
		public BinarySearchTree()
		{
			this.root = null;
		}
		
		public void insert(T i)
		{
			root = insert(i, root);
		}
		public BinaryNode<T> insert(T i, BinaryNode<T> node)
		{
			if(node == null)
				{
					node = new BinaryNode<T>(i);
					return node;
					
				}
			int compare = i.compareTo(node.getData());
			if(compare <0)
				node.setLeft(insert(i,node.getLeft()));
			else if (compare > 0)
				node.setRight(insert(i,node.getRight()));
			else {
				
			}
			return node;		
			}

		public void inOrder()
		{
			inOrder(root);
		}
		public void inOrder(BinaryNode<T> root)
		{
			if(root != null)
			{
				inOrder(root.getLeft());
				System.out.println(root.getData());
				inOrder(root.getRight());
			}
		}	

		public BinaryNode<T> search(BinaryNode<T> node, T value)
		{
			if(node.getData().equals(value))
				return node;
			if(value.compareTo(node.getData()) < 0)
				return search(node.getLeft(), value);
			else if(value.compareTo(node.getData()) >0)
				return search(node.getRight(), value);
			return node;
		}
		
		public void delete(T value)
		{
			root = delete(value,root);
		}
	    protected BinaryNode<T> delete(T data, BinaryNode<T> root) {
	        if(root == null) {
	            return null;
	        }

	        int compareResult = root.getData().compareTo(data);

	        if(compareResult > 0) {
	            root.setLeft(delete(data, root.getLeft()));
	        } else if(compareResult < 0) {
	            root.setRight(delete(data, root.getRight()));
	        } else if(root.getLeft() != null && root.getRight() != null) {
	            root.setData(minValue(root.getRight()));
	            root.setRight(delete(root.getData(), root.getRight()));
	        } else {
	            root = root.getLeft() != null ? root.getLeft() : root.getRight();
	        }

	        return root;
	    }
		
		public T minValue(BinaryNode<T> node)
		{
			T min = node.getData();
			while(node.getLeft() !=null)
			{
				min = node.getLeft().getData();
				node = node.getLeft();
			}
			return min;
		}
		public int getHeight()
		{
			return getHeight(root);	

		}
		public int getHeight(BinaryNode<T> node)
		{
			if(node == null)
				return -1;
			else {
				int lDepth = getHeight(node.getLeft());
				int rDepth = getHeight(node.getRight());
				
				if(lDepth > rDepth)
					return (lDepth + 1);
				else
					return (rDepth + 1);
			}
		}
		public boolean isEmpty()
		{
			return root == null;
		}
		
		public BinaryNode<T> getRoot()
		{
			return this.root;
		}
}
