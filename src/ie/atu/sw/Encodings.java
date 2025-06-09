package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Encodings {

    static EncodingPair outputEncodings() throws FileNotFoundException {
        File file = new File("src/encodings-10000.csv");

        // First pass: Count number of pairs
        Scanner counter = new Scanner(file);
        int entryCount = 0;

        while (counter.hasNextLine()) {
            String line = counter.nextLine();
            String[] values = line.split(",");
            entryCount += values.length / 2;  // divide by two because each line will have two values
        }
        counter.close();

        // Allocate arrays
        String[] Word_endings = new String[entryCount];
        int[] Word_encodings = new int[entryCount];

        // Second pass: Fill arrays
        Scanner valuesScanner = new Scanner(file);
        int index = 0;

        while (valuesScanner.hasNextLine()) {
            String line = valuesScanner.nextLine();
            String[] values = line.split(",");

            // Only proceed if we have complete pairs
            for (int i = 0; i + 1 < values.length; i += 2) {
                Word_endings[index] = values[i];
                Word_encodings[index] = Integer.parseInt(values[i + 1]);
                index++;
            }
        }

        // Now close the scanner
        valuesScanner.close();

        // Print results
        System.out.println("Encodings extracted successfully ...");

        return new EncodingPair(Word_endings, Word_encodings);
    }



    
    // create a method to encode books using the encodings from the above method

    static void encodeBooks() throws FileNotFoundException {

        // initiate the two arrays
        EncodingPair encodingPair = outputEncodings(); // instantiate the variables by calling them from the other method

        // Now use the two arrays as before
        String[] Word_endings = encodingPair.Word_endings;
        int[] Word_encodings = encodingPair.Word_encodings;

        // test if it works - works, tested
        System.out.println("Word endings:");
        for (String ending : Word_endings) {
            System.out.println(ending);
        }

        // test if it works - works, tested
        System.out.println("Word encodings:");
        for (int encoding : Word_encodings) {
            System.out.println(encoding);
        }


    }
}
