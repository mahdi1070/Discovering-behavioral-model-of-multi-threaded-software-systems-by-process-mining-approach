package AtmTeam;


import java.util.LinkedList;
import java.util.List;

public class Reader implements Runnable {

	List<Transaction> trans_List;
	private Operation1 operation1;
	private Operation2 operation2;
	private Operation3 operation3;
	private LinkedList<TransactionState> link;

	public Reader(List<Transaction> trans_List, LinkedList<TransactionState> link, Operation1 operation1,
			Operation2 operation2, Operation3 operation3) {
		this.trans_List = trans_List;
		this.link = link;
		this.operation1 = operation1;
		this.operation2 = operation2;
		this.operation3 = operation3;
	}

	public void run() {

		for (int i = 0; i < trans_List.size(); i++) {
			Transaction line = trans_List.get(i);
			ReadByLine(line);
		}
		operation1.cancel();
		operation2.cancel();
		operation3.cancel();
//		System.out.println("redear finish");

	}

	private void ReadByLine(Transaction line) {
		link.add(new TransactionState(line, false));
	//	displayThreadResult(line, "Complete");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	//	displayThreadSleepStatus();
	}

//	private void displayThreadResult(Transaction line, String result) {
//		System.out.println("Read Thread " + "\t" + line.getTransId());
//	}
//
//	private void displayThreadSleepStatus() {
//		String line = "I am sleeping";
//		System.out.println("Read Thread " + "\t" + line);
//
//	}
}
