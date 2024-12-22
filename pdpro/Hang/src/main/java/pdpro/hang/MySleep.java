package pdpro.hang;

public class MySleep {
	
	void doSleep(long msec) {
		System.out.println("> MyLoop.doLoop() - sleep " + msec +" milliseconds");
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("< MyLoop.doLoop()");
	}

}
