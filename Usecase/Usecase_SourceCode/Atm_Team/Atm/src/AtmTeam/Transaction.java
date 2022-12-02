package AtmTeam;

public class Transaction {
	
	
	private long transId ;
	private long cardId ;
	private String pin ;
	private long accId ;
	private String transDate ;
	private int type ;
	private long amount ;
	private long accId_To ;
	private long cardId_to ;
	private String result ;

	
	public Transaction(long transId , long cardId,String pin, long accId ,String transDate , int type , long amount ,long cardId_to  , long accId_To, String result) {
		this.accId = accId ;
		this.cardId = cardId ;
		this.pin = pin ;
		this.transId = transId ; 
		this.transDate = transDate ;
		this.type = type ;
		this.amount = amount ;
		this.accId_To = accId_To;
		this.result = result ;
		this.accId_To = accId_To ; 
	}
	

	public long getCardId_to() {
		return cardId_to;
	}


	public void setCardId_to(long cardId_to) {
		this.cardId_to = cardId_to;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public long getTransId() {
		return transId;
	}


	public void setTransId(long transId) {
		this.transId = transId;
	}


	public long getAccId() {
		return accId;
	}


	public void setAccId(long accId) {
		this.accId = accId;
	}


	public String getTransDate() {
		return transDate;
	}


	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public long getAmount() {
		return amount;
	}


	public void setAmount(long amount) {
		this.amount = amount;
	}


	public long getAccId_To() {
		return accId_To;
	}


	public void setAccId_To(long accId_To) {
		this.accId_To = accId_To;
	}
	
	

}
