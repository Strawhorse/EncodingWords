package ie.atu.sw;


// class used to transport the two encoding arrays
public class EncodingPair {

    public String[] Word_endings;
    public int[] Word_encodings;

    public EncodingPair(String[] word_endings, int[] word_encodings) {
        Word_endings = word_endings;
        Word_encodings = word_encodings;
    }
}
