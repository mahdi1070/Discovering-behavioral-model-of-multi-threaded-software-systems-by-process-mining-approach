package atmDis;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkerThread implements Runnable {

	private BlockingQueue<Transaction> taskQueue = new LinkedBlockingQueue<Transaction>();
	private boolean empty = false;
	private Transaction job;

	public WorkerThread(BlockingQueue<Transaction> queue) {
		this.taskQueue = queue;
		if (taskQueue.isEmpty())
			empty = true;
		job = null;
	}

	@Override
	public synchronized void run() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";
			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();

			while (empty) {
				Thread.sleep(1000);
				wait();
			}
			empty = false;
			notify();

			if (!taskQueue.isEmpty()) {

				Thread.sleep(500);
				job = taskQueue.take();
				int type = job.getType();
				if (type > 0 && type < 4) {
					if (type == 1) {
						Task.Withdraw(job, con, st, false);
//						String a = Task.Withdraw(job, con, st, false);
				//		displayThreadResult(Thread.currentThread().getName(), type, a);
						Thread.sleep(500);
				//		displayThreadSleepStatus(Thread.currentThread().getName());
					} else if (type == 2) {
						Task.Balance(job, con, st);
//						String b = Task.Balance(job, con, st);
				//		displayThreadResult(Thread.currentThread().getName(), type, b);
						Thread.sleep(500);
				//		displayThreadSleepStatus(Thread.currentThread().getName());
					} else {
						Map<String, Long> hashMap;
						hashMap = Task.Transfer(job, con, st);
//						String c = "";
						if (hashMap.get("AccId") != (long) 0) {
//							c = "Receiver Error";
							Task.rollback_Operation(con, st, hashMap.get("amount"), hashMap.get("AccId"));
						} else {
//							c = "Sender Error";
						}
			//			displayThreadResult(Thread.currentThread().getName(), type, c);
						Thread.sleep(500);
			//			displayThreadSleepStatus(Thread.currentThread().getName());
					}
				} else {
			//		System.out.println("Worker Thread " + Thread.currentThread().getName() + " I am sleeping");
				}
			}
			st.close();
			con.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException
				| InterruptedException e) {
			e.printStackTrace();
		}
	}

//	private void displayThreadResult(String thread, int type, String Res) {
//		String line = type + " ===> " + "Result = " + Res;
//		System.out.println("worker " + thread + "\t" + line);
//
//	}
//
//	private void displayThreadSleepStatus(String sleepStatus) {
//		String line = "I am sleeping";
//		System.out.println("worker " + sleepStatus + "\t" + line);
//
//	}
}
