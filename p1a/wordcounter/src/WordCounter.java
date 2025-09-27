import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This WordCounter program implements an application that searches for
 * one or more specified words in a given text file and counts how many
 * times each word appears.
 * 
 * The results are displayed either as a summary for a single word or
 * in a formatted table for multiple words.
 */
public class WordCounter {
    /**
     * Main entry point for the program.
     * 
     * This method will print the count of each word found or an error
     * message if the file cannot be found.
     * 
     * @param args The command-line arguments ( required at least two).
     *             The first should be file name, followed by one or more
     *             words to search for in the file.
     */
    public static void main(String[] args) {

        // Check if arguments are missing and display usage instructions
        if (args.length < 2) {
            System.out.println("Usage: java WordCounter <filename> <searchTerm>");
            return;
        }

        // Check if searching worlds are valid
        for (int i = 1; i < args.length; i++) {
            if (!args[i].matches("[A-Za-z0-9_]+")) {
                System.out.println("Invalid input: "
                                    + "searching word should consist of "
                                    + "letters, numbers or '_'.");
                return;
            }
        }

        File file = new File(args[0]);
        String[] words = Arrays.copyOfRange(args, 1, args.length);

        try {
            new WordCountPrinter(words, countWords(file, words)).print();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getPath());
        }
    }

    /**
     * Counts the occurrences of the specified words in the a given file.
     *
     * This method reads a file, and then counts how many times each specified word
     * appears in the file. A word is defined as a contiguous sequence of characters
     * from the ranges A–Z, a–z, 0–9, and the underscore (_) character.
     * The counts for each word are stored in an integer array.
     *
     * @param file  The file to search.
     * @param words An array of words to search for in the file.
     * @return  An array of integers representing the number of occurrences
     *          for each corresponding word in the 'words' array.
     * @throws  FileNotFoundException If the specified file cannot be found.
     */
    public static int[] countWords(File file, String[] words) throws FileNotFoundException {
        try (Scanner sc = new Scanner(file)) {
            int[] counts = new int[words.length];
            sc.useDelimiter("[^A-Za-z0-9_]+");

            while (sc.hasNext()) {
                String next = sc.next();
                for (int i = 0; i < words.length; i++) {
                    if (words[i].equals(next)) {
                        counts[i]++;
                    }
                }
            }
            return counts;
        }
    }
}
