package ie.atu.sw;


// class used to transport the two encoding arrays; makes method calls a bit easier
// just created this class from scratch because we can't use ArrayLists to carry arrays with different values
public class EncodingPair {

    public String[] Word_endings;
    public int[] Word_encodings;

    public EncodingPair(String[] word_endings, int[] word_encodings) {
        Word_endings = word_endings;
        Word_encodings = word_encodings;
    }
}
