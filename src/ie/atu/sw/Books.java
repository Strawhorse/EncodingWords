package ie.atu.sw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Books {

//    Get the names of the books to encode - can link the book numbers later

    public static void bookList() {
        List<String> booksInList = new ArrayList<>();
        File[] listOfFiles = new File("src/textFiles").listFiles();

//        used instead of a while loop to avoid nesting loops all over the place
        assert listOfFiles != null;

        int counter = 1;

        for(File file : listOfFiles) {
            if(file.isFile()) {

                String fileName = file.getName();
                // Remove the .txt extension if present
                if (fileName.endsWith(".txt")) {
                    fileName = fileName.substring(0, fileName.length() - 4);
                }

                booksInList.add(counter + " " + fileName);
                counter++;
            }
        }
        String[] books = booksInList.toArray(new String[0]);

        for(String book : books) {
            System.out.println(book);
        }

    }

}
