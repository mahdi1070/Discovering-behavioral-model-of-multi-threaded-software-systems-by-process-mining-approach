package AtmTeam;

public class TransactionState {
	Transaction tran ;
	Boolean end ;

	public TransactionState(Transaction tran, Boolean end) {
		this.tran = tran;
		this.end = end;
	}
	

	public Transaction getWords() {
		return tran;
	}


	public void setWords(Transaction tran) {
		this.tran = tran;
	}


	public Boolean getEnd() {
		return end;
	}
	public void setEnd(Boolean end) {
		this.end = end;
	}
	
	
}
