package pip2;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WriterResult implements Runnable {

	private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
	private String job;
	private boolean cancel = false;

	public void run() {

		while (exit()) {
			double res;
			try {
				if (!queue.isEmpty()) {

					job = queue.poll();

					String[] words = job.split(" ");
					if (words.length > 0) {
						// compute them .
						res = Double.parseDouble(words[9]);
						displayThreadResult(job, res);
						Thread.sleep(20);
					}

				} else {
				}
			} catch (InterruptedException e) {

			}
		}

	}

//
	
//	public boolean QueueSize() {
//		int Qsize = queue.size();
//		if(Qsize == 0)
//			return false ;
//		return true ; 
//	}
	
	public void enqueue(String data) {
		queue.offer(data);
	}

	private void displayThreadResult(String job, Double result) {
		String line = job + " ===> " + "Result = " + Double.toString(result);
		System.out.println("WriterResult Thread " + "\t" + line);

	}
	private synchronized boolean exit() {
		boolean ex = false;
		if(cancel) {
			ex = !queue.isEmpty() ; 
		}	
		else
		{
			ex = true ;
		}
		return ex;
	}

	public void cancel() {
		cancel = true;
	}

}
