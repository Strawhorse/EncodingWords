package ie.atu.sw;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BooksList {

    // Main entry point to list and select books from
    public static void bookList() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose the directory where the books are stored: ");
        String folderPath = scanner.nextLine();

        File folder = new File(folderPath);

        // Check if it's a valid directory and that it exists on the computer
        if (folder.exists() && folder.isDirectory()) {

//            array of files used here, rather than a List or ArrayList etc.
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles == null || listOfFiles.length == 0) {
                System.out.println("No files found in this folder. Returning you to the main menu.");
                return;
            }

            // Convert files into a String[] array of book names
            String[] books = getBooksInFolder(listOfFiles);

            // Print out the books
            for (int i = 0; i < books.length; i++) {
                System.out.println((i + 1) + ". " + books[i]);
            }

            // Let user select a book
            System.out.println("Which book do you want to encode? [1-" + books.length + "]");
            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= books.length) {
                String chosenBookName = books[choice - 1];
                File chosenFile = listOfFiles[choice - 1];

                Book book = new Book(chosenFile);

            } else {
                System.out.println("Invalid choice.");
            }

        } else {
            System.out.println("The path you entered is not a valid folder.");
        }
    }

    // Helper method: returns a String[] array of book names
    public static String[] getBooksInFolder(File[] listOfFiles) {

        // Count how many text files there are
        int count = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                count++;
            }
        }

        // Build an array of just the book names
        String[] books = new String[count];
        int index = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String fileName = file.getName();
                // Remove the .txt extension
                fileName = fileName.substring(0, fileName.length() - 4);
                books[index++] = fileName;
            }
        }

        return books;
    }
}