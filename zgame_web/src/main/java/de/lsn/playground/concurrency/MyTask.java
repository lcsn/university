package de.lsn.playground.concurrency;

public class MyTask implements Runnable {

	private int id;

	public MyTask(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Running Task: " + id);
	}

}
