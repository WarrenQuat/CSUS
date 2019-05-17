public class BinaryNode<T extends Comparable<T>>
{
	private BinaryNode<T> left, right;
	private T value;
	private int height;
			
	public BinaryNode(T value)
	{
		this.value = value;
		left = right = null;
		this.height = 1;
	}
	public T getData()
	{
		return this.value;
	}
  
	public BinaryNode<T> getLeft()
	{
		return this.left;
	}
	
    public void setLeft(BinaryNode<T> left)
    {
        this.left = left;
    }
    
	public BinaryNode<T> getRight()
	{
		return this.right;
	}
	
	public void setRight(BinaryNode<T> right)
	{
	    this.right = right;
	}
	
	public void setData(T data)
	{
		this.value = data;
	}
	public int getHeight()
	{
		return this.height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
}
