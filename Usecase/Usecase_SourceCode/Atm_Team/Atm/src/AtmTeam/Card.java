package AtmTeam;

public class Card {
	
	private long accNumber ;
	private long PersonNumber ;
	private long CardNumber ;
	private String pin ;
	private String ExpDate ;
	private int limitPin ;
	private int DefaultAcc ;

	public Card(long accNumber, long personNumber, long cardNumber, String pin, String expDate, int limitPin,int defaultAcc) {
		this.accNumber = accNumber;
		this.PersonNumber = personNumber;
		this.CardNumber = cardNumber;
		this.pin = pin;
		this.ExpDate = expDate;
		this.limitPin = limitPin;
		this.DefaultAcc = defaultAcc ;
	}

	
	
	
	public int getDefaultAcc() {
		return DefaultAcc;
	}




	public void setDefaultAcc(int defaultAcc) {
		this.DefaultAcc = defaultAcc;
	}




	public long getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(long accNumber) {
		this.accNumber = accNumber;
	}

	public long getPersonNumber() {
		return PersonNumber;
	}

	public void setPersonNumber(long personNumber) {
		PersonNumber = personNumber;
	}

	public long getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(long cardNumber) {
		CardNumber = cardNumber;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getExpDate() {
		return ExpDate;
	}

	public void setExpDate(String expDate) {
		ExpDate = expDate;
	}

	public int getLimitPin() {
		return limitPin;
	}

	public void setLimitPin(int limitPin) {
		this.limitPin = limitPin;
	}
	
	
	
}
