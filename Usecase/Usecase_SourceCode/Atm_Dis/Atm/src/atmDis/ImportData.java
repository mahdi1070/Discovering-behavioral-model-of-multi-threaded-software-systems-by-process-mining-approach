package atmDis;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImportData {
	static Random rand = new Random();

	
	static int person = 100 ; 
	static int acc = 150 ; 
	static int updateState = 25 ; 
	static int card = 100 ; 
	static int tran = 120 ; 

	
	public static void main(String[] args) {

//		insertPerson();
//		insertAccount();
//		updateField();
//		insertCard();
		insertTrans();
	}

	public static long generateID(int digit) {
		Random rnd = new Random();
		char[] digits = new char[digit];
		digits[0] = (char) (rnd.nextInt(9) + '1');
		for (int i = 1; i < digits.length; i++) {
			digits[i] = (char) (rnd.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

	public static void insertPerson() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";

			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();
			ResultSet resultSet = null;
			int i = 0;
			while (i < person) {
				Long personNum = generateID(5);
				String personId = "" + personNum;
				Long personIdLong = Long.parseLong(personId);

				String query_read = "SELECT * FROM `person` WHERE `personNumber`=%s";
				query_read = String.format(query_read, personIdLong);
				resultSet = st.executeQuery(query_read);
				if (!resultSet.next()) {
					String query = "INSERT INTO `person`(`personNumber`) VALUES (%s)";
					query = String.format(query, personIdLong);
					st.execute(query);
					i++;
				}
			}
			resultSet.close();
			st.close();
			con.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertAccount() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";

			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();
			ResultSet resultSet = null;
			int i = 0;
			while (i < acc) {
				Long personNum = generateID(5);
				String personId = "" + personNum;
				Long personIdLong = Long.parseLong(personId);

				String query_read = "SELECT * FROM `person` WHERE `personNumber`=%s";
				query_read = String.format(query_read, personIdLong);
				resultSet = st.executeQuery(query_read);
				if (resultSet.next()) {
					Long accNum = generateID(7);
					String accId = "" + accNum;
					Long accIdLong = Long.parseLong(accId);

					String query_read2 = "SELECT * FROM `account` WHERE `accountNumber`=%s";
					query_read2 = String.format(query_read2, accIdLong);
					resultSet = st.executeQuery(query_read2);
					if (!resultSet.next()) {

						int mojodi = rand.nextInt(30000) + 1000;
						int limit = rand.nextInt(5);
						int state = 0;
						String query = "INSERT INTO `account`(`accountNumber`, `personNumber`, `balance`, `limitRequestPerDay`, `accountState`) VALUES (%s , %s , %s , %s , %s)";
						query = String.format(query, accIdLong, personIdLong, mojodi, limit, state);
						st.execute(query);
						i++;
					}
				}
			}
			resultSet.close();
			st.close();
			con.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateField() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";

			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();
			int i = 0;
			while (i < updateState) {
				String query_read = "SELECT * FROM  `account` ORDER BY RAND() LIMIT 1";
				ResultSet resultSet = st.executeQuery(query_read);
				if (resultSet.next()) {
					int state = resultSet.getInt("accountState");
					Long id = resultSet.getLong("accountNumber");

					if (state == 0) {
						String query = "UPDATE `account` SET `accountState`='%s' WHERE `accountNumber`=%s";
						query = String.format(query, 1, id);
						st.execute(query);
						i++;
					}
				}
				resultSet.close();

			}
			st.close();
			con.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public static void insertCard() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";

			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();
			int i = 0;
			while (i < card) {
				String query_read = "SELECT * FROM  `account` ORDER BY RAND() LIMIT 1";
				ResultSet resultSet = st.executeQuery(query_read);
				if (resultSet.next()) {
					Long PersonId = resultSet.getLong("personNumber");
					Long accIdLong = resultSet.getLong("accountNumber");

					Long cardNum = generateID(5);
					String cardId = "6104" + cardNum;
					Long cardIdLong = Long.parseLong(cardId);

					String query_read2 = "SELECT * FROM  `card` WHERE `accountNumber`=%s";
					query_read2 = String.format(query_read2, accIdLong);
					resultSet = st.executeQuery(query_read2);
					if (!resultSet.next()) {
						String query_read3 = "SELECT * FROM  `card` WHERE `personNumber`=%s";
						query_read3 = String.format(query_read3, PersonId);
						resultSet = st.executeQuery(query_read3);
						if (!resultSet.next()) {

							String query_read4 = "SELECT * FROM  `card` WHERE `cardNumber`=%s";
							query_read4 = String.format(query_read4, cardIdLong);
							resultSet = st.executeQuery(query_read4);
							if (!resultSet.next()) {

								String password = generatePass();
								String localdate = generateDate().toString();

								String query = "INSERT INTO `card`(`cardNumber`, `pin`, `ExpirationDate`, `LimitWrongPin`, `accountNumber`, `personNumber`,`DefaultAcc`) VALUES (%s,'%s','%s',0,%s,%s,%s)";
								query = String.format(query, cardIdLong, password, localdate, accIdLong, PersonId, 1);
								st.execute(query);
								i++;
							}

						} else {
							Long cardId2 = resultSet.getLong("cardNumber");
							String password = resultSet.getString("pin");
							String localdate = resultSet.getString("ExpirationDate");
							int limit = resultSet.getInt("LimitWrongPin");

							String query = "INSERT INTO `card`(`cardNumber`, `pin`, `ExpirationDate`, `LimitWrongPin`, `accountNumber`, `personNumber`,`DefaultAcc`) VALUES (%s,'%s','%s',%s,%s,%s,%s)";
							query = String.format(query, cardId2, password, localdate, limit, accIdLong, PersonId, 0);
							st.execute(query);
							i++;
						}
					}
				}
				resultSet.close();

			}
			st.close();
			con.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<Transaction> readTrans() {

		List<Transaction> trans_List = new ArrayList<Transaction>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";
			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();

			String query_read = "SELECT * FROM `trans`";
			ResultSet resultSet = st.executeQuery(query_read);
			if (resultSet.next()) {
				resultSet.beforeFirst();
				while (resultSet.next()) {
					if (resultSet.getInt("type") == 1) {
						Transaction t = new Transaction(resultSet.getLong("trans_id"),
								resultSet.getLong("card_id"), resultSet.getString("pin"),
								resultSet.getLong("account_id"), resultSet.getString("date"),
								resultSet.getInt("type"),
								resultSet.getLong("amount"),0,0,"");
						trans_List.add(t);
					} else if (resultSet.getInt("type") == 2) {
						Transaction t = new Transaction(resultSet.getLong("trans_id"),
								resultSet.getLong("card_id"), resultSet.getString("pin"),
								resultSet.getLong("account_id"), resultSet.getString("date"),
								resultSet.getInt("type"),0,0,0,"");
						trans_List.add(t);
					} else if (resultSet.getInt("type") == 3) {
						Transaction t = new Transaction(resultSet.getLong("trans_id"),
								resultSet.getLong("card_id"), resultSet.getString("pin"),
								resultSet.getLong("account_id"), resultSet.getString("date"),
								resultSet.getInt("type"),
								resultSet.getLong("amount"),
								resultSet.getLong("card_id_to"),
								resultSet.getLong("account_id_to"),"");
						trans_List.add(t);
					}
				}

			}
			resultSet.close();
			st.close();
			con.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return trans_List;

	}

	public static String generatePass() {
		int a = rand.nextInt(10);
		int b = rand.nextInt(10);
		int c = rand.nextInt(10);
		int d = rand.nextInt(10);
		String pass = a + "" + b + "" + c + "" + d + "";
		return pass;

	}

	public static String changePin(String pass) {
		int a = Integer.parseInt(pass.charAt(0) + "");
		int b = Integer.parseInt(pass.charAt(1) + "");
		int c = Integer.parseInt(pass.charAt(2) + "");
		int d = Integer.parseInt(pass.charAt(3) + "");
		int r = rand.nextInt(10);
		if (r > 7) {
			if (a != 9)
				a = a + 1;
			else
				a = 0;
		}
		String password = a + "" + b + "" + c + "" + d + "";
		return password;

	}

	public static LocalDate generateDate() {
		int minDay = (int) LocalDate.of(2019, 8, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2030, 1, 1).toEpochDay();
		long randomDay = minDay + rand.nextInt(maxDay - minDay);

		LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);

		return randomBirthDate;
	}

	public static void insertTrans() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/atm?user=root";

			java.sql.Connection con = DriverManager.getConnection(url);
			java.sql.Statement st = con.createStatement();
			int i = 0;
			while (i < tran) {
				String query_read = "SELECT * FROM  `card` ORDER BY RAND() LIMIT 1";
				ResultSet resultSet = st.executeQuery(query_read);
				if (resultSet.next()) {
					Long accIdLong = resultSet.getLong("accountNumber");
					Long cardIdLong = resultSet.getLong("cardNumber");
					String pin2 = resultSet.getString("pin");

					String pin = changePin(pin2);

					int type = rand.nextInt(3) + 1;
					// 3 = transfer , 1 = withdraw , 2 = mojody

					if (type == 3) {
						while (true) {
							String query_read2 = "SELECT * FROM  `card` ORDER BY RAND() LIMIT 1";
							resultSet = st.executeQuery(query_read2);
							if (resultSet.next()) {
								int Default = resultSet.getInt("DefaultAcc");
								if (Default == 1) {
									Long acc_id_to = resultSet.getLong("accountNumber");
									Long cardId_to = resultSet.getLong("cardNumber");
									if (!acc_id_to.toString().equals(accIdLong.toString())) {
										if (!cardId_to.toString().equals(cardIdLong.toString())) {
											LocalDate myObj2 = LocalDate.now();
											int amount = rand.nextInt(10000) + 100;
											String query = "INSERT INTO `trans`(`card_id`, `pin`, `account_id`, `date`, `type`, `amount`, `card_id_to`, `account_id_to`) VALUES (%s,'%s',%s,'%s',%s,%s,%s,%s)";
											query = String.format(query, cardIdLong, pin, accIdLong, myObj2, type,
													amount,cardId_to, acc_id_to);
											st.execute(query);
											i++;
											break;
										}
									}
								}
							}
						}
					} else if (type == 2) {
						LocalDate myObj2 = LocalDate.now();
						String query = "INSERT INTO `trans`(`card_id`, `pin`, `account_id`, `date`, `type`) VALUES (%s,'%s',%s,'%s',%s)";
						query = String.format(query, cardIdLong, pin, accIdLong, myObj2, type);
						st.execute(query);
						i++;

					} else if (type == 1) {
						LocalDate myObj2 = LocalDate.now();
						int amount = rand.nextInt(10000) + 100;
						String query = "INSERT INTO `trans`(`card_id`, `pin`, `account_id`, `date`, `type`, `amount`) VALUES (%s,'%s',%s,'%s',%s,%s)";
						query = String.format(query, cardIdLong, pin, accIdLong, myObj2, type, amount);
						st.execute(query);
						i++;
					}

				}
				resultSet.close();

			}
			st.close();
			con.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
