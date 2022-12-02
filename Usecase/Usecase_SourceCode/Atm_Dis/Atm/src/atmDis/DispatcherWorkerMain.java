package atmDis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DispatcherWorkerMain {

	static Runnable worker;
	static ExecutorService executor = Executors.newFixedThreadPool(10);
	private final static BlockingQueue<Transaction> taskQueue = new LinkedBlockingQueue<Transaction>();
	private static boolean empty = true;
	private static Transaction line;

	static List<Transaction> trans_List = new ArrayList<Transaction>();
	static long Basket_money;

	public static void main(String args[]) throws IOException {

		trans_List = ImportData.readTrans();
		Basket_money = new Random().nextInt(40000) + 20000;

		AssignToWorker();
	}

	public static void AssignToWorker() {

		try {
			while (!empty) {
			//	System.out.println("I am sleeping");
			}
			empty = false;

			for (int i = 0; i < trans_List.size(); i++) {
				line = trans_List.get(i);
				taskQueue.put(line); // put the task in the queue
				Thread.sleep(200);
				worker = new WorkerThread(taskQueue); // create the worker thread
				executor.execute(worker); // execute the worker thread
			}
			executor.shutdown();
			executor.awaitTermination(5000, TimeUnit.MILLISECONDS);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
