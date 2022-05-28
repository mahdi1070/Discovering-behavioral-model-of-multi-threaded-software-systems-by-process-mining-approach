package classes;

public class ResultOfConstructorInPartitions {
	public int ret;
	public String callerMethod;
	public Long idhash ;
	
	public ResultOfConstructorInPartitions(int ret, String callerMethod , long idhash) {
		this.ret = ret;
		this.callerMethod = callerMethod;
		this.idhash = idhash ; 
	}
}