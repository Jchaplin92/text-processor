package analysis;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class StringChomper {

    private final String[] words;

    public StringChomper(MultipartFile inputFile) throws IOException {
        String content = new String(inputFile.getBytes());
        this.words = content.replaceAll("\\s+", " ").split(" ");
    }

    public String totalWordAnalysis() {
        return "";
    }

    public String getWordCountFromFile() throws IOException {
        return String.format("Word count = %d", words.length);
    }

    public String getAverageWordLength() throws IOException {
        double averageWordLength = Arrays.stream(words).mapToDouble(String::length).average().getAsDouble();

        return String.format("Average word length = %.3f", averageWordLength);
    }

    public String[] numberOfWordsPerWordLength() {
        // creating an array for every word - O(N)

        // create hashMap for each word length, and a counter for each one
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (String word : words) {
            hashMap.computeIfPresent(word.length(), (key, val) -> val + 1); //todo fix this
        }

        return hashMap.entrySet().stream()
                .map(entry -> String.format("Number of wordws of length %d is %d", entry.getKey(), entry.getValue()))
                .toArray(String[]::new);
    }
}
