import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


class Locky implements Lock
{
    ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer> () {
        protected Integer initialValue() {
            return 0;
        }
    };

    AtomicInteger tail;
    volatile boolean[] flag;
    int size;

    public Locky(int capacity)
    {
        size = capacity;
        tail = new AtomicInteger(0);
        flag = new boolean[capacity];
        flag[0] = true;
    }

    public void lock()
    {
        int slot = tail.getAndIncrement() % size; mySlotIndex.set(slot);
        while (! flag[slot]) {};
    }

    public void unlock()
    {
        int slot = mySlotIndex.get(); flag[slot] = false;
        flag[(slot + 1) % size] = true;
    }
     
    @Override
    public Condition newCondition() {
        
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        
    }

    @Override
    public boolean tryLock() {
        
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        
        return false;
    }
}

class Guest implements Runnable
{
    Locky vaseQueue;

    public Guest(Locky vaseQueue)
    {
        this.vaseQueue = vaseQueue;
    }

	@Override
	public void run()
	{
		while (true)
        {
            vaseQueue.lock();
            // hey look at that vase, neat
            vaseQueue.unlock();
        }
	}
}
class ProblemTwo
{
	public static void main(String[] args) throws InterruptedException
	{
        Locky vaseQueue = new Locky(100);
        for (int i = 0; i < 100; i++)
		{
			Guest someGuest = new Guest(vaseQueue);
			Thread someGuestThread = new Thread(someGuest);
			someGuestThread.start();
		}

        System.out.println("The queue will close in 5 seconds.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Time's up!");
        System.exit(0);
        
	}
}