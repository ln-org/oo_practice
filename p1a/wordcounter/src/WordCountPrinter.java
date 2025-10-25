import java.util.Arrays;

/**
 * WordCountPrinter负责以格式化表格或简单摘要的形式
 * 打印单词及其计数。
 *
 * 它打印每个单词及其对应的计数，以及总计数。
 * 列的宽度根据最长单词的长度和最大计数动态调整。
 */
public class WordCountPrinter {
    private String[] words;
    private int[] counts;
    private int wordColWidth = "TOTAL".length();
    private int countColWidth = "COUNT".length();

    /**
     * 使用给定的单词及其对应的计数构造一个WordCountPrinter对象。
     *
     * @param words  要显示的单词数组。
     * @param counts 与每个单词对应的计数数组。
     */
    public WordCountPrinter(String[] words, int[] counts) {
        this.words = Arrays.copyOf(words, words.length);
        this.counts = Arrays.copyOf(counts, counts.length);

        // 根据单词和计数计算最大列宽
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
     * 打印单词计数结果。如果只统计一个单词，则打印简单摘要。
     * 如果统计多个单词，则以格式化表格的形式打印结果。
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
     * 打印一个表格，显示每个单词及其对应的计数，
     * 最后显示所有单词的总计数。
     */
    private void printTable() {
        // 打印表头
        printBorder();
        printRow("WORD", "COUNT");

        // 打印内容
        printBorder();
        for (int i = 0; i < words.length; i++) {
            printRow(words[i], counts[i]);
        }

        // 打印表尾
        printBorder();
        printRow("TOTAL", getTotalCount());
        printBorder();
    }

    // 计算所有搜索单词的总计数
    private int getTotalCount() {
        int totalCount = 0;
        for (int i : counts) {
            totalCount = totalCount + i;
        }
        return totalCount;
    }

    /**
     * 打印表格的边框线。
     * 根据列宽绘制由竖线和横线组成的边框。
     */
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

    /**
     * 打印表格中的一行，包含单词和计数。
     * 
     * @param word  要显示的单词
     * @param count 要显示的计数（整数类型）
     */
    private void printRow(String word, int count) {
        printRow(word, Integer.toString(count));
    }

    /**
     * 打印表格中的一行，包含单词和计数字符串。
     * 自动调整列宽以对齐显示。
     * 
     * @param word  要显示的单词
     * @param count 要显示的计数（字符串类型）
     */
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
