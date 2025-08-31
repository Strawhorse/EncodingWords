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
				break;
			}

			switch (input) {
                case "0" -> {
                    instructions();
                }
                case "1" -> {
					System.out.println("Setup status as follows: \n");
					// output status of mapping file, available books, encoded books, and decoded books
				}
				case "2" -> System.out.println("You chose option 2: Specify Mapping File");
				case "3" -> {
					System.out.println("You chose option 3: Choose Text File to Encode");
					BooksList.bookList();
				}
				case "4" -> System.out.println("You chose option 4: Encode Text File");
				case "5" -> System.out.println("You chose option 5: Choose Text File to Decode");
				case "6" -> System.out.println("You chose option 6: Decode Text File");
				case "7" -> menu.menu();
				case "8" -> {
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

	public static void instructions() {
		System.out.println("To encode a book, you must do the following: \n");
		System.out.println("Specify a mapping/encoding file (.csv)\n");
		System.out.println("Choose a book you wish to encode\n");
		System.out.println("Choose the (4)Encode Text File option from the menu\n");
		System.out.println("Specify the output file location where you want to save your encoded book\n");
		System.out.println("Book will then be encoded and saved to this location\n");
		System.out.println("***************************************\n");
		System.out.println("To decode a book, you must do the following: \n");
		System.out.println("Choose the [encoded] book you wish to decode\n");
		System.out.println("Select (6)Decode Text File option from the menu\n");
		System.out.println("Specify the output file location where you want to save your decoded book\n");
		System.out.println("The book will then be decoded and saved to this location\n");
		System.out.println("***************************************\n");
	}

}