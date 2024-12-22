package pdpro.hang;

public class MyLockB {
	static int countB = 0;
	static Object lockB = new Object();
	
	public void upCountBA() {
		synchronized (lockB) {
			System.out.println("D MyLockB.upCountBA() - lockB is locked..");
			countB = countB + 1;
			MyLockA a = new MyLockA();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("D MyLockA.upCountBA() - trying to lock lockA ..");
			a.upCountA();
		}
	}
	
	public void upCountB() {
		synchronized (lockB) {
			countB = countB + 1;
		}
	}
	
	public synchronized void downCountB() {
		synchronized (lockB) {
			countB = countB - 1;
		}
	}
	
}
