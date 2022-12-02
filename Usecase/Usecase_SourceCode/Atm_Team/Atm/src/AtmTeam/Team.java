package AtmTeam;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Mahdi
 *
 */
public class Team {

	public Team() {
	}
	static long Basket_money;
	
	public static void main(String args[]) throws IOException {

		List<Transaction> trans_List = new ArrayList<Transaction>();
		trans_List = ImportData.readTrans();
		Basket_money = new Random().nextInt(40000) + 20000;
//		InitilizeThreads init = new InitilizeThreads(trans_List, Basket_money);
//		Thread t0 = new Thread(init);
//		t0.start();
		
		InitilizeThreads(trans_List) ;

	}
	
	public static void InitilizeThreads(List<Transaction> trans_List) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";
			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();

			LinkedList<TransactionState> link = new LinkedList<TransactionState>();
			Operation1 Operation1 = new Operation1(link , con , st);
			Operation2 Operation2 = new Operation2(link, con , st);
			Operation3 Operation3 = new Operation3(link , con , st);

//			Reader Reader = new Reader(trans_List, link, Operation1, Operation2, Operation3);

//			Thread t1 = new Thread(Reader);
			Thread t2 = new Thread(Operation1);
			Thread t3 = new Thread(Operation2);
			Thread t4 = new Thread(Operation3);

//			t1.start();
			t2.start();
			t3.start();
			t4.start();

			
			for (int i = 0; i < trans_List.size(); i++) {
				Transaction line = trans_List.get(i);
				ReadByLine(line , link);
			}
			Operation1.cancel();
			Operation2.cancel();
			Operation3.cancel();
//			System.out.println("redear finish");
			
			
			while(true) 
			{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Operation1.fin && Operation2.fin && Operation3.fin)
					break ; 
			}
			

			st.close();
			con.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void ReadByLine(Transaction line , LinkedList<TransactionState> link) {
		link.add(new TransactionState(line, false));
	//	displayThreadResult(line, "Complete");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	//	displayThreadSleepStatus();
	}

}
