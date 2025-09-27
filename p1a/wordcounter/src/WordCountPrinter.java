import java.util.Arrays;

/**
 * WordCountPrinter is responsible for printing words and their counts
 * in a formatted table or a simple summary.
 *
 * It prints each word and its corresponding count, along with the total
 * count. The width of the columns is dynamically adjusted based on the
 * length of the longest word and the largest count.
 */
public class WordCountPrinter {
    private String[] words;
    private int[] counts;
    private int wordColWidth = "TOTAL".length();
    private int countColWidth = "COUNT".length();

    /**
     * Constructs a WordCountPrinter object with the given words and
     * their corresponding counts.
     *
     * @param words  The array of words to display.
     * @param counts The array of counts corresponding to each word.
     */
    public WordCountPrinter(String[] words, int[] counts) {
        this.words = Arrays.copyOf(words, words.length);
        this.counts = Arrays.copyOf(counts, counts.length);

        // Calculate the maximum column widths based on the words and counts
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > wordColWidth) {
                wordColWidth = words[i].length();
            }

            if (Integer.toString(counts[i]).length() > countColWidth) {
                countColWidth = Integer.toString(counts[i]).length();
            }
        }
    }

    /**
     * Prints the word count results. If only one word is counted, it prints
     * a simple summary. If multiple words are counted, it prints the results
     * in a formatted table.
     */
    public void print() {
        if (words.length <= 1) {
            System.out.print("The word '" + words[0] + "' appears " + counts[0]);
            if (counts[0] == 1) {
                System.out.println(" time.");
            } else {
                System.out.println(" times.");
            }
        } else {
            WordCountPrinter table = new WordCountPrinter(words, counts);
            table.printTable();
        }
    }

    /**
     * Prints a table displaying each word and its corresponding count,
     * followed by a total count of all words.
     */
    private void printTable() {
        // print header
        printBorder();
        printRow("WORD", "COUNT");

        // print content
        printBorder();
        for (int i = 0; i < words.length; i++) {
            printRow(words[i], counts[i]);
        }

        // print footer
        printBorder();
        printRow("TOTAL", getTotalCount());
        printBorder();
    }

    // Calculate the total count of all searching words.
    private int getTotalCount() {
        int totalCount = 0;
        for (int i : counts) {
            totalCount = totalCount + i;
        }
        return totalCount;
    }

    private void printBorder() {
        System.out.print("|-");
        for (int i = 0; i < wordColWidth; i++) {
            System.out.print("-");
        }
        System.out.print("-|-");
        for (int i = 0; i < countColWidth; i++) {
            System.out.print("-");
        }
        System.out.println("-|");
    }

    private void printRow(String word, int count) {
        printRow(word, Integer.toString(count));
    }

    private void printRow(String word, String count) {
        System.out.print("| ");
        System.out.print(word);
        for (int i = 0; i < wordColWidth - word.length(); i++) {
            System.out.print(" ");
        }

        System.out.print(" | ");

        for (int i = 0; i < countColWidth - count.length(); i++) {
            System.out.print(" ");
        }
        System.out.print(count);
        System.out.println(" |");
    }
}
