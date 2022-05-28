package classes;


public class PartitionInformation {
	public int id;
	public String content;
//	public Boolean end;
	public int trace ; 
	public PartitionInformation(int id, String content , int trace ) {
		this.id = id;
		this.content = content;
//		this.end = end;
		this.trace = trace ; 
	}	
}