package ie.atu.sw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Books {

//    Get the names of the books to encode - can link the book numbers later
//    check use of arrays/lists etc. for final assignment

    public static void bookList() {

//        Instructions on assignment:
        /* Provide a simple command-line user interface that enables a user to specify the
                following:
        1. A path and name for the text file to encode or decode.  */

//        use scanner object to read in the path first
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the directory where the books are stored: ");
        String folderPath = scanner.nextLine();

        File folder = new File(folderPath);

        // Check if it's a valid directory
        if (folder.exists() && folder.isDirectory()) {
            System.out.println("Files in " + folderPath + ":");

            List<String> booksInList = getBooksInFolder();

            String[] books = booksInList.toArray(new String[0]);

            for (String book : books) {
                System.out.println(book);
            }
            System.out.println("Which book do you want to encode? [1-?]");
            // to add code here to select book
        } else {
            System.out.println("The path you entered is not a valid folder.");
        }

    }

    public static List<String> getBooksInFolder() {
        List<String> booksInList = new ArrayList<>();
        File[] listOfFiles = new File("src/textFiles").listFiles();

        // initiate a book counter so the books can be numbered
        int counter = 1;
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {

                    String fileName = file.getName();
                    // Remove the .txt extension if present
                    if (fileName.endsWith(".txt")) {
                        fileName = fileName.substring(0, fileName.length() - 4);
                    }

                    booksInList.add(counter + " " + fileName);
                    counter++;
                }
            }
        }
        return booksInList;
    }
}
