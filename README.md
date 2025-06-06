# EncodingWords
Word encoder and decoder written in Java

To do:
- Move the progress meter to a new class with a new method to keep the main method in Runner clear - DONE
- Experiment with reading in one book as an array and then outputting it to the terminal to show it works - DONE
- 
- May need to add some search functionality to search around the project folder for a text file - DONE
- Alternatively, could give a list of all the books in a book folder (not to be included with final project) - DONE
- Change the encodings method to return the word endings and encodings as two separate arrays, or alternatively, create two separate methods to return each of these
[https://www.baeldung.com/java-method-return-multiple-values]


After review, a good plan would be:
1. Have a 'Book' object that is created after a book is chosen from the list (can check to see if a book object already exists)
2. This book can then be in an encoded or decoded state. The books can be outputted like this to a file
3. With the book object created (and populated by the array of words), you can then run the encoding and decoding methods on it
4. Might move the OutputBook class method to the Book class
5. 
