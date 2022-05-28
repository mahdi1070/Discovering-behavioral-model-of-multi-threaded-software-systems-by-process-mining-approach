//package timing;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.Set;
//
//import classes.PartitionInformation;
//import classes.TimingClass;
//import classes.TimingOperationValue;
//
//public class Timing {
//	LinkedList<TimingClass> timeList;
//	LinkedList<TimingOperationValue> timingvalue;
//
//	public void calculate( LinkedList<PartitionInformation> TraceOut,
//			String[][] conceptName , int[][] ThreadId, long[][] nano , String[][] transition) {
//
//		timingvalue = new LinkedList<TimingOperationValue>();
//		timeList = new LinkedList<TimingClass>();
//		for (int n = 0; n < TraceOut.size(); n++) {
//			int idd = TraceOut.get(n).trace ; 
//			for (int m = 0; m < ThreadId[idd].length; m++) {
//				if (TraceOut.get(n).id == ThreadId[idd][m]) {
//					if (transition[idd][m].equals("start")) {
//						int in = SearchInLink(timeList, conceptName[idd][m]);
//						if (in == -1) {
//							TimingClass tc = new TimingClass(n, TraceOut.get(n).id, conceptName[idd][m],
//									nano[idd][m], false, 0);
//							timeList.add(tc);
//						} else {
//							updateInLink(1, in, timeList, conceptName[idd][m],
//									nano[idd][m]);
//						}
//					} else if (transition[idd][m].equals("complete")) {
//						int in = SearchInLink(timeList,  conceptName[idd][m]);
//						if (in != -1) {
//							updateInLink(2, in, timeList, conceptName[idd][m], nano[idd][m]);
//						}
//
//					}
//				}
//			}
//		}
//
//		try {
//			FileWriter myWriter;
//			myWriter = new FileWriter("output\\timing.txt", true);
//			
//			Iterator<TimingClass> timeListIterator3 = timeList.iterator();
////			System.out.println("trace" + "\t" + "id" + "\t" + "nanotime" + "\t" + "method");
//
//
//			myWriter.write("trace" + "\t" + "id" + "\t" + "nanotime" + "\t" + "method");
//			myWriter.write("\n");
//			
//			
//			while (timeListIterator3.hasNext()) {
//				TimingClass tc2 = timeListIterator3.next();
////				System.out.println(tc2.trace + "\t" + tc2.id  + "\t" + tc2.nano + "\t" + tc2.method);
//				
//				myWriter.write(tc2.trace + "\t" + tc2.id  + "\t" + tc2.nano + "\t" + tc2.method);
//				myWriter.write("\n");
//			}
//
//			myWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////		calculateMeyarha() ; 
////		System.out.println("");
//	}
//
//	void calculateMeyarha() {
//		Set<String> set = new HashSet<String>();
//		Iterator<TimingClass> timeListIterator = timeList.iterator();
//		while (timeListIterator.hasNext()) {
//			TimingClass tc2 = timeListIterator.next();
//			set.add(tc2.getMethod());
//		}
//		Iterator<String> i = set.iterator();
//		while (i.hasNext()) {
//			String CurrentName = String.valueOf(i.next());
//			LinkedList<Long> data = new LinkedList<Long>();
//			Iterator<TimingClass> timeListIterator2 = timeList.iterator();
//			while (timeListIterator2.hasNext()) {
//				TimingClass tc2 = timeListIterator2.next();
//				if (tc2.getMethod().equals(CurrentName)) {
//					data.add(tc2.nano);
//				}
//			}
//			Operation(CurrentName, data);
//		}
//		Iterator<TimingOperationValue> timingvalueItar = timingvalue.iterator();
//		System.out.println("min(10^-6 = ms)" + "\t" + "max(10^-6 = ms)" + "\t" + "avg(10^-6 = ms)" + "\t"
//				+ "sum(10^-6 = ms)" + "\t" + "var(10^-12 = ms)" + "\t" + "sd(10^-6 = ms)" + "\t" + "method" + "\t");
//
//		while (timingvalueItar.hasNext()) {
//			TimingOperationValue tc2 = timingvalueItar.next();
//			System.out.println(tc2.min + "\t" + tc2.max + "\t" + tc2.avg + "\t" + tc2.sum + "\t" + tc2.var + "\t"
//					+ tc2.sd + "\t" + tc2.method + "\t");
//		}
//	}
//
//	public void Operation(String name, LinkedList<Long> data) {
//		long min = findMin(data);
//		long max = findMax(data);
//		long[] ret = findAvg(data);
//		long var = findVar(data, ret[0]);
//		long sd = findSD(var);
//		TimingOperationValue tov = new TimingOperationValue(name, min, max, ret[0], var, sd, ret[1], ret[2]);
//		timingvalue.add(tov);
//	}
//
//	public long findMin(LinkedList<Long> data) {
//		long min = 9223372036854775807L;
//		Iterator<Long> iterator = data.iterator();
//		while (iterator.hasNext()) {
//			long tc2 = iterator.next();
//			if (min > tc2)
//				min = tc2;
//		}
//		return min;
//	}
//
//	public long findMax(LinkedList<Long> data) {
//		long max = -9223372036854775808L;
//		Iterator<Long> iterator = data.iterator();
//		while (iterator.hasNext()) {
//			long tc2 = iterator.next();
//			if (max < tc2)
//				max = tc2;
//		}
//		return max;
//	}
//
//	public long[] findAvg(LinkedList<Long> data) {
//		long sum = 0L;
//		long index = 0L;
//		Iterator<Long> iterator = data.iterator();
//		while (iterator.hasNext()) {
//			long tc2 = iterator.next();
//			sum += tc2;
//			index++;
//		}
//
//		long avg = sum / index;
//		long[] ret = new long[3];
//
//		ret[0] = avg;
//		ret[1] = sum;
//		ret[2] = index;
//		return ret;
//	}
//
//	public long findVar(LinkedList<Long> data, long avg) {
//		double sum = 0;
//		int index = 0;
//		Iterator<Long> iterator = data.iterator();
//		while (iterator.hasNext()) {
//			long tc2 = iterator.next();
//			double ad = tc2 - avg;
//			double power = Math.pow(ad, 2);
//			sum += power;
//			index++;
//		}
//		long var = (long) (sum / (index - 1));
//		return var;
//
//	}
//
//	public long findSD(long var) {
//		return (long) Math.sqrt(var);
//	}
//
//	public void updateInLink(int oper, int in, LinkedList<TimingClass> timeList, String event, long CompleteNano) {
//		Iterator<TimingClass> timeListIterator2 = timeList.iterator();
//		int index = 0;
//		while (timeListIterator2.hasNext()) {
////			TimingClass tc2 = timeListIterator2.next();
//			index++;
//			if (in == index) {
//				if (oper == 2) // complete
//				{
//
//					if (timeList.get(in - 1).num == 0) {
//						timeList.get(in - 1).end = true;
//						timeList.get(in - 1).nano = CompleteNano - timeList.get(in - 1).nano;
//					} else {
//						timeList.get(in - 1).num = timeList.get(in - 1).num - 1;
//					}
//
////					if (tc2.num == 0) {
////						tc2.end = true;
////						tc2.nano = CompleteNano - tc2.nano;
////					} else {
////						tc2.num = tc2.num - 1;
////					}
//				} else if (oper == 1) // start
//				{
//					timeList.get(in - 1).num = timeList.get(in - 1).num + 1;
//				}
//			}
//
//		}
//
//	}
//
//	public int SearchInLink(LinkedList<TimingClass> timeList, String event) {
//		int in = -1, index = 0;
//		Iterator<TimingClass> timeListIterator = timeList.iterator();
//		while (timeListIterator.hasNext()) {
//			TimingClass tc2 = timeListIterator.next();
//			index++;
//			if (tc2.method.equals(event) && !tc2.end) {
//				in = index;
//			}
//		}
//		return in;
//	}
//
//}

package timing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import classes.FirstMethodInPartition;
import classes.PartitionInformation;
import classes.TimingClass;
import classes.TimingOperationValue;

public class Timing {
	LinkedList<TimingClass> timeList;
	LinkedList<TimingOperationValue> timingvalue;

	public void calculate(LinkedList<PartitionInformation> TraceOut, String[][] conceptName, int[][] ThreadId,
			long[][] nano, String[][] transition, Map<String, Integer> modelFM,
			LinkedList<FirstMethodInPartition> FirstMethod) {

		timingvalue = new LinkedList<TimingOperationValue>();
		timeList = new LinkedList<TimingClass>();
		for (int n = 0; n < TraceOut.size(); n++) {
			int idd = TraceOut.get(n).trace;
			int label = modelFM.get(FirstMethod.get(n).concept);
			int NOFCurrentTrace = ThreadId[idd].length;
			for (int m = 0; m < NOFCurrentTrace; m++) {
				if (TraceOut.get(n).id == ThreadId[idd][m]) {
					if (transition[idd][m].equals("start")) {
						int in = SearchInLink(timeList, conceptName[idd][m]);
						if (in == -1) {
							TimingClass tc = new TimingClass(conceptName[idd][m], nano[idd][m], idd, TraceOut.get(n).id,
									label, false, 0);
							timeList.add(tc);
						} else {
							updateInLink(1, in, timeList, conceptName[idd][m], nano[idd][m]);
						}
					} else if (transition[idd][m].equals("complete")) {
						int in = SearchInLink(timeList, conceptName[idd][m]);
						if (in != -1) {
							updateInLink(2, in, timeList, conceptName[idd][m], nano[idd][m]);
						}

					}
				}
				if (ThreadId[idd][m] == 0)
					break;
			}
//			System.out.println("Finish" + TraceOut.get(n) );
		}

		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\timing.txt", true);

			Iterator<TimingClass> timeListIterator3 = timeList.iterator();
//			System.out.println("trace" + "\t" + "id" + "\t" + "nanotime" + "\t" + "method");

			myWriter.write("trace" + "\t" + "id" + "\t" + "nanotime" + "\t" + "method" + "\t" + "lable");
			myWriter.write("\n");

			while (timeListIterator3.hasNext()) {
				TimingClass tc2 = timeListIterator3.next();
//				System.out.println(tc2.trace + "\t" + tc2.id  + "\t" + tc2.nano + "\t" + tc2.method);
				String meth = tc2.method.substring(tc2.method.length() - 20);
				myWriter.write(tc2.trace + "\t" + tc2.id + "\t" + tc2.nano + "\t" + meth + "\t" + tc2.lable);
				myWriter.write("\n");
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		perMethod();
		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\MeyarhaMethodtiming.txt", true);

			Iterator<TimingOperationValue> timeListIterator3 = timingvalue.iterator();
//			System.out.println("trace" + "\t" + "id" + "\t" + "nanotime" + "\t" + "method");

			myWriter.write("min" + "\t" + "max" + "\t" + "avg" + "\t" + "var" + "\t" + "sd" + "\t" + "sum" + "\t"
					+ "size" + "\t" + "method" + "\n");
//			myWriter.write("\n");

			while (timeListIterator3.hasNext()) {
				TimingOperationValue tc2 = timeListIterator3.next();
//				System.out.println(tc2.trace + "\t" + tc2.id  + "\t" + tc2.nano + "\t" + tc2.method);
				myWriter.write(tc2.min + "\t" + tc2.max + "\t" + tc2.avg + "\t" + tc2.var + "\t" + tc2.sd + "\t"
						+ tc2.sum + "\t" + tc2.size + "\t" + tc2.method + "\n");
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		timingvalue.clear();
		
		
		perThread();
		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\MeyarhaThreadtiming.txt", true);

			Iterator<TimingOperationValue> timeListIterator3 = timingvalue.iterator();
//			System.out.println("trace" + "\t" + "id" + "\t" + "nanotime" + "\t" + "method");

			myWriter.write("min" + "\t" + "max" + "\t" + "avg" + "\t" + "var" + "\t" + "sd" + "\t" + "sum" + "\t"
					+ "size" + "\t" + "Thread" + "\t" + "method" + "\n");
//			myWriter.write("\n");

			while (timeListIterator3.hasNext()) {
				TimingOperationValue tc2 = timeListIterator3.next();
//				System.out.println(tc2.trace + "\t" + tc2.id  + "\t" + tc2.nano + "\t" + tc2.method);
				myWriter.write(tc2.min + "\t" + tc2.max + "\t" + tc2.avg + "\t" + tc2.var + "\t" + tc2.sd + "\t"
						+ tc2.sum + "\t" + tc2.size + "\t" + tc2.id + "\t" + tc2.method + "\n");
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		timingvalue.clear();

		
		
		
		
		
		
		perModel();
		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\MeyarhaModeltiming.txt", true);

			Iterator<TimingOperationValue> timeListIterator3 = timingvalue.iterator();
//			System.out.println("trace" + "\t" + "id" + "\t" + "nanotime" + "\t" + "method");

			myWriter.write("min" + "\t" + "max" + "\t" + "avg" + "\t" + "var" + "\t" + "sd" + "\t" + "sum" + "\t"
					+ "size" + "\t" + "Model" + "\t" + "method" + "\n");
//			myWriter.write("\n");

			while (timeListIterator3.hasNext()) {
				TimingOperationValue tc2 = timeListIterator3.next();
//				System.out.println(tc2.trace + "\t" + tc2.id  + "\t" + tc2.nano + "\t" + tc2.method);
				myWriter.write(tc2.min + "\t" + tc2.max + "\t" + tc2.avg + "\t" + tc2.var + "\t" + tc2.sd + "\t"
						+ tc2.sum + "\t" + tc2.size + "\t" + tc2.id + "\t" + tc2.method + "\n");
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		timingvalue.clear();

//		calculateMeyarha() ; 
//		System.out.println("");
	}

	public void perMethod() {
		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\Methodtiming.txt", true);

			Iterator<TimingClass> timeListIterator3 = timeList.iterator();

//			myWriter.write("id" + "\t" + "nanotime" + "\t" + "method");
//			myWriter.write("\n");

			Map<String, String> mttime = new HashMap<String, String>();
			while (timeListIterator3.hasNext()) {
				TimingClass tc2 = timeListIterator3.next();

				boolean flag = mttime.containsKey(tc2.method);
				if (flag) {
					String p = mttime.get(tc2.method) + "\n" + tc2.nano + "\t" + tc2.method;
					mttime.put(tc2.method, p);
				} else {
					String p = tc2.nano + "\t" + tc2.method;
					mttime.put(tc2.method, p);
				}
			}

			// using iterators
			Iterator<Map.Entry<String, String>> itr = mttime.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, String> entry = itr.next();
				myWriter.write(entry.getKey() + "\n" + entry.getValue());
				myWriter.write("\n");

				Operation(entry.getKey(), entry.getValue());

			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void perModel() {
		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\Modeltiming.txt", true);

			Iterator<TimingClass> timeListIterator3 = timeList.iterator();

			Map<Integer, String> motime = new HashMap<Integer, String>();
			while (timeListIterator3.hasNext()) {
				TimingClass tc2 = timeListIterator3.next();

				boolean flag = motime.containsKey(tc2.lable);
				if (flag) {
					String p = motime.get(tc2.lable) + "\n" + tc2.nano + "\t" + tc2.method;
					motime.put(tc2.lable, p);
				} else {
					String p = tc2.nano + "\t" + tc2.method;
					motime.put(tc2.lable, p);
				}

			}

			// using iterators
			Iterator<Map.Entry<Integer, String>> itr = motime.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<Integer, String> entry = itr.next();
				myWriter.write(entry.getKey() + "\n" + entry.getValue());
				myWriter.write("\n");
				
				Operation(entry.getKey(), entry.getValue());

			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void perThread() {
		try {
			FileWriter myWriter;
			myWriter = new FileWriter("output\\Threadtiming.txt", true);

			Iterator<TimingClass> timeListIterator3 = timeList.iterator();

//			myWriter.write("id" + "\t" + "nanotime" + "\t" + "method");
//			myWriter.write("\n");

			Map<Integer, String> Thtime = new HashMap<Integer, String>();
			while (timeListIterator3.hasNext()) {
				TimingClass tc2 = timeListIterator3.next();

				boolean flag = Thtime.containsKey(tc2.id);
				if (flag) {
					String p = Thtime.get(tc2.id) + "\n" + tc2.nano + "\t" + tc2.method;
					Thtime.put(tc2.id, p);
				} else {
					String p = tc2.nano + "\t" + tc2.method;
					Thtime.put(tc2.id, p);
				}

			}

			// using iterators
			Iterator<Map.Entry<Integer, String>> itr = Thtime.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<Integer, String> entry = itr.next();
				myWriter.write(entry.getKey() + "\n" + entry.getValue());
				myWriter.write("\n");

				Operation(entry.getKey(), entry.getValue());
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void updateInLink(int oper, int in, LinkedList<TimingClass> timeList, String event, long CompleteNano) {
//		Iterator<TimingClass> timeListIterator2 = timeList.iterator();
//		int index = 0;
//		while (timeListIterator2.hasNext()) {
////			TimingClass tc2 = timeListIterator2.next();
//			index++;
//			if (in == index) {
//				if (oper == 2) // complete
//				{
//
//					if (timeList.get(in - 1).num == 0) {
//						timeList.get(in - 1).end = true;
//						timeList.get(in - 1).nano = CompleteNano - timeList.get(in - 1).nano;
//					} else {
//						timeList.get(in - 1).num = timeList.get(in - 1).num - 1;
//					}
//
////					if (tc2.num == 0) {
////						tc2.end = true;
////						tc2.nano = CompleteNano - tc2.nano;
////					} else {
////						tc2.num = tc2.num - 1;
////					}
//					break;
//				}
//				else if (oper == 1) // start
//				{
//					timeList.get(in - 1).num = timeList.get(in - 1).num + 1;
//				}
//			}
//
//		}
		if (oper == 2) // complete
		{
			if (timeList.get(in - 1).num == 0) {
				timeList.get(in - 1).end = true;
				timeList.get(in - 1).nano = CompleteNano - timeList.get(in - 1).nano;
			} else {
				timeList.get(in - 1).num = timeList.get(in - 1).num - 1;
			}
		} else if (oper == 1) // start
		{
			timeList.get(in - 1).num = timeList.get(in - 1).num + 1;
		}
	}

	public int SearchInLink(LinkedList<TimingClass> timeList, String event) {
		int in = -1, index = 0;
		Iterator<TimingClass> timeListIterator = timeList.iterator();
		while (timeListIterator.hasNext()) {
			TimingClass tc2 = timeListIterator.next();
			index++;
			if (tc2.method.equals(event) && !tc2.end) {
				in = index;
			}
		}
		return in;

	}

	// thread
	public void Operation(Integer name, String data) {

		Map<String, String> mttime = new HashMap<String, String>();
		Scanner myReader = new Scanner(data);
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String[] lines = line.split("\t");
			boolean flag = mttime.containsKey(lines[1]);
			if (flag) {
				String p = mttime.get(lines[1]) + "\n" + lines[0] + "\t" + lines[1];
				mttime.put(lines[1], p);
			} else {
				String p = lines[0] + "\t" + lines[1];
				mttime.put(lines[1], p);
			}
		}
		myReader.close();

		// using iterators
		Iterator<Map.Entry<String, String>> itr = mttime.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			
			String name2 = "" ;
			LinkedList<Long> nano = new LinkedList<Long>();
			Scanner myReader2 = new Scanner(entry.getValue());
			while (myReader2.hasNextLine()) {
				String line = myReader2.nextLine();
				String[] lines = line.split("\t");
				name2 = lines[1];
				nano.add(Long.parseLong(lines[0]));
			}
			myReader2.close();
			
			long min = findMin(nano);
			long max = findMax(nano);
			long[] ret = findAvg(nano);
			long var = findVar(nano, ret[0]);
			long sd = findSD(var);
			
			TimingOperationValue tov = new TimingOperationValue(name2 , min, max, ret[0], var, sd, ret[1], ret[2] , name);
			timingvalue.add(tov);
			nano.clear();
		}
	}

	// method
	public void Operation(String name, String data) {
		LinkedList<Long> nano = new LinkedList<Long>();
		Scanner myReader = new Scanner(data);
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String[] lines = line.split("\t");
			nano.add(Long.parseLong(lines[0]));
		}
		myReader.close();

		long min = findMin(nano);
		long max = findMax(nano);
		long[] ret = findAvg(nano);
		long var = findVar(nano, ret[0]);
		long sd = findSD(var);
		TimingOperationValue tov = new TimingOperationValue(name, min, max, ret[0], var, sd, ret[1], ret[2]);
		timingvalue.add(tov);
		nano.clear();
	}

	public long findMin(LinkedList<Long> data) {
		long min = 9223372036854775807L;
		Iterator<Long> iterator = data.iterator();
		while (iterator.hasNext()) {
			long tc2 = iterator.next();
			if (min > tc2)
				min = tc2;
		}
		return min;
	}

	public long findMax(LinkedList<Long> data) {
		long max = -9223372036854775808L;
		Iterator<Long> iterator = data.iterator();
		while (iterator.hasNext()) {
			long tc2 = iterator.next();
			if (max < tc2)
				max = tc2;
		}
		return max;
	}

	public long[] findAvg(LinkedList<Long> data) {
		long sum = 0;
		int index = 0;
		Iterator<Long> iterator = data.iterator();
		while (iterator.hasNext()) {
			long tc2 = iterator.next();
			sum += tc2;
			index++;
		}

		long avg = (long)(sum / index);
		long[] ret = new long[3];
		
		ret[0] = avg;
		ret[1] = sum;
		ret[2] = (long)index;
		return ret;
	}

	public long findVar(LinkedList<Long> data, long avg) {
		double sum = 0;
		int index = 0;
		Iterator<Long> iterator = data.iterator();
		while (iterator.hasNext()) {
			long tc2 = iterator.next();
			long ad = tc2 - avg;
			long power = (long) Math.pow(ad, 2);
			sum += power;
			index++;
		}
		long var = (long) (sum / (index - 1));
		return var;

	}

	public long findSD(long var) {
		return (long) Math.sqrt(var);
	}

}
