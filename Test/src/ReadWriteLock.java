import java.util.HashSet;
import java.util.Set;
 
public class ReadWriteLock {
    private Set<Thread> readLocks = new HashSet<>();
    private Thread writeLock = null;
     
    private void justWait() {
        try {
            wait();
        } catch (InterruptedException e) {
        }
    }
     
    public synchronized void getReadLock() {
        while (writeLock != null) 
            justWait();
        readLocks.add(Thread.currentThread());
    }
 
    public synchronized void getWriteLock() {
        while (writeLock != null || !readLocks.isEmpty())
            justWait();
        writeLock = Thread.currentThread();
    }
     
    public synchronized void unlock() {
        Thread t = Thread.currentThread(); 
        if (writeLock == t) {
            writeLock = null;
            notifyAll();
        }
        else if (readLocks.contains(t)) {
            readLocks.remove(t);
            if (readLocks.isEmpty())
                notifyAll();
            // why notifyAll and not notify?
        }
        else
            throw new RuntimeException("Thread does not own lock!");
    }   
}
