package pdpro.hang;

public class MyLockA {
	static int countA = 0;
	static Object lockA = new Object();
	
	public void upCountAB() {
		synchronized(lockA) {
			System.out.println("D MyLockA.upCountAB() - lockA is locked..");
			countA = countA + 1;
			MyLockB b = new MyLockB();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("D MyLockA.upCountAB() - trying to lock lockB ..");
			b.upCountB();
		}
	}
	
	public synchronized void upCountA() {
		synchronized(lockA) {
			countA = countA + 1;
		}
	}
	
	public synchronized void downCountA() {
		synchronized(lockA) {
			countA = countA - 1;
		}
	}
}
