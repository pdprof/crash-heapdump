package pdpro.hang;

import java.util.Date;

public class MyLoop {

	int doLoop(long times) {
		System.out.println("> MyLoop.doLoop() - looping " + times + " times");
		String alphabets = "abcdefghijklmnopqrstuvwxyz";
		Date start_date = new Date();
		for (int j = 0; j < times; j++) {
			for (int i = 0; i < alphabets.length(); i++) {
				String alphabet = alphabets.substring(i, i + 1);
				alphabet = alphabet.toUpperCase();
				String temp = new String(alphabet.getBytes());
				if(temp.equalsIgnoreCase(alphabet)) {
					temp.hashCode();
				}
			}
		}
		Date end_date = new Date();
		int elapsed = (int)(end_date.getTime() - start_date.getTime());
		System.out.println("D MyLoop.doLoop() - looping elapsed " + elapsed + " milliseconds");
		System.out.println("< MyLoop.doLoop()");
		return elapsed;
	}

}
