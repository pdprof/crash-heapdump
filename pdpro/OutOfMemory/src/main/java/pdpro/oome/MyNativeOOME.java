package pdpro.oome;

public class MyNativeOOME {
	public native void doMallocJNI(int loop);
	static boolean isInit  = false;
	
	/*
	 * Constructor to load My JNI Library
	 * at only first initial time.
	 */
	MyNativeOOME () {
		if(!isInit) {
			System.loadLibrary("pdpro.native");
		}
	}

	public void callMallocJNI(int loop) {
		System.out.println("> MyNativeOOME.callMallocJNI() loop - " + loop);
		new MyNativeOOME().doMallocJNI(loop);
		System.out.println("< MyNativeOOME.callMallocJNI()");
	}

}
