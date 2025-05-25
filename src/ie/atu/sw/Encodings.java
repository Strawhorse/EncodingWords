package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Encodings {

    static void outputEncodings() throws FileNotFoundException {

        File myObj = new File("src/encodings-10000.csv");
        Scanner myReader = new Scanner(myObj);

//        create arraylists with more functionality than normal arrays; can convert back later
        List<String> endings = new ArrayList<>();
        List<String> encodings = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] values = data.split(",");

//            separate the endings and the encodings into different arraylists
            for (int i = 0; i < values.length; i++) {
                if (i % 2 == 0) {
                    endings.add(values[i]);
                } else {
                    encodings.add(values[i]);
                }
            }
        }

        myReader.close();  // Close the scanner

        // Convert lists to arrays
        String[] endingsArray = endings.toArray(new String[0]);
        String[] encodingsArray = encodings.toArray(new String[0]);

        // Print the arrays to test
        System.out.println("Printing the word endings ...");
        System.out.println(Arrays.toString(endingsArray));
        System.out.println("Printing the word encodings ...");
        System.out.println(Arrays.toString(encodingsArray));
    }
}
