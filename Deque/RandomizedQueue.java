/*---------------------
 * A resizing array implementation of Randomized queue
 *---------------------*/
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] a;
    private int size;
    
    public RandomizedQueue()
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
    
    public void enqueue(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException("can't add null item!");
            
        if (size == a.length)
            resize(2 * size);
        
        a[size++] = item;
    }
    
    public Item dequeue()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Queue is already empty!");
        
        int index = StdRandom.uniform(size);
        Item toBeReturned = a[index];
        a[index] = a[--size];
        a[size] = null;
        
        if (size == a.length / 4 && size > 0)
            resize(a.length / 2);
        
        return toBeReturned;
    }
    
    public Item sample()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Queue is already empty!");        
        
        int index = StdRandom.uniform(size);
        Item toBeReturned = a[index];
        
        return toBeReturned;
    }
    
    public Iterator<Item> iterator()
    {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item>
    {
        private Item[] aCopy = (Item[]) new Object[size];
        private int iterSize;
        
        public ArrayIterator()
        {
            for (int i = 0; i < size; i++)
                aCopy[i] = a[i];
        
            StdRandom.shuffle(aCopy);
            iterSize = size;
        }
        
        public boolean hasNext()
        {
            return iterSize != 0;
        }
        
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException("Not supported!");
        }
        
        public Item next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException("Queue is already empty!");
            
            Item toBeReturned = aCopy[--iterSize];
            aCopy[iterSize] = null;
            
            return toBeReturned;
        }
    }
    
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> testQueue = new RandomizedQueue<Integer>();
        testQueue.enqueue(5);
        StdOut.println(testQueue.dequeue());
        testQueue.enqueue(5);
        StdOut.println(testQueue.dequeue());
        /*testQueue.enqueue(7);
        testQueue.enqueue(9);
        testQueue.enqueue(11);
        testQueue.enqueue(2);
        for (int s : testQueue)
            StdOut.println(s);
        StdOut.println(testQueue.dequeue());
        StdOut.println(testQueue.dequeue());
        StdOut.println(testQueue.dequeue());
        StdOut.println(testQueue.sample());
        StdOut.println(testQueue.sample());
        StdOut.println(testQueue.sample());
        */
    }
}