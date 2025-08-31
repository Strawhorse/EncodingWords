package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


//This class is for a book object, which is created when you select one of the books from your list
//This class is created by the BookList class which is the entry point into choosing a book

public class Book {

//    Only three class variables; the book name, a String array for the text, and the filename
    private String name;
    private String[] text;
    private File file;

    public Book(File file) throws IOException {
        this.file = file;
        this.name = file.getName();

        // Read the text file into a single string
        String content = new String(Files.readAllBytes(file.toPath()));

        // Split into words (basic split on whitespace using Regex again) and put in text array
        this.text = content.split("\\s+");

        System.out.println("New book created from file: " + name);
    }

    // Getters; no setters as we are not manually setting anything
    public String getName() {
        return name;
    }

    public String[] getText() {
        return text;
    }

    // NEW: return the original file path
    public String getPath() {
        return file.getAbsolutePath();
    }
}
