import sys
import re
import os

class WordCountPrinter:
    """
    WordCountPrinter负责以格式化表格或简单摘要的形式
    打印单词及其计数。
    
    它打印每个单词及其对应的计数，以及总计数。
    列的宽度根据最长单词的长度和最大计数动态调整。
    """
    def __init__(self, words, counts):
        """
        使用给定的单词及其对应的计数构造一个WordCountPrinter对象。
        
        Args:
            words: 要显示的单词列表
            counts: 与每个单词对应的计数列表
        """
        self.words = list(words)
        self.counts = list(counts)
        # 根据单词和计数计算最大列宽
        self.word_col_width = max(len("TOTAL"), *(len(w) for w in self.words))
        self.count_col_width = max(len("COUNT"), *(len(str(c)) for c in self.counts))

    def print(self):
        """
        打印单词计数结果。如果只统计一个单词，则打印简单摘要。
        如果统计多个单词，则以格式化表格的形式打印结果。
        """
        if len(self.words) <= 1:
            print(f"The word '{self.words[0]}' appears {self.counts[0]}", end="")
            if self.counts[0] == 1:
                print(" time.")
            else:
                print(" times.")
        else:
            self.print_table()

    def print_table(self):
        """
        打印一个表格，显示每个单词及其对应的计数，
        最后显示所有单词的总计数。
        """
        # 打印表头
        self.print_border()
        self.print_row("WORD", "COUNT")
        
        # 打印内容
        self.print_border()
        for word, count in zip(self.words, self.counts):
            self.print_row(word, count)
        
        # 打印表尾
        self.print_border()
        self.print_row("TOTAL", self.get_total_count())
        self.print_border()

    def get_total_count(self):
        """
        计算所有搜索单词的总计数。
        
        Returns:
            所有单词计数的总和
        """
        return sum(self.counts)

    def print_border(self):
        """
        打印表格的边框线。
        根据列宽绘制由竖线和横线组成的边框。
        """
        print("|-" + "-" * self.word_col_width + "-|-" + "-" * self.count_col_width + "-|")

    def print_row(self, word, count):
        """
        打印表格中的一行，包含单词和计数。
        自动调整列宽以对齐显示。
        
        Args:
            word: 要显示的单词
            count: 要显示的计数
        """
        word_str = str(word)
        count_str = str(count)
        print(f"| {word_str}{' ' * (self.word_col_width - len(word_str))} | "
              f"{' ' * (self.count_col_width - len(count_str))}{count_str} |")


def count_words(file_path, words):
    """
    统计指定单词在给定文件中的出现次数。
    
    此函数读取一个文件，然后统计每个指定单词在文件中出现的次数。
    单词被定义为由A-Z、a-z、0-9范围内的字符和下划线（_）字符
    组成的连续字符序列。
    
    Args:
        file_path: 要搜索的文件路径
        words: 要在文件中搜索的单词列表
        
    Returns:
        一个列表，包含每个对应单词的出现次数
        
    Raises:
        SystemExit: 如果无法找到指定的文件
    """
    counts = [0] * len(words)
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            text = f.read()
            # 使用正则表达式提取所有有效的单词
            tokens = re.findall(r'[A-Za-z0-9_]+', text)
            for i, word in enumerate(words):
                counts[i] = tokens.count(word)
        return counts
    except FileNotFoundError:
        print(f"File not found: {file_path}")
        sys.exit(1)

def main():
    """
    程序的主要入口点。
    
    此函数处理命令行参数，验证输入，并协调单词计数和结果打印。
    程序将打印找到的每个单词的计数，或在无法找到文件时打印错误信息。
    """
    # 检查是否缺少参数并显示使用说明
    if len(sys.argv) < 3:
        print("Usage: python word_counter.py <filename> <searchTerm>")
        sys.exit(1)

    file_path = sys.argv[1]
    words = sys.argv[2:]

    # 检查搜索的单词是否有效
    for word in words:
        if not re.match(r'^[A-Za-z0-9_]+$', word):
            print("Invalid input: searching word should consist of letters, numbers or '_'.")
            sys.exit(1)

    counts = count_words(file_path, words)
    WordCountPrinter(words, counts).print()

if __name__ == "__main__":
    # 当脚本被直接执行时运行主函数
    main()
