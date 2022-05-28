package PerMethod;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import classes.ArrowInformation;
import classes.MethodRelationModelsPerMethods;
import classes.PartitionInformation;

public class PerMethods {

	LinkedList<MethodRelationModelsPerMethods> MethodRelModelsList = new LinkedList<MethodRelationModelsPerMethods>();

	public void calculate( LinkedList<PartitionInformation> TraceOut,
			String[][] conceptName, int NumberOfTrace, String global, LinkedList<ArrowInformation> arrowLink , 
			int[][] ThreadId, String[][] events , String[][] transition) {
		
		
		Set<String> setModelRelated = new HashSet<String>();
		Set<String> set = new HashSet<String>();
		for (int j = 0; j < NumberOfTrace; j++) {
			for (int i = 0; i < conceptName[j].length; i++) {
				if (conceptName[j][i] != null)
					set.add(conceptName[j][i]);
			}
		}
		String current = "";
		int num = 0;
		Boolean exist = false;
		Iterator<String> i = set.iterator();
		try {
			while (i.hasNext()) {
				String CurrentName = String.valueOf(i.next());
				// System.out.println(CurrentName);
				for (int n = 0; n < TraceOut.size(); n++) {
					int idd = TraceOut.get(n).trace ;
					for (int m = 0; m < ThreadId[idd].length; m++) {
						if (TraceOut.get(n).id == ThreadId[idd][m]) {
							if (CurrentName.equals(conceptName[idd][m])) {
								if (!exist)
									current += "	 <trace>\n";
								exist = true;
								current += events[idd][m] + "\n";
								setModelRelated.add(conceptName[idd][m]);
								if (transition[idd][m].equals("start"))
									num++;
								else if (transition[idd][m].equals("complete"))
									num--;

							} else if (num > 0) {
								current += events[idd][m] + "\n";
								setModelRelated.add(conceptName[idd][m]);
							}
						}
					}
					if (exist)
						current += "	 </trace>\n";
					exist = false;
					num = 0;
				}

				FileWriter myWriter;
				myWriter = new FileWriter("output\\" + CurrentName + ".xes", true);
				myWriter.write(global);
				myWriter.write("\n");
				myWriter.write(current);
				myWriter.write("</log>");
				myWriter.close();

				current = "";

				String[] mdls = findModelRel(setModelRelated, arrowLink);
				if (mdls[0] != "") {
					MethodRelationModelsPerMethods md = new MethodRelationModelsPerMethods(CurrentName, mdls);
					MethodRelModelsList.add(md);

				}

				setModelRelated.clear();

			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\method_relModel.txt", true);

			Iterator<MethodRelationModelsPerMethods> MethodRelModelsListIterator = MethodRelModelsList.iterator();
			while (MethodRelModelsListIterator.hasNext()) {
				MethodRelationModelsPerMethods res2 = MethodRelModelsListIterator.next();
//			if(res2.models.length > 1)
//			{
				System.out.println("");
				myWriter.write("\n");

				System.out.print(res2.method + "\t");
				myWriter.write(res2.method + "\t");

				for (int jk = 0; jk < res2.models.length; jk++) {
					System.out.print(res2.models[jk] + "\t");
					myWriter.write(res2.models[jk] + "\t");
				}
				// }
				
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("");
	}

	String[] findModelRel(Set<String> setModelRelated, LinkedList<ArrowInformation> arrowLink) {

		String modelsStr = "";

		Iterator<String> i = setModelRelated.iterator();
		while (i.hasNext()) {
			String CurrentName = String.valueOf(i.next());
			Iterator<ArrowInformation> arrowLinkIterator = arrowLink.iterator();
			while (arrowLinkIterator.hasNext()) {
				ArrowInformation res2 = arrowLinkIterator.next();
				String caller = res2.CallerMethodInCallerModel;
				if (caller != null) {
					if (caller.equals(CurrentName)) {
						modelsStr += res2.model + " ";
					}
				}
			}
		}

//		String[] models = modelsStr.split(" ") ;
//		
//		for(int cv = 0 ; cv < models.length ; cv++) {
//			Iterator<ArrowInformation> arrowLinkIterator = arrowLink.iterator();
//			while (arrowLinkIterator.hasNext()) {
//				ArrowInformation res2 = arrowLinkIterator.next();
//				if (models[cv].equals(res2.CallerModel+"")) {
//					modelsStr += res2.model+" ";
//				}
//			}
//		}

		if (modelsStr != "") {
			Set<String> modelsSet = new HashSet<String>();
			Set<String> modelsSetTemp = new HashSet<String>();

			String[] models = modelsStr.split(" ");
			int modelsize = models.length;
			for (int j = 0; j < modelsize; j++) {
				modelsSet.add(models[j]);
				modelsSetTemp.add(models[j]);
			}
			while (true) {
				int lastSize = modelsSet.size();

				Iterator<String> l2 = modelsSetTemp.iterator();
				while (l2.hasNext()) {
					String CurrentSet = String.valueOf(l2.next());
					modelsSet.add(CurrentSet + "");
				}

//				modelsSet = modelsSetTemp ; 
				Iterator<String> l = modelsSet.iterator();
				while (l.hasNext()) {
					String CurrentSet = String.valueOf(l.next());
					Iterator<ArrowInformation> arrowLinkIterator = arrowLink.iterator();
					while (arrowLinkIterator.hasNext()) {
						ArrowInformation res2 = arrowLinkIterator.next();
						if (CurrentSet.equals(res2.CallerModel + "")) {
//							modelsSet.add(res2.model + "");
							modelsSetTemp.add(res2.model + "");
						}
					}
				}

//				int nextSize = modelsSet.size();
//				if (lastSize == nextSize)
//					break;

				// int nextSize = modelsSet.size();
				if (modelsSetTemp.size() == lastSize)
					break;
			}

			modelsStr = "";
			Iterator<String> l = modelsSet.iterator();
			while (l.hasNext()) {
				String CurrentSet = String.valueOf(l.next());
				modelsStr += CurrentSet + " ";

			}
		}
		String[] modelsFinal = modelsStr.split(" ");
		return modelsFinal;

	}
}
