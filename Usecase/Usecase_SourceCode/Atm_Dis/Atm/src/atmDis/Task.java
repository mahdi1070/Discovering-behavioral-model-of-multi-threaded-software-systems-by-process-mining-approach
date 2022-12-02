package atmDis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Task {

	static List<Roleback> RoleBack_List = new ArrayList<Roleback>();
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static String today = sdf.format(new Date());
	static Random rand = new Random();

	
	public static String Withdraw(Transaction b, java.sql.Connection con, java.sql.Statement st, boolean trensfer) {
		String Val = "";
		Long AccId = b.getAccId();
		Card ca = GetCardInformation(AccId, con, st);
		Account ac = GetAccountInformation(AccId, con, st);
		String cardExpDate = ca.getExpDate();
		String UserPin = b.getPin();
		String CardPin = ca.getPin();
		int isIt = authorizeCard(cardExpDate, CardPin, UserPin, AccId, con, st, 0);
		if (isIt == 10) {
			int accState = ac.getState();
			boolean acState = Chech_account_state(accState);
			if (acState) {
				boolean BalLimit = true;
				Long amount = b.getAmount();
				if (!trensfer)
					BalLimit = ChechAmountBetween2Numbers(amount);
				if (BalLimit) {
					boolean BalCHECK = ChechBalanceInAccount(amount, AccId, con, st);
					if (BalCHECK) {
						boolean limitDay = Chech_limit_Request_perday(AccId, con, st);
						if (limitDay) {
							boolean Balbasket = true;
							if (!trensfer)
								Balbasket = ChechMoneyInBasket(amount);
							if (Balbasket) {
								boolean widState = Withdraw_Operation_inDataBase(amount, AccId, con, st);
								if (widState) {
									int limitdayValue = ac.getLimitDay();
									Update_LimitPerDay_DB(AccId, limitdayValue, con, st);
									if (!trensfer) {
										Val = "Succesful";
									} else {
										Val = "Succesful";
									}
								} else {
									DispatcherWorkerMain.Basket_money = DispatcherWorkerMain.Basket_money + amount;
									Val = "Withdraw_inDB failed sender";
								}
							} else {
								Val = "mojody EXCEED basket sender " + amount + " > " + DispatcherWorkerMain.Basket_money;
							}
						} else {
							Val = "limit per day sender ";
						}

					} else {
						Val = "mojody EXCEED FROM balance sender ";
					}
				} else {
					Val = "Balance Limit sender 100 < x < 10000";
				}
			} else {
				Val = "acc State sender " + accState;
			}
		} else {
			if (isIt == 0) {
				Val = "Exp Card sender " + cardExpDate;
			} else if (isIt == 1) {
				int limitpinValue = ca.getLimitPin();
				Update_LimitWrongPin_DB(AccId, limitpinValue, con, st);
				Val = "Pin Wrong sender " + CardPin;
			} else if (isIt == 2) {
				Val = "limit pin sender ";
			}

		}
		Long transactionId = b.getTransId();
		Update_Transaction_Result_InDB(Val, transactionId, con, st);
		return Val;
	}

	public static String Balance(Transaction b, java.sql.Connection con, java.sql.Statement st) {
		String Val = "";
		Long AccId = b.getAccId();
		Card ca = GetCardInformation(AccId, con, st);
		Account ac = GetAccountInformation(AccId, con, st);
		String cardExpDate = ca.getExpDate();
		String UserPin = b.getPin();
		String CardPin = ca.getPin();
		int isIt = authorizeCard(cardExpDate, CardPin, UserPin, AccId, con, st, 0);
		if (isIt == 10) {
			int accState = ac.getState();
			boolean acState = Chech_account_state(accState);
			if (acState) {
				boolean limitDay = Chech_limit_Request_perday(AccId, con, st);
				if (limitDay) {
					boolean BalRead = Balance_Operation_inDataBase(AccId, con, st);
					if (BalRead) {
						Val = "Succesful";
						int limitdayValue = ac.getLimitDay();
						Update_LimitPerDay_DB(AccId, limitdayValue, con, st);
					} else {
						Val = "Balance_readDB failed sender";
					}
				} else {
					Val = "limit per day sender ";
				}
			} else {
				Val = "acc State sender " + accState;
			}
		} else {
			if (isIt == 0) {
				Val = "Exp Card sender " + cardExpDate;
			} else if (isIt == 1) {
				int limitpinValue = ca.getLimitPin();
				Update_LimitWrongPin_DB(AccId, limitpinValue, con, st);
				Val = "Pin Wrong sender " + CardPin;
			} else if (isIt == 2) {
				Val = "limit pin sender ";
			}
		}
		Long transactionId = b.getTransId();
		Update_Transaction_Result_InDB(Val, transactionId, con, st);
		return Val;
	}

	public static String Deposit(Transaction b, java.sql.Connection con, java.sql.Statement st, Boolean trnsfer) {
		String Val = "";
		Long AccIdto = b.getAccId_To();
		Card ca2 = GetCardInformation(AccIdto, con, st);
		Account ac2 = GetAccountInformation(AccIdto, con, st);
		String cardExpDate = ca2.getExpDate();
		int isIt = authorizeCard(cardExpDate, "", "", (long) 0, con, st, 1);
		if (isIt == 1) {
			int accState = ac2.getState();
			boolean acState = Chech_account_state(accState);
			if (acState) {
				Long amount = b.getAmount();
				boolean widState = Deposit_Operation_inDataBase(amount, AccIdto, con, st);
				if (widState) {
					Val = "Succesful";
				} else {
					Val = "Withdraw_inDB failed receiver";
				}
			} else {
				Val = "acc State receiver " + accState;
			}
		} else {
			Val = "Exp Card receiver " + cardExpDate;
		}
		Long transactionId = b.getTransId();
		Update_Transaction_Result_InDB(Val, transactionId, con, st);
		return Val;
	}

	public static Map<String,Long> Transfer(Transaction b, java.sql.Connection con, java.sql.Statement st) {
		Map<String,Long> hashMap = new HashMap<>();
		hashMap.put("amount",(long) 0);
		hashMap.put("AccId",(long) 0);

		String value = Withdraw(b, con, st, true);
		String value2 = "";
		if (value.equals("Succesful")) {
			value2 = Deposit(b, con, st, true);
			if (value2.equals("Succesful")) {
			} 
			else {
				long amount = b.getAmount();
				long AccId = b.getAccId();
				hashMap.put("amount",amount);
				hashMap.put("AccId",AccId);
			}
		} 
		else {
		}
		
		return hashMap ; 
						
	}
	

//	public static void Add_To_Roleback_List(long amo, long id) {
//		RoleBack_List.add(new Roleback(amo, id));
//	}

	public static synchronized boolean checkPassword(String pass, String Userpass) {
		if (pass.equals(Userpass))
			return true;
		return false;
	}

	public static synchronized int authorizeCard(String date, String pass, String Userpass, Long id,
			java.sql.Connection con, java.sql.Statement st, int type) {
		int valid = 0;
		if (date.compareTo(today) >= 0) {
			valid = 1;
			if (type == 0) {
				if (pass.equals(Userpass)) {
					valid = 2;
					try {
						String query_read = "SELECT * FROM `card` WHERE  `accountNumber`=%s";
						query_read = String.format(query_read, id);
						ResultSet resultSet = st.executeQuery(query_read);
						if (resultSet.next()) {
							int limit = resultSet.getInt("LimitWrongPin");
							if (limit < 3)
								valid = 10;
						}
						resultSet.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			}

		}
		return valid;
	}

	public static synchronized boolean authorizeCardExpDate(String date) {
		if (date.compareTo(today) >= 0)
			return true;
		return false;

	}

	public static synchronized Boolean ChechAmountBetween2Numbers(long amo) {
		if (amo <= 10000) {
			if (amo >= 100) {
				return true;
			}
		}
		return false;
	}

	public static synchronized Boolean ChechBalanceInAccount(long amo, Long id, java.sql.Connection con,
			java.sql.Statement st) {
		boolean lim = false;
		try {
			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {
				Long bal = resultSet.getLong("balance");
				if (bal >= amo)
					lim = true;
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lim;
	}

	public static synchronized Boolean ChechMoneyInBasket(long amo) {
		if (amo <= DispatcherWorkerMain.Basket_money) {
			DispatcherWorkerMain.Basket_money = DispatcherWorkerMain.Basket_money - amo;
			return true;
		}
		return false;

	}

	public static synchronized Boolean Chech_User_Reject_card() {
		if (rand.nextInt(50) + 50 < 90)
			return true;
		return false;

	}

	public static synchronized Boolean Chech_User_Reject_Money() {
		if (rand.nextInt(50) + 50 < 90)
			return true;
		return false;

	}

	public static synchronized Boolean Chech_limit_Request_perday(Long id, java.sql.Connection con, java.sql.Statement st) {
//		if (limitPerDay < 7)
//			return true;
//		return false;
		boolean lim = false;
		try {
			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {
				int limitPerDay = resultSet.getInt("limitRequestPerDay");
				if (limitPerDay < 7)
					lim = true;
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lim;
	}

	public static synchronized Boolean Chech_limit_Enter_Wrong_pin(Long id, java.sql.Connection con,
			java.sql.Statement st) {
		boolean lim = false;
		try {
			String query_read = "SELECT * FROM `card` WHERE  `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {
				int limit = resultSet.getInt("LimitWrongPin");
				if (limit < 3)
					lim = true;
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lim;
	}

	public static synchronized Boolean Chech_account_state(int state) {
		if (state == 0)
			return true;
		return false;
	}

	public static synchronized Account GetAccountInformation(long id, java.sql.Connection con, java.sql.Statement st) {

		try {
			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {
				Account ac = new Account(resultSet.getLong("accountNumber"), resultSet.getLong("personNumber"),
						resultSet.getLong("balance"), resultSet.getInt("limitRequestPerDay"),
						resultSet.getInt("accountState"));
				return ac;
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static synchronized Card GetCardInformation(long id, java.sql.Connection con, java.sql.Statement st) {

		try {
			String query_read = "SELECT * FROM `card` WHERE  `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {
				Card ca3 = new Card(resultSet.getLong("accountNumber"), resultSet.getLong("personNumber"),
						resultSet.getLong("cardNumber"), resultSet.getString("pin"),
						resultSet.getString("ExpirationDate"), resultSet.getInt("LimitWrongPin"),
						resultSet.getInt("DefaultAcc"));
				return ca3;
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static synchronized boolean Withdraw_Operation_inDataBase(Long amo, Long id, java.sql.Connection con,
			java.sql.Statement st) {

		boolean valid = false;
		try {
			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);

			if (resultSet.next()) {
				long mojodi = resultSet.getLong("balance") - amo;

				String query = "UPDATE `account` SET `balance`=%s WHERE `accountNumber`=%s";
				query = String.format(query, mojodi, id);
				st.execute(query);
				valid = true;
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;
	}

	public static synchronized boolean Deposit_Operation_inDataBase(Long amo, Long id, java.sql.Connection con,
			java.sql.Statement st) {

		boolean valid = false;

		try {

			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {

				long mojodi = resultSet.getLong("balance") + amo;

				String query = "UPDATE `account` SET `balance`=%s WHERE `accountNumber`=%s";
				query = String.format(query, mojodi, id);
				st.execute(query);
				valid = true;
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return valid;
	}

	public static synchronized boolean Balance_Operation_inDataBase(Long id, java.sql.Connection con,
			java.sql.Statement st) {

		boolean valid = false;

		try {

			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next())
				valid = true;
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return valid;
	}
	
	public static synchronized boolean rollback_Operation(java.sql.Connection con, java.sql.Statement st, long amo, long id) {
		boolean valid = false;
		try {
			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {
				Long balANCE = resultSet.getLong("balance") + amo;
				String query = "UPDATE `account` SET `balance`=%s WHERE `accountNumber`=%s";
				query = String.format(query, balANCE, id);
				st.execute(query);
				valid = true;
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return valid;
	}

//	public static synchronized boolean rollback_Operation_inDataBase(java.sql.Connection con, java.sql.Statement st) {
//		boolean valid = false;
//		try {
//
//			for (Roleback r : RoleBack_List) {
//				long id = r.getId();
//				String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
//				query_read = String.format(query_read, id);
//				ResultSet resultSet = st.executeQuery(query_read);
//				if (resultSet.next()) {
//					Long balANCE = resultSet.getLong("balance") + r.getAmo();
//					String query = "UPDATE `account` SET `balance`=%s WHERE `accountNumber`=%s";
//					query = String.format(query, balANCE, id);
//					st.execute(query);
//					valid = true;
//				}
//				resultSet.close();
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return valid;
//	}

	public static synchronized void Update_LimitPerDay_DB(Long id, int value, java.sql.Connection con,
			java.sql.Statement st) {

		try {

			String query_read = "SELECT * FROM `account` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {

				int value2 = resultSet.getInt("limitRequestPerDay") + 1;

				String query = "UPDATE `account` SET `limitRequestPerDay`=%s WHERE `accountNumber`=%s";
				query = String.format(query, value2, id);
				st.execute(query);

			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static synchronized void Update_LimitWrongPin_DB(Long id, int value, java.sql.Connection con,
			java.sql.Statement st) {

		try {

			String query_read = "SELECT * FROM `card` WHERE `accountNumber`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {

				int value2 = resultSet.getInt("LimitWrongPin") + 1;

				String query = "UPDATE `card` SET `LimitWrongPin`=%s WHERE `accountNumber`=%s";
				query = String.format(query, value2, id);
				st.execute(query);
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static synchronized void Update_Transaction_Result_InDB(String value, long id, java.sql.Connection con,
			java.sql.Statement st) {

		try {

			String query_read = "SELECT * FROM `trans` WHERE `trans_id`=%s";
			query_read = String.format(query_read, id);
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {

				String query = "UPDATE `trans` SET `Result`='%s' WHERE `trans_id`=%s";
				query = String.format(query, value, id);
				st.execute(query);

			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
