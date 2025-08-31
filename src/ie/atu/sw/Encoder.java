package ie.atu.sw;

import java.io.*;

public class Encoder {
    String[] tokens;
    int[] codes;
    String mappingFilePath;

    public Encoder(int size) {
        tokens = new String[size];
        codes = new int[size];
    }

    /**
     * Load word/suffix mappings from the CSV file.
     */
    public void loadEncodings(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int i = 0;
        while ((line = br.readLine()) != null && i < tokens.length) {
            String[] parts = line.split(",");
            tokens[i] = parts[0];
            codes[i] = Integer.parseInt(parts[1]);
            i++;
        }
        br.close();
        mappingFilePath = path;
        System.out.println("Encoding table loaded successfully (" + i + " entries).");
    }

    /**
     * Encode a text file into numeric codes and write to output file.
     * Unknown words are mapped to 0.
     */
    public void encodeFile(String inputPath, String outputPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));

        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split("\\s+"); // split on whitespace
            for (String word : words) {
                if (word.isEmpty()) continue;

                // Encode the word into one or more codes
                String encoded = encodeWord(word);
                bw.write(encoded + " ");
            }
            bw.newLine();
        }

        br.close();
        bw.close();
    }

    /**
     * Encode a single word into numeric codes (handles whole words or suffix splits).
     */
    private String encodeWord(String word) {
        int code = findCodeForToken(word);
        if (code != -1) {
            return String.valueOf(code);
        }

        // Try splitting into base + suffix
        for (int i = word.length(); i > 0; i--) {
            String base = word.substring(0, i);
            String suffix = "@@" + word.substring(i);

            int baseCode = findCodeForToken(base);
            int suffixCode = findCodeForToken(suffix);

            if (baseCode != -1 && suffixCode != -1) {
                return baseCode + " " + suffixCode;
            }
        }

        // Unknown word
        return "0";
    }

    /**
     * Lookup token → code.
     */
    public int findCodeForToken(String token) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] != null && tokens[i].equals(token)) {
                return codes[i];
            }
        }
        return -1;
    }

    /**
     * Lookup code → token (needed by Decoder).
     */
    public String findTokenForCode(int code) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] == code) {
                return tokens[i];
            }
        }
        return "???"; // unknown code
    }

    public String[] getTokens() {
        return tokens;
    }

    public int[] getCodes() {
        return codes;
    }

    public String getMappingFilePath() {
        return mappingFilePath;
    }
}
