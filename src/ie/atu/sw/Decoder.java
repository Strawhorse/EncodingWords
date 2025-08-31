package ie.atu.sw;

import java.io.*;

public class Decoder {
    private Encodings encodings;

    public Decoder(Encodings encodings) {
        this.encodings = encodings;
    }

    public void decodeFile(String inputPath, String outputPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));

        String line;
        String currentWord = null;

        while ((line = br.readLine()) != null) {
            for (String num : line.split("\\s+")) {
                if (num.isEmpty()) continue;

                String token = encodings.findTokenForCode(Integer.parseInt(num));

                if (token.startsWith("@@")) {
                    if (currentWord != null) {
                        currentWord += token.substring(2);
                    } else {
                        currentWord = token.substring(2);
                    }
                } else {
                    if (currentWord != null) {
                        bw.write(currentWord + " ");
                    }
                    currentWord = token;
                }
            }

            if (currentWord != null) {
                bw.write(currentWord + " ");
                currentWord = null;
            }
            bw.newLine();
        }

        br.close();
        bw.close();
    }
}
