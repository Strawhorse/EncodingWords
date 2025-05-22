package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Encodings {

//    test out reading in the encodings to two separate arrays
//    One to hold the text words/suffixes and the other to hold the numeric values
//    need to find size of the arrays first before instantiating them

//    Could create a helper method which runs a count on the csv file first and then sends the values to this method

    // pass method2 as argument to method1
//    public void method1(method2());

//    OR

//    obj.square(obj.add(15, 9));


    static void outputEncodings() throws FileNotFoundException {



        File myObj = new File("src/encodings-10000.csv");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();
    }
}
