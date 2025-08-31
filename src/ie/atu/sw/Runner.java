package ie.atu.sw;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) throws Exception {

		Menu menu = new Menu();
		menu.menu();

//		reading in the input from the menu
		Scanner scanner = new Scanner(System.in);


		while (true) {
			System.out.print("Enter an option or enter ? to quit: ");
			String input = scanner.nextLine();

			if (input.equals("?")) {
				System.out.println("Exiting...");
				break;  // Exit the loop
			}

			switch (input) {
				case "0" -> {
					System.out.println("Setup status as follows: \n");
					// output status of mapping file, available books, encoded books, and decoded books
				}
				case "1" -> System.out.println("You chose option 1: Specify Mapping File");
				case "2" -> {System.out.println("You chose option 2: Choose Text File to Encode from list");
					Books.bookList();
				}
				case "3" -> System.out.println("You chose option 3: Specify Output File (default: ./out.txt)");
				case "4" -> System.out.println("You chose option 5: Encode Text File");
				case "5" -> System.out.println("You chose option 6: Decode Text File");
				case "6" -> menu.menu();
				case "7" -> {
//					Check that this works before finishing; only works on terminal with ANSI escape code support
					System.out.print("\033[H\033[2J");
					System.out.flush();
				}
				default -> System.out.println("Invalid option. Have another look at the list");
			}
		}

		// Clean up, close scanner object
		scanner.close();





//		This should run when the program is performing an action

		System.out.print(ConsoleColour.YELLOW);	//Change the colour of the console text
		int size = 100;							//The size of the meter. 100 equates to 100%
		for (int i =0 ; i < size ; i++) {		//The loop equates to a sequence of processing steps
			PrintProgress.printProgress(i + 1, size); 		//After each (some) steps, update the progress meter
			Thread.sleep(5);					//Slows things down so the animation is visible
		}
		
	}

}