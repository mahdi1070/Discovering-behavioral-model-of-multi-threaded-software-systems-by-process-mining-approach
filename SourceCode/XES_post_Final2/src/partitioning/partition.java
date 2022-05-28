package partitioning;


import java.util.LinkedList;
import classes.FirstMethodInPartition;
import classes.PartitionInformation;

public class partition {

	LinkedList<FirstMethodInPartition> FirstMethod ;
	
	public LinkedList<PartitionInformation> calculate(int NumberOfTrace ,  int[] NumberOfEventPerTrace , int[][] ThreadId , String[][] event , String[][] conceptName)
	{
		FirstMethod = new LinkedList<FirstMethodInPartition>();
		
		
		LinkedList<PartitionInformation> TraceOut = new LinkedList<PartitionInformation>();
		for (int k = 0; k < NumberOfTrace; k++) {
			for (int j = 0; j < NumberOfEventPerTrace[k]; j++) {
				boolean exist = false;
				int index = 0;
				for (int n = 0; n < TraceOut.size(); n++) {
//					if ((TraceOut.get(n).id == ThreadId[k][j]) && (TraceOut.get(n).end == )) {
					if ((TraceOut.get(n).id == ThreadId[k][j]) && (TraceOut.get(n).trace == k )) {
						exist = true;
						index = n;
						break;
					}
				}
				if (exist) {
					TraceOut.get(index).content = TraceOut.get(index).content.concat("\n" + event[k][j]);					
				} else {
//					PartitionInformation list = new PartitionInformation(ThreadId[k][j], event[k][j], false ,k);
					PartitionInformation list = new PartitionInformation(ThreadId[k][j], event[k][j] ,k);
					TraceOut.add(list);	
					
					FirstMethodInPartition list2 = new FirstMethodInPartition(event[k][j], conceptName[k][j]);
					FirstMethod.add(list2) ; 
										
				}
			}
//			for (int n = 0; n < TraceOut.size(); n++) {
//				TraceOut.get(n).end = true;
//			}
		}
		
		setFirstMethod(FirstMethod) ;

		return TraceOut ; 
	}
	

	
	public LinkedList<FirstMethodInPartition> getFirstMethod() {
		return FirstMethod;
	}

	public void setFirstMethod(LinkedList<FirstMethodInPartition> firstMethod) {
		FirstMethod = firstMethod;
	}
}
