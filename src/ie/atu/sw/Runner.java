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

		encoder = new Encoder(10000); // create empty encoder

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
						encoder.loadEncodings(csvPath);
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
							outputDir = ".";
						}

						String outputPath = outputDir + File.separator + selectedBook.getName() + "_encoded.txt";
						try {
							// NOTE: requires Book#getPath(); if you don't have it, either add it,
							// or change this call to encoder.encodeBook(selectedBook, outputPath)
							encoder.encodeFile(selectedBook.getPath(), outputPath);
							System.out.println("Encoded file written to " + outputPath);
							selectedEncodedFile = outputPath;

							// record success
							recordEncoded(selectedBook.getName(), outputPath);
						} catch (IOException e) {
							System.out.println("Encoding failed: " + e.getMessage());
						}
					}
					break;

				case "5":
					System.out.print("Enter full path and filename to encoded file: ");
					selectedEncodedFile = sc.nextLine();
					break;

				case "6":
					if (selectedEncodedFile == null || selectedEncodedFile.isBlank()) {
						System.out.println("No encoded file selected! Please select an encoded file");
					} else {
						File inFile = new File(selectedEncodedFile);
						if (!inFile.exists() || !inFile.isFile()) {
							System.out.println("Encoded file not found:\n  " + inFile.getAbsolutePath());
							System.out.println("Please choose a valid file (menu option 5).");
							break;
						}

						String outputPath = selectedEncodedFile + ".decoded.txt";
						try {
							new Decoder(encoder).decodeFile(selectedEncodedFile, outputPath);
							System.out.println("Decoded file written to " + outputPath);

							// record success
							recordDecoded(selectedEncodedFile, outputPath);
						} catch (java.io.FileNotFoundException e) {
							System.out.println("Could not open input file:\n  " + selectedEncodedFile);
						} catch (java.io.IOException e) {
							System.out.println("I/O error during decoding: " + e.getMessage());
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

	// ======== stats helpers ========
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
	// ===============================

	private void showStatus() {
		System.out.println("Program setup status:");
		if (encoder != null && encoder.getTokens()[0] != null) {
			System.out.println(" - Mapping file loaded: " + encoder.getMappingFilePath());
		} else {
			System.out.println(" - Mapping file loaded: No");
		}

		System.out.println(" - Book selected: " +
				(selectedBook != null ? selectedBook.getName() : "None"));

		// ======= NEW: summary + history =======
		System.out.println("\nEncoding history (count: " + encodedCount + "):");
        if (encodedCount != 0) {
            // show up to last 10 entries (most recent last for simplicity)
            int start = Math.max(0, encodedCount - 10);
            for (int i = start; i < encodedCount; i++) {
                System.out.println("  " + (i + 1) + ". " + encodedBookNames[i] + " -> " + encodedOutputPaths[i]);
            }
        } else {
            System.out.println("  (none yet)");
        }

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
