package read;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import classes.ReturnValue;

public class ReadFile {

	public static ReturnValue Process() {
		try {
			File myObj = new File("log.xes");
			Scanner myReader = new Scanner(myObj);
			String Alltrace = "", global = "";
			int NumberOfTrace = 1;
			String[] Trace = new String[NumberOfTrace];
			int[] NumberOfEventPerTrace = new int[NumberOfTrace];
			int NumberOfEvent = 0;
			Boolean ExistsNextTrace = false;
			String CurrentTrace = "";
			int CurrentTraceIndex = 0;
			Boolean g = true;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (g) {
					global = global.concat(data);
					if (data.contains("</global>")) {
						g = false;
					} else {
						global = global + "\n";
					}
				} else {
					Alltrace = Alltrace.concat(data);
					if (!data.contains("\n")) {
						CurrentTrace = CurrentTrace.concat(data);
					}
					if (data.contains("<event>")) {
						NumberOfEvent = NumberOfEvent + 1;
					}
					if (!data.contains("</log>")) {
						Alltrace = Alltrace + "\n";
					}
					if (!data.contains("</trace>")) {
						CurrentTrace = CurrentTrace + "\n";
					}
					if (data.contains("</trace>")) {
						Trace[CurrentTraceIndex] = CurrentTrace;
						NumberOfEventPerTrace[CurrentTraceIndex] = NumberOfEvent;
						CurrentTraceIndex = CurrentTraceIndex + 1;
						NumberOfEvent = 0;
						CurrentTrace = "";
						ExistsNextTrace = true;
					}
					if (data.contains("<trace>") && ExistsNextTrace) {
						NumberOfTrace = NumberOfTrace + 1;
						String[] temp = new String[NumberOfTrace];
						for (int k = 0; k < NumberOfTrace - 1; k++) {
							temp[k] = Trace[k];
						}
						Trace = temp;

						int[] temp2 = new int[NumberOfTrace];
						for (int k = 0; k < NumberOfTrace - 1; k++) {
							temp2[k] = NumberOfEventPerTrace[k];
						}
						NumberOfEventPerTrace = temp2;

					}
				}
			}

			myReader.close();
			if(Trace[0] == null)
			{
				Trace[0] = "" ; 
			}
			int NumberEventForCurrentTrace = Arrays.stream(NumberOfEventPerTrace).max().getAsInt();

			String[][] event = new String[NumberOfTrace][NumberEventForCurrentTrace];
			int[][] ThreadId = new int[NumberOfTrace][NumberEventForCurrentTrace];
			int[][] linear = new int[NumberOfTrace][NumberEventForCurrentTrace];
			String[][] ConceptName = new String[NumberOfTrace][NumberEventForCurrentTrace];
			String[][] CallerConceptName = new String[NumberOfTrace][NumberEventForCurrentTrace];
			String[][] transition = new String[NumberOfTrace][NumberEventForCurrentTrace];
			long[][] nanotime = new long[NumberOfTrace][NumberEventForCurrentTrace];
			long[][] idhashcode = new long[NumberOfTrace][NumberEventForCurrentTrace];

			Scanner scanner = null;

			for (int TraceIndex = 0; TraceIndex < NumberOfTrace; TraceIndex++) {
				CurrentTrace = Trace[TraceIndex];
				scanner = new Scanner(CurrentTrace);
				int index = 0;
				String CurrentEvent = "";

				while (scanner.hasNextLine()) {
					String data = scanner.nextLine();
					if (!data.contains("<trace>")) {
						if (!data.contains("</event>")) {
							CurrentEvent = CurrentEvent.concat(data);
							CurrentEvent = CurrentEvent + "\n";
						} else {
							CurrentEvent = CurrentEvent.concat(data);
							event[TraceIndex][index] = CurrentEvent;
							CurrentEvent = "";
							index = index + 1;
						}
						if (data.contains("apprun:threadid")) {
							int th = 0, in = 40;
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
							ThreadId[TraceIndex][index] = th;
						}
						if (data.contains("concept:name")) {
							String name = "";
							int in = 37;
							while (data.length() > 0) {
								char ch = data.charAt(in);
								if (ch != '"') {
									name = name.concat(Character.toString(ch));
									in++;
								} else {
									break;
								}
							}
							ConceptName[TraceIndex][index] = name;
						}
						if (data.contains("apploc:caller:joinpoint")) {
							String name = "";
							int in = 48;
							while (data.length() > 0) {
								char ch = data.charAt(in);
								if (ch != '"') {
									name = name.concat(Character.toString(ch));
									in++;
								} else {
									break;
								}
							}
							CallerConceptName[TraceIndex][index] = name;
						}
						if (data.contains("lifecycle:transition")) {
							String name = "";
							int in = 45;
							while (data.length() > 0) {
								char ch = data.charAt(in);
								if (ch != '"') {
									name = name.concat(Character.toString(ch));
									in++;
								} else {
									break;
								}
							}
							transition[TraceIndex][index] = name;
						}
						if (data.contains("apploc:linenr")) {
							int th = 0, in = 35;
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
							linear[TraceIndex][index] = th;
						}
						
						if (data.contains("apprun:nanotime")) {
							long th = 0 ; 
							int in = 40;
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
							nanotime[TraceIndex][index] = th;
						}
						if (data.contains("apploc:idhashcode")) {
							long th = 0 ; 
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
							idhashcode[TraceIndex][index] = th;
						}
					}
				}
			}
			scanner.close();
			ReturnValue r = new ReturnValue(NumberOfTrace , NumberOfEventPerTrace , event , ThreadId , global
					,ConceptName , CallerConceptName , transition , linear , idhashcode , nanotime) ;
			return r;

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		}


	}
}
