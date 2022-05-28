package classes;


public class TimingClass {

	public int trace ;
	public int id ;
	public String method ;
	public Long nano ;
	public int num ;
	public boolean end ;
	public int lable;
	public TimingClass(int trace, int id, String method, Long nano , boolean end, int num) {
		this.trace = trace;
		this.id = id;
		this.method = method;
		this.nano = nano;
		this.num = num ; 
		this.end = end ;
	}
	
	public TimingClass(String method, Long nano , int trace, int id, int lable  , boolean end , int num) {
		this.trace = trace;
		this.id = id;
		this.method = method;
		this.nano = nano;
		this.lable = lable;
		this.end = end ;
		this.num = num ; 
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
