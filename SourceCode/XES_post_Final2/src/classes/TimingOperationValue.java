package classes;


public class TimingOperationValue {
	public String method ;
	public long min;
	public long max ;
	public long avg ;
	public long var;
	public long sd;
	public long sum ; 
	public long size ;
	public int id;
	public TimingOperationValue(String method, long min, long max, long avg, long var, long sd , long sum , long size , int id) {
		this.method = method;
		this.min = min;
		this.max = max;
		this.avg = avg;
		this.var = var;
		this.sd = sd;
		this.sum = sum ; 
		this.size = size ;
		this.id = id ;
	}
	
	public TimingOperationValue(String method, long min, long max, long avg, long var, long sd , long sum , long size ) {
		this.method = method;
		this.min = min;
		this.max = max;
		this.avg = avg;
		this.var = var;
		this.sd = sd;
		this.sum = sum ; 
		this.size = size ;
	}
	
	
}
