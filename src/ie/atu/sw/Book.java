package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//This is the class for a book that is chosen to be encoded

public class Book {

    // name of the book
    private String name;

    // array to hold the words in the book
    private String[] text;



    // constructor from file only, called from the BooksList class bookList method
    // No args and n0-args constructors used here as the only way a book object will be created will be using the method in the other class

    public Book(File file) throws IOException {
        this.name = file.getName();

        // Read the file into a single string
        String content = new String(Files.readAllBytes(file.toPath()));

        // Split into words (basic split on whitespace)
        this.text = content.split("\\s+");

        System.out.println("Book created from file: " + name);
    }

    // Getters
    public String getName() {
        return name;
    }

    public String[] getText() {
        return text;
    }
}
