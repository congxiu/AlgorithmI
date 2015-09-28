/*-------------------
 * A linked list implementation of Deque (double-ended queue)
 *-------------------*/
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{   
    private class Node
    {
        private Item item;
        private Node next;
        private Node prev;
        
        public Node(Item item)
        {
            this.item = item;
            next = null;
            prev = null;
        }
    }
        
    private Node first;
    private Node last;
    private int size;
    
    public Deque()
    {
        first = new Node(null);
        last = new Node(null);
        first.next = last;
        last.prev = first;
        size = 0;
    }
    
    
    private class ListIterator implements Iterator<Item>
    {
        private Node currNode = first.next;
            
        public boolean hasNext()
        {
            return currNode.item != null;
        }
        
        public void remove() 
        {
            throw new java.lang.UnsupportedOperationException("Unsupported!");
        }
        
        public Item next() 
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException("Deque is already empty!");
                
            Item toBeReturned = currNode.item;
            currNode = currNode.next;
            
            return toBeReturned;
        }
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public int size()
    {
        return size;
    }
    
    public void addFirst(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException("Can't add null item!");
        
        Node newFirst = new Node(item);
        newFirst.prev = first;
        newFirst.next = first.next;
        first.next.prev = newFirst;
        first.next = newFirst;
        
        size++;   
    }
    
    public void addLast(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException("Can't add null item!");
        
        Node newLast = new Node(item);
        newLast.next = last;
        newLast.prev = last.prev;
        last.prev.next = newLast;
        last.prev = newLast;
        
        size++;
    }
    
    public Item removeFirst()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque is already empty!");
        
        Node toBeRemoved = first.next;
        Item toBeReturned = toBeRemoved.item;
        first.next = toBeRemoved.next;
        first.next.prev = first;
        
        size--;
        return toBeReturned;
    }
    
    public Item removeLast()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque is already empty!");
        
        Node toBeRemoved = last.prev;
        Item toBeReturned = toBeRemoved.item;
        last.prev = toBeRemoved.prev;
        last.prev.next = last;
        
        size--;
        return toBeReturned;
    }
    
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }
    
    public static void main(String[] args)
    {
        Deque<Integer> testDeque = new Deque<Integer>();
        
        testDeque.addFirst(5);
        testDeque.addLast(7);
        testDeque.addLast(2);
        testDeque.addLast(9);
        testDeque.addFirst(3);
        for (int a : testDeque)
            StdOut.println(a);
        
        
        Deque<Integer> testDeque2 = new Deque<Integer>();
        testDeque2.addFirst(5);
        StdOut.println(testDeque2.removeFirst());
        testDeque2.addFirst(5);
        StdOut.println(testDeque2.removeLast());
        testDeque2.addLast(5);
        StdOut.println(testDeque2.removeLast());
        
    }
}