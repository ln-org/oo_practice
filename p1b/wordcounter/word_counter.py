import sys
import re
import os

class WordCountPrinter:
    def __init__(self, words, counts):
        self.words = list(words)
        self.counts = list(counts)
        self.word_col_width = max(len("TOTAL"), *(len(w) for w in self.words))
        self.count_col_width = max(len("COUNT"), *(len(str(c)) for c in self.counts))

    def print(self):
        if len(self.words) <= 1:
            print(f"The word '{self.words[0]}' appears {self.counts[0]}", end="")
            if self.counts[0] == 1:
                print(" time.")
            else:
                print(" times.")
        else:
            self.print_table()

    def print_table(self):
        self.print_border()
        self.print_row("WORD", "COUNT")
        self.print_border()
        for word, count in zip(self.words, self.counts):
            self.print_row(word, count)
        self.print_border()
        self.print_row("TOTAL", self.get_total_count())
        self.print_border()

    def get_total_count(self):
        return sum(self.counts)

    def print_border(self):
        print("|-" + "-" * self.word_col_width + "-|-" + "-" * self.count_col_width + "-|")

    def print_row(self, word, count):
        word_str = str(word)
        count_str = str(count)
        print(f"| {word_str}{' ' * (self.word_col_width - len(word_str))} | "
              f"{' ' * (self.count_col_width - len(count_str))}{count_str} |")


def count_words(file_path, words):
    counts = [0] * len(words)
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            text = f.read()
            tokens = re.findall(r'[A-Za-z0-9_]+', text)
            for i, word in enumerate(words):
                counts[i] = tokens.count(word)
        return counts
    except FileNotFoundError:
        print(f"File not found: {file_path}")
        sys.exit(1)

def main():
    if len(sys.argv) < 3:
        print("Usage: python word_counter.py <filename> <searchTerm>")
        sys.exit(1)

    file_path = sys.argv[1]
    words = sys.argv[2:]

    for word in words:
        if not re.match(r'^[A-Za-z0-9_]+$', word):
            print("Invalid input: searching word should consist of letters, numbers or '_'.")
            sys.exit(1)

    counts = count_words(file_path, words)
    WordCountPrinter(words, counts).print()

if __name__ == "__main__":
    main()
