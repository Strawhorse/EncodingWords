package ie.atu.sw;

import java.io.*;

public class Encodings {
    private String[] tokens;
    private int[] codes;
    private String mappingFilePath; // optional: remember which file was loaded

    public Encodings(int size) {
        tokens = new String[size];
        codes = new int[size];
    }

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

    public String[] getTokens() {
        return tokens;
    }

    public int[] getCodes() {
        return codes;
    }

    public String getMappingFilePath() {
        return mappingFilePath;
    }

    public String findTokenForCode(int code) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] == code) {
                return tokens[i];
            }
        }
        return "??"; // unknown code
    }

    public int findCodeForToken(String token) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] != null && tokens[i].equals(token)) {
                return codes[i];
            }
        }
        return -1;
    }
}
