package analysis;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;

public class StringChomper {

    private HashMap<Integer, Integer> wordLengthsAndFrequencies;
    private final String[] words;

    public StringChomper(MultipartFile inputFile) throws IOException {
        String content = new String(inputFile.getBytes());
        this.words = content
                .replaceAll("\\s+", " ")
                .replaceAll("[,.;:]", "")
                .split(" ");
    }

    public String totalWordAnalysis() throws IOException {
        String analysis = getWordCountFromFile() +
                getAverageWordLength();
        for (String wordsPerWordLength : numberOfWordsPerWordLength()) {
            analysis += wordsPerWordLength;
        }
        analysis += mostFrequentWordLengthsGiven();
        return analysis;
    }

    public String getWordCountFromFile() throws IOException {
        return String.format("Word count = %d\n", words.length);
    }

    public String getAverageWordLength() throws IOException {
        double averageWordLength = Arrays.stream(words).mapToDouble(String::length).average().getAsDouble();

        return String.format("Average word length = %.3f\n", averageWordLength);
    }

    public String[] numberOfWordsPerWordLength() {
        createAndStoreWordLengthToFrequencyMapIfNotPresent();

        return wordLengthsAndFrequencies.entrySet().stream()
                .map(entry -> String.format("Number of words of length %d is %d\n", entry.getKey(), entry.getValue()))
                .toArray(String[]::new);
    }

    public String mostFrequentWordLengthsGiven() {
        createAndStoreWordLengthToFrequencyMapIfNotPresent();
        Integer maxlengthFrequency = wordLengthsAndFrequencies.entrySet().stream().max(Comparator.comparing(Entry::getValue)).get().getValue();
        Integer[] mostFrequentWordLengths = mostFrequentWordLengthsGiven(maxlengthFrequency);
        String mostFrequentStatement = makeStatementOnMostFrequentWordLength(maxlengthFrequency, mostFrequentWordLengths);
        return mostFrequentStatement;
    }

    private Integer[] mostFrequentWordLengthsGiven(Integer maxlengthFrequency) {
        return wordLengthsAndFrequencies.entrySet()
                .stream().filter(entry -> entry.getValue().equals(maxlengthFrequency))
                .map(entry -> entry.getKey())
                .toArray(Integer[]::new);
    }

    private String makeStatementOnMostFrequentWordLength(Integer maxlengthFrequency, Integer[] mostFrequentWordLengths) {
        if (mostFrequentWordLengths.length == 1)
            return String.format("The most frequently occurring word length is %d, for a word length of %d\n", maxlengthFrequency, mostFrequentWordLengths[0]);

        String mostFrequentStatement = String.format("The most frequently occurring word length is %d, for word lengths of ", maxlengthFrequency);
        for (int i = 0; i < mostFrequentWordLengths.length - 2; i++) {
            mostFrequentStatement += String.format("%d, ", mostFrequentWordLengths[i]);
        }
        mostFrequentStatement += String.format("%d & %d\n", mostFrequentWordLengths[mostFrequentWordLengths.length - 2], mostFrequentWordLengths[mostFrequentWordLengths.length - 1]);
        return mostFrequentStatement;
    }

    private void createAndStoreWordLengthToFrequencyMapIfNotPresent() {
        if (wordLengthsAndFrequencies == null) {
            HashMap<Integer, Integer> hashMap = new HashMap<>();

            for (String word : words) {
                if (hashMap.get(word.length()) == null) {
                    hashMap.put(word.length(), 1);
                } else {
                    hashMap.compute(word.length(), (len, freq) -> freq + 1);
                }
            }

            wordLengthsAndFrequencies = hashMap;
        }
    }
}
