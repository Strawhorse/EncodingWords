package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Runner {
	private Encodings encodings;
	private Book selectedBook;
	private String selectedEncodedFile;

	public static void main(String[] args) throws IOException {
		new Runner().start();
	}

	private void start() throws IOException {
		Menu menu = new Menu();
		Scanner sc = new Scanner(System.in);
		String choice = "";

		encodings = new Encodings(10000); // create empty encodings

		while (!choice.equals("?")) {
			menu.menu(); // print menu at the start of each loop
			choice = sc.nextLine();

			switch (choice) {
				case "0":
					instructions();
					break;

				case "1":
					showStatus();
					break;

				case "2":
					System.out.print("Enter path to mapping CSV file: ");
					String csvPath = sc.nextLine();
					try {
						encodings.loadEncodings(csvPath);
					} catch (IOException e) {
						System.out.println("Error loading mapping file: " + e.getMessage());
					}
					break;

				case "3":
					selectedBook = BooksList.bookList();
					if (selectedBook != null) {
						System.out.println("Selected book: " + selectedBook.getName());
					}
					break;

				case "4":
					if (selectedBook == null) {
						System.out.println("No book selected!");
					} else {
						System.out.print("Enter directory path where you want to save the encoded file: ");
						String outputDir = sc.nextLine();

						File dir = new File(outputDir);
						if (!dir.exists() || !dir.isDirectory()) {
							System.out.println("Invalid directory. Using current working directory instead.");
							outputDir = "."; // default
						}

						// Create output file as chosen directory + book name + ".txt"
						String outputPath = outputDir + File.separator + selectedBook.getName() + "_encoded.txt";

						new Encoder(encodings).encodeBook(selectedBook, outputPath);
						System.out.println("Encoded file written to " + outputPath);
						selectedEncodedFile = outputPath;
					}
					break;

				case "5":
					System.out.print("Enter path to encoded file: ");
					selectedEncodedFile = sc.nextLine();
					break;

				case "6":
					if (selectedEncodedFile == null) {
						System.out.println("No encoded file selected!");
					} else {
						String outputPath = selectedEncodedFile + ".decoded.txt";
						new Decoder(encodings).decodeFile(selectedEncodedFile, outputPath);
						System.out.println("Decoded file written to " + outputPath);
					}
					break;

				case "7":
					// just reprint menu for user again
					break;

				case "8":
					System.out.print("\033[H\033[2J");
					System.out.flush();
					break;

				case "?":
					System.out.println("Goodbye!");
					break;

				default:
					System.out.println("Invalid choice.");
			}
		}
		sc.close();
	}

//	This method shows the status of the required files needed to work
	private void showStatus() {
		System.out.println("Program setup status:");
		if (encodings != null && encodings.getTokens()[0] != null) {
			System.out.println(" - Mapping file loaded: " + encodings.getMappingFilePath());
		} else {
			System.out.println(" - Mapping file loaded: No");
		}

		System.out.println(" - Book selected: " +
				(selectedBook != null ? selectedBook.getName() : "None"));
	}


//	created a method for giving the instructions as the menu was getting a bit cluttered
	public static void instructions() {
		System.out.println("Instructions:");
		System.out.println(" 1. Specify a mapping CSV file first.");
		System.out.println(" 2. Select a book to encode.");
		System.out.println(" 3. Encode the selected book to a specific location.");
		System.out.println(" 4. Select an encoded file to decode.");
		System.out.println(" 5. Decode the encoded file to a specific location.");
	}
}
