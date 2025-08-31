package ie.atu.sw;

import java.io.*;

// This is the class and methods that will read in the encodings csv file and parse it into two separate arrays, tokens and codes
public class Encoder {

    // Three class variables created; firstly the tokens and codes for encoding (and later decoding) the text
    // The mappingFilePath variable is for system outs showing what files were created
    String[] tokens;
    int[] codes;
    String mappingFilePath;

    // Variables to record whether the encodings file has been loaded first
    private boolean loaded = false;
    private int loadedEntries = 0;

    // Args constructor (default size is 10000 taken from the Runner class)
    public Encoder(int size) {
        tokens = new String[size];
        codes = new int[size];
    }

    // Load word/suffix mappings from the CSV file
    // This method populates the Class variables, tokens and codes, with the contents of the encoding csv file
    // Void method because it's just populating the variables, not outputting anything
    public void loadEncodings(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null && i < tokens.length) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue; // skip malformed lines
                tokens[i] = parts[0];
                codes[i] = Integer.parseInt(parts[1]);
                i++;
            }
            mappingFilePath = path;
            loadedEntries = i;
            loaded = (i > 0);
            System.out.println("Encoding table loaded successfully (" + i + " entries).");
        }
    }

    // Encode a text file into numeric codes and write to output file
    // Unknown words are mapped to 0 by default
    public void encodeFile(String inputPath, String outputPath) throws IOException {

        // Check to make sure to avoid accidental encoding without a mapping loaded
//        Encoding without the mappings will result in the text file being turned into zeroes
        if (!loaded) {
            throw new IllegalStateException("Mapping not loaded. Please load encodings (menu option 2) before encoding.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputPath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            String line;
            while ((line = br.readLine()) != null) {

                // As with the Decoder class, split on whitespace using Regex \\s+
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }

                    // Encode the word into one or more codes using the below method, encodeWord, then write and put a space after
                    String encoded = encodeWord(word);
                    bw.write(encoded + " ");
                }
                bw.newLine();
            }
        }
    }

    // Encode a single word into numeric codes
    // Attempts to deal with whole words or words with suffixes
    // If this method doesn't find the word, then it moves to the next loop
    private String encodeWord(String word) {
        int code = findCodeForToken(word);
        if (code != -1) {
            return String.valueOf(code);
        }

        // Try splitting the word into base + suffix
        for (int i = word.length(); i > 0; i--) {
            String base = word.substring(0, i);
            String suffix = "@@" + word.substring(i);

            int baseCode = findCodeForToken(base);
            int suffixCode = findCodeForToken(suffix);

            // Would return -1 if not found
            if (baseCode != -1 && suffixCode != -1) {
                return baseCode + " " + suffixCode;
            }
        }

        // Unknown words are returned as a zero
        return "0";
    }

    // Method to look up token to find the code
    // Used by this Encoder class
    public int findCodeForToken(String token) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] != null && tokens[i].equals(token)) {
                return codes[i];
            }
        }
        return -1;
    }

    // Lookup code to find the associated token
    // Needed by Decoder class, which inherits this Encoder when it is constructed
    public String findTokenForCode(int code) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] == code) {
                return tokens[i];
            }
        }
        // unknown code
        return "???";
    }

    // Getter helper methods
    public String[] getTokens() {
        return tokens;
    }

    public int[] getCodes() {
        return codes;
    }

    public String getMappingFilePath() {
        return mappingFilePath;
    }


    // Methods to expose the loaded status of the encodings file Runner
    public boolean isLoaded() {
        return loaded;
    }

    public int getLoadedEntries() {
        return loadedEntries;
    }
}
