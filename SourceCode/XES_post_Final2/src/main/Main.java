package main;


import java.util.LinkedList;
import java.util.Map;

import PerMethod.PerMethods;
import PerThread.PerThreads;
import classes.ArrowInformation;
import classes.FirstMethodInPartition;
import classes.PartitionInformation;
import classes.ReturnValue;
import multimodels.MultiModel;
import partitioning.partition;
import read.ReadFile;
import timing.Timing;

public class Main {

	public static void main(String[] args) {

		ReturnValue r = ReadFile.Process();
		int NumberOfTrace = r.getNumberOfTrace();
		int[] NumberOfEventPerTrace = r.getNumberOfEventPerTrace();
		String[][] event = r.getEvent();
		int[][] ThreadId = r.getThreadId();
		String global = r.getGlobal();
		String[][] conceptName = r.getEventName();
		String[][] transition = r.getTransition();
//		int[][] linear = r.getLinear() ;
//		String[][] CallerConceptName = r.getCallereventName() ;
		long[][] nanotime = r.getNanotime() ;
		long[][] idhashcode = r.getIdhashcode() ;
		System.out.println("Read Finish");
		
		MultiModel pl = new MultiModel();
		PerThreads pt = new PerThreads();
		PerMethods pm = new PerMethods();
		partition toc = new partition();
//		Timing tim = new Timing() ; 
		
		
		
		LinkedList<FirstMethodInPartition> FirstMethod ;
		LinkedList<ArrowInformation> arrowLink ;   
		
		
		LinkedList<PartitionInformation> partitionOut = toc.calculate(NumberOfTrace, NumberOfEventPerTrace, ThreadId, event,
				conceptName);
		
		FirstMethod = toc.getFirstMethod() ; 
		
		pl.calculate(ThreadId, conceptName  , transition , partitionOut , FirstMethod , idhashcode);
		pl.WriteInFile(global, partitionOut , FirstMethod);
		System.out.println("Multi Model Finish");
		
		arrowLink = pl.getArrowLink();
		Map<String, Integer> modelFM = pl.getModelOfFirstMethod();
		pm.calculate(partitionOut, conceptName, NumberOfTrace, global , arrowLink , ThreadId , event , transition);
		System.out.println("Per Method Finish");
		
		pt.WriteInFile(global, partitionOut);
		System.out.println("Per Threads Finish");
//		tim.calculate(partitionOut, conceptName , ThreadId ,  nanotime , transition);
//		tim.calculate(partitionOut, conceptName , ThreadId ,  nanotime , transition ,modelFM , FirstMethod );
//		System.out.println("Timing Finish");

	
	}
}
