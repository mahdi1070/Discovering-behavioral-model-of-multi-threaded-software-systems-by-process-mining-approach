package PerThread;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import classes.PartitionInformation;

public class PerThreads {

	public void WriteInFile(String global, LinkedList<PartitionInformation> TraceOut) {
		try {

			Set<Integer> see = new HashSet<Integer>();
			for (int n = 0; n < TraceOut.size(); n++) {

				FileWriter myWriter;
				myWriter = new FileWriter("output\\Thread" + TraceOut.get(n).id + ".xes", true);
				if (!see.contains(TraceOut.get(n).id)) {
					myWriter.write(global);
				}
				myWriter.write("\n");
				myWriter.write("	<trace>\n");
				myWriter.write(TraceOut.get(n).content);
				myWriter.write("\n");
				myWriter.write("	</trace>\n");
				see.add(TraceOut.get(n).id);

				myWriter.close();

			}
			see.clear();
			for (int n = 0; n < TraceOut.size(); n++) {

				FileWriter myWriter;
				myWriter = new FileWriter("output\\Thread" + TraceOut.get(n).id + ".xes", true);
				if (!see.contains(TraceOut.get(n).id)) {
					myWriter.write("</log>");
				}
				see.add(TraceOut.get(n).id);
				myWriter.close();
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
