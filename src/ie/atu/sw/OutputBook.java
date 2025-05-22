package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OutputBook {

    static void outputBook() throws FileNotFoundException {
        File myObj = new File("src/textFiles/1984Orwell.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();
}}
