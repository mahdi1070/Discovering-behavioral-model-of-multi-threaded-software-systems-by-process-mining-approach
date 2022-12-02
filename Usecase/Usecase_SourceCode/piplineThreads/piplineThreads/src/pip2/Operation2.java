package pip2;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Operation2 implements Runnable {

	private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
	private final Task taskAssigned = new Task();
	private String job;
	private double result = 0.0;
	private boolean cancel = false;
	private Operation3 nextComponent;

	public void run() {

		
		nextComponent = new Operation3();
		Thread t2 = new Thread(nextComponent);
		t2.start(); 
		
		while (exit()) {
			double x;
			double res;
			try {
				if (!queue.isEmpty()) {

					job = queue.poll();

					String[] words = job.split(" ");
					if (words.length > 0) {
						x = Double.parseDouble(words[7]); 
						res = Double.parseDouble(words[4]);

						result = taskAssigned.subtraction(x, res);

						nextComponent.enqueue(job + " " + result);
						Thread.sleep(20);

					}

				} else {
					// System.out.println("Queue Is Empty");
					// Thread.sleep(1000);
				}
			} catch (InterruptedException e) {

			}
		}
		nextComponent.cancel();

	}

//	public boolean QueueSize() {
//		int Qsize = queue.size();
//		if (Qsize == 0)
//			return false;
//		return true;
//	}

	private synchronized boolean exit() {
		boolean ex = false;
		if (cancel) {
			ex = !queue.isEmpty();
		} else {
			ex = true;
		}
		return ex;
	}

	public void enqueue(String data) {
		queue.offer(data);
	}

	public void cancel() {
		cancel = true;
	}

//	public void setNextOperands(Operation3 nextComponent) {
//		this.nextComponent = nextComponent;
//	}
//
//	public boolean nextComponentQueueSize() {
//		boolean ex = false;
//		if (nextComponent.QueueSize())
//			ex = true;
//		return ex;
//	}
}
