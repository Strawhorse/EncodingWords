package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Runner {
	private Encoder encoder;
	private Book selectedBook;
	private String selectedEncodedFile;

	// for the showStatus method at the bottom, keeps track of the last 50 encoded and decoded books
	String[] encodedBookNames = new String[50];
	String[] encodedOutputPaths = new String[50];
	int encodedCount = 0;
	String[] decodedInputFiles = new String[50];
	String[] decodedOutputFiles = new String[50];
	int decodedCount = 0;
	// =====================================================

	public static void main(String[] args) throws IOException {
		new Runner().start();
	}

	private void start() throws IOException {
		Menu menu = new Menu();
		Scanner sc = new Scanner(System.in);
		String choice = "";

//		creates a new Encoder object when the encoding file is selected
		encoder = new Encoder(10000); // create empty encoder

		while (!choice.equals("?")) {

			// print menu at the start of each loop
			menu.menu();
			choice = sc.nextLine();

			switch (choice) {
				case "0":
					instructions();
					break;

//				This menu option shows a basic status of the program, including whether you have chosen an encoding file etc.
//				Some basic use metrics, too.
				case "1":
					showStatus();
					break;

//					This option chooses the encoding file then runs a method from the Encoder class to populate its variables
				case "2":
					System.out.print("Enter path to mapping CSV file: ");
					String csvPath = sc.nextLine();
					try {
						encoder.loadEncodings(csvPath);
					} catch (IOException e) {
						System.out.println("Error loading mapping file: " + e.getMessage());
					}
					break;

//					Selects the text file to encode, using a method from the BooksList class (which then creates a Book object)
				case "3":
					selectedBook = BooksList.bookList();
					if (selectedBook != null) {
						System.out.println("Selected book: " + selectedBook.getName());
					}
					break;

//					Runs the encoder
				case "4":
					if (selectedBook == null) {
						System.out.println("No book selected!");
					} else {
						System.out.print("Enter directory path where you want to save the encoded file: ");
						String outputDir = sc.nextLine();

						File dir = new File(outputDir);
						if (!dir.exists() || !dir.isDirectory()) {
							System.out.println("Invalid directory. Using current working directory instead.");

//							This specifies the default path to be the current one by setting it to a full stop
							outputDir = ".";
						}

						String outputPath = outputDir + File.separator + selectedBook.getName() + "_encoded.txt";
						try {

//							After choosing the path where you want to put the book, the encodeFile method from the Encoder class is called
//							This is the method which encodes the book using the encodings .csv file values
							encoder.encodeFile(selectedBook.getPath(), outputPath);
							System.out.println("Encoded file written to " + outputPath);
							selectedEncodedFile = outputPath;

							// If successful ...
							recordEncoded(selectedBook.getName(), outputPath);
						} catch (IOException e) {
							System.out.println("Encoding failed: " + e.getMessage());
						}
					}
					break;

//					Now choose the encoded file you wish to decode
				case "5":
					System.out.print("Enter full path and filename to encoded file: ");
					selectedEncodedFile = sc.nextLine();
					break;

//					Now run the Decoder class and methods to decode the encoded book
				case "6":
					if (selectedEncodedFile == null || selectedEncodedFile.isBlank()) {
						System.out.println("No encoded file selected! Please select an encoded file");
					} else {
						File inFile = new File(selectedEncodedFile);
						if (!inFile.exists() || !inFile.isFile()) {
							System.out.println("Encoded file not found:\n  " + inFile.getAbsolutePath());
							System.out.println("Please choose a valid file (menu option 5 from main menu).");
							break;
						}

						String outputPath = selectedEncodedFile + ".decoded.txt";
						try {
							new Decoder(encoder).decodeFile(selectedEncodedFile, outputPath);
							System.out.println("Decoded file written to " + outputPath);

							// if successful ...
							recordDecoded(selectedEncodedFile, outputPath);
						} catch (java.io.FileNotFoundException e) {
							System.out.println("Could not open encoded input file:\n  " + selectedEncodedFile);
						} catch (java.io.IOException e) {
							System.out.println("I/O error during decoding process: " + e.getMessage());
						}
					}
					break;

				case "7":
					// just reprint menu for user again
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

	// helper methods to create details for the showStatus method at the bottom
//	These are basically tracking all the encodings and decodings that are taking place.
	private void recordEncoded(String bookName, String outPath) {
		if (encodedCount < 50) {
			encodedBookNames[encodedCount] = bookName;
			encodedOutputPaths[encodedCount] = outPath;
			encodedCount++;
		}
	}

	private void recordDecoded(String inPath, String outPath) {
		if (decodedCount < 50) {
			decodedInputFiles[decodedCount] = inPath;
			decodedOutputFiles[decodedCount] = outPath;
			decodedCount++;
		}
	}


// This method will give you a general status for what files are selected, and how many encodings/decodings have taken place
	private void showStatus() {
		System.out.println("Program setup status:");
		if (encoder != null && encoder.getTokens()[0] != null) {
			System.out.println(" - Mapping file loaded: " + encoder.getMappingFilePath());
		} else {
			System.out.println(" - Mapping file loaded: None");
		}

		System.out.println(" - Book selected: " + (selectedBook != null ? selectedBook.getName() : "None"));


		// Summary history of the program encoding history
		System.out.println("\nEncoding history (count: " + encodedCount + "):");
        if (encodedCount != 0) {
            // show up to last 10 entries
            int start = Math.max(0, encodedCount - 10);
            for (int i = start; i < encodedCount; i++) {
                System.out.println("  " + (i + 1) + ". " + encodedBookNames[i] + " -> " + encodedOutputPaths[i]);
            }
        } else {
            System.out.println("  (no books encoded yet)");
        }

//		Summary history of the decoding done
        System.out.println("\nDecoding history (count: " + decodedCount + "):");
		if (decodedCount == 0) {
			System.out.println("  (none yet)");
		} else {
			int start = Math.max(0, decodedCount - 10);
			for (int i = start; i < decodedCount; i++) {
				System.out.println("  " + (i + 1) + ". " + decodedInputFiles[i] + " -> " + decodedOutputFiles[i]);
			}
		}
		// ======================================

		System.out.println("*********************");
	}

	public static void instructions() {
		System.out.println("Instructions:");
		System.out.println(" 1. Specify a mapping CSV file first.");
		System.out.println(" 2. Select a book to encode.");
		System.out.println(" 3. Encode the selected book to a specific location.");
		System.out.println(" 4. Select an encoded file to decode.");
		System.out.println(" 5. Decode the encoded file to a specific location.");
		System.out.println("*********************");
	}
}
