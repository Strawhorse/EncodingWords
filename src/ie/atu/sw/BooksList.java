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

//            Create an array of the files in the directory chosen
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles == null || listOfFiles.length == 0) {
                System.out.println("No files found in this folder. Returning you to the main menu.");
                return null;
            }

            // Convert files into a String[] array of book names using method near bottom of class, getBooksInFolder
//            This is searching for files with .txt at the end, and will ignore all other files
//            The String[] books below will just be a numbered list  of the text files in the folder

            String[] books = getBooksInFolder(listOfFiles);

            if (books.length == 0) {
                System.out.println("No .txt files found in this folder.");
                return null;
            }

            // Print out the books, adding a 1 to the book number so it doesn't start on 0
//            The adding 1 gets removed in the below section when the user selects a book number - 1
            for (int i = 0; i < books.length; i++) {
                System.out.println((i + 1) + ". " + books[i]);
            }

            // Let user select a book
            System.out.println("Which book do you want to encode? [1-" + books.length + "]");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

//            Users can select the book by number instead of name as it's faster (have the choice - 1 to pick the correct book)
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

//     Helper method: returns a String[] array of book names
//    Needed a lot of research to write this method using a Stream
//    This method iterates over the File array and filters for the file having .txt at the end,
//    then removes these characters when outputting the filename (so no .txt at the end),
//    and then puts these raw filenames into the String array it returns

    public static String[] getBooksInFolder(File[] listOfFiles) {
        return java.util.Arrays.stream(listOfFiles)
                .filter(file -> file.isFile() && file.getName().endsWith(".txt"))
                .map(file -> file.getName().substring(0, file.getName().length() - 4))
                .toArray(String[]::new);
    }
}
