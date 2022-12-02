package pip2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class PipeLine  {

    public PipeLine() {
    }

    public static void main(String args[]) throws IOException {
      	
      fr = new FileReader("in.txt");
      br = new BufferedReader(fr);
		
      
      try {
			String line = br.readLine();
			nextComponent = new Operation1();
			Thread t2 = new Thread(nextComponent);
			t2.start(); 
			
			while (EndOfLine(line)) {
				nextComponent.enqueue(line);
				Thread.sleep(20);
				line = br.readLine();
			}
			nextComponent.cancel();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }

    
	public static boolean EndOfLine(String line) {	
		boolean ex = false ;
		if (line != null)
			ex = true ;
		return ex ; 
	}

    //Variables
    private static FileReader fr;
    private static  BufferedReader br;
    private static Operation1 nextComponent;

    
}
