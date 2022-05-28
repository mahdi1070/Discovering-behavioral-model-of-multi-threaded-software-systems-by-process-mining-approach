package multimodels;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import classes.ArrowInformation;
import classes.FirstMethodInPartition;
import classes.PartitionInformation;

public class MultiModel {

	static int ModelCount;
	Map<String, Integer> ModelOfFirstMethod;
	LinkedList<ArrowInformation> ArrowLink;

	public void calculate( int[][] ThreadId, String[][] conceptName , String[][] transition ,
			LinkedList<PartitionInformation> TraceOut, LinkedList<FirstMethodInPartition> FirstMethod ,  long[][]  idhash) {

		ArrowLink = new LinkedList<ArrowInformation>();
		ModelOfFirstMethod = new HashMap<String, Integer>();
		boolean mainconcept = false;
		ModelCount = 1;
		for (int n = 0; n < TraceOut.size(); n++) {
			int id = TraceOut.get(n).id;
			String concept = FirstMethod.get(n).concept;
			String event = FirstMethod.get(n).content;

			if (id == 1) {
				if (!mainconcept) {
					ArrowInformation arrowInf = new ArrowInformation(ModelCount, concept);
					ArrowLink.add(arrowInf);
				}
				mainconcept = true;
				ModelOfFirstMethod.put(concept, 1);
			}
			else {
				int ex = SearchMethod(ModelOfFirstMethod, concept);
				if (ex == 0) {
					ModelCount++;
					ModelOfFirstMethod.put(concept, ModelCount);
					
//					ResultOfConstructorInPartitions result = SearchModelCaller(n, TraceOut, concept,
//							event , ThreadId  ,  conceptName  , nano , transition ,  idhash);
//					if(result != null) {
//						String callerModel = FirstMethod.get(result.ret).concept;
//						int lev = SearchMethod(ModelOfFirstMethod, callerModel);
//						
//						ArrowInformation arrowInf = new ArrowInformation(ModelCount, concept, result.callerMethod,
//								callerModel, lev);
//						ArrowLink.add(arrowInf);
//					}
					
					
					String result = SearchModelCaller(n, TraceOut,
							event , ThreadId ,  idhash ,  transition , conceptName );
					if(result != "") {
						String[] Results =  result.split(" ");
						String callerModel = FirstMethod.get(Integer.parseInt(Results[0])).concept;
						int lev = SearchMethod(ModelOfFirstMethod, callerModel);
						
						ArrowInformation arrowInf = new ArrowInformation(ModelCount, concept, Results[1],
								callerModel, lev);
						ArrowLink.add(arrowInf);
					}

				} else {
					ModelOfFirstMethod.put(concept, ex);

//					ResultOfConstructorInPartitions result = SearchModelCaller(n, TraceOut, concept, event , ThreadId , conceptName , nano , transition , idhash);
//					if(result != null) {
//						String callerModel = FirstMethod.get(result.ret).concept;
//						int lev = SearchMethod(ModelOfFirstMethod, callerModel);
//						int modelC = SearchMethod(ModelOfFirstMethod, concept);
//						
//						ArrowInformation arrowInf = new ArrowInformation(modelC, concept, result.callerMethod, callerModel,
//								lev);
//						if(searchArrowInformation(arrowInf))
//						{	
//							ArrowLink.add(arrowInf);
//						}
//					}
					
					
					String result = SearchModelCaller(n, TraceOut,
							event , ThreadId ,  idhash ,  transition , conceptName );
					if(result != "") {
						String[] Results =  result.split(" ");
						String callerModel = FirstMethod.get(Integer.parseInt(Results[0])).concept;
						int lev = SearchMethod(ModelOfFirstMethod, callerModel);
						int modelC = SearchMethod(ModelOfFirstMethod, concept);
						
						ArrowInformation arrowInf = new ArrowInformation(modelC, concept, Results[1],
								callerModel, lev);
						if(searchArrowInformation(arrowInf))
							{
							ArrowLink.add(arrowInf);
							}
					}
				}

			}
		}

		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\model_relation.txt", true);

			setArrowLink(ArrowLink);
			Iterator<ArrowInformation> arrowLinkIterator = ArrowLink.iterator();
			while (arrowLinkIterator.hasNext()) {
				ArrowInformation res2 = arrowLinkIterator.next();
				String print = res2.model + "\t" + res2.FirstConceptInThisModel + "\t" + res2.FirstConceptInCallerModel
						+ "\t" + res2.CallerMethodInCallerModel + "\t" + res2.CallerModel;
				System.out.println(print);

				myWriter.write(print);
				myWriter.write("\n");
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Map<String, Integer> getModelOfFirstMethod()
	{
		return ModelOfFirstMethod;
	}

	public Boolean searchArrowInformation(ArrowInformation arrowInf) {

		boolean ex = true;
		Iterator<ArrowInformation> arrowLinkIterator = ArrowLink.iterator();
		while (arrowLinkIterator.hasNext()) {
			ArrowInformation res2 = arrowLinkIterator.next();
			if (res2.model == arrowInf.model && res2.CallerModel == arrowInf.CallerModel
					&& res2.FirstConceptInThisModel.equals(arrowInf.FirstConceptInThisModel)
					&& res2.CallerMethodInCallerModel.equals(arrowInf.CallerMethodInCallerModel)
					&& res2.FirstConceptInCallerModel.equals(arrowInf.FirstConceptInCallerModel)) {
				ex = false;
				break;
			}
		}
		return ex;
	}

//	public ResultOfConstructorInPartitions SearchModelCaller(int index, LinkedList<PartitionInformation> TraceOut, String concept,
//			String event , int[][] threadid , String[][] conceptName , long[][] nano , String[][]  transition , long[][] idhash) {
//		// boolean fin = false;
//		int a = concept.length();
//		String con = "" ;
//		if(concept.contains(".run()"))
//		{
//			con = concept.substring(0, a - 6);
//		}else if(concept.contains(".compute()"))
//		{
//			con = concept.substring(0, a - 10);
//		}
////		if(con.contains("("))
////		{
////			int s = con.indexOf("(");
////			con = con.substring(0, s);
////		}
//		con = con.concat("(");		
//		LinkedList<String> linkCallerMethod = new LinkedList<String>();
//		LinkedList<ResultOfConstructorInPartitions> listCaller = new LinkedList<ResultOfConstructorInPartitions>();
//		
//		for (int n = index - 1; n >= 0; n--) {
//			// fin = false;
//			int idd = TraceOut.get(n).trace ;
//			for (int m = 0; m < threadid[idd].length ; m++) {
//				if ((TraceOut.get(n).id == threadid[idd][m] )) {
//					if (conceptName[idd][m].contains(con)) {
//					//	linkCallerMethod.remove(MethodOut.get(m).eventname);
//						
////						long hash = Calculate_IdHashCode(events[idd][m]);
//						long hash = idhash[idd][m];
//						ResultOfConstructorInPartitions result = new ResultOfConstructorInPartitions(n,
//								linkCallerMethod.getLast(), nano[idd][m], hash);
//						listCaller.add(result);
//						// fin = true;
//					}else
//					{
////						if(idhash[idd][m] == Calculate_IdHashCode(event))
////						{
////							long hash = idhash[idd][m];
////							ResultOfConstructorInPartitions result = new ResultOfConstructorInPartitions(n,
////									linkCallerMethod.getLast(), nano[idd][m], hash);
////							listCaller.add(result);
////						}else
////						{
//						
//							if (transition[idd][m].equals("start")) {
//								linkCallerMethod.add(conceptName[idd][m]);
//							} else if (transition[idd][m].equals("complete")) {
//								linkCallerMethod.remove(conceptName[idd][m]);
//							}
//			//			}
//					}
//				}
////				if (fin)
////					break;
//			}
//			linkCallerMethod.clear();
//		}
//		if(listCaller.isEmpty())
//		{
//	//		System.out.println(con +"     "+ event + "" );
//
//		}else
//		{
//			LinkedList<ResultOfConstructorInPartitions> listCallerFinal = new LinkedList<ResultOfConstructorInPartitions>();
//
//			Iterator<ResultOfConstructorInPartitions> listCallerIterator = listCaller.iterator();
//			while (listCallerIterator.hasNext()) {
//				ResultOfConstructorInPartitions res2 = listCallerIterator.next();
//				if (res2.idhash == Calculate_IdHashCode(event)) {
//					listCallerFinal.add(res2);
//					break;
//				}
//			}
//			if(listCallerFinal.isEmpty())
//			{
//		//		System.out.println(con +"     "+ event + "" );
//
//			}else
//			{
//		//		System.out.println(con +"     "+ listCallerFinal.getFirst() + "" );
//				return listCallerFinal.getFirst();
//			}
//		}
//		return null;
//
//	}
	
	
	public String SearchModelCaller(int index, LinkedList<PartitionInformation> TraceOut,
			String event , int[][] threadid  , long[][] idhash , String[][]   transition , String[][]   conceptName ) {	
		String Caller = "" ; 
		LinkedList<String> linkCallerMethod = new LinkedList<String>();
		for (int n = index - 1; n >= 0; n--) {
			int idd = TraceOut.get(n).trace ;
			for (int m = 0; m < threadid[idd].length ; m++) {
				if ((TraceOut.get(n).id == threadid[idd][m] )) {
					if (Calculate_IdHashCode(event) == idhash[idd][m]) {  
						Caller = n + " "+linkCallerMethod.getLast() + "" ;
					}else
					{	
						if (transition[idd][m].equals("start")) {
							linkCallerMethod.add(conceptName[idd][m]);
						} else if (transition[idd][m].equals("complete")) {
							linkCallerMethod.remove(conceptName[idd][m]);
						}
					}
				}
			}
			
			linkCallerMethod.clear();
			
			if(Caller != "")
			{
				break ;
			}
		}
		return Caller;
	}

	public long Calculate_IdHashCode(String event) {
		long th = 0;
		Scanner scanner = new Scanner(event);
		while (scanner.hasNextLine()) {
			String data = scanner.nextLine();
			if (data.contains("apploc:idhashcode")) {
				int in = 42;
				while (data.length() > 0) {
					char ch = data.charAt(in);
					int asciiValue = (int) ch;
					if (asciiValue >= 48 && asciiValue <= 57) {
						th = th * 10;
						th += Character.getNumericValue(ch);
						in++;
					} else {
						break;
					}
				}
			}
		}
		scanner.close();
		return th;
	}

	public void WriteInFile(String global, LinkedList<PartitionInformation> TraceOut , LinkedList<FirstMethodInPartition> FirstMethod) {
		int gggg = 1;
		try {
			while (gggg <= ModelCount) {
				FileWriter myWriter;
				myWriter = new FileWriter("output\\model" + gggg + ".xes", true);
				myWriter.write(global);
				myWriter.write("\n");

				for (int n = 0; n < TraceOut.size(); n++) {
//					int id = TraceOut.get(n).id;
//					if (ModelOfThreadId.get(id + "") == gggg) {
//						myWriter.write("	<trace>\n");
//						myWriter.write(TraceOut.get(n).content);
//						myWriter.write("\n");
//						myWriter.write("	</trace>\n");
//					}
						String con = FirstMethod.get(n).concept ;
				//		int id = TraceOut.get(n).id;
						if (ModelOfFirstMethod.get(con) == gggg) {
							myWriter.write("	<trace>\n");
							myWriter.write(TraceOut.get(n).content);
							myWriter.write("\n");
							myWriter.write("	</trace>\n");
						}
				}
				myWriter.write("</log>");
				myWriter.close();
				gggg++;
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
//
//	static int SearchThread(Map<String, Integer> level, int id) {
//
//		boolean flag = level.containsKey(id + "");
//		if (flag) {
//			return level.get(id + "");
//		}
//		return 0;
//	}

	static int SearchMethod(Map<String, Integer> level, String name) {

		boolean flag = level.containsKey(name);
		if (flag) {
			return level.get(name);
		}
		return 0;
	}

	public LinkedList<ArrowInformation> getArrowLink() {
		return ArrowLink;
	}

	public void setArrowLink(LinkedList<ArrowInformation> arrowLink) {
		this.ArrowLink = arrowLink;
	}

}
