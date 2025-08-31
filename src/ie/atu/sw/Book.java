package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Book {
    private String name;
    private String[] text;
    private File file; // keep a reference to the source file

    public Book(File file) throws IOException {
        this.file = file;
        this.name = file.getName();

        // Read the file into a single string
        String content = new String(Files.readAllBytes(file.toPath()));

        // Split into words (basic split on whitespace)
        this.text = content.split("\\s+");

        System.out.println("New book created from file: " + name);
    }

    // Getters
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
