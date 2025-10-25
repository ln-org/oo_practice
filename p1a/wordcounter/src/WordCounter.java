import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 这个WordCounter程序实现了一个应用，用于在给定的文本文件中搜索
 * 一个或多个指定的单词，并统计每个单词出现的次数。
 * 
 * 结果要么以单个单词的摘要形式显示，要么以多个单词的
 * 格式化表格形式显示。
 */
public class WordCounter {
    /**
     * 程序的主要入口点。
     * 
     * 此方法将打印找到的每个单词的计数，或在无法找到文件时
     * 打印错误信息。
     * 
     * @param args 命令行参数（至少需要两个）。
     *             第一个应该是文件名，后面跟着一个或多个
     *             要在文件中搜索的单词。
     */
    public static void main(String[] args) {

        // 检查是否缺少参数并显示使用说明
        if (args.length < 2) {
            System.out.println("Usage: java WordCounter <filename> <searchTerm>");
            return;
        }

        // 检查搜索的单词是否有效
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
     * 统计指定单词在给定文件中的出现次数。
     *
     * 此方法读取一个文件，然后统计每个指定单词在文件中出现的次数。
     * 单词被定义为由A-Z、a-z、0-9范围内的字符和下划线（_）字符
     * 组成的连续字符序列。每个单词的计数存储在一个整数数组中。
     *
     * @param file  要搜索的文件。
     * @param words 要在文件中搜索的单词数组。
     * @return  一个整数数组，表示'words'数组中每个对应单词的
     *          出现次数。
     * @throws  FileNotFoundException 如果无法找到指定的文件。
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
