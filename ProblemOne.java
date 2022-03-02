import java.util.*;

class Global
{
	/*
	1 indicates that there is a cupcake.
	0 indicates that it has been eaten.
	Every guest will eat a cupcake (set cupcake to 0) EXACTLY once.
	Only the leader will replace a cupcake (set cupcake to 1).
	Once the leader has replaced it 100 times, everyone can be done.
	*/
    public static int cupcake = 1;
	public static int finished = 0;
	public static int count = 0;
	public static int headcount = 100;
	public static Object mutex = new Object();
}

class Guest implements Runnable
{
	private int guestID;
	private int isLeader;
	private int hasEaten = 0;

	// the "Leader" determines when we are done
	public Guest(int guestID, int isLeader)
	{
		this.guestID = guestID;
		this.isLeader = isLeader;
	}

	@Override
	public void run()
	{
		// Threads all compete for the lock.
		while (Global.finished == 0)
		{
			// Guest that obtains the lock will enter the cupcake room.
			synchronized(Global.mutex)
			{
				// If the guest has yet to eat a cupcake, and one is present, they will eat.
				if (hasEaten == 0 && Global.cupcake == 1)
				{
					Global.cupcake = 0;
					//System.out.println("Guest #" + guestID + " just ate a cupcake!");
					hasEaten = 1;
				}
				// If the guest is the leader, they will check if the cupcake needs
				// to be replenished and increment the count if so.
				if (isLeader == 1)
				{
					if (Global.cupcake == 0)
					{
						Global.cupcake = 1;
						Global.count++;
						//System.out.println("Count increased to: " + Global.count);
					}
					// At this point, 100 cupcakes have been eaten.
					// Therefore, all guests have entered the room.
					if (Global.count == Global.headcount)
					{
						Global.finished = 1;
						System.out.println("Everyone has been inside the showroom!");
					}
				}
			}
		}
	}
}

class ProblemOne
{
	public static void main(String[] args)
	{
		Guest leader = new Guest(1, 1); // Determines when we are done
		Thread leaderThread = new Thread(leader);
		leaderThread.start();
		for (int i = 2; i <= Global.headcount; i++)
		{
			Guest someGuest = new Guest(i, 0);
			Thread someGuestThread = new Thread(someGuest);
			someGuestThread.start();
		}
	}
}
