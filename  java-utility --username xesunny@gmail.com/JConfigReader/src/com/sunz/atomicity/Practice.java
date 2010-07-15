package com.sunz.atomicity;

import java.util.concurrent.atomic.*;

public class Practice implements Runnable {
	AtomicInteger atomic_a = new AtomicInteger();
	AtomicInteger atomic_b = new AtomicInteger();
	int normal_a;
	int normal_b;
	private static int count = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Practice pr = new Practice();
		new Thread(pr).start();// 1 2 3
		new Thread(pr).start();// 4 5 6
		new Thread(pr).start(); // 7 8 9
		new Thread(pr).start(); // 10 11 12
		while (true) {
			if (count == 4) {
				System.out.println(pr.atomic_a.get());
				System.out.println(pr.normal_a);
				break;
			}
		}

	}

	public void run() {
		System.out.println("Inside run method...");
		doCalc();

	}

	private void doCalc() {
		try {
			atomic_b = atomic_a;
			normal_b = normal_a;
			atomic_b.incrementAndGet();
			Thread.sleep(1000);
			atomic_b.decrementAndGet();
			Thread.sleep(1000);
			atomic_b.incrementAndGet();
			normal_b = normal_b + 1;
			Thread.sleep(1000);
			atomic_a = atomic_b;
			
			//-------------NORMAL----------------
			normal_b = normal_b + 1;
			normal_b = normal_b - 1;
			normal_b = normal_b + 1;
			normal_a = normal_b;
			synchronized (this) {
				count++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
