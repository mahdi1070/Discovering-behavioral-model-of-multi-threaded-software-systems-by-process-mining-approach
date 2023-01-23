
import java.util.Scanner;

public class Main  {

	public static void main(String[] args) {
        System.out.println("Enter the integer: ");
        Scanner s = new Scanner(System.in);
        int input = s.nextInt();
		a(input);
		
		Thread t1 = new Thread(new Runnable() { 
			@Override
			public void run() 
			{ 
				
				d(input); 
			
			} 
		}); 

		t1.start();
		
		try {
			t1.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	  
	public static void a(int input)  {		 

		System.out.println("a start");

		if (input % 2 == 0)
		{
			e(input);
		}else {
			for (int i = 1; i <= 2 ;i++)
			{	
				int j = i;
				Thread t1 = new Thread(new Runnable() { 
					@Override
					public void run() 
					{ 
						
						b(j); 
					
					} 
				}); 

				t1.start();
				
				try {
					t1.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			}
			
		}
			
		System.out.println("a complete");

		
	}
	public static void e(int input) {
		System.out.println("e start");

		Thread t1 = new Thread(new Runnable() { 
			@Override
			public void run() 
			{ 
				
				b(input); 
			
			} 
		}); 

		t1.start();
		
		try {
			t1.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("e complete");

	}
	
	public static void b(int input)  {
		System.out.println("b start");

		if (input % 2 == 1)
		{
			c();
		}
		System.out.println("b complete");

	}
	
	public static void c() {
		System.out.println("c start");
		System.out.println("c complete");

	}
	
	public static void d(int input)  {
		System.out.println("d start");

		if (input % 2 == 0)
		{
			c();
		}
		System.out.println("d complete");

	}
	

}
