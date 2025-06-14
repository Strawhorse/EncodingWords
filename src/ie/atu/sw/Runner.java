package ie.atu.sw;

import java.util.Scanner;

import static ie.atu.sw.Encodings.encodeBooks;

public class Runner {

	public static void main(String[] args) throws Exception {

		Menu menu = new Menu();
		menu.menu();

//		reading in the input from the menu
		Scanner scanner = new Scanner(System.in);
		String input;

		while (true) {
			System.out.print("Enter an option or enter ? to quit: ");
			input = scanner.nextLine();

			if (input.equals("?")) {
				System.out.println("Exiting...");
				break;  // Exit the loop
			}

			switch (input) {
				case "1" -> System.out.println("You chose option 1: Specify Mapping File");
				case "1a" -> Encodings.outputEncodings();
				case "2" -> {System.out.println("You chose option 2: Specify Text File to Encode");
					System.out.println("Outputting list of books in directory\n");
					Books.bookList();
					System.out.println();
					System.out.println("Which book do you want to encode? [1-?]");

					// testing out new book creation
					Book book = new Book();
				}
				case "3" -> System.out.println("You chose option 3: Specify Output File (default: ./out.txt)");
				case "4" -> System.out.println("You chose option 4: Configure Options");
				case "5" -> {
					System.out.println("You chose option 5: Encode Text File");
					encodeBooks();
				}
				case "6" -> System.out.println("You chose option 6: Decode Text File");
				case "7" -> menu.menu();
				case "8" -> {
					System.out.println("You chose option 8: Extra options");
					System.out.println("Please choose an additional option to continue... \n******************************* \n ");
				}
				default -> System.out.println("Invalid option. Have another look at the list");
			}
		}

		// Clean up, close scanner object
		scanner.close();





		//You may want to include a progress meter in you assignment!
//		This should run when the program is performing an action

		System.out.print(ConsoleColour.YELLOW);	//Change the colour of the console text
		int size = 100;							//The size of the meter. 100 equates to 100%
		for (int i =0 ; i < size ; i++) {		//The loop equates to a sequence of processing steps
			PrintProgress.printProgress(i + 1, size); 		//After each (some) steps, update the progress meter
			Thread.sleep(5);					//Slows things down so the animation is visible
		}
		
	}

}