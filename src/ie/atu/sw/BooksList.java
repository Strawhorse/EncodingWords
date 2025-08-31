package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BooksList {

    // Main entry point to list and select books from
    public static Book bookList() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose the directory where the books are stored: ");
        String folderPath = scanner.nextLine();

        File folder = new File(folderPath);

        // Check if it's a valid directory
        if (folder.exists() && folder.isDirectory()) {
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles == null || listOfFiles.length == 0) {
                System.out.println("No files found in this folder. Returning you to the main menu.");
                return null;
            }

            // Convert files into a String[] array of book names
            String[] books = getBooksInFolder(listOfFiles);

            if (books.length == 0) {
                System.out.println("No .txt files found in this folder.");
                return null;
            }

            // Print out the books
            for (int i = 0; i < books.length; i++) {
                System.out.println((i + 1) + ". " + books[i]);
            }

            // Let user select a book
            System.out.println("Which book do you want to encode? [1-" + books.length + "]");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice >= 1 && choice <= books.length) {
                File chosenFile = listOfFiles[choice - 1];
                return new Book(chosenFile); // Return the chosen Book
            } else {
                System.out.println("Invalid choice.");
                return null;
            }

        } else {
            System.out.println("The path you entered is not a valid folder.");
            return null;
        }
    }

    // Helper method: returns a String[] array of book names
    public static String[] getBooksInFolder(File[] listOfFiles) {
        return java.util.Arrays.stream(listOfFiles)
                .filter(file -> file.isFile() && file.getName().endsWith(".txt"))
                .map(file -> file.getName().substring(0, file.getName().length() - 4))
                .toArray(String[]::new);
    }
}
