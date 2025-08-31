package ie.atu.sw;

import java.io.*;

public class Decoder {

//    create an instance of the Encoder class to be able to access its methods (and getters), this is then passed to the constructor (below)
    Encoder encoder;

    public Decoder(Encoder encoder) {
        this.encoder = encoder;
    }

//     Decode a (text) file of numeric codes back into words
//    Uses bufferedReader and Writer objects to read the text file and write the new one

    public void decodeFile(String inputPath, String outputPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));

        String line;
//        StringBuilder objects are malleable unlike String objects so can change size
        StringBuilder currentWord = new StringBuilder();

        while ((line = br.readLine()) != null) {

//            Use regex here to detect whitespace characters, multiple of them (\\s+)
//            Assign to the codes Array
            String[] codes = line.trim().split("\\s+");

//            Now parse the codes String array for the number codes
            for (String num : codes) {
                if (num.isEmpty()) {
                    continue;
                }

//                Now the key logic here; sets a code as an int and then uses this int with the findTokenForCode method from the Encoder object that this Decoder was created with
                int code = Integer.parseInt(num);
                String token = encoder.findTokenForCode(code);

                if (token == null || token.equals("???")) {
                    // Check for an unknown code, remove the current word if needed
                    if (!currentWord.isEmpty()) {
                        bw.write(currentWord.toString() + " ");
                        currentWord.setLength(0);
                    }
                    bw.write("??? ");
                    continue;
                }

//                Next token check for the suffix checker @@, and if it's there then use the substring(2) to remove it and append the base word for currentWord Stringbuilder object
                if (token.startsWith("@@")) {
                    // Append suffix to the current word
                    currentWord.append(token.substring(2));
                } else {
                    // If we already have a word being built, remove
                    if (!currentWord.isEmpty()) {
                        bw.write(currentWord.toString() + " ");
                        currentWord.setLength(0);
                    }
                    // Start new word
                    currentWord.append(token);
                }
            }

            // Remove any unfinished word at end of the current line
            if (!currentWord.isEmpty()) {
                bw.write(currentWord.toString() + " ");
                currentWord.setLength(0);
            }

//            Now create a new line to move onto and restart loop
            bw.newLine();
        }

//        Close the reader and writer objects
        br.close();
        bw.close();
        System.out.println("Decoding completed: " + outputPath);
    }
}
