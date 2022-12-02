package atmDis;

public class Roleback {
	private long amo ;
	private long id ;
	
	public Roleback(long amo, long id) {
		this.amo = amo;
		this.id = id;
	}

	public long getAmo() {
		return amo;
	}

	public void setAmo(long amo) {
		this.amo = amo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
