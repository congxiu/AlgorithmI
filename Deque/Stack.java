/*-------------------
 * An resizing array implementation of Stack
 *-------------------*/
import java.util.Iterator;

public class Stack<Item> implements Iterable<Item>
{
    private Item[] a;
    int size;
    
    public Stack()
    {
        a = (Item[]) new Object[1];
        size = 0;
    }
    
    private void resize(int capacity)
    {
        Item[] aCopy = (Item[]) new Object[capacity];
        
        for (int i = 0; i < size; i++)
            aCopy[i] = a[i];
        
        a = aCopy;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public int size()
    {
        return size;
    }
    
    public void push(Item item)
    {
        if (size == a.length)
            resize(size * 2);
        
        a[size++] = item;
    }
    
    public Item pop()
    {
        Item toBeReturned = a[--size];
        a[size] = null;
        
        if (size == a.length / 4)
            resize(a.length / 2);
        
        return toBeReturned;
    }
    
    public Iterator<Item> iterator()
    {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item>
    {
        private int N = size;
        
        public boolean hasNext()
        {
            return N != 0;
        }
        
        public Item next()
        {
            return a[--N];
        }
        
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException("Not supported!");
        }
    }
    
    public static void main(String[] args)
    {
        Stack<Integer> testStack = new Stack<Integer>();
        testStack.push(5);
        testStack.push(7);
        testStack.push(9);
        StdOut.println(testStack.size());
        
        for (int s : testStack)
            StdOut.println(s);
        
        StdOut.println(testStack.pop());
        StdOut.println(testStack.pop());
        StdOut.println(testStack.pop());
        StdOut.println(testStack.isEmpty());
    }
}