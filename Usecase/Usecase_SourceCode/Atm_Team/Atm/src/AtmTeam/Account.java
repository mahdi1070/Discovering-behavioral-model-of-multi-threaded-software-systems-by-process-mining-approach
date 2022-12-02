package AtmTeam;

public class Account {
	private long accId ;
	private long PersonNumber ;
	private long balance ;
	private int limitDay ;
	private int state ;
	
	public Account(long accId, long PersonNumber,long balance,int limitDay, int state) {
		this.accId = accId;
		this.PersonNumber = PersonNumber;
		this.balance = balance;
		this.limitDay = limitDay;
		this.state = state;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	
	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}


	

	public long getPersonNumber() {
		return PersonNumber;
	}

	public void setPersonNumber(long personNumber) {
		PersonNumber = personNumber;
	}

	public int getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(int limitDay) {
		this.limitDay = limitDay;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
	

}
