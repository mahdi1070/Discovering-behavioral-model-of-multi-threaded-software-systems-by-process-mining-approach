package AtmTeam;

import java.util.LinkedList;

public class Operation1 implements Runnable {

	private boolean cancel = false;
	private LinkedList<TransactionState> link;
	java.sql.Connection con ;
	java.sql.Statement st ;
	
	public Operation1(LinkedList<TransactionState> link , java.sql.Connection con ,java.sql.Statement st ) {
		this.link = link;
		this.con = con ;
		this.st = st ; 
	}

	public void run() {
		int index = 0;
		while (isContinue()) {
			int job = 0;
			synchronized (link) {
				if (link.size() > index) {
					job = link.get(index).tran.getType();
					Boolean finish = link.get(index).end;
					String print =  link.get(index).tran + " " ;

					if (job == 1) {
						Transaction t = link.get(index).tran ; 
						oper1(index , t , finish , print) ; 
					}
					index++;
				}
			}
			
		}
		finish() ; 
//		System.out.println("op1 finish");


	}
	private void oper1(int index , Transaction t , Boolean finish , String print ) {
		boolean ee = assignWork(link, index, finish);
		
		if (ee) {
			Task.Withdraw(t, con, st, false);
//			String a = Task.Withdraw(t, con, st, false);
//			displayThreadResult(Thread.currentThread().getName(), 1 , a);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			displayThreadSleepStatus(Thread.currentThread().getName());
		}
	}

	private synchronized boolean isContinue() {
		boolean ex = false;
		if(cancel) {
			for (int i = 0; i < link.size(); i++) {
				if (!link.get(i).end) {
					ex = true;
					break;
				}
			}
		}	
		else
		{
			ex = true ;
		}
		return ex;
	}

	private synchronized Boolean assignWork(LinkedList<TransactionState> link, int index, Boolean finish) {
		if (finish == false) {
			link.get(index).end = true;
			return true;
		}

		return false;
	}

//	private void displayThreadResult(String thread, int type, String Res) {
//		String line = "=> "+type + " ===> " + "Result = " + Res;
//		System.out.println("Withdaw " + thread + "\t" + line);
//
//	}
//
//	private void displayThreadSleepStatus(String sleepStatus) {
//		String line = "I am sleeping";
//		System.out.println("Withdaw " + sleepStatus + "\t" + line);
//
//	}

	public void cancel() {
		cancel = true;
	}
	
	boolean fin = false ; 
	public void finish() {
		fin = true ; 
	}
}
