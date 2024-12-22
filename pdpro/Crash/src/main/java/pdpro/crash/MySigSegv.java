package pdpro.crash;

public class MySigSegv {
	public native void doSigSegvJNI();
	static boolean isInit = false;
	
	/*
	 * Constructor to load My JNI Library
	 * at only first initial time.
	 */
	MySigSegv() {
		if(!isInit) {
			System.loadLibrary("pdpro.native");
		}
	}
	
   /*
    * Call doSIgSegvJNI method.
    */
	public void callSigSegvJNI() {
		System.out.println("> MySigSegv.callSigSegvJNI()");
		new MySigSegv().doSigSegvJNI();	
		System.out.println("< MySigSegv.callSigSegvJNI()");
	}
}
