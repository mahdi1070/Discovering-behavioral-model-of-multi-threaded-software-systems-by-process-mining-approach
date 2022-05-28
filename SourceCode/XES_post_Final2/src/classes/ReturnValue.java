package classes;


public class ReturnValue {
	int numberOfTrace ;
	int[] NumberOfEventPerTrace ;
	String[][] event ;
	int[][] ThreadId ;
	String global ;
	String[][] eventName ;
	String[][] CallereventName ;
	String[][] transition ; 
	int[][] linear ;
	long[][] nanotime ;
	long[][] idhashcode ;

	public ReturnValue(int numberOfTrace, int[] numberOfEventPerTrace, String[][] event, int[][] threadId ,String global ,String[][] eventName
			,String[][] CallereventName  ,String[][]  transition , int[][] linear , long[][] idhashcode , long[][] nanotime) {
		this.numberOfTrace = numberOfTrace; 
		this.NumberOfEventPerTrace = numberOfEventPerTrace ;
		this.event = event ;
		this.ThreadId = threadId;
		this.global = global ;
		this.eventName = eventName ; 
		this.CallereventName = CallereventName ;
		this.transition = transition ; 
		this.nanotime = nanotime ;
		this.idhashcode = idhashcode ;
		this.linear = linear ; 
	}

	

	public String[][] getTransition() {
		return transition;
	}



	public void setTransition(String[][] transition) {
		this.transition = transition;
	}



	public String[][] getEventName() {
		return eventName;
	}


	public void setEventName(String[][] eventName) {
		this.eventName = eventName;
	}


	public String[][] getCallereventName() {
		return CallereventName;
	}


	public void setCallereventName(String[][] callereventName) {
		CallereventName = callereventName;
	}


	public String getGlobal() {
		return global;
	}


	public void setGlobal(String global) {
		this.global = global;
	}


	public int getNumberOfTrace() {
		return numberOfTrace;
	}


	public void setNumberOfTrace(int numberOfTrace) {
		this.numberOfTrace = numberOfTrace;
	}


	public int[] getNumberOfEventPerTrace() {
		return NumberOfEventPerTrace;
	}


	public void setNumberOfEventPerTrace(int[] numberOfEventPerTrace) {
		NumberOfEventPerTrace = numberOfEventPerTrace;
	}


	public String[][] getEvent() {
		return event;
	}


	public void setEvent(String[][] event) {
		this.event = event;
	}


	public int[][] getThreadId() {
		return ThreadId;
	}


	public void setThreadId(int[][] threadId) {
		ThreadId = threadId;
	}



	public int[][] getLinear() {
		return linear;
	}



	public void setLinear(int[][] linear) {
		this.linear = linear;
	}



	public long[][] getNanotime() {
		return nanotime;
	}



	public void setNanotime(long[][] nanotime) {
		this.nanotime = nanotime;
	}



	public long[][] getIdhashcode() {
		return idhashcode;
	}



	public void setIdhashcode(long[][] idhashcode) {
		this.idhashcode = idhashcode;
	}
	
	
}
