package ie.atu.sw;

import java.io.*;

public class Encoder {
    private Encodings encodings;

    public Encoder(Encodings encodings) {
        this.encodings = encodings;
    }

    // Old method still available if you want to encode directly from a file path
    public void encodeFile(String inputPath, String outputPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));

        String line;
        while ((line = br.readLine()) != null) {
            for (String w : line.split("\\s+")) {
                if (w.isEmpty()) continue;
                encodeWord(w, bw);
            }
            bw.newLine();
        }
        br.close();
        bw.close();
    }

    // New method: encode directly from a Book object
    public void encodeBook(Book book, String outputPath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));

        for (String w : book.getText()) {
            if (w.isEmpty()) continue;
            encodeWord(w, bw);
        }
        bw.newLine();
        bw.close();
    }

    private void encodeWord(String word, BufferedWriter bw) throws IOException {
        String[] tokens = encodings.getTokens();
        int[] codes = encodings.getCodes();

        String remaining = word;
        boolean first = true;

        while (!remaining.isEmpty()) {
            int bestIdx = -1;
            String bestToken = null;

            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i] == null) continue;

                if (first) {
                    if (remaining.startsWith(tokens[i])) {
                        if (bestToken == null || tokens[i].length() > bestToken.length()) {
                            bestToken = tokens[i];
                            bestIdx = i;
                        }
                    }
                } else {
                    if (tokens[i].startsWith("@@")) {
                        String suffix = tokens[i].substring(2);
                        if (remaining.startsWith(suffix)) {
                            if (bestToken == null || suffix.length() > bestToken.length()) {
                                bestToken = tokens[i];
                                bestIdx = i;
                            }
                        }
                    }
                }
            }

            if (bestIdx == -1) {
                bw.write("-1 ");
                return;
            }

            bw.write(codes[bestIdx] + " ");

            if (bestToken.startsWith("@@")) {
                remaining = remaining.substring(bestToken.length() - 2);
            } else {
                remaining = remaining.substring(bestToken.length());
            }
            first = false;
        }
    }
}
